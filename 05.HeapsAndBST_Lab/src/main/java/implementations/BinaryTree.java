package implementations;

import interfaces.AbstractBinaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BinaryTree<E> implements AbstractBinaryTree<E> {
    private E key;
    private BinaryTree<E> left;
    private BinaryTree<E> right;

    public BinaryTree(E key, BinaryTree<E> left, BinaryTree<E> right) {
        this.key = key;
        this.left = left;
        this.right = right;
    }

    @Override
    public E getKey() {
        return key;
    }

    @Override
    public AbstractBinaryTree<E> getLeft() {
        return left;
    }

    @Override
    public AbstractBinaryTree<E> getRight() {
        return right;
    }

    @Override
    public void setKey(E key) {
        this.key = key;
    }

    @Override
    public String asIndentedPreOrder(int indent) {
        StringBuilder result = new StringBuilder();
        asIndentedPreOrder(this, indent, result);
        return result.substring(0, result.lastIndexOf(System.lineSeparator()));
    }

    @Override
    public List<AbstractBinaryTree<E>> preOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();
        preOrder(this, result);
        return result;
    }

    @Override
    public List<AbstractBinaryTree<E>> inOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();
        inOrder(this, result);
        return result;
    }

    @Override
    public List<AbstractBinaryTree<E>> postOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();
        postOrder(this, result);
        return result;
    }

    @Override
    public void forEachInOrder(Consumer<E> consumer) {
        inOrder(this, consumer);
    }

    private String getIndentString(int indent) {
        StringBuilder result = new StringBuilder(indent);
        for (int i = 0; i < indent; i++) {
            result.append(" ");
        }
        return result.toString();
    }

    private void asIndentedPreOrder(AbstractBinaryTree<E> currentTree, int indent, StringBuilder builder) {
        if (currentTree == null) {
            return;
        }

        builder.append(getIndentString(indent)).append(currentTree.getKey()).append(System.lineSeparator());
        asIndentedPreOrder(currentTree.getLeft(), indent + 2, builder);
        asIndentedPreOrder(currentTree.getRight(), indent + 2, builder);
    }

    private void preOrder(BinaryTree<E> currentTree, List<AbstractBinaryTree<E>> trees) {
        if (currentTree == null) {
            return;
        }

        trees.add(currentTree);
        preOrder(currentTree.left, trees);
        preOrder(currentTree.right, trees);
    }

    private void inOrder(BinaryTree<E> currentTree, List<AbstractBinaryTree<E>> trees) {
        if (currentTree == null) {
            return;
        }

        inOrder(currentTree.left, trees);
        trees.add(currentTree);
        inOrder(currentTree.right, trees);
    }

    private void postOrder(BinaryTree<E> currentTree, List<AbstractBinaryTree<E>> trees) {
        if (currentTree == null) {
            return;
        }

        postOrder(currentTree.left, trees);
        postOrder(currentTree.right, trees);
        trees.add(currentTree);
    }

    private void inOrder(BinaryTree<E> currentTree, Consumer<E> consumer) {
        if (currentTree == null) {
            return;
        }

        inOrder(currentTree.left, consumer);
        consumer.accept(currentTree.key);
        inOrder(currentTree.right, consumer);
    }
}
