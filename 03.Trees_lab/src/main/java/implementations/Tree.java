package implementations;

import interfaces.AbstractTree;

import java.util.*;

public class Tree<E> implements AbstractTree<E> {
    private E value;
    private List<Tree<E>> children;
    private HashMap<E, Tree<E>> childrenValueNodeMap; //TODO finish up the methods addChild, removeNode and swap.

    public Tree(E value, Tree<E>... subTrees) {
        this.value = value;
        children = new ArrayList<>(Arrays.asList(subTrees));

        childrenValueNodeMap = new HashMap<>(children.size());
        for (Tree<E> child : children) {
            childrenValueNodeMap.put(child.value, child);
        }
    }

    @Override
    public List<E> orderBfs() {
        ArrayList<E> result = new ArrayList<>();

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
        try {
            Tree<E> parentTree = findParentTreeByChildValueThrowExceptionIfNotFound(nodeKey);

            if (parentTree == null) {
                this.value = null;
                this.children = new ArrayList<>();
            } else {
                this.children = new ArrayList<>();
            }
        } catch (NodeNotFoundException nnfe) {

        }
    }

    @Override
    public void swap(E firstKey, E secondKey) {

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

    private Tree<E> findParentTreeByChildValueThrowExceptionIfNotFound(E childValue) {
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

        throw new NodeNotFoundException();
    }

    private static class NodeNotFoundException extends RuntimeException {

    }
}



