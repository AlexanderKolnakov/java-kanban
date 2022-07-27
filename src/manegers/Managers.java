package manegers;

import interfaces.HistoryManager;
import interfaces.TaskManager;

import java.io.File;

public class Managers {

    public static TaskManager getDefault(File file) {
        TaskManager manager = new FileBackedTasksManager(file);
        return manager;
    }
    public static HistoryManager getDefaultHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        return historyManager;
    }
}
