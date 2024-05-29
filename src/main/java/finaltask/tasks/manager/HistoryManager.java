package finaltask.tasks.manager;

import finaltask.tasks.Task;

import java.util.List;

public interface HistoryManager {

    List<Task> getHistory();

    void addTask(Task task);

    void remove(int id);
}
