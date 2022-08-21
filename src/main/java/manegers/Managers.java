package manegers;

import interfaces.HistoryManager;
import interfaces.TaskManager;

import java.io.File;

public class Managers {

    public static TaskManager getDefault() {
        TaskManager manager = new FileBackedTasksManager(new File("memoryTask.csv"));
        return manager;
    }

    public static HistoryManager getDefaultHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        return historyManager;
    }
}
