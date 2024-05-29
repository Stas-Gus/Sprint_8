package finaltask.tasks.manager;

import finaltask.tasks.Epic;
import finaltask.tasks.Subtask;
import finaltask.tasks.Task;
import finaltask.tasks.TaskStatus;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryTaskManager implements TaskManager {

    protected final Map<Integer, Task> taskStorage = new HashMap<>();
    protected final Map<Integer, Epic> epicStorage = new HashMap<>();
    protected final Map<Integer, Subtask> subtaskStorage = new HashMap<>();
    protected final HistoryManager historyManager = Managers.getHistoryDefault();
    private int generatedId = 0;

    @Override
    public Task saveTask(Task task) {
        int id = generateId();
        task.setId(id);
        taskStorage.put(id, task);
        return task;
    }

    @Override
    public Subtask saveSubTask(Subtask subtask) {
        int id = generateId();
        subtask.setId(id);
        taskStorage.put(id, subtask);
        return subtask;
    }

    @Override
    public List<Task> getPrioritizedTasks(int epicId) {
        return epicStorage.get(epicId).getSubtasks().stream()
                .sorted(Comparator.comparing(Task::getStartTime))
                .collect(Collectors.toList());
    }

    @Override
    public Epic saveEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        epicStorage.put(id, epic);
        return epic;
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(taskStorage.values());
    }

    @Override
    public List<Subtask> getAllSubTasks() {
        return new ArrayList<>(subtaskStorage.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epicStorage.values());
    }

    @Override
    public List<Subtask> getListTasksByEpic(Integer epicId) {
        Epic epic = epicStorage.get(epicId);
        List<Integer> subTasksIds = epic.getSubtasks().stream()
                .map(Task::getId)
                .collect(Collectors.toList());
        return getSubtasksByIds(subTasksIds);
    }

    @Override
    public List<Subtask> getSubtasksByIds(List<Integer> subTasksIds) {
        List<Subtask> subtasks = new ArrayList<>();
        for (Integer subtaskId : subTasksIds) {
            Subtask subtask = subtaskStorage.get(subtaskId);
            subtasks.add(subtask);
        }
        return subtasks;
    }

    @Override
    public Task getTaskById(int id) {
        Task task = taskStorage.get(id);
        historyManager.addTask(task);
        return task;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtaskStorage.get(id);
        historyManager.addTask(subtask);
        return subtask;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epicStorage.get(id);
        historyManager.addTask(epic);
        return epic;
    }

    @Override
    public void removeTaskById(int id) {
        taskStorage.remove(id);
    }

    @Override
    public void removeSubTaskById(int id) {
        Subtask subtask = subtaskStorage.get(id);
        subtaskStorage.remove(id);
        updateEpicStatus(subtask.getEpicId());
    }

    @Override
    public void updateEpicStatus(int epicId) {
        Epic epic = epicStorage.get(epicId);


        List<Subtask> epicSubtasks = epic.getSubtasks();
        TaskStatus epicStatus = calculateEpicStatus(epicSubtasks);
        epic.setStatus(epicStatus);
    }

    @Override
    public TaskStatus calculateEpicStatus(Collection<Subtask> epicSubtasks) {
        if (epicSubtasks.isEmpty()) {
            return TaskStatus.NEW;
        }
        boolean allDone = epicSubtasks.stream()
                .allMatch(subtask -> subtask.getStatus() == TaskStatus.DONE);
        if (allDone) {
            return TaskStatus.DONE;
        }
        boolean allNew = epicSubtasks.stream()
                .allMatch(subtask -> subtask.getStatus() == TaskStatus.NEW);
        if (allNew) {
            return TaskStatus.NEW;
        }
        return TaskStatus.IN_PROGRESS;
    }

    @Override
    public void removeEpic(int id) {
        Epic epic = epicStorage.get(id);
        epicStorage.remove(id);

        List<Integer> subTasksIds = epic.getSubtasks().stream()
                .map(Task::getId).toList();

        subTasksIds.forEach(subtaskStorage::remove);
    }

    @Override
    public void removeAll() {
        taskStorage.clear();
        epicStorage.clear();
        subtaskStorage.clear();
    }

    @Override
    public Epic createEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        epicStorage.put(id, epic);
        updateEpicStatus(id);
        return epic;
    }

    @Override
    public void updateTask(Task task) {
        Task saved = taskStorage.get(task.getId());
        if (saved == null) {
            return;
        }
        taskStorage.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic saved = epicStorage.get(epic.getId());
        if (saved == null) {
            return;
        }
        epicStorage.put(epic.getId(), epic);
        updateEpicStatus(epic.getId());
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        Subtask saved = subtaskStorage.get(subtask.getId());
        if (saved == null) {
            return;
        }
        subtaskStorage.put(subtask.getId(), subtask);
        updateEpicStatus(saved.getEpicId());
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public int generateId() {
        return generatedId++;
    }
}
