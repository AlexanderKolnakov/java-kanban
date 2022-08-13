package manegers;

import interfaces.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import taskTracker.Status;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class InMemoryTaskManagerTest {
    File file = new File("memoryTask.csv");
    TaskManager manager = new Managers().getDefault(file);
    @Test
    public void shouldAddAndShowTask () {
        manager.addTask("Задача 1", "описание Задачи 1", Status.NEW);
        manager.addEpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1");
        manager.addSubTask("Подзадача 1 Эпик_Задачи 1", "описание Подзадачи 1",
                2, Status.NEW);

        Assertions.assertEquals("Задача 1", manager.showTask(1).getName(),
                "Имя созданного Task е совпадает");
        Assertions.assertNull(manager.showTask(111),"Показан несуществующий Task");
        Assertions.assertEquals("Эпик_Задача 1", manager.showEpicTask(2).getName(),
                "Имя созданного EpicTask е совпадает");
        Assertions.assertNull(manager.showEpicTask(111),"Показан несуществующий EpicTask");
        Assertions.assertEquals("Подзадача 1 Эпик_Задачи 1", manager.showSubTask(3).getName(),
                "Имя созданного SubTask не совпадает");
        Assertions.assertNull(manager.showSubTask(111),"Показан несуществующий SubTask");
        Assertions.assertEquals("Подзадача 1 Эпик_Задачи 1",
                manager.showSubTaskToEpic(2).get(0).getName(),
                "Имя созданного SubTask не совпадает c именем в списке EpicTask");
        Assertions.assertNull(manager.showSubTaskToEpic(1111),
                "Показан несуществующие SubTask в списке EpicTask");
    }

    @Test
    public void shouldShowAllTask () {
        manager.addTask("Задача 1", "описание Задачи 1", Status.NEW);
        manager.addEpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1");
        manager.addSubTask("Подзадача 1 Эпик_Задачи 1", "описание Подзадачи 1",
                2, Status.NEW);

        List<String> listOfTaskName = List.of("Эпик_Задача 1", "Подзадача 1 Эпик_Задачи 1", "Задача 1");

        Assertions.assertEquals(listOfTaskName, manager.showAllTusk(), "Не совпадает вывод всех задач");
    }

    @Test
    public void shouldDeleteAllTask () {
        manager.addTask("Задача 1", "описание Задачи 1", Status.NEW);
        manager.addTask("Задача 2", "описание Задачи 2", Status.DONE);
        manager.addEpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1");
        manager.addSubTask("Подзадача 1 Эпик_Задачи 1", "описание Подзадачи 1",
                2, Status.NEW);

        manager.deleteAllTask();
        List<String> listOfTaskName = List.of("Эпик_Задача 1", "Подзадача 1 Эпик_Задачи 1");

        Assertions.assertEquals(listOfTaskName, manager.showAllTusk(), "все Task не удалены");
    }

    @Test
    public void shouldDeleteAllEpickTask () {
        manager.addTask("Задача 1", "описание Задачи 1", Status.NEW);
        manager.addEpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1");
        manager.addEpicTask("Эпик_Задача 2", "описание Эпик_Задачи 2");
        manager.addSubTask("Подзадача 1 Эпик_Задачи 1", "описание Подзадачи 1",
                2, Status.NEW);
        manager.addSubTask("Подзадача 2 Эпик_Задачи 2", "описание Подзадачи 2",
                3, Status.NEW);

        manager.deleteAllEpicTask();
        List<String> listOfTaskName = List.of("Задача 1");

        Assertions.assertEquals(listOfTaskName, manager.showAllTusk(), "все EpicTask и SubTask не удалены");
    }

    @Test
    public void shouldDeleteAllSubTask () {
        manager.addTask("Задача 1", "описание Задачи 1", Status.NEW);
        manager.addEpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1");
        manager.addEpicTask("Эпик_Задача 2", "описание Эпик_Задачи 2");
        manager.addSubTask("Подзадача 1 Эпик_Задачи 1", "описание Подзадачи 1",
                2, Status.NEW);
        manager.addSubTask("Подзадача 2 Эпик_Задачи 2", "описание Подзадачи 2",
                3, Status.NEW);

        manager.deleteAllSubTask();
        List<String> listOfTaskName = List.of("Эпик_Задача 1", "Эпик_Задача 2", "Задача 1");
        List<String> emptyList = new ArrayList<>();

        Assertions.assertEquals(listOfTaskName, manager.showAllTusk(), "все SubTask не удалены");
        Assertions.assertEquals(emptyList, manager.showSubTaskToEpic(2),
                "все SubTask не удалены в списке EpicTask");
    }

    @Test
    public void shouldDeleteTask () {
        manager.addTask("Задача 1", "описание Задачи 1", Status.NEW);
        HashMap emptyMap = new HashMap();

        Assertions.assertEquals(emptyMap, manager.deleteTask(1), "Task не удален по ID");
        Assertions.assertNull(manager.deleteTask(1111), "удален Task по несуществующему ID");
    }

    @Test
    public void shouldDeleteEpicTask () {
        manager.addEpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1");
        manager.addSubTask("Подзадача 1 Эпик_Задачи 1", "описание Подзадачи 1",
                1, Status.NEW);
        HashMap emptyMap = new HashMap();

        Assertions.assertEquals(emptyMap, manager.deleteEpicTask(1), "EpicTask не удален по ID");
        Assertions.assertNull(manager.deleteEpicTask(1111), "удален EpicTask по несуществующему ID");
    }

    @Test
    public void shouldDeleteSubTask () {
        manager.addEpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1");
        manager.addSubTask("Подзадача 1 Эпик_Задачи 1", "описание Подзадачи 1",
                1, Status.NEW);
        HashMap emptyMap = new HashMap();

        Assertions.assertEquals(emptyMap, manager.deleteSubTask(2), "SubTack не удален по ID");
        Assertions.assertNull(manager.deleteSubTask(1111), "удален SubTack по несуществующему ID");
    }

    @Test
    public void shouldUpdateTask () {
        manager.addTask("Задача 1", "описание Задачи 1", Status.NEW);

        Assertions.assertEquals("New 1", manager.updateTask("New 1", "New 1",
                1 , Status.DONE).getName(), "Не обновлен Task");
        Assertions.assertNull(manager.updateTask("New 1", "New 1", 111 , Status.DONE)
                , "Обновление не существующего Task");
    }

    @Test
    public void shouldUpdateEpicTask () {
        manager.addEpicTask("Задача 1", "описание Задачи 1");

        Assertions.assertEquals("New 1", manager.updateEpicTask("New 1", "New 1",
                1).getName(), "Не обновлен EpicTask");
        Assertions.assertNull(manager.updateEpicTask("New 1", "New 1", 111),
                "Обновление не существующего EpicTask");
    }

    @Test
    public void shouldUpdateSubTask () {
        manager.addEpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1");
        manager.addSubTask("Подзадача 1 Эпик_Задачи 1", "описание Подзадачи 1",
                1, Status.NEW);

        Assertions.assertEquals("New 1", manager.updateSubTask("New 1", "New 1",
                2, 1 , Status.DONE).getName(), "Не обновлен SubTask");
        Assertions.assertNull(manager.updateEpicTask("New 1", "New 1", 111),
                "Обновление не существующего SubTask");
    }

    @Test
    public void shouldTaskChangeStatus () {
        manager.addTask("Задача 1", "описание Задачи 1", Status.NEW);

        Assertions.assertEquals(Status.DONE, manager.taskChangeStatus(1, Status.DONE).getStatus(),
                "Статус Task не изменен");
        Assertions.assertNull(manager.taskChangeStatus(111, Status.DONE),
                "Изменение статуса у не существующего Task");
    }

    @Test
    public void shouldSubTaskChangeStatus () {
        manager.addEpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1");
        manager.addSubTask("Подзадача 1 Эпик_Задачи 1", "описание Подзадачи 1",
                1, Status.NEW);

        Assertions.assertEquals(Status.DONE, manager.subChangeStatus(2, Status.DONE).getStatus(),
                "Статус SubTask не изменен");
        Assertions.assertNull(manager.subChangeStatus(111, Status.DONE),
                "Изменение статуса у не существующего SubTask");
    }

    @Test
    public void shouldEpicTaskStatusCheck () {
        manager.addEpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1");
        manager.addEpicTask("Эпик_Задача 2", "описание Эпик_Задачи 2");
        manager.addEpicTask("Эпик_Задача 3", "описание Эпик_Задачи 3");
        manager.addEpicTask("Эпик_Задача 4", "описание Эпик_Задачи 4");
        manager.addEpicTask("Эпик_Задача 5", "описание Эпик_Задачи 5");

        manager.addSubTask("Подзадача 1 Эпик_Задачи 2", "описание Подзадачи 1",
                2, Status.NEW);
        manager.addSubTask("Подзадача 2 Эпик_Задачи 2", "описание Подзадачи 2",
                2, Status.NEW);
        manager.addSubTask("Подзадача 3 Эпик_Задачи 3", "описание Подзадачи 3",
                3, Status.DONE);
        manager.addSubTask("Подзадача 4 Эпик_Задачи 3", "описание Подзадачи 4",
                3, Status.DONE);
        manager.addSubTask("Подзадача 4 Эпик_Задачи 3", "описание Подзадачи 4",
                4, Status.DONE);
        manager.addSubTask("Подзадача 4 Эпик_Задачи 3", "описание Подзадачи 4",
                4, Status.NEW);
        manager.addSubTask("Подзадача 4 Эпик_Задачи 3", "описание Подзадачи 4",
                5, Status.IN_PROGRESS);
        manager.addSubTask("Подзадача 4 Эпик_Задачи 3", "описание Подзадачи 4",
                5, Status.IN_PROGRESS);

        Assertions.assertEquals(Status.NEW, manager.checkEpicStatus(1).getStatus(),
                "Статусы не совпадаю у Epic без Sub.");
        Assertions.assertEquals(Status.NEW, manager.checkEpicStatus(2).getStatus(),
                "Статусы не совпадаю у Epic с Sub статусами New.");
        Assertions.assertEquals(Status.DONE, manager.checkEpicStatus(3).getStatus(),
                "Статусы не совпадаю у Epic с Sub статусами DONE.");
        Assertions.assertEquals(Status.IN_PROGRESS, manager.checkEpicStatus(4).getStatus(),
                "Статусы не совпадаю у Epic с Sub статусами DONE и NEW");
        Assertions.assertEquals(Status.IN_PROGRESS, manager.checkEpicStatus(5).getStatus(),
                "Статусы не совпадаю у Epic с Sub статусами IN_PROGRESS.");
    }

    @Test
    public void shouldGetHistory () {
        manager.addTask("Задача 1", "описание задачи 1", Status.NEW);
        manager.addEpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1");
        manager.addSubTask("Подзадача 1 Эпик_Задачи 2", "описание Подзадачи 1",
                2, Status.NEW);
        manager.showTask(1);
        manager.showEpicTask(2);
        manager.showSubTask(3);
        List<Integer> history = List.of(1, 2, 3);

        Assertions.assertEquals(history, manager.getHistory());
        System.out.println();
    }

    @Test
    public void shouldAddTaskID () {
        manager.addTaskID("Задача 1", "описание задачи 1", Status.NEW, 1);
        manager.addEpicTaskID("Эпик_Задача 1", "описание Эпик_Задачи 1", 2);
        manager.addSubTaskID("Подзадача 1 Эпик_Задачи 2", "описание Подзадачи 1",
                2, Status.NEW, 3);

        Assertions.assertEquals(1, manager.showTask(1).getTaskCode());
        Assertions.assertEquals(2, manager.showEpicTask(2).getTaskCode());
        Assertions.assertEquals(3, manager.showSubTask(3).getTaskCode());
    }


}