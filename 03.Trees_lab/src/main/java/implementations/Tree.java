package implementations;

import interfaces.AbstractTree;

import java.util.*;

public class Tree<E> implements AbstractTree<E> {
    private static final String ELEMENT_NOT_FOUND_MESSAGE = "Element not found!";

    private E value;
    private List<Tree<E>> children;

    public Tree(E value, Tree<E>... subTrees) {
        this.value = value;
        children = new ArrayList<>(Arrays.asList(subTrees));
    }

    @Override
    public List<E> orderBfs() {
        ArrayList<E> result = new ArrayList<>();

        if (this.value == null) {
            return result;
        }

        ArrayDeque<Tree<E>> treeQueue = new ArrayDeque<>();
        treeQueue.offer(this);

        while (!treeQueue.isEmpty()) {
            Tree<E> currentTree = treeQueue.poll();
            result.add(currentTree.value);

            treeQueue.addAll(currentTree.children); // "addAll" uses "addLast". "offer" is just "addLast" with a different name
        }

        return result;
    }

    @Override
    public List<E> orderDfs() {
        ArrayList<E> result = new ArrayList<>();
        orderDfs(this, result);
        return result;
    }

    @Override
    public void addChild(E parentKey, Tree<E> child) {
        Tree<E> parentTree = findTreeByValueBFS(parentKey);
        if (parentTree != null) {
            parentTree.addChild(child);
        }
    }

    @Override
    public void removeNode(E nodeKey) {
        Tree<E> parentTree = findParentTreeByChildValue(nodeKey);

        if (parentTree == null) { // if the root is the searched tree
            this.value = null;
            this.children = new ArrayList<>();
        } else {
            int indexOfTreeToRemove = parentTree.getChildIndex(nodeKey);
            parentTree.children.remove(indexOfTreeToRemove);
        }
    }

    @Override
    public void swap(E firstKey, E secondKey) {
        Tree<E> firstParent = findParentTreeByChildValue(firstKey);
        Tree<E> secondParent = findParentTreeByChildValue(secondKey);

        if (firstParent == null) { //firstElement is root
            Tree<E> newRoot = secondParent.children.get(secondParent.getChildIndex(secondKey));
            this.value = newRoot.value;
            this.children = newRoot.children;
            return;
        }

        if (secondParent == null) { //secondElement is root
            Tree<E> newRoot = firstParent.children.get(firstParent.getChildIndex(firstKey));
            this.value = newRoot.value;
            this.children = newRoot.children;
            return;
        }

        //TODO hmm, something ain't working right. And again - something not working correctly. Check out the tests

        int firstIndex = firstParent.getChildIndex(firstKey);
        int secondIndex = secondParent.getChildIndex(secondKey);

        Tree<E> temp = firstParent.children.get(firstIndex);
        firstParent.children.set(firstIndex, secondParent.children.get(secondIndex));
        secondParent.children.set(secondIndex, temp);
//        E temp = firstParent.children.get(firstIndex).value;
//        firstParent.children.get(firstIndex).value = secondParent.children.get(secondIndex).value;
//        secondParent.children.get(secondIndex).value = temp;
    }

    private void orderDfs(Tree<E> currentTree, List<E> values) {
        for (Tree<E> child : currentTree.children) {
            orderDfs(child, values);
        }

        values.add(currentTree.value);
    }

    private void addChild(Tree<E> child) {
        children.add(child);
    }

    private Tree<E> findTreeByValueBFS(E value) {
        ArrayDeque<Tree<E>> treeQueue = new ArrayDeque<>();
        treeQueue.offer(this);

        while (!treeQueue.isEmpty()) {
            Tree<E> currentTree = treeQueue.poll();
            if (currentTree.value.equals(value)) {
                return currentTree;
            }
            treeQueue.addAll(currentTree.children);
        }

        return null;
    }

    public Tree<E> findParentTreeByChildValue(E childValue) {
        if (this.value.equals(childValue)) {
            return null;
        }

        ArrayDeque<Tree<E>> treeQueue = new ArrayDeque<>();
        treeQueue.offer(this);

        while (!treeQueue.isEmpty()) {
            Tree<E> currentTree = treeQueue.poll();

            for (Tree<E> child : currentTree.children) {
                if (child.value.equals(childValue)) {
                    return currentTree;
                }
            }

            treeQueue.addAll(currentTree.children);
        }

        throw new IllegalArgumentException(ELEMENT_NOT_FOUND_MESSAGE);
    }

    private int getChildIndex(E value) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).value.equals(value)) {
                return i;
            }
        }

        return -1;
    }
}



