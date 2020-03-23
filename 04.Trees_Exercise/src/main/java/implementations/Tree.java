package implementations;

import interfaces.AbstractTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tree<E> implements AbstractTree<E> {
    private E key;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E key) {
        this.key = key;
        children = new ArrayList<>();
    }

    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return parent;
    }

    @Override
    public E getKey() {
        return key;
    }

    @Override
    public String getAsString() {
        StringBuilder result = new StringBuilder();
        getAsString(this, 0, result);
        return result.substring(0, result.lastIndexOf(System.lineSeparator()));
    }

    @Override
    public List<E> getLeafKeys() {
        return getLeafs().stream().map(l -> l.key).collect(Collectors.toList());
    }

    @Override
    public List<E> getMiddleKeys() {
        //TODO
        return null;
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        int deepestLevel = -1;

        List<Tree<E>> leafs = getLeafs();
        Tree<E> result = null;

        for (Tree<E> currentLeaf : leafs) {
            int currentLeafLevel = currentLeaf.getLevel();
            if (currentLeafLevel > deepestLevel) {
                deepestLevel = currentLeafLevel;
                result = currentLeaf;
            }
        }

        return result;
    }

    @Override
    public List<E> getLongestPath() {
        Tree<E> deepestLeftmostNode = getDeepestLeftmostNode();
        return deepestLeftmostNode.getPath();
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        List<List<E>> listOfAllPaths = this.getLeafs().stream().map(l -> l.getPath()).collect(Collectors.toList());
        List<List<E>> listOfPathsWithGivenSum = new ArrayList<>();

        for (List<E> currentPath : listOfAllPaths) {
            Integer currentSum = 0;
            for (E currentElement : currentPath) {
                currentSum += (Integer) currentElement;
                if (currentSum > sum) {
                    break;
                }
            }

            if (currentSum == sum) {
                listOfPathsWithGivenSum.add(currentPath);
            }
        }

        return listOfPathsWithGivenSum;
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        //TODO
        return null;
    }

    private void getAsString(Tree<E> currentTree, int indentation, StringBuilder stringBuilder) {
        stringBuilder.append(getKeyWithIndentation(currentTree.key, indentation)).append(System.lineSeparator());

        for (Tree<E> childTree : currentTree.children) {
            getAsString(childTree, indentation + 2, stringBuilder);
        }
    }

    private String getKeyWithIndentation(E key, int indentation) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < indentation; i++) {
            result.append(" ");
        }

        result.append(key.toString());
        return result.toString();
    }

    private List<Tree<E>> getLeafs() {
        List<Tree<E>> result = new ArrayList<>();
        getLeafs(this, result);
        return result;
    }

    private void getLeafs(Tree<E> currentTree, List<Tree<E>> leafs) {
        if (currentTree.children.isEmpty()) {
            leafs.add(currentTree);
            return;
        }

        for (Tree<E> child : currentTree.children) {
            getLeafs(child, leafs);
        }
    }

    /**
     *
     * @return level of tree. A root (no parents) will return level 1.
     */
    private int getLevel() {
        Tree<E> currentTree = this;
        int levelCounter = 0;

        while (currentTree != null) {
            currentTree = currentTree.parent;
            levelCounter++;
        }

        return levelCounter;
    }

    private List<E> getPath() {
        ArrayDeque<E> stack = new ArrayDeque<>();
        Tree<E> currentTree = this;

        while (currentTree != null) {
            stack.push(currentTree.key);
            currentTree = currentTree.parent;
        }

        return new ArrayList<>(stack);
    }
}



