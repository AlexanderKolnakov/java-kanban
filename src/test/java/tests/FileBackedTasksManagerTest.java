package tests;

import interfaces.TaskManager;
import manegers.FileBackedTasksManager;
import manegers.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import taskTracker.EpicTask;
import taskTracker.Status;
import taskTracker.SubTask;
import taskTracker.Task;

import java.nio.file.FileSystems;


public class FileBackedTasksManagerTest extends TaskManagerTest <FileBackedTasksManager> {
//    File fileTest;
    private Task task;
    private EpicTask epicTask;
    private SubTask subTask;
    private TaskManager taskManager;

    @BeforeEach
    void setUpFileBack() throws InMemoryTaskManager.IntersectionDataException {
        String s = FileSystems.getDefault().getSeparator();
        taskManager = new FileBackedTasksManager("http://localhost:8078/register");
        task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        epicTask = new EpicTask("Эпик Задача 1", "Описание эпик задачи 1", 2);
        subTask = new SubTask("Подзадача 1", "Описание задачи 1", 3, Status.NEW, 2);
        taskManager.addTask(task);
        taskManager.addEpicTask(epicTask);
        taskManager.addSubTask(subTask);
    }
}