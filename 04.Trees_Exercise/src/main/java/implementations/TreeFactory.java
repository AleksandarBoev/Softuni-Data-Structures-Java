package implementations;

import java.util.LinkedHashMap;
import java.util.Map;

public class TreeFactory {
    private Map<Integer, Tree<Integer>> nodesByKeys;
    private Tree<Integer> root;

    public TreeFactory() {
        this.nodesByKeys = new LinkedHashMap<>();
    }

    public Tree<Integer> createTreeFromStrings(String[] input) {
        fillDataStructure(input);
        setRoot();
        return root;
    }

    private void fillDataStructure(String[] input) {
        for (int i = 0; i < input.length; i++) {
            int parentKey = getParentKey(input[i]);
            int childKey = getChildKey(input[i]);

            createNodeIfNotExists(parentKey);
            createNodeIfNotExists(childKey);

            addEdge(parentKey, childKey);
        }
    }

    private int getParentKey(String string) {
        return Integer.parseInt(string.split(" ")[0]);
    }

    private int getChildKey(String string) {
        return Integer.parseInt(string.split(" ")[1]);
    }

    //    public Tree<Integer> createNodeByKey(int key) {
    private void createNodeIfNotExists(int key) {
        if (nodesByKeys.containsKey(key)) {
            return;
        }

        Tree<Integer> newTree = new Tree<Integer>(key);
        nodesByKeys.put(key, newTree);
    }

    //was public
    private void addEdge(int parent, int child) {
        Tree<Integer> parentTree = nodesByKeys.get(parent);
        Tree<Integer> childTree = nodesByKeys.get(child);

        parentTree.addChild(childTree);
        childTree.setParent(parentTree);
    }

    private void setRoot() {
        for (Tree<Integer> currentTree : nodesByKeys.values()) {
            if (currentTree.getParent() == null) {
                root = currentTree;
                return;
            }
        }
    }
}



