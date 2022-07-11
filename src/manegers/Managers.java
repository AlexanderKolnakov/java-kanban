package manegers;

import interfaces.HistoryManager;
import interfaces.TaskManager;

public class Managers {

    public TaskManager getDefault() {
        TaskManager manager = new InMemoryTaskManager();
        return manager;
    }
    public static HistoryManager getDefaultHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        return historyManager;
    }
}
