package finaltask.tasks.manager;

import java.io.File;
import java.io.IOException;

public class TaskManagerUtils {
    public static void testHistoryLoading() {
        try {
            TaskManager taskManager = Managers.getFileDefault(
                    File.createTempFile("history", ".csv"));
            taskManager.getHistory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
