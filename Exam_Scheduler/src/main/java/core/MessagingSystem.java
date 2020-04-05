package core;

import model.Message;
import shared.DataTransferSystem;

import java.util.ArrayList;
import java.util.List;

public class MessagingSystem implements DataTransferSystem {
    private Node root;
    private int size;

    @Override
    public void add(Message message) {
        if (root == null) {
            root = new Node(message);
            size++;
            return;
        }

        Node currentNode = root;
        Node parentNode = null;
        int messageToCurrentComparisonResult = 0;
        while (currentNode != null) {
            messageToCurrentComparisonResult = Integer.compare(message.getWeight(), currentNode.value.getWeight());

            parentNode = currentNode;
            if (messageToCurrentComparisonResult > 0) {
                currentNode = currentNode.rightChild;
            } else if (messageToCurrentComparisonResult < 0) {
                currentNode = currentNode.leftChild;
            } else {
                throw new IllegalArgumentException();
            }
        }

        Node newNode = new Node(message);
        newNode.parent = parentNode;

        if (messageToCurrentComparisonResult > 0) {
            parentNode.rightChild = newNode;
        } else {
            parentNode.leftChild = newNode;
        }

        size++;
    }

    @Override
    public Message getByWeight(int weight) {
        Node currentNode = root;

        while (currentNode != null) {
            int messageToCurrentComparisonResult = Integer.compare(weight, currentNode.value.getWeight());

            if (messageToCurrentComparisonResult > 0) {
                currentNode = currentNode.rightChild;
            } else if (messageToCurrentComparisonResult < 0) {
                currentNode = currentNode.leftChild;
            } else {
                return currentNode.value;
            }
        }

        throw new IllegalArgumentException();
    }

    @Override
    public Message getLightest() {
        throwExceptionIfEmptyStructure();

        Node currentNode = root;
        while (currentNode.leftChild != null) {
            currentNode = currentNode.leftChild;
        }

        return currentNode.value;
    }

    @Override
    public Message getHeaviest() {
        throwExceptionIfEmptyStructure();

        Node currentNode = root;
        while (currentNode.rightChild != null) {
            currentNode = currentNode.rightChild;
        }

        return currentNode.value;
    }

    @Override
    public Message deleteLightest() {
        throwExceptionIfEmptyStructure();

        Node leftmostNode = root;

        while (leftmostNode.leftChild != null) {
            leftmostNode = leftmostNode.leftChild;
        }

        if (leftmostNode == root) {
            if (root.rightChild == null) {
                root = null;
            } else {
                root = root.rightChild;
                root.parent = null;
            }
        } else {
            if (leftmostNode.rightChild != null) {
                leftmostNode.parent.leftChild = leftmostNode.rightChild;
                leftmostNode.rightChild.parent = leftmostNode.parent;
            } else {
                leftmostNode.parent.leftChild = null;
            }
        }

        size--;
        return leftmostNode.value;
    }

    @Override
    public Message deleteHeaviest() {
        throwExceptionIfEmptyStructure();

        Node rightmostNode = root;

        while (rightmostNode.rightChild != null) {
            rightmostNode = rightmostNode.rightChild;
        }

        if (rightmostNode == root) {
            if (root.leftChild == null) {
                root = null;
            } else {
                root = root.leftChild;
                root.parent = null;
            }
        } else {
            if (rightmostNode.leftChild != null) {
                rightmostNode.parent.rightChild = rightmostNode.leftChild;
                rightmostNode.leftChild.parent = rightmostNode.parent;
            } else {
                rightmostNode.parent.rightChild = null;
            }
        }

        size--;
        return rightmostNode.value;
    }

    @Override
    public Boolean contains(Message message) {
        try {
            getByWeight(message.getWeight());
            return true;
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }

    @Override
    public List<Message> getOrderedByWeight() {
        return getInOrder();
    }

    @Override
    public List<Message> getPostOrder() {
        List<Message> result = new ArrayList<>();
        postOrder(root, result);
        return result;
    }

    private void postOrder(Node currentNode, List<Message> messagesList) {
        if (currentNode == null) {
            return;
        }

        postOrder(currentNode.leftChild, messagesList);
        postOrder(currentNode.rightChild, messagesList);
        messagesList.add(currentNode.value);
    }

    @Override
    public List<Message> getPreOrder() {
        List<Message> result = new ArrayList<>();
        preOrder(root, result);
        return result;
    }

    private void preOrder(Node currentNode, List<Message> messagesList) {
        if (currentNode == null) {
            return;
        }

        messagesList.add(currentNode.value);
        preOrder(currentNode.leftChild, messagesList);
        preOrder(currentNode.rightChild, messagesList);
    }

    @Override
    public List<Message> getInOrder() {
        List<Message> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    private void inOrder(Node currentNode, List<Message> messagesList) {
        if (currentNode == null) {
            return;
        }

        inOrder(currentNode.leftChild, messagesList);
        messagesList.add(currentNode.value);
        inOrder(currentNode.rightChild, messagesList);
    }

    @Override
    public int size() {
        return size;
    }

    private void throwExceptionIfEmptyStructure() {
        if (root == null) {
            throw new IllegalStateException();
        }
    }

    private static class Node {
        private Message value;
        private Node leftChild;
        private Node rightChild;
        private Node parent;

        public Node(Message value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "" + value.getWeight();
        }
    }
}
