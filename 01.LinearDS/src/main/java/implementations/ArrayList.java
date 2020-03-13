package implementations;

import interfaces.List;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class ArrayList<E> implements List<E> {
    private static final int INITIAL_DEFAULT_CAPACITY = 8;
    private static final int CAPACITY_INCREASE_MULTIPLIER = 2;
    private static final String INDEX_OUT_OF_BOUNDS_MESSAGE = "Index is out of bounds!";

    private E[] data;
    private int capacity;
    private int size;

    public ArrayList() {
        this(INITIAL_DEFAULT_CAPACITY);
    }
    
    public ArrayList(int initialCapacity) {
        capacity = initialCapacity;
        data = (E[])new Object[capacity];
    }

    @Override
    public boolean add(E element) {
        growIfNeeded();
        data[size++] = element;
        return true;
    }

    @Override
    public boolean add(int index, E element) {
        growIfNeeded();
        shiftRight(index);
        data[index] = element;
        size++;
        return true;
    }

    @Override
    public E get(int index) {
        throwExceptionIfOutOfBounds(index);

        return data[index];
    }

    @Override
    public E set(int index, E element) {
        throwExceptionIfOutOfBounds(index);

        E oldValue = data[index];
        data[index] = element;
        return oldValue;
    }

    @Override
    public E remove(int index) {
        throwExceptionIfOutOfBounds(index);
        E removedValue = data[index];
        shiftLeft(index);
        size--;
        return removedValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(element)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int indexCounter = 0;

            @Override
            public boolean hasNext() {
                return indexCounter < size;
            }

            @Override
            public E next() {
                return data[indexCounter++];
            }
        };
    }

    private boolean needsToGrow() {
        return size + 1 > capacity;
    }

    private void increaseCapacity() {
        capacity *= CAPACITY_INCREASE_MULTIPLIER;
    }

    private void growIfNeeded() {
        if (needsToGrow()) {
            increaseCapacity();
            data = Arrays.copyOf(data, capacity);
        }
    }

    private void shiftRight(int fromIndex) {
        for (int i = size + 1; i > fromIndex; i--) {
            data[i] = data[i - 1];
        }
    }

    private void shiftLeft(int fromIndex) {
        for (int i = fromIndex; i < size; i++) {
            data[i] = data[i + 1];
        }
    }

    private boolean indexIsInBounds(int index) {
        return index >= 0 && index < size;
    }

    private void throwExceptionIfOutOfBounds(int index) {
        if (!indexIsInBounds(index)) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS_MESSAGE);
        }
    }
}
