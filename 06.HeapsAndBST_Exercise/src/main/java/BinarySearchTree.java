import java.util.ArrayList;
import java.util.function.Consumer;

import java.util.List;

public class BinarySearchTree<E extends Comparable<E>> {
    private static final String OPERATION_ON_EMPTY_STRUCTURE_ERROR_MESSAGE = "Can't do that on an empty tree!";
    private static final String PROVIDED_ELEMENT_NOT_FOUND_MESSAGE = "Provided element not found!";

    private Node<E> root;

    public BinarySearchTree() {
    }

    private BinarySearchTree(E rootElement) {
        root = new Node<>(rootElement);
    }

    public static class Node<E> {
        private E value;
        private Node<E> leftChild;
        private Node<E> rightChild;
        private Node<E> parent;

        public Node(E value) {
            this.value = value;
        }

        public Node<E> getLeft() {
            return this.leftChild;
        }

        public Node<E> getRight() {
            return this.rightChild;
        }

        public Node<E> getParen() {
            return this.parent;
        }

        public E getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    public void eachInOrder(Consumer<E> consumer) {
        eachInOrder(root, consumer);
    }

    public Node<E> getRoot() {
        return root;
    }

    public void insert(E element) {
        if (element == null) {
            return;
        }

        root = insert(root, null, element);
    }

    public boolean contains(E element) {
        return findNode(element) != null;
    }

    public BinarySearchTree<E> search(E element) {
        Node<E> nodeFound = findNode(element);
        if (nodeFound == null) {
            throw new IllegalArgumentException(PROVIDED_ELEMENT_NOT_FOUND_MESSAGE);
        }
        BinarySearchTree<E> result = new BinarySearchTree<>();
        result.copy(nodeFound);
        return result;
    }

    /**
     * @return list of all elements with values between the given ones. Range is inclusive.
     */
    public List<E> range(E first, E second) {
        List<E> elementsInRange = new ArrayList<>();
        eachInOrderRange(root, first, second, elementsInRange);
        return elementsInRange;
    }

    /**
     * Deletes leftmost element.
     * <ul>
     * <li>If root is null, IllegalArgumentException is thrown. <li/>
     * <li>If root is the smallest element (has no left child) - it is removed
     * and the new root is the right child.</li>
     * <li> If root has no children - it becomes null.</li>
     * <ul/>
     */
    public void deleteMin() {
        throwExceptionIfEmptyStructure();

        Node<E> leftmostNode = root;

        while (leftmostNode.leftChild != null) {
            leftmostNode = leftmostNode.leftChild;
        }

        if (leftmostNode == root) {
            if (root.rightChild == null) {
                root = null;
            } else {
                root = root.rightChild;
                root.parent = null;
            }
        } else {
            if (leftmostNode.rightChild != null) {
                leftmostNode.parent.leftChild = leftmostNode.rightChild;
                leftmostNode.rightChild.parent = leftmostNode.parent;
            } else {
                leftmostNode.parent.leftChild = null;
            }
        }
    }

    /**
     * Deletes rightmost element.
     * <ul>
     * <li>If root is null, IllegalArgumentException is thrown. <li/>
     * <li>If root is the biggest element (has no right child) - it is removed
     * and the new root is the left child.</li>
     * <li> If root does not children - it becomes null.</li>
     * <ul/>
     */
    public void deleteMax() {
        throwExceptionIfEmptyStructure();

        Node<E> rightmostNode = root;

        while (rightmostNode.rightChild != null) {
            rightmostNode = rightmostNode.rightChild;
        }

        if (rightmostNode == root) {
            if (root.leftChild == null) {
                root = null;
            } else {
                root = root.leftChild;
                root.parent = null;
            }
        } else {
            if (rightmostNode.leftChild != null) {
                rightmostNode.parent.rightChild = rightmostNode.leftChild;
                rightmostNode.leftChild.parent = rightmostNode.parent;
            } else {
                rightmostNode.parent.rightChild = null;
            }
        }
    }

    public int count() {
        if (root == null) {
            return 0;
        } else {
            return getCountOfNodes(root);
        }
    }

    /**
     * @return number of smaller elements. If provided element is a leaf, returned value is 0.
     */
    public int rank(E element) {
        List<E> smallerElements = new ArrayList<>();
        floorInOrder(root, element, smallerElements);
        return smallerElements.size();
    }

    public E ceil(E element) {
        if (!contains(element)) {
            return null;
        }

        List<E> biggerElements = new ArrayList<>();
        ceilReverseInOrder(root, element, biggerElements);

        if (biggerElements.isEmpty()) {
            return null;
        } else {
            return biggerElements.get(biggerElements.size() - 1);
        }

        /*
        Solution by lecturer (has problem in my tests!):
        if (root == null) {
            return null;
        }

        Node<E> current = root;
        Node<E> nearestBigger = null;

        while (current != null) {
            if (element.compareTo(current.value) < 0) {
                nearestBigger = current;
                current = current.leftChild;
            } else if (element.compareTo(current.value) > 0) {
                current = current.rightChild;
            } else {
                Node<E> right = current.getRight();
                if (right != null && nearestBigger != null) {
                    nearestBigger = right.value.compareTo(nearestBigger.value) < 0 ? right : nearestBigger;
                } else if (nearestBigger == null) {
                    nearestBigger = right;
                }

                break;
            }
        }

        return nearestBigger == null ? null : nearestBigger.value;

         */
    }

    public E floor(E element) {
        if (!contains(element)) {
            return null;
        }

        List<E> smallerElements = new ArrayList<>();
        floorInOrder(root, element, smallerElements);

        if (smallerElements.isEmpty()) {
            return null;
        } else {
            return smallerElements.get(smallerElements.size() - 1);
        }
    }

    private Node<E> insert(Node<E> currentNode, Node<E> parentNode, E valueToInsert) {
        if (currentNode == null) {
            currentNode = new Node<>(valueToInsert);
            currentNode.parent = parentNode;
            return currentNode;
        }

        int comparisonResult = currentNode.value.compareTo(valueToInsert);

        if (comparisonResult > 0) {
            currentNode.leftChild = insert(currentNode.leftChild, currentNode, valueToInsert);
            return currentNode;
        } else if (comparisonResult < 0) {
            currentNode.rightChild = insert(currentNode.rightChild, currentNode, valueToInsert);
            return currentNode;
        } else {
            return currentNode;
        }
    }

    private void eachInOrder(Node<E> currentNode, Consumer<E> consumer) {
        if (currentNode == null) {
            return;
        }

        eachInOrder(currentNode.leftChild, consumer);
        consumer.accept(currentNode.value);
        eachInOrder(currentNode.rightChild, consumer);
    }

    /**
     * @return node with value equal to given element or null if such value is not found.
     */
    private Node<E> findNode(E element) {
        Node<E> currentNode = root;

        while (currentNode != null) {
            int comparisonResult = currentNode.value.compareTo(element);

            if (comparisonResult > 0) {
                currentNode = currentNode.leftChild;
            } else if (comparisonResult < 0) {
                currentNode = currentNode.rightChild;
            } else {
                return currentNode;
            }
        }

        return null;
    }

    private void copy(Node<E> node) {
        if (node == null) {
            return;
        }

        this.insert(node.value);
        this.copy(node.leftChild);
        this.copy(node.rightChild);
    }

    private boolean isBetweenInclusive(E value, E lowerBoundary, E upperBoundary) {
        return value.compareTo(lowerBoundary) >= 0 && value.compareTo(upperBoundary) <= 0;
    }

    private void eachInOrderRange(Node<E> currentNode, E lowerBoundary, E upperBoundary, List<E> elements) {
        if (currentNode == null) {
            return;
        }

        /*
        All elements left from current value are smaller.
        So if current value is smaller than lowerBoundary then all values to the left are also
        smaller than lowerBoundary. And there is no need to check them if they are in range.
         */
        if (currentNode.value.compareTo(lowerBoundary) >= 0) {
            eachInOrderRange(currentNode.leftChild, lowerBoundary, upperBoundary, elements);
        }

        if (isBetweenInclusive(currentNode.value, lowerBoundary, upperBoundary)) {
            elements.add(currentNode.value);
        }

        /*
        All elements right from current value are bigger.
        So if current value is bigger than upperBoundary then all values to the right are also
        bigger than upperBoundary. And there is no need to check them if they are in range.
         */
        if (currentNode.value.compareTo(upperBoundary) <= 0) {
            eachInOrderRange(currentNode.rightChild, lowerBoundary, upperBoundary, elements);
        }
    }

    private void throwExceptionIfEmptyStructure() {
        if (root == null) {
            throw new IllegalArgumentException(OPERATION_ON_EMPTY_STRUCTURE_ERROR_MESSAGE);
        }
    }

    private int getCountOfNodes(Node<E> node) {
        Integer[] counterArray = new Integer[]{0}; // using array because it is a reference type
        eachInOrder(node, new CountConsumer(counterArray));
        return counterArray[0];
    }

    /**
     * Fills the given collection with elements bigger than the provided one. They are sorted
     * in descending order. So the nearest bigger element will be last one in the collection.<br/>
     * Algorithm complexity: O(n)
     */
    private void ceilReverseInOrder(Node<E> currentNode, E element, List<E> biggerValues) {
        if (currentNode == null) {
            return;
        }

        ceilReverseInOrder(currentNode.rightChild, element, biggerValues);

        if (currentNode.value.compareTo(element) > 0) {
            biggerValues.add(currentNode.value);
            ceilReverseInOrder(currentNode.leftChild, element, biggerValues);
        }
    }

    /**
     * Fills the given collection with elements smaller than the provided one. They are sorted
     * in ascending order. So the nearest smaller element will be last one in the collection.<br/>
     * Algorithm complexity: O(n)
     */
    private void floorInOrder(Node<E> currentNode, E element, List<E> smallerElements) {
        if (currentNode == null) {
            return;
        }

        floorInOrder(currentNode.leftChild, element, smallerElements);

        if (currentNode.value.compareTo(element) < 0) { // stop recursive callings when reaching element bigger than provided one
            smallerElements.add(currentNode.value);
            floorInOrder(currentNode.rightChild, element, smallerElements);
        }
    }

    private class CountConsumer implements Consumer<E> {
        Integer[] counterArray;

        public CountConsumer(Integer[] counterArray) {
            this.counterArray = counterArray;
        }

        @Override
        public void accept(E e) {
            counterArray[0]++;
        }
    }
}
