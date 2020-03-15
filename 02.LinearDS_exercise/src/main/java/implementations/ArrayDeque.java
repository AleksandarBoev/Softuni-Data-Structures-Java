package implementations;

import interfaces.Deque;

import java.util.Iterator;

//TODO cover more edge cases, clean up and tidy the code, make a "shrink" method
public class ArrayDeque<E> implements Deque<E> {
    private static final String INDEX_OUT_OF_BOUNDS_FORMAT_MESSAGE = "Index {%d} is out of bounds!";
    private static final String EMPTY_STRUCTURE_MESSAGE = "Data structure is empty!";

    private static final int INITIAL_CAPACITY = 8;
    private static final int MULTIPLIER = 2;

    private E[] data;
    private int startIndex;
    private int endIndex;
    private int size;

    public ArrayDeque() {
        data = (E[]) new Object[INITIAL_CAPACITY];
        startIndex = endIndex = 0;
    }

    @Override
    public void add(E Element) {
        addLast(Element);
    }

    @Override
    public void offer(E element) {
        addLast(element);
    }

    @Override
    public void addFirst(E element) {
        if (isEmpty()) {
            data[startIndex] = element;
            size++;
            return;
        }

        size++;
        growIfNeeded();
        if (startIndex == 1) {
            int no = 2;
        }
        startIndex--;
        if (startIndex == -1) {
            startIndex = data.length - 1;
        }
        data[startIndex] = element;
    }

    @Override
    public void addLast(E element) {
        if (isEmpty()) {
            data[startIndex] = element;
            size++;
            return;
        }

        size++;
        growIfNeeded();
        endIndex++;
        data[endIndex] = element;
    }

    @Override
    public void push(E element) {
        addFirst(element);
    }

    @Override
    public void insert(int index, E element) {
        throwExceptionIfIndexOutOfBounds(index);

        if (index == 0) {
            addFirst(element);
            return;
        }

        if (index == size - 1) {
            addLast(element);
        }

        size++;
        growIfNeeded();
        shiftRight(getActualIndex(index));
        incrementEndIndex();
        set(index, element);
    }

    @Override
    public void set(int index, E element) {
        throwExceptionIfIndexOutOfBounds(index);
        data[getActualIndex(index)] = element;
    }

    @Override
    public E peek() {
        throwExceptionIfEmpty();
        return data[startIndex];
    }

    @Override
    public E poll() {
        return removeFirst();
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public E get(int index) {
        throwExceptionIfIndexOutOfBounds(index);
        return data[getActualIndex(index)];
    }

    @Override
    public E get(Object object) {
        for (int i = startIndex; i < startIndex + size; i++) {
            if (data[i % data.length].equals(object)) {
                return data[i % data.length];
            }
        }

        return null;
    }

    @Override
    public E remove(int index) {
        throwExceptionIfEmpty();
        throwExceptionIfIndexOutOfBounds(index);

        if (index == 0) {
            return removeFirst();
        }

        E removedValue = data[getActualIndex(index)];
        shiftLeft(getActualIndex(index));
        decrementEndIndex();
        size--;

        return removedValue;
    }

    @Override
    public E remove(Object object) {
        for (int i = startIndex; i < startIndex + size; i++) {
            if (data[i % data.length].equals(object)) {
                if (i % data.length == startIndex) {
                    return removeFirst();
                }

                E removedValue = data[i % data.length];
                shiftLeft(i);
                size--;
                decrementEndIndex();
                return removedValue;
            }
        }

        return null;
    }

    @Override
    public E removeFirst() {
        throwExceptionIfEmpty();

        E removedValue = data[startIndex];
        shiftLeft(startIndex);
        size--;
        decrementEndIndex();
        return removedValue;
    }

    @Override
    public E removeLast() {
        throwExceptionIfEmpty();

        E removedElement = data[endIndex];
        decrementEndIndex();
        size--;
        return removedElement;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacity() {
        return data.length;
    }

    @Override
    public void trimToSize() {
        //this method is for the "middleIndex" implementation of this structure
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<E> {
        int currentIndex = startIndex;
        boolean endIndexVisited = false;

        @Override
        public boolean hasNext() {
            boolean condition1 = size > 0;

            int indexToCheck = currentIndex % data.length;

            boolean result = condition1 && !endIndexVisited;
            if (indexToCheck == endIndex) {
                endIndexVisited = true;
            }

            return result;
        }

        @Override
        public E next() {
            return data[currentIndex++ % data.length];
        }
    }

    private int getMiddleIndex(int capacity) {
        return capacity / 2 + capacity % 2;
    }

    private int getActualIndex(int index) {
        return (startIndex + index) % data.length;
    }

    private void growIfNeeded() {
        if (size <= data.length) {
            return;
        }

        E[] increasedCapacityData = (E[]) new Object[data.length * MULTIPLIER];
        for (int i = 0; i < data.length; i++) {
            int actualIndex = getActualIndex(i);
            increasedCapacityData[i] = data[actualIndex];
        }

        startIndex = 0;
        endIndex = data.length - 1;

        data = increasedCapacityData;
    }

    private boolean indexIsOutOfBounds(int index) {
        return index < 0 || index >= size;
    }

    private void throwExceptionIfIndexOutOfBounds(int index) {
        if (indexIsOutOfBounds(index)) {
            throw new IndexOutOfBoundsException(String.format(INDEX_OUT_OF_BOUNDS_FORMAT_MESSAGE, index));
        }
    }

    private void shiftLeft(int fromIndex) {
        for (int i = fromIndex; i < size - 1; i++) {
            data[i % data.length] = data[(i + 1) % data.length];
        }
    }

    private void throwExceptionIfEmpty() {
        if (isEmpty()) {
            throw new IllegalStateException(EMPTY_STRUCTURE_MESSAGE);
        }
    }

    private void incrementStartIndex() {
        startIndex = (startIndex + 1) % data.length;
    }

    private void decrementEndIndex() {
        endIndex = (endIndex - 1) % data.length;
    }

    private void incrementEndIndex() {
        endIndex = (endIndex + 1) % data.length;
    }

    private void shiftRight(int fromIndex) {
        //TODO
        for (int i = endIndex + 1; i != fromIndex; i--) { // start from next element and end with ...
            int index1 = i % data.length;
            int index2 = (i - 1) % data.length;
            data[i % data.length] = data[(i - 1) % data.length];
        }
    }
}
