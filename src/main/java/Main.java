import manegers.InMemoryTaskManager;
import server.KVServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InMemoryTaskManager.IntersectionDataException, IOException {

//        TaskManager taskManager = new Managers().getDefault();
//        taskManager.load();
//
//        Task task = new Task("Задача_1", "Описание задачи 1", 1, Status.NEW,10, LocalDateTime.now());
//
//        taskManager.addTask(task);
//        taskManager.showTask(1);
//        System.out.println(task.getStartTime());
//        System.out.println("History");
//        System.out.println(taskManager.getHistory());

        new KVServer().start();


    }
}
