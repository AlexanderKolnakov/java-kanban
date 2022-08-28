package manegers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.KVServer;
import taskTracker.EpicTask;
import taskTracker.Status;
import taskTracker.SubTask;
import taskTracker.Task;
import tests.TaskManagerTest;

import java.io.IOException;

class HTTPTaskManagerTest extends TaskManagerTest<HTTPTaskManager> {
    private KVServer kvServer;
    private Task task;
    private EpicTask epicTask;
    private SubTask subTask;

    @BeforeEach
    void setUp() throws IOException, InMemoryTaskManager.IntersectionDataException {
        taskManage = new HTTPTaskManager(KVServer.PORT);
        kvServer = Managers.getDefaultKVServer();

        task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        epicTask = new EpicTask("Эпик Задача 1", "Описание эпик задачи 1", 2);
        subTask = new SubTask("Подзадача 1", "Описание задачи 1", 3, Status.NEW, 2);
        taskManage.addTask(task);
        taskManage.addEpicTask(epicTask);
        taskManage.addSubTask(subTask);
        kvServer.start();
    }

    @AfterEach
    void tearDown() {
        kvServer.stop();
    }

    @Test
    void load() {
        taskManage.showTask(task.getTaskCode());


    }

}