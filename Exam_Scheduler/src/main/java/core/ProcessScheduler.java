package core;

import model.ScheduledTask;
import model.Task;
import shared.Scheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProcessScheduler implements Scheduler {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(Task task) {
        if (size == 0) {
            Node onlyNode = new Node(null, null, task);
            head = onlyNode;
            tail = onlyNode;
            size++;
            return;
        }

        Node newLastNode = new Node(tail, null, task);
        tail.next = newLastNode;
        tail = newLastNode;
        size++;
    }

    @Override
    public Task process() {
        if (head == null) {
            return null;
        }

        Task removedTask = head.value;
        head = head.next;
        size--;

        if (size == 0) {
            tail = null;
        }

        return removedTask;
    }

    @Override
    public Task peek() {
        if (head == null) {
            return null;
        }

        return head.value;
    }

    @Override
    public Boolean contains(Task task) {
        return findTaskNode(task.getId()) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Boolean remove(Task task) {
        return remove(task.getId());
    }

    @Override
    public Boolean remove(int id) {
        Node nodeToRemove = findTaskNode(id);
        throwExceptionIfNullNode(nodeToRemove);

        if (size == 1) {
            head = tail = null;
            size--;
            return true;
        }

        if (nodeToRemove == head) {
            head = head.next;
        } else if (nodeToRemove == tail){
            tail = tail.prev;
        } else {
            nodeToRemove.prev.next = nodeToRemove.next;
            nodeToRemove.next.prev = nodeToRemove.prev;
        }

        size--;
        return true;
    }

    @Override
    public void insertBefore(int id, Task task) {
        Node nodeFound = findTaskNode(id);
        throwExceptionIfNullNode(nodeFound);

        Node newNode = new Node(null, null, task);
        if (nodeFound == head) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        } else {
            nodeFound.prev.next = newNode;
            newNode.prev = nodeFound.prev;

            nodeFound.prev = newNode;
            newNode.next = nodeFound;
        }
    }

    @Override
    public void insertAfter(int id, Task task) {
        Node nodeFound = findTaskNode(id);
        throwExceptionIfNullNode(nodeFound);

        Node newNode = new Node(null, null, task);
        if (nodeFound == tail) {
            add(task);
        } else {
            nodeFound.next.prev = newNode;
            newNode.next = nodeFound.next;

            nodeFound.next = newNode;
            newNode.prev = nodeFound;
        }
    }

    @Override
    public void clear() {
        size = 0;
        head = tail = null;
    }

    @Override
    public Task[] toArray() {
        Task[] result = new ScheduledTask[size]; //TODO does not look good here

        Node currentNode = head;
        int counter = 0;
        while (currentNode != null) {
            result[counter++] = currentNode.value;
            currentNode = currentNode.next;
        }

        return result;
    }

    @Override
    public void reschedule(Task first, Task second) {
        Node firstNode = null;
        Node secondNode = null;

        Node currentNode = head;
        while (currentNode != null) {
            if (currentNode.value.getId() == first.getId()) {
                firstNode = currentNode;
            }

            if (currentNode.value.getId() == second.getId()) {
                secondNode = currentNode;
            }

            currentNode = currentNode.next;
        }

        if (firstNode == null || secondNode == null) {
            throw new IllegalArgumentException();
        }

        swap(firstNode, secondNode);
    }

    private void swap(Node firstNode, Node secondNode) {
        Task temp = firstNode.value;
        firstNode.value = secondNode.value;
        secondNode.value = temp;
    }

    @Override
    public List<Task> toList() {
        List<Task> result = new ArrayList<>(size);

        Iterator<Task> iterator = this.iterator();
        while (this.iterator().hasNext()) {
            result.add(iterator.next());
        }

        return result;
    }

    @Override
    public void reverse() {
        for (int i = 0; i < size; i++) {
            add(process());
        }
    }

    @Override
    public Task find(int id) {
        Node nodeFound = findTaskNode(id);
        throwExceptionIfNullNode(nodeFound);

        return nodeFound.value;
    }

    @Override
    public Task find(Task task) {
        return find(task.getId());
    }

    /**
     * Returns node with given id or null if not found.
     */
    private Node findTaskNode(int id) {
        Node currentNode = head;
        while (currentNode != null) {
            if (currentNode.value.getId() == id) {
                return currentNode;
            }

            currentNode = currentNode.next;
        }

        return null;
    }

    private void throwExceptionIfNullNode(Node node) {
        if (node == null) {
            throw new IllegalArgumentException();
        }
    }

    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            Node currentNode;

            @Override
            public boolean hasNext() {
                return currentNode != null;
            }

            @Override
            public Task next() {
                Task value = currentNode.value;
                currentNode = currentNode.next;
                return value;
            }
        };
    }

    private static class Node {
        Node prev;
        Node next;
        Task value;

        public Node(Node prev, Node next, Task value) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
