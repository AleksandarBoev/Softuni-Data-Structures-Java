package implementations;

import interfaces.Heap;

import java.util.ArrayList;
import java.util.List;

public class MaxHeap<E extends Comparable<E>> implements Heap<E> {
    private static String EMPTY_COLLECTION_MESSAGE_FORMAT = "Can't call method \"%s\" on an empty collection!";

    private int size;
    private List<E> elements;

    public MaxHeap() {
        elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(E element) {
        size++;
        elements.add(element);
        heapifyUp();
    }

    @Override
    public E peek() {
        throwExceptionIfEmpty("peek");
        return elements.get(0);
    }

    protected void setSize(int size) {
        this.size = size;
    }

    protected List<E> getElements() {
        return elements;
    }

    protected int getLeftChildIndex(int parentIndex) {
        return 2 * parentIndex + 1;
    }

    protected int getRightChildIndex(int parentIndex) {
        return 2 * parentIndex + 2;
    }

    protected int getParentIndex(int childIndex) {
        return (childIndex - 1) / 2;
    }

    protected void swap(int index1, int index2) {
        E temp = elements.get(index1);
        elements.set(index1, elements.get(index2));
        elements.set(index2, temp);
    }

    private void heapifyUp() {
        int currentIndex = size - 1;
        int parentIndex = getParentIndex(currentIndex);

        while (parentIndex >= 0 && elements.get(currentIndex).compareTo(elements.get(parentIndex)) > 0) {
            swap(currentIndex, parentIndex);
            currentIndex = parentIndex;
            parentIndex = getParentIndex(currentIndex);
        }
    }

    protected void throwExceptionIfEmpty(String nameOfMethodThrowingException) {
        if (size == 0) {
            throw new IllegalStateException(String.format(EMPTY_COLLECTION_MESSAGE_FORMAT, nameOfMethodThrowingException));
        }
    }
}
