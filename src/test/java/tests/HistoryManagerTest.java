package tests;

import interfaces.HistoryManager;
import manegers.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskTracker.EpicTask;
import taskTracker.Status;
import taskTracker.SubTask;
import taskTracker.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    HistoryManager historyManager;
    private Task task;
    private EpicTask epicTask;
    private SubTask subTask;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();

        task = new Task("Задача 1", "описание Задачи 1", 1, Status.NEW);
        epicTask = new EpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1", 2);
        subTask = new SubTask("Подзадача 1 Эпик_Задачи 1", "описание Подзадачи 1",
                3, Status.NEW, 2);
    }

    @Test
    void add() {
        historyManager.addHistory(task);
        final List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не корректного размера.");
    }

    @Test
    void getHistory() {
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не пустая.");
        assertTrue(history.isEmpty(), "Пустая история задач.");
    }

    @Test
    void addTwice() {
        historyManager.addHistory(task);
        historyManager.addHistory(task);
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не корректного размера.");
    }

    @Test
    void removeFirst() {
        historyManager.addHistory(task);
        historyManager.addHistory(epicTask);
        historyManager.addHistory(subTask);
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не пустая.");
        assertEquals(3, history.size(), "История не корректного размера.");

        historyManager.remove(task.getTaskCode());
        history = historyManager.getHistory();

        assertNotNull(history, "История не пустая.");
        assertEquals(2, history.size(), "История не корректного размера.");
        assertEquals(epicTask, history.get(0), "Не корректынй порядок истории");
        assertEquals(subTask, history.get(1), "Не корректынй порядок истории");
    }

    @Test
    void removeMiddle() {
        historyManager.addHistory(task);
        historyManager.addHistory(epicTask);
        historyManager.addHistory(subTask);
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не пустая.");
        assertEquals(3, history.size(), "История не корректного размера.");

        historyManager.remove(epicTask.getTaskCode());
        history = historyManager.getHistory();

        assertNotNull(history, "История не пустая.");
        assertEquals(2, history.size(), "История не корректного размера.");
        assertEquals(task, history.get(0), "Не корректынй порядок истории");
        assertEquals(subTask, history.get(1), "Не корректынй порядок истории");
    }

    @Test
    void removeLast() {
        historyManager.addHistory(task);
        historyManager.addHistory(epicTask);
        historyManager.addHistory(subTask);
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не пустая.");
        assertEquals(3, history.size(), "История не корректного размера.");

        historyManager.remove(subTask.getTaskCode());
        history = historyManager.getHistory();

        assertNotNull(history, "История не пустая.");
        assertEquals(2, history.size(), "История не корректного размера.");
        assertEquals(task, history.get(0), "Не корректынй порядок истории");
        assertEquals(epicTask, history.get(1), "Не корректынй порядок истории");
    }

    @Test
    void removeEmptyHistory() {
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не пустая.");
        assertEquals(0, history.size(), "История не корректного размера.");

        historyManager.remove(epicTask.getTaskCode());
        history = historyManager.getHistory();

        assertNotNull(history, "История не пустая.");
        assertEquals(0, history.size(), "История не корректного размера.");
    }
}