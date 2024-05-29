package finaltask.tasks.manager;

import finaltask.tasks.Task;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private Node<Task> first;
    private Node<Task> last;

    private final Map<Integer, Node<Task>> taskIdToNode = new HashMap<>();

    @Override
    public List<Task> getHistory() {
        List<Task> result = new ArrayList<>();
        if (first == null) {
            return result;
        }
        Node<Task> current = first;

        while (current != null) {
            result.add(current.getValue());
            current = current.getNext();
        }
        return result;
    }

    @Override
    public void addTask(Task task) {
        if (taskIdToNode.containsKey(task.getId())) {
            remove(task.getId());
        }

        Node<Task> node = new Node<>(task);
        taskIdToNode.put(task.getId(), node);

        if (last != null) {
            last.setNext(node);
            node.setPrev(last);
        } else {
            first = node;
        }
        last = node;
    }

    @Override
    public void remove(int id) {
        Node<Task> node = taskIdToNode.get(id);
        Node<Task> next = node.getNext();
        Node<Task> prev = node.getPrev();

        if (prev != null) {
            prev.setNext(next);
        } else {
            first = next;
        }

        if (next != null) {
            next.setPrev(prev);
        } else {
            last = prev;
        }

        taskIdToNode.remove(id);
    }
}
