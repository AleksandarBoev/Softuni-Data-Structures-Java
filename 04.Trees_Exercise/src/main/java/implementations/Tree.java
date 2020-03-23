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
        return getAllTreesDFS().stream()
                .filter(t -> t.hasParentAndAnyChildren())
                .map(t -> t.key)
                .collect(Collectors.toList());
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

    // Not optimal solution. Sums are calculated multiple times.
    // Solution would be for each tree to have a field to hold the value of sum
    // and each time a new tree is created, all of it's parents would have to increase their sums
    // with the value of the new tree. But that would slow down a more important method - adding a new tree.
    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        List<Tree<E>> allTrees = getAllTreesDFS();

        return allTrees.stream()
                .filter(t -> t.getSum() == sum)
                .collect(Collectors.toList());
    }

    /**
     *
     * @return sum of current tree and all subtrees
     */
    private int getSum() {
        return getAllTreesDFS().stream()
                .map(t -> (Integer)t.key)
                .reduce(0, (accumulator, element) -> accumulator += element);
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

    /**
     *
     * @return current tree and subtrees in pre-order (root -> left -> right).
     */
    private List<Tree<E>> getAllTreesDFS() {
        List<Tree<E>> result = new ArrayList<>();
        getAllTreesDFS(this, result);
        return result;
    }

    private void getAllTreesDFS(Tree<E> currentTree, List<Tree<E>> allTrees) {
        allTrees.add(currentTree);

        for (Tree<E> child : currentTree.children) {
            getAllTreesDFS(child, allTrees);
        }
    }

    private List<Tree<E>> getLeafs() {
        return getAllTreesDFS().stream().filter(t -> t.children.isEmpty()).collect(Collectors.toList());
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

    /**
     *
     * @return list of tree keys from root to current tree inclusive
     */
    private List<E> getPath() {
        ArrayDeque<E> stack = new ArrayDeque<>();
        Tree<E> currentTree = this;

        while (currentTree != null) {
            stack.push(currentTree.key);
            currentTree = currentTree.parent;
        }

        return new ArrayList<>(stack);
    }

    private boolean hasParentAndAnyChildren() {
        return this.parent != null && !this.children.isEmpty();
    }
}



