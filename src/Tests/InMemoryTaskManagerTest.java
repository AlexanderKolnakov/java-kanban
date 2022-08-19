package Tests;

import manegers.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskTracker.Status;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest <InMemoryTaskManager>{


    @BeforeEach
    void setUp() throws InMemoryTaskManager.IntersectionDataException {
        taskManage = new InMemoryTaskManager();
        taskManage.addTask("Задача 1", "описание Задачи 1", Status.NEW);
        taskManage.addEpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1");
        taskManage.addSubTask("Подзадача 1 Эпик_Задачи 1", "описание Подзадачи 1",
                2, Status.NEW);
    }
    @Test
    public void shouldAddAndShowTask () {
        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals("Задача 1", taskManage.showTask(1).getName(),
                "Имя созданного Task е совпадает");
        assertNull(taskManage.showTask(111),"Показан несуществующий Task");
        assertEquals("Эпик_Задача 1", taskManage.showEpicTask(2).getName(),
                "Имя созданного EpicTask е совпадает");
        assertNull(taskManage.showEpicTask(111),"Показан несуществующий EpicTask");
        assertEquals("Подзадача 1 Эпик_Задачи 1", taskManage.showSubTask(3).getName(),
                "Имя созданного SubTask не совпадает");
        assertNull(taskManage.showSubTask(111),"Показан несуществующий SubTask");
        assertEquals("Подзадача 1 Эпик_Задачи 1",
                taskManage.showSubTaskToEpic(2).get(0).getName(),
                "Имя созданного SubTask не совпадает c именем в списке EpicTask");
        assertNull(taskManage.showSubTaskToEpic(1111),
                "Показан несуществующие SubTask в списке EpicTask");
    }

    @Test
    public void shouldAddTask () throws InMemoryTaskManager.IntersectionDataException {
        taskManage.addTask("Задача 2022.01.01", "", Status.NEW, 10,
                LocalDateTime.of(2022, Month.DECEMBER, 1, 0, 0));
        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals(4, taskManage.showAllTusk().size(), "Не корректная длинна списка задач.");
        assertEquals("Задача 2022.01.01", taskManage.showTask(4).getName(),
                "Не верная запись задачи.");
    }

    @Test
    public void shouldAddEpicTask () throws InMemoryTaskManager.IntersectionDataException {
        taskManage.addEpicTask("Задача 2022.01.01", "", 10,
                LocalDateTime.of(2022, Month.DECEMBER, 1, 0, 0));
        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals(4, taskManage.showAllTusk().size(), "Не корректная длинна списка задач.");
        assertEquals("Задача 2022.01.01", taskManage.showEpicTask(4).getName(),
                "Не верная запись задачи.");
    }
    @Test
    public void shouldAddSubTask () throws InMemoryTaskManager.IntersectionDataException {
        taskManage.addSubTask("Задача 2022.01.01", "", 2, Status.NEW, 10,
                LocalDateTime.of(2022, Month.DECEMBER, 1, 0, 0));
        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals(4, taskManage.showAllTusk().size(), "Не корректная длинна списка задач.");
        assertEquals("Задача 2022.01.01", taskManage.showSubTask(4).getName(),
                "Не верная запись задачи.");
    }

    @Test
    public void shouldShowAllTask () {
        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals(3, taskManage.showAllTusk().size(), "Не корректная длинна списка задач.");
    }

    @Test
    public void shouldDeleteAllTask () throws InMemoryTaskManager.IntersectionDataException {
        taskManage.addTask("Задача 2", "описание Задачи 2", Status.NEW);
        taskManage.deleteAllTask();

        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals(2, taskManage.showAllTusk().size(), "все Task не удалены.");
    }

    @Test
    public void shouldDeleteAllEpickTask () throws InMemoryTaskManager.IntersectionDataException {
        taskManage.addEpicTask("Эпик_Задача 2", "описание Эпик_Задачи 2");
        taskManage.deleteAllEpicTask();

        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals(1, taskManage.showAllTusk().size(), "все EpicTask и SubTask не удалены.");
    }

    @Test
    public void shouldDeleteAllSubTask () throws InMemoryTaskManager.IntersectionDataException {
        taskManage.addSubTask("Подзадача 2 Эпик_Задачи 2", "описание Подзадачи 2",
                2, Status.NEW);
        taskManage.deleteAllSubTask();
        List<String> emptyList = new ArrayList<>();

        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals(2, taskManage.showAllTusk().size(), "все SubTask не удалены");
        assertEquals(emptyList, taskManage.showSubTaskToEpic(2),
                "все SubTask не удалены в списке EpicTask");
    }

    @Test
    public void shouldDeleteTask () {
        taskManage.deleteTask(1);
        taskManage.deleteTask(1111);

        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals(2, taskManage.showAllTusk().size(), "Task не удален по ID");
    }

    @Test
    public void shouldDeleteEpicTask () {
        taskManage.deleteEpicTask(2);
        taskManage.deleteEpicTask(1111);

        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals(1, taskManage.showAllTusk().size(), "EpicTask не удален по ID");
    }

    @Test
    public void shouldDeleteSubTask () {
        taskManage.deleteSubTask(3);
        taskManage.deleteSubTask(1111);

        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals(2, taskManage.showAllTusk().size(), "SubTack не удален по ID");
    }

    @Test
    public void shouldUpdateTask () {
        taskManage.updateTask("New 1", "New 1",
                1 , Status.DONE);
        taskManage.updateTask("New 1", "New 1", 111 , Status.DONE);

        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals("New 1", taskManage.showTask(1).getName(), "Не обновлен Task");
    }
    @Test
    public void shouldUpdateTaskWithsDataTime () {
        taskManage.updateTask("New 1", "New 1",
                1 , Status.DONE, 1, LocalDateTime.now());
        taskManage.updateTask("New 1", "New 1",
                111 , Status.DONE, 1, LocalDateTime.now());

        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals("New 1", taskManage.showTask(1).getName(), "Не обновлен Task");
    }

    @Test
    public void shouldUpdateEpicTask () {
        taskManage.updateEpicTask("New 1", "New 1",
                2);
        taskManage.updateEpicTask("New 1", "New 1", 111);

        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals("New 1", taskManage.showEpicTask(2).getName(), "Не обновлен EpicTask");
    }
    @Test
    public void shouldUpdateEpicTaskWithsDataTime () {
        taskManage.updateEpicTask("New 1", "New 1",
                2,1, LocalDateTime.now());
        taskManage.updateEpicTask("New 1", "New 1", 111, 1, LocalDateTime.now());

        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals("New 1", taskManage.showEpicTask(2).getName(), "Не обновлен EpicTask");
    }

    @Test
    public void shouldUpdateSubTask () {
        taskManage.updateSubTask("New 1", "New 1",
                3, 1 , Status.DONE);
        taskManage.updateSubTask("New 1", "New 1",
                111, 1 , Status.DONE);

        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals("New 1", taskManage.showSubTask(3).getName(), "Не обновлен SubTask");
    }

    @Test
    public void shouldUpdateSubTaskWithsDataTime () {
        taskManage.updateSubTask("New 1", "New 1",
                3, 1 , Status.DONE, 1, LocalDateTime.now());
        taskManage.updateSubTask("New 1", "New 1",
                111, 1 , Status.DONE, 1, LocalDateTime.now());

        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals("New 1", taskManage.showSubTask(3).getName(), "Не обновлен SubTask");
    }

    @Test
    public void shouldTaskChangeStatus () {
        taskManage.taskChangeStatus(1, Status.DONE);
        taskManage.taskChangeStatus(111, Status.DONE);

        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals(Status.DONE, taskManage.showTask(1).getStatus(),
                "Статус Task не изменен");
    }

    @Test
    public void shouldSubTaskChangeStatusDONE () {
        taskManage.subChangeStatus(3, Status.DONE);
        taskManage.subChangeStatus(111, Status.DONE);

        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals(Status.DONE, taskManage.showSubTask(3).getStatus(),
                "Статус SubTask не изменен");
    }
    @Test
    public void shouldSubTaskChangeStatusDONEAndNew () throws InMemoryTaskManager.IntersectionDataException {
        taskManage.addSubTask("Подзадача 2 Эпик_Задачи 1", "описание Подзадачи 2",
                2, Status.NEW);
        taskManage.subChangeStatus(3, Status.DONE);
        taskManage.subChangeStatus(4, Status.NEW);

        assertNotNull(taskManage, "Список задач не пустой.");
        assertEquals(Status.IN_PROGRESS, taskManage.showEpicTask(2).getStatus(),
                "Статус SubTask не изменен");
    }


    @Test
    public void shouldEpicTaskStatusCheck () throws InMemoryTaskManager.IntersectionDataException {
        taskManage.addEpicTask("Эпик_Задача 2", "описание Эпик_Задачи 2");
        taskManage.addEpicTask("Эпик_Задача 3", "описание Эпик_Задачи 3");
        taskManage.addEpicTask("Эпик_Задача 4", "описание Эпик_Задачи 4");
        taskManage.addEpicTask("Эпик_Задача 5", "описание Эпик_Задачи 5");

        taskManage.addSubTask("Подзадача 2 Эпик_Задачи 2", "описание Подзадачи 2",
                2, Status.NEW);
        taskManage.addSubTask("Подзадача 3 Эпик_Задачи 3", "описание Подзадачи 3",
                5, Status.DONE);
        taskManage.addSubTask("Подзадача 4 Эпик_Задачи 3", "описание Подзадачи 4",
                5, Status.DONE);
        taskManage.addSubTask("Подзадача 4 Эпик_Задачи 3", "описание Подзадачи 4",
                6, Status.DONE);
        taskManage.addSubTask("Подзадача 4 Эпик_Задачи 3", "описание Подзадачи 4",
                6, Status.NEW);
        taskManage.addSubTask("Подзадача 4 Эпик_Задачи 3", "описание Подзадачи 4",
                7, Status.IN_PROGRESS);
        taskManage.addSubTask("Подзадача 4 Эпик_Задачи 3", "описание Подзадачи 4",
                7, Status.IN_PROGRESS);

        assertEquals(Status.NEW, taskManage.checkEpicStatus(4).getStatus(),
                "Статусы не совпадаю у Epic без Sub.");
        assertEquals(Status.NEW, taskManage.checkEpicStatus(2).getStatus(),
                "Статусы не совпадаю у Epic с Sub статусами New.");
        assertEquals(Status.DONE, taskManage.checkEpicStatus(5).getStatus(),
                "Статусы не совпадаю у Epic с Sub статусами DONE.");
        assertEquals(Status.IN_PROGRESS, taskManage.checkEpicStatus(6).getStatus(),
                "Статусы не совпадаю у Epic с Sub статусами DONE и NEW");
        assertEquals(Status.IN_PROGRESS, taskManage.checkEpicStatus(7).getStatus(),
                "Статусы не совпадаю у Epic с Sub статусами IN_PROGRESS.");
    }

    @Test
    public void shouldGetHistory () {
        taskManage.showTask(1);
        taskManage.showEpicTask(2);
        taskManage.showSubTask(3);
        List<Integer> history = List.of(1, 2, 3);

        assertEquals(history, taskManage.getHistory(), "Не корректная запись истории");
    }

    @Test
    public void shouldAddTaskID () throws InMemoryTaskManager.IntersectionDataException {
        taskManage.addTaskID("Задача 1", "описание задачи 1", Status.NEW, 1);
        taskManage.addEpicTaskID("Эпик_Задача 1", "описание Эпик_Задачи 1", 2);
        taskManage.addSubTaskID("Подзадача 1 Эпик_Задачи 2", "описание Подзадачи 1",
                2, Status.NEW, 3);

        assertEquals(1, taskManage.showTask(1).getTaskCode(), "Не правильное добавление Task");
        assertEquals(2, taskManage.showEpicTask(2).getTaskCode(), "Не правильное добавление Task");
        assertEquals(3, taskManage.showSubTask(3).getTaskCode(), "Не правильное добавление Task");
    }
    @Test
    public void shouldGetPrioritizedTasks () throws InMemoryTaskManager.IntersectionDataException {
        taskManage.deleteAllTask();
        taskManage.deleteAllEpicTask();
        taskManage.deleteAllSubTask();

        taskManage.addTaskID("Задача 1", "2022.01.01", Status.NEW, 1, 10,
                LocalDateTime.of(2022, Month.DECEMBER, 5, 0, 0));
        taskManage.addEpicTaskID("Эпик_Задача 1", "2022.01.02", 2, 10,
                LocalDateTime.of(2022, Month.DECEMBER, 6, 0, 0));
        taskManage.addSubTaskID("Подзадача 1 Эпик_Задачи 2", "2022.01.03",
                2, Status.NEW, 3, 10,
                LocalDateTime.of(2022, Month.DECEMBER, 3, 0, 0));

        assertEquals(3, taskManage.getPrioritizedTasks().size(), "Не корректная длинна списка");
        assertEquals("Задача 1", taskManage.getPrioritizedTasks().get(1).getName(),
                "Не корректное добавление задач в список приоретета задач");
        assertEquals("Эпик_Задача 1", taskManage.getPrioritizedTasks().get(2).getName(),
                "Не корректное добавление задач в список приоретета задач");
        assertEquals("Подзадача 1 Эпик_Задачи 2", taskManage.getPrioritizedTasks().get(0).getName(),
                "Не корректное добавление задач в список приоретета задач");
    }

//    @Test
//    public void shouldValidate() throws InMemoryTaskManager.IntersectionDataException {
//        manager.deleteAllTask();
//        manager.deleteAllEpicTask();
//        manager.deleteAllSubTask();
//
//        manager.addTaskID("Задача 1", "2022.01.01", Status.NEW, 1, 9999,
//                LocalDateTime.of(2022, Month.DECEMBER, 1, 0, 0));
//        manager.addEpicTaskID("Эпик_Задача 1", "2022.01.02", 2, 99999,
//                LocalDateTime.of(2022, Month.DECEMBER, 1, 0, 1));
//        assert;
//        assertDoesNotThrow(InMemoryTaskManager.IntersectionDataException, "Обнаружено пересечение с уже существующими задачами : \n" +
//                " новая задача началась до завершения уже текущей задачи");
//    }

}