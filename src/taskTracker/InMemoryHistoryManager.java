package taskTracker;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
//    static List<Task> dataRequest = new ArrayList<>();
    protected CustomLinkedList<Task> dataRequest = new CustomLinkedList<>();

    @Override
    public void addHistory(Task task) {
//        dataRequest.add(task);
//        if (dataRequest.size() > 10) {
//            dataRequest.remove(0);
//        }

        dataRequest.addLast(task, task.taskCode);
    }

    @Override
    public void remove(int taskCode) {

        dataRequest.removeT(taskCode);

//        List<Task> removeTask = new ArrayList<>();
//        for (Task task : dataRequest.getTasks()) {
//            if (task.taskCode == taskCode) {
//                removeTask.add(task);
//            }
//        }


//        dataRequest.getTasks().r
//        dataRequest.getTasks().removeAll(removeTask);
//        dataRequest.addLast();

    }

    @Override
    public List<Task> getHistory() {
        return dataRequest.getTasks();
    }

    public static class CustomLinkedList<T> {
        static class Node<E> {
            public E task;
            public Node<E> next;
            public Node<E> prev;

            public Node(Node<E> prev, E task, Node<E> next) {
                this.task = task;
                this.next = next;
                this.prev = prev;
            }
        }
        private final Map<Integer, Node<T>> nodeMap = new HashMap<>();

        private Node<T> head;
        private Node<T> tail;
        private int size = 0;
        public void add (Integer taskCode, Node<T> task) {
            nodeMap.put(taskCode, task);
        }

        public void addLast(T task, Integer taskCode) {
            final Node<T> oldTail = tail;
            final Node<T> newNode = new Node<>(oldTail, task, null);
            tail = newNode;
            if (oldTail == null)
                head = newNode;
            else
                oldTail.next = newNode;
            size++;
            add(taskCode, newNode);
        }
        public List<T> getTasks() {
            List<T> tasks = new ArrayList<>();
            Node<T> oldHead = head;
            tasks.add(oldHead.task);
            while (oldHead.next != null) {
                oldHead = oldHead.next;
                tasks.add(oldHead.task);
            }
            return tasks;
        }
        public void removeT (Integer taskCode) {
            Node<T> removeElement = nodeMap.get(taskCode);
            if (removeElement == head) {
                removeFirstNode(removeElement);
            } else if (removeElement == tail) {
                removeLastNode(removeElement);
            } else {
                removeNode(removeElement);
            }
        }

        private T removeFirstNode (Node<T> node) {
            final T element = node.task;
            final Node<T> next = node.next;
            node.task = null;
            node.next = null;
            head = next;
            if (next == null)
                tail = null;
            else
                next.prev = null;
            size--;
            return element;
        }

        private T removeLastNode (Node<T> node) {
            final T element = node.task;
            final Node<T> prev = node.prev;
            node.task = null;
            node.prev = null;
            tail = prev;
            if (prev == null)
                head = null;
            else
                prev.next = null;
            size--;
            return element;
        }

        T removeNode (Node<T> node) {
            final T element = node.task;
            final Node<T> next = node.next;
            final Node<T> prev = node.prev;

            if (prev == null) {
                head = next;
            } else {
                prev.next = next;
                node.prev = null;
            }

            if (next == null) {
                tail = prev;
            } else {
                next.prev = prev;
                node.next = null;
            }

            node.task = null;
            size--;
            return element;
        }
    }
}
