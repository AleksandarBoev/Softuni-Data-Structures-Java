package implementations;

import interfaces.LinkedList;

import java.util.Iterator;

public class SinglyLinkedList<E> implements LinkedList<E> {
    private static final String EMPTY_STRUCTURE_MESSAGE = "There are no elements!";

    private Node<E> head;
    private int size;

    @Override
    public void addFirst(E element) {
        Node<E> newNode = new Node<>(head, element);
        head = newNode;
        size++;
    }

    @Override
    public void addLast(E element) {
        if (isEmpty()) {
            addFirst(element);
            return;
        }

        Node<E> lastNode = getLastNode();

        Node<E> newNode = new Node<>(null, element);
        lastNode.next = newNode;
        size++;
    }

    @Override
    public E removeFirst() {
        throwExceptionIfEmpty();
        E removedElement = head.value;
        head = head.next;
        size--;
        return removedElement;
    }

    @Override
    public E removeLast() {
        if (size <= 1) {
            return removeFirst();
        }

        Node<E> lastNodeParent = getLastNodeParent();
        E removedValue = lastNodeParent.next.value;
        lastNodeParent.next = null;
        size--;

        return removedValue;
    }

    @Override
    public E getFirst() {
        return head.value;
    }

    @Override
    public E getLast() {
        return getLastNode().value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> currentNode;

            @Override
            public boolean hasNext() {
                return currentNode != null;
            }

            @Override
            public E next() {
                E value = currentNode.value;
                currentNode = currentNode.next;
                return value;
            }
        };
    }

    private void throwExceptionIfEmpty() {
        if (isEmpty()) {
            throw new IllegalStateException(EMPTY_STRUCTURE_MESSAGE);
        }
    }

    private boolean hasMoreThanOneElements() {
        return size > 1;
    }

    private Node<E> getLastNodeParent() {
        Node<E> lastNodeParent = head;
        while (lastNodeParent.next.next != null) {
            lastNodeParent = lastNodeParent.next;
        }

        return lastNodeParent;
    }

    private Node<E> getLastNode() {
        Node<E> result = head;
        while (result.next != null) {
            result = result.next;
        }

        return result;
    }

    private static class Node<E> {
        Node<E> next;
        E value;

        public Node(Node<E> next, E value) {
            this.next = next;
            this.value = value;
        }
    }
}
