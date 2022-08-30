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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class HttpTaskManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    private KVServer server;
    private Task task;
    private EpicTask epicTask;
    private SubTask subTask;
    @BeforeEach
    private void setUp() throws IOException, InterruptedException, InMemoryTaskManager.IntersectionDataException {
        server = new KVServer();
        server.start();
        taskManage = new HTTPTaskManager("http://localhost:8078/register");
        task = new Task("Задача 1", "Описание задачи 1", 1, Status.NEW);
        epicTask = new EpicTask("Эпик Задача 1", "Описание эпик задачи 1", 2);
        subTask = new SubTask("Подзадача 1", "Описание задачи 1", 3, Status.NEW, 2);
        taskManage.addTask(task);
        taskManage.addEpicTask(epicTask);
        taskManage.addSubTask(subTask);

    }

    @AfterEach
    private void tearDown() {
        server.stop();
    }

    @Test
    void load() throws InMemoryTaskManager.IntersectionDataException {
        List<Task> tasks = taskManage.getTasks();
        assertNotNull(tasks, "Задачи не добавлена");
        assertEquals(3, tasks.size(), "Задачи не добавлена");
        assertEquals(2, tasks.get(0).getTaskCode(), "Задача не добавлена");
    }

    @Test
    void loadEmptyHistory() {
        List<Integer> tasks = taskManage.getHistory();
        assertNotNull(tasks, "Задачи не добавлена");
        assertEquals(0, tasks.size(), "Список задач не корректного размера");
    }

    @Test
    void loadHistory() {
        taskManage.showTask(1);
        taskManage.showSubTask(3);
        List<Integer> tasks = taskManage.getHistory();
        assertNotNull(tasks, "Задачи не добавлена");
        assertEquals(2, tasks.size(), "Список задач не корректного размера");
    }

    @Test
    void deleteTasks() {
        taskManage.deleteAllTask();
        taskManage.deleteAllSubTask();
        taskManage.deleteAllEpicTask();
        List<Task> tasks = taskManage.getTask();
        List<Task> subTasks = taskManage.getSubTask();
        List<Task> epicTasks = taskManage.getEpicTask();

        assertEquals(0, tasks.size(), "Задача не удалена");
        assertEquals(0, subTasks.size(), "Подзадача не удалена");
        assertEquals(0, epicTasks.size(), "Эпик задача не удалена");
    }
    @Test
    void updateTasks() {
        taskManage.updateTask("Новое имя задачи 1", "", 1, Status.DONE);
        taskManage.updateSubTask("Новое имя подзадачи 3",
                "", 3, 2, Status.DONE);
        taskManage.updateEpicTask("Новое имя Эпик задачи 2", "Новое имя Эпик задачи 2", 2);

        assertEquals("Новое имя задачи 1", taskManage.showTask(1).getName(), "Задача не обновлена");
        assertEquals("Новое имя Эпик задачи 2", taskManage.showEpicTask(2).getName(), "Подзадача не обновлена");
        assertEquals("Новое имя подзадачи 3", taskManage.showSubTask(3).getName(),"Эпик задача не обновлена");
    }

    @Test
    void addTaskWhitAlreadyExistID() throws InMemoryTaskManager.IntersectionDataException {
        taskManage.addTaskID("Новое имя задачи 1", "", Status.DONE, 1);
        taskManage.addSubTaskID("Новое имя подзадачи 3",
                "", 2,  Status.DONE, 3);
        taskManage.addEpicTaskID("Новое имя Эпик задачи 2", "", 2);
        List<Task> tasks = taskManage.getTask();
        List<Task> subTasks = taskManage.getSubTask();
        List<Task> epicTasks = taskManage.getEpicTask();

        assertEquals(1, tasks.size(), "Добавлена лишняя задача");
        assertEquals(1, subTasks.size(), "Добавлена лишняя подзадача");
        assertEquals(1, epicTasks.size(), "Добавлена лишняя эпик задача");
    }
}
