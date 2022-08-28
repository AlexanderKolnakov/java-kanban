package manegers;

import adapter.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import interfaces.HistoryManager;
import interfaces.TaskManager;
import server.KVServer;

import java.io.IOException;
import java.time.LocalDateTime;

public class Managers {

    public static TaskManager getDefault() throws IOException, InterruptedException {
//        TaskManager manager = new FileBackedTasksManager(new File("memoryTask.csv"));
        TaskManager manager = new HTTPTaskManager("http://localhost:8079/register");
        return manager;
    }

    public static HistoryManager getDefaultHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        return historyManager;
    }

    public static Gson getGsons() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        return gsonBuilder.create();
    }
}
