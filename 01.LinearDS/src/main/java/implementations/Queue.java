package implementations;

import interfaces.AbstractQueue;

import java.util.Iterator;

public class Queue<E> implements AbstractQueue<E> {
    private static final String NO_ELEMENTS_MESSAGE = "There are no elements in the structure!";

    private Node<E> head;
    private Node<E> tail;
    private int size;

    @Override
    public void offer(E element) {
        if (isEmpty()) {
            head = tail = new Node<>(null, null, element);
        } else {
            Node<E> newNode = new Node<>(tail, null, element);
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    @Override
    public E poll() {
        E polledElement = peek();
        head = head.next;

        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }

        size--;
        return polledElement;
    }

    @Override
    public E peek() {
        throwExceptionIfEmpty();
        return head.value;
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
            Node<E> currentNode = head;

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
            throw new IllegalStateException(NO_ELEMENTS_MESSAGE);
        }
    }

    private static class Node<E> {
        Node<E> prev;
        Node<E> next;
        E value;

        Node(E value) {
            this.value = value;
        }

        Node(Node<E> prev, Node<E> next, E value) {
            this(value);
            this.prev = prev;
            this.next = next;
        }

    }
}
