package finaltask.tasks.manager.file;

import finaltask.tasks.Epic;
import finaltask.tasks.Subtask;
import finaltask.tasks.Task;
import finaltask.tasks.TaskStatus;
import finaltask.tasks.manager.InMemoryTaskManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(CSVFormatHandler.getHeader());

            for (Task task : taskStorage.values()) {
                writer.write(CSVFormatHandler.toString(task));
                writer.newLine();
            }

            for (Epic epic : epicStorage.values()) {
                writer.write(CSVFormatHandler.toString(epic));
                writer.newLine();
            }

            for (Subtask subtask : subtaskStorage.values()) {
                writer.write(CSVFormatHandler.toString(subtask));
                writer.newLine();
            }
            writer.newLine();

            writer.write(CSVFormatHandler.historyToString(historyManager));
        } catch (IOException e) {
            throw new IllegalArgumentException("Не возможна запись файла");
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (lines.size() == 1) {
            return new FileBackedTasksManager(file);
        }
        FileBackedTasksManager tasksManager = new FileBackedTasksManager(file);
        for (int i = 1; i < lines.size(); i++) {
            Task task = CSVFormatHandler.fromString(lines.get(i));
            tasksManager.historyManager.addTask(task);
        }
        return tasksManager;
    }

    @Override
    public Task saveTask(Task task) {
        Task saved = super.saveTask(task);
        save();
        return saved;
    }

    @Override
    public Subtask saveSubTask(Subtask subtask) {
        save();
        return super.saveSubTask(subtask);
    }

    @Override
    public Epic saveEpic(Epic epic) {
        save();
        return super.saveEpic(epic);
    }

    @Override
    public List<Task> getAllTasks() {
        return super.getAllTasks();
    }

    @Override
    public List<Subtask> getAllSubTasks() {
        return super.getAllSubTasks();
    }

    @Override
    public List<Epic> getAllEpics() {
        return super.getAllEpics();
    }

    @Override
    public List<Subtask> getListTasksByEpic(Integer epicId) {
        return super.getListTasksByEpic(epicId);
    }

    @Override
    public List<Subtask> getSubtasksByIds(List<Integer> subTasksIds) {
        return super.getSubtasksByIds(subTasksIds);
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        return super.getSubtaskById(id);
    }

    @Override
    public Epic getEpicById(int id) {
        return super.getEpicById(id);
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
    }

    @Override
    public void removeSubTaskById(int id) {
        super.removeSubTaskById(id);
    }

    @Override
    public void updateEpicStatus(int epicId) {
        super.updateEpicStatus(epicId);
    }

    @Override
    public TaskStatus calculateEpicStatus(Collection<Subtask> epicSubtasks) {
        return super.calculateEpicStatus(epicSubtasks);
    }

    @Override
    public void removeEpic(int id) {
        super.removeEpic(id);
    }

    @Override
    public void removeAll() {
        super.removeAll();
    }

    @Override
    public Epic createEpic(Epic epic) {
        return super.createEpic(epic);
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }

    @Override
    public int generateId() {
        return super.generateId();
    }
}
