import interfaces.TaskManager;
import manegers.InMemoryTaskManager;
import manegers.Managers;
import server.KVServer;
import taskTracker.EpicTask;
import taskTracker.Status;
import taskTracker.SubTask;
import taskTracker.Task;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InMemoryTaskManager.IntersectionDataException, IOException, InterruptedException {

        KVServer server = new KVServer();
        server.start();
        TaskManager taskManage = Managers.getDefault();

        Task task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        EpicTask epicTask = new EpicTask("Эпик Задача 1", "Описание эпик задачи 1", 2);
        SubTask subTask = new SubTask("Подзадача 1", "Описание задачи 1", 3, Status.NEW, 2);
        taskManage.addTask(task);
        taskManage.addEpicTask(epicTask);
        taskManage.addSubTask(subTask);


        taskManage.showTask(1);
        taskManage.showEpicTask(2);


    }
}
