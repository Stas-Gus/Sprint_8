package finaltask.tasks.manager;

import finaltask.tasks.manager.file.FileBackedTasksManager;

import java.io.File;

public final class Managers {

    private Managers() {
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getFileDefault(File file) {
        return FileBackedTasksManager.loadFromFile(file);
    }

    public static HistoryManager getHistoryDefault() {
        return new InMemoryHistoryManager();
    }
}