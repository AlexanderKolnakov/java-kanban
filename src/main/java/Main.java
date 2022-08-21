import interfaces.TaskManager;
import manegers.InMemoryTaskManager;
import manegers.Managers;
import taskTracker.Status;
import taskTracker.Task;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws InMemoryTaskManager.IntersectionDataException {

        TaskManager taskManager = new Managers().getDefault();
        taskManager.load();

        Task task = new Task("Задача_1", "Описание задачи 1", 1, Status.NEW,10, LocalDateTime.now());

        taskManager.addTask(task);
        taskManager.showTask(1);
        System.out.println(task.getStartTime());
        System.out.println("History");
        System.out.println(taskManager.getHistory());
    }
}
