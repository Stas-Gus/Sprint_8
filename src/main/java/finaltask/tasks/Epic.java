package finaltask.tasks;

import finaltask.tasks.manager.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

public class Epic extends Task {
    private final ArrayList<Subtask> tasks;

    public Epic(String name, String description, ArrayList<Subtask> tasks) {
        super(name, description);
        this.tasks = tasks;
        this.type = TaskType.EPIC;
    }

    public ArrayList<Subtask> getSubtasks() {
        return tasks;
    }

    @Override
    public Duration getDuration() {
        return Duration.between(getStartTime(), getEndTime());
    }

    @Override
    public LocalDateTime getStartTime() {
        return tasks.stream()
                .min(Comparator.comparing(Task::getStartTime))
                .orElseThrow(IllegalStateException::new)
                .getStartTime();
    }

    @Override
    public LocalDateTime getEndTime() {
        return tasks.stream()
                .max(Comparator.comparing(Task::getEndTime))
                .orElseThrow(IllegalStateException::new)
                .getStartTime();

    }
}
