package finaltask.tasks.manager;

public class Node<Task> {
    private final Task value;
    private Node<Task> next;
    private Node<Task> prev;

    public Node(Task value) {
        this.value = value;
    }

    public Task getValue() {
        return value;
    }

    public Node<Task> getNext() {
        return next;
    }

    public void setNext(Node<Task> next) {
        this.next = next;
    }

    public Node<Task> getPrev() {
        return prev;
    }

    public void setPrev(Node<Task> prev) {
        this.prev = prev;
    }
}
