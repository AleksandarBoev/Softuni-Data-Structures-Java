package implementations;

import interfaces.AbstractBinarySearchTree;

public class BinarySearchTree<E extends Comparable<E>> implements AbstractBinarySearchTree<E> {
    private Node<E> root;

    public BinarySearchTree() {
    }

    public BinarySearchTree(Node<E> root) {
        copy(root);
    }

    @Override
    public void insert(E element) {
        if (element == null) {
            return;
        }

        root = insert(root, element);
    }

    @Override
    public boolean contains(E element) {
        return findNode(element) != null;
    }

    @Override
    public AbstractBinarySearchTree<E> search(E element) {
        Node<E> nodeFound = findNode(element);
        return new BinarySearchTree<E>(nodeFound);
    }

    @Override
    public Node<E> getRoot() {
        return root;
    }

    @Override
    public Node<E> getLeft() {
        return root.leftChild;
    }

    @Override
    public Node<E> getRight() {
        return root.rightChild;
    }

    @Override
    public E getValue() {
        return root.value;
    }

    private Node<E> insert(Node<E> currentNode, E valueToInsert) {
        if (currentNode == null) {
            return new Node<>(valueToInsert);
        }

        int comparisonResult = currentNode.value.compareTo(valueToInsert);

        if (comparisonResult > 0) {
            currentNode.leftChild = insert(currentNode.leftChild, valueToInsert);
            return currentNode;
        } else if (comparisonResult < 0) {
            currentNode.rightChild = insert(currentNode.rightChild, valueToInsert);
            return currentNode;
        } else {
            return currentNode;
        }
    }

    /**
     * @param element
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
}
