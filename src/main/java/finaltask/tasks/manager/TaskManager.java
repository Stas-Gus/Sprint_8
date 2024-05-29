package finaltask.tasks.manager;
import finaltask.tasks.Epic;
import finaltask.tasks.Subtask;
import finaltask.tasks.Task;
import finaltask.tasks.TaskStatus;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface TaskManager {

    Task saveTask(Task task);

    Subtask saveSubTask(Subtask subtask);

    List<Task> getPrioritizedTasks(int epicId);

    Epic saveEpic(Epic epic);

    List<Task> getAllTasks();

    List<Subtask> getAllSubTasks();

    List<Epic> getAllEpics();

    List<Subtask> getListTasksByEpic(Integer epicId);

    List<Subtask> getSubtasksByIds(List<Integer> subTasksIds);

    Task getTaskById(int id);

    Subtask getSubtaskById(int id);

    Epic getEpicById(int id);

    void removeTaskById(int id);

    void removeSubTaskById(int id);

    void updateEpicStatus(int epicId);

    TaskStatus calculateEpicStatus(Collection<Subtask> epicSubtasks);

    void removeEpic(int id);

    void removeAll();

    Epic createEpic(Epic epic);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    List<Task> getHistory();

    int generateId();
}
