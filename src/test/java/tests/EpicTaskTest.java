package tests;

import org.junit.jupiter.api.Test;
import taskTracker.EpicTask;
import taskTracker.Status;
import taskTracker.SubTask;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpicTaskTest {

    @Test
    public void shouldEpicTaskWithEmptySub () {
        EpicTask epicTaskOne = new EpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1", 1);

        assertEquals(Status.NEW, epicTaskOne.getStatus(), "Статус у созданного Epic без Sub не NEW");
    }

    @Test
    public void shouldEpicTaskWithAllSubNEW () {
        EpicTask epicTaskOne = new EpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1", 1);
        SubTask subTaskOne = new SubTask("Подзадача 1", "Описание подзадачи 1", 2,
                Status.NEW, 1);
        SubTask subTaskTwo = new SubTask("Подзадача 1", "Описание подзадачи 1", 2,
                Status.NEW, 1);
        epicTaskOne.addSubTask(subTaskOne);
        epicTaskOne.addSubTask(subTaskTwo);

        assertEquals(Status.NEW, epicTaskOne.getStatus(), "Статус Epic c подзадачами NEW не NEW");
    }

    @Test
    public void shouldEpicTaskWithAllSubDONE () {
        EpicTask epicTaskOne = new EpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1", 1);
        SubTask subTaskOne = new SubTask("Подзадача 1", "Описание подзадачи 1", 2,
                Status.DONE, 1);
        SubTask subTaskTwo = new SubTask("Подзадача 1", "Описание подзадачи 1", 2,
                Status.DONE, 1);
        epicTaskOne.addSubTask(subTaskOne);
        epicTaskOne.addSubTask(subTaskTwo);

        assertEquals(Status.DONE, epicTaskOne.getStatus(), "Статус Epic c подзадачами DONE не DONE");
    }

    @Test
    public void shouldEpicTaskWithSubDONEAndNEW () {
        EpicTask epicTaskOne = new EpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1", 1);
        SubTask subTaskOne = new SubTask("Подзадача 1", "Описание подзадачи 1", 2,
                Status.NEW, 1);
        SubTask subTaskTwo = new SubTask("Подзадача 1", "Описание подзадачи 1", 2,
                Status.DONE, 1);
        epicTaskOne.addSubTask(subTaskOne);
        epicTaskOne.addSubTask(subTaskTwo);

        assertEquals(Status.IN_PROGRESS, epicTaskOne.getStatus(), "Статус Epic c подзадачами DONE и NEW не IN_PROGRESS");
    }

    @Test
    public void shouldEpicTaskWithAllSubINPROGRESS () {
        EpicTask epicTaskOne = new EpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1", 1);
        SubTask subTaskOne = new SubTask("Подзадача 1", "Описание подзадачи 1", 2,
                Status.IN_PROGRESS, 1);
        SubTask subTaskTwo = new SubTask("Подзадача 1", "Описание подзадачи 1", 2,
                Status.IN_PROGRESS, 1);
        epicTaskOne.addSubTask(subTaskOne);
        epicTaskOne.addSubTask(subTaskTwo);

        assertEquals(Status.IN_PROGRESS, epicTaskOne.getStatus(), "Статус Epic c подзадачами IN_PROGRESS не IN_PROGRESS");
    }
}
