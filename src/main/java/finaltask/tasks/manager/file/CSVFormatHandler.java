package finaltask.tasks.manager.file;

import finaltask.tasks.Subtask;
import finaltask.tasks.Task;
import finaltask.tasks.TaskStatus;
import finaltask.tasks.manager.HistoryManager;
import finaltask.tasks.manager.TaskType;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class CSVFormatHandler {

    private static final String DELIMITER = ",";
    protected static String toString(Task task) {
        String result = task.getId() + DELIMITER +
                task.getType() + DELIMITER +
                task.getName() + DELIMITER +
                task.getStatus() + DELIMITER +
                task.getDescription() + DELIMITER +
                task.getDuration().toMillis() + DELIMITER +
                task.getStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        if (task.getType() == TaskType.SUBTASK) {
                result = result + ((Subtask)task).getEpicId();
        }
        return result;
    }

    protected static Task fromString(String value) {
        String[] columns = value.split(",");

        Duration durationInEpochMillis = Duration.ofMillis(Long.parseLong(columns[5]));

        LocalDateTime startDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(Long.parseLong(columns[6])), ZoneId.systemDefault());

        Task task = new Task(Integer.parseInt(
                columns[0]), columns[2], columns[4], durationInEpochMillis, startDateTime);

        task.setStatus(TaskStatus.valueOf(columns[3]));
        return task;
    }

    protected static String historyToString(HistoryManager manager) {
        List<String> result = new ArrayList<>();
        manager.getHistory().forEach(task -> result.add(String.valueOf(task.getId())));
        return String.join(DELIMITER, result);
    }

    protected static String getHeader() {
        return "id,type,name,status,description,epic\n";
    }

}
