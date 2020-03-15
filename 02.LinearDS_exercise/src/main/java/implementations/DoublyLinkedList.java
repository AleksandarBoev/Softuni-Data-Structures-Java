package implementations;

import interfaces.LinkedList;

import java.util.Iterator;

public class DoublyLinkedList<E> implements LinkedList<E> {
    private static final String EMPTY_STRUCTURE_MESSAGE = "There are no elements!";

    private Node<E> head;
    private Node<E> tail;
    private int size;

    @Override
    public void addFirst(E element) {
        Node<E> newFirstNode = new Node<>(null, head, element);

        head = newFirstNode;

        if (isEmpty()) {
            tail = newFirstNode;
        }

        size++;
    }

    @Override
    public void addLast(E element) {
        if (isEmpty()) {
            addFirst(element);
            return;
        }

        Node<E> newLastNode = new Node<>(tail, null, element);
        tail.next = newLastNode;
        tail = newLastNode;
        size++;
    }

    @Override
    public E removeFirst() {
        throwExceptionIfEmpty();
        E removedElement = head.value;
        head = head.next;
        size--;

        if (isEmpty()) {
            tail = null;
        }

        return removedElement;
    }

    @Override
    public E removeLast() {
        if (size <= 1) {
            return removeFirst();
        }

        E removedValue = tail.value;
        tail = tail.prev;
        tail.next = null;
        size--;

        return removedValue;
    }

    @Override
    public E getFirst() {
        return head.value;
    }

    @Override
    public E getLast() {
        return tail.value;
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

    private static class Node<E> {
        Node<E> prev;
        Node<E> next;
        E value;

        public Node(Node<E> prev, Node<E> next, E value) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
