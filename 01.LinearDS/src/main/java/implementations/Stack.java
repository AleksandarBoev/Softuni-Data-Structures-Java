package implementations;

import interfaces.AbstractStack;

import java.util.Iterator;

public class Stack<E> implements AbstractStack<E> {
    private static final String NO_ELEMENTS_MESSAGE = "There are no elements in the structure!";

    private Node<E> tail;
    private int size;

    @Override
    public void push(E element) {
        if (tail == null) {
            tail = new Node<>(null, element);
        } else {
            tail = new Node<>(tail, element);
        }

        size++;
    }

    @Override
    public E pop() {
        E poppedElement = peek();
        tail = tail.prev;
        size--;
        return poppedElement;
    }

    @Override
    public E peek() {
        throwExceptionIfEmptyStructure();
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
            Node<E> currentNode = tail;

            @Override
            public boolean hasNext() {
                return currentNode != null;
            }

            @Override
            public E next() {
                E value = currentNode.value;
                currentNode = currentNode.prev;
                return value;
            }
        };
    }

    private static class Node<E> {
        Node<E> prev;
        E value;

        Node(Node<E> prev, E value) {
            this.prev = prev;
            this.value = value;
        }
    }

    private void throwExceptionIfEmptyStructure() {
        if (size == 0) {
            throw new IllegalStateException(NO_ELEMENTS_MESSAGE);
        }
    }
}
