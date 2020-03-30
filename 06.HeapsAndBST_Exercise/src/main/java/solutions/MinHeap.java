package solutions;

import interfaces.Decrease;
import interfaces.Heap;

import java.util.ArrayList;
import java.util.List;

/**
 * Parent is always smaller than its children. Polled element is always the smallest.
 * <br/>
 * <br/>
 * Heapify up is used when an element is added. If it is smaller than its parent, then they swap. This continues until the parent is smaller.
 * If the new smallest element is added, then it will work itself up until it becomes the new first element.
 * <br/>
 * <br/>
 * Heapify down is used when polling an element. The first element(smallest) is saved into a temporary variable.
 * Then it is swapped with the last element. And finally deleted. The new first element is heapified down meaning
 * it is swapped with every child which is smaller than it.
 */
public class MinHeap<E extends Comparable<E> & Decrease<E>> implements Heap<E> {
    private static String EMPTY_COLLECTION_MESSAGE_FORMAT = "Can't call method \"%s\" on an empty collection!";

    private int size;
    private List<E> elements;

    public MinHeap() {
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

    @Override
    public E poll() {
        throwExceptionIfEmpty("poll");

        E smallestElement = getElements().get(0);
        swap(0, size() - 1);
        getElements().remove(size() - 1);
        setSize(size() - 1);
        heapifyDown();

        return smallestElement;
    }

    private void setSize(int size) {
        this.size = size;
    }

    private List<E> getElements() {
        return elements;
    }

    private int getLeftChildIndex(int parentIndex) {
        return 2 * parentIndex + 1;
    }

    private int getRightChildIndex(int parentIndex) {
        return 2 * parentIndex + 2;
    }

    private int getParentIndex(int childIndex) {
        return (childIndex - 1) / 2;
    }

    private void swap(int index1, int index2) {
        E temp = elements.get(index1);
        elements.set(index1, elements.get(index2));
        elements.set(index2, temp);
    }

    private void heapifyUp() {
        heapifyUp(size - 1);
    }

    private void heapifyUp(int index) {
        int currentIndex = index;
        int parentIndex = getParentIndex(currentIndex);

        while (parentIndex >= 0 && elements.get(currentIndex).compareTo(elements.get(parentIndex)) < 0) {
            swap(currentIndex, parentIndex);
            currentIndex = parentIndex;
            parentIndex = getParentIndex(currentIndex);
        }
    }

    private void throwExceptionIfEmpty(String nameOfMethodThrowingException) {
        if (size == 0) {
            throw new IllegalStateException(String.format(EMPTY_COLLECTION_MESSAGE_FORMAT, nameOfMethodThrowingException));
        }
    }

    /**
     * Used to preserve minHeap properties when removing an element. The most important property is that
     * the first element is always the smallest. So the main purpose of this method is to put the smallest element
     * on index 0 so that the next poll invocation returns the smallest element, as it should.
     * <br/>
     * <br/>
     * The element at index 0 is swapped with the smaller of its two children. After that it is swapped with
     * the smaller of its two new children and so on until both its children are bigger than it or until
     * it has no children (has become a leaf).
     */
    private void heapifyDown() {
        if (size() == 0) {
            return;
        }

        int currentParentIndex = 0;
        while (true) {
            // Heap structure is filled top-down from left to right.
            // If there is no left child, there won't be a right child either.
            // So the element is at the correct index.
            if (!hasLeftChild(currentParentIndex)) {
                return;
            }

            if (!hasRightChild(currentParentIndex)) {
                if (isBiggerThanLeftChild(currentParentIndex)) {
                    swapParentWithLeftChild(currentParentIndex);
                }

                return; // if there is no right child, then the left child won't have children
            }

            if (isSmallerThanBothChildren(currentParentIndex)) {
                return;
            }

            currentParentIndex = swapWithSmallerChild(currentParentIndex);
        }
    }

    private boolean isInBounds(int index) {
        return index >= 0 && index < size();
    }

    private boolean isSmallerThanBothChildren(int parentIndex) {
        return getElements().get(parentIndex).compareTo(getLeftChild(parentIndex)) < 0
                && getElements().get(parentIndex).compareTo(getRightChild(parentIndex)) < 0;
    }

    private boolean hasLeftChild(int parentIndex) {
        return isInBounds(getLeftChildIndex(parentIndex));
    }

    private boolean hasRightChild(int parentIndex) {
        return isInBounds(getRightChildIndex(parentIndex));
    }

    private E getLeftChild(int parentIndex) {
        return getElements().get(getLeftChildIndex(parentIndex));
    }

    private E getRightChild(int parentIndex) {
        return getElements().get(getRightChildIndex(parentIndex));
    }

    private boolean isBiggerThanLeftChild(int parentIndex) {
        return getElements().get(parentIndex).compareTo(getLeftChild(parentIndex)) < 0;
    }

    private boolean isSmallerThanRightChild(int parentIndex) {
        return getElements().get(parentIndex).compareTo(getRightChild(parentIndex)) < 0;
    }

    private void swapParentWithLeftChild(int parentIndex) {
        swap(parentIndex, getLeftChildIndex(parentIndex));
    }

    private void swapParentWithRightChild(int parentIndex) {
        swap(parentIndex, getRightChildIndex(parentIndex));
    }

    /**
     * If children are equal then the parent is swapped with left child. This way elements are polled
     * first by min value, then by insertion order (like a normal queue).
     *
     * @return index of smaller child
     */
    private int swapWithSmallerChild(int parentIndex) {
        E leftChild = getLeftChild(parentIndex);
        E rightChild = getRightChild(parentIndex);

        if (leftChild.compareTo(rightChild) <= 0) {
            swapParentWithLeftChild(parentIndex);
            return getLeftChildIndex(parentIndex);
        } else {
            swapParentWithRightChild(parentIndex);
            return getRightChildIndex(parentIndex);
        }
    }

    @Override
    public void decrease(E element) {
        for (int i = 0; i < elements.size(); i++) {
            if (element.equals(elements.get(i))) {
                elements.get(i).decrease();
                heapifyUp(i);
                break;
            }
        }
    }
}
