package interfaces;

import implementations.BinarySearchTree;

public interface AbstractBinarySearchTree<E extends Comparable<E>> {
    public static class Node<E extends Comparable<E>> implements Comparable<Node<E>> {
        public E value;
        public Node<E> leftChild;
        public Node<E> rightChild;

        public Node(E value) {
            this.value = value;
        }

        public Node(E value, Node<E> leftChild, Node<E> rightChild) {
            this.value = value;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        @Override
        public int compareTo(Node<E> otherNode) {
            return this.value.compareTo(otherNode.value);
        }
    }

    void insert(E element);
    boolean contains(E element);
    AbstractBinarySearchTree<E> search(E element);
    Node<E> getRoot();
    Node<E> getLeft();
    Node<E> getRight();
    E getValue();
}
