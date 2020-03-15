package implementations;

import java.util.Arrays;
import java.util.Iterator;

public class ReversedList<E> implements Iterable<E> {
    private static final int INITIAL_DEFAULT_CAPACITY = 2;
    private static final int CAPACITY_INCREASE_MULTIPLIER = 2;
    private static final String INDEX_OUT_OF_BOUNDS_MESSAGE = "Index is out of bounds!";

    private E[] data;
    private int capacity;
    private int size;

    public ReversedList() {
        capacity = INITIAL_DEFAULT_CAPACITY;
        data = (E[])new Object[capacity];
    }

    public boolean add(E element) {
        growIfNeeded();
        data[size++] = element;
        return true;
    }

    public E get(int index) {
        throwExceptionIfOutOfBounds(index);

        return data[getReversedIndex(index)];
    }

    public E removeAt(int index) {
        throwExceptionIfOutOfBounds(index);
        int wat = getReversedIndex(index);
        E removedValue = data[getReversedIndex(index)];
        shiftLeft(getReversedIndex(index));
        size--;
        return removedValue;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int indexCounter = size - 1;

            @Override
            public boolean hasNext() {
                return indexCounter >= 0;
            }

            @Override
            public E next() {
                return data[indexCounter--];
            }
        };
    }

    private boolean isEmpty() {
        return size == 0;
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

    private void shiftLeft(int fromIndex) {
        for (int i = fromIndex; i < size - 1; i++) {
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

    private int getReversedIndex(int index) {
        // 1 2 3 4 5 6
        // 0 1 2 3 4 5
        return size - 1 - index;
    }
}
