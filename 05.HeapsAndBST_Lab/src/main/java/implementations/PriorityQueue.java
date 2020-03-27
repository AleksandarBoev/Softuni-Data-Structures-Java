package implementations;

import interfaces.AbstractQueue;

public class PriorityQueue<E extends Comparable<E>> extends MaxHeap<E> implements AbstractQueue<E> {
    @Override
    public E poll() {
        throwExceptionIfEmpty("poll");

        E biggestElement = getElements().get(0);
        swap(0, size() - 1);
        getElements().remove(size() - 1);
        setSize(size() - 1);
        heapifyDown();

        return biggestElement;
    }

    /**
     * Used to preserve maxheap properties when removing an element. The most important property is that
     * the first element is always the biggest. So the main purpose of this method is to put the biggest element
     * on index 0 so that the next poll invocation returns the biggest element, as it should.
     * <p>
     * The element at index 0 is swapped with the bigger of its two children. After that it is swapped with
     * the bigger of its two new children and so on until both its children are smaller than it or until
     * it has no children (has become a leaf).
     */
    private void heapifyDown() {
        if (size() == 0) {
            return;
        }

        int currentIndex = 0;
        while (true) {
            // Heap structure is filled top-down from left to right.
            // If there is no left child, there won't be a right child either.
            // So the element is at the correct index.
            if (!hasLeftChild(currentIndex)) {
                return;
            }

            if (!hasRightChild(currentIndex)) {
                if (isSmallerThanLeftChild(currentIndex)) {
                    swapParentWithLeftChild(currentIndex);
                }

                return; // if there is no right child, then the left child won't have children
            }

            if (isBiggerThanBothChildren(currentIndex)) {
                return;
            }

            currentIndex = swapWithBiggerChild(currentIndex);
        }
    }

    private boolean isInBounds(int index) {
        return index >= 0 && index < size();
    }

    private boolean isBiggerThanBothChildren(int parentIndex) {
        return getElements().get(parentIndex).compareTo(getLeftChild(parentIndex)) > 0
                && getElements().get(parentIndex).compareTo(getRightChild(parentIndex)) > 0;
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

    private boolean isSmallerThanLeftChild(int parentIndex) {
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
     * first by max value, then by insertion order (like a normal queue).
     *
     * @param parentIndex
     * @return index of bigger child
     */
    private int swapWithBiggerChild(int parentIndex) {
        E leftChild = getLeftChild(parentIndex);
        E rightChild = getRightChild(parentIndex);

        if (leftChild.compareTo(rightChild) >= 0) {
            swapParentWithLeftChild(parentIndex);
            return getLeftChildIndex(parentIndex);
        } else {
            swapParentWithRightChild(parentIndex);
            return getRightChildIndex(parentIndex);
        }
    }
}
