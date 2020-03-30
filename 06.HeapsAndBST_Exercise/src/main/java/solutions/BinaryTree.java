package solutions;

import java.util.*;

/*
    Solution for my tree implementation would be to find the first node, save all its parents up to the root into a Set.
    Then start iterating over all the parents of the second node and the first one to be present in the Set is the
    lowest common ancestor.
 */
public class BinaryTree {
    private Integer key;
    private BinaryTree first;
    private BinaryTree second;
    private int offset;

    public BinaryTree(int key, BinaryTree first, BinaryTree second) {
        this.key = key;

        this.first = first;
        this.second = second;
    }

    public Integer findLowestCommonAncestor(int first, int second) {
        Set<Integer> firstPathSet  = new HashSet<>(getPath(first));
        List<Integer> secondPathList = getPath(second);

        //Start checking every element from second to its root. The first found occurrence is LCA.
        for (int i = 0; i < secondPathList.size(); i++) {
            if (firstPathSet.contains(secondPathList.get(i))) {
                return secondPathList.get(i);
            }
        }

        return null;
    }

    public List<Integer> topView() {
        addOffsets(this, 0);
        return getVisibleElements();
    }

    private void addOffsets(BinaryTree currentTree, int offset) {
        if (currentTree == null) {
            return;
        }

        currentTree.offset = offset;
        addOffsets(currentTree.first, offset - 1);
        addOffsets(currentTree.second, offset + 1);
    }

    private List<Integer> getVisibleElements() {
        List<Integer> visibleElements = new ArrayList<>();
        visibleElements.add(this.key); // root is always visible

        ArrayDeque<BinaryTree> queue = new ArrayDeque<>();
        int minOffset = 0;
        int maxOffset = 0;

        queue.offer(this);
        while (!queue.isEmpty()) {
            BinaryTree currentTree = queue.poll();

            if (currentTree.offset < minOffset) {
                minOffset = currentTree.offset;
                visibleElements.add(currentTree.key);
            }

            if (currentTree.offset > maxOffset) {
                maxOffset = currentTree.offset;
                visibleElements.add(currentTree.key);
            }

            if (currentTree.first != null) {
                queue.offer(currentTree.first);
            }

            if (currentTree.second != null) {
                queue.offer(currentTree.second);
            }
        }

        return visibleElements;
    }

    /**
     * Returns path to element. On first index is the element and on last - root.
     */
    private List<Integer> getPath(Integer element) {
        ArrayDeque<Integer> pathStack = new ArrayDeque<>();

        try {
            getPath(this, element, pathStack);
        } catch (ElementFoundException efe) {

        }

        return new ArrayList<>(pathStack);
    }

    private void getPath(BinaryTree currentTree, Integer element, ArrayDeque<Integer> pathStack) {
        if (currentTree == null) {
            return;
        }

        pathStack.push(currentTree.key);
        if (element.equals(currentTree.key)) {
            throw new ElementFoundException();
        }

        getPath(currentTree.first, element, pathStack);
        getPath(currentTree.second, element, pathStack);
        pathStack.pop();
    }

    private class ElementFoundException extends RuntimeException {

    }
}
