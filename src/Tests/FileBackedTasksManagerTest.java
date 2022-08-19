package Tests;

import manegers.FileBackedTasksManager;
import manegers.InMemoryTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import taskTracker.Status;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends TaskManagerTest <FileBackedTasksManager> {
    File fileTest;


    @BeforeEach
    void setUpFileBack() throws InMemoryTaskManager.IntersectionDataException {
        fileTest = new File("memoryTaskTest.csv");
        taskManage = new FileBackedTasksManager(fileTest);
        taskManage.addTaskID("Задача 1", "описание Задачи 1", Status.NEW, 1);
        taskManage.addEpicTaskID("Эпик_Задача 1", "описание Эпик_Задачи 1", 2);
        taskManage.addSubTaskID("Подзадача 1 Эпик_Задачи 1", "описание Подзадачи 1",
                2, Status.NEW, 3);
    }


    @AfterEach
    void tearDown() {
        assertTrue(fileTest.delete());
    }

    @Disabled
    @Test
    void save() {

    }

    @Test
    void shouldLoadFromFile() throws InMemoryTaskManager.IntersectionDataException {
        taskManage.showTask(1);

        taskManage.loadFromFile(fileTest);
        List<String> lt = taskManage.showAllTusk();


        assertNotNull(lt, "Список задач не пустой.");
        assertEquals(3, lt.size(), "Не корректная длинна списка задач.");
        assertEquals("Эпик_Задача 1", lt.get(0), "Не корректная длинна списка задач.");
    }
}
