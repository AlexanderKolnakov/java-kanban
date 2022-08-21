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
    String HISTORY_IS_EMPTY = "Пустая история";
    String HISTORY_SIZE_NOT_CORRECT = "История не корректного размера.";
    String HISTORY_ORDER_NOT_CORRECT = "Не корректный порядок истории";

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

        assertNotNull(history, HISTORY_IS_EMPTY);
        assertEquals(1, history.size(), HISTORY_SIZE_NOT_CORRECT);
    }

    @Test
    void getHistory() {
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, HISTORY_IS_EMPTY);
        assertTrue(history.isEmpty(), "Пустая история задач.");
    }

    @Test
    void addTwice() {
        historyManager.addHistory(task);
        historyManager.addHistory(task);
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, HISTORY_IS_EMPTY);
        assertEquals(1, history.size(), HISTORY_SIZE_NOT_CORRECT);
    }

    @Test
    void removeFirst() {
        addHistoryTaskEpicTaskSubTask();
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, HISTORY_IS_EMPTY);
        assertEquals(3, history.size(), HISTORY_SIZE_NOT_CORRECT);

        historyManager.remove(task.getTaskCode());
        history = historyManager.getHistory();

        assertNotNull(history, HISTORY_IS_EMPTY);
        assertEquals(2, history.size(), HISTORY_SIZE_NOT_CORRECT);
        assertEquals(epicTask, history.get(0), HISTORY_ORDER_NOT_CORRECT);
        assertEquals(subTask, history.get(1), HISTORY_ORDER_NOT_CORRECT);
    }

    @Test
    void removeMiddle() {
        addHistoryTaskEpicTaskSubTask();
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, HISTORY_IS_EMPTY);
        assertEquals(3, history.size(), HISTORY_SIZE_NOT_CORRECT);

        historyManager.remove(epicTask.getTaskCode());
        history = historyManager.getHistory();

        assertNotNull(history, HISTORY_IS_EMPTY);
        assertEquals(2, history.size(), HISTORY_SIZE_NOT_CORRECT);
        assertEquals(task, history.get(0), HISTORY_ORDER_NOT_CORRECT);
        assertEquals(subTask, history.get(1), HISTORY_ORDER_NOT_CORRECT);
    }

    @Test
    void removeLast() {
        addHistoryTaskEpicTaskSubTask();
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, HISTORY_IS_EMPTY);
        assertEquals(3, history.size(), HISTORY_SIZE_NOT_CORRECT);

        historyManager.remove(subTask.getTaskCode());
        history = historyManager.getHistory();

        assertNotNull(history, HISTORY_IS_EMPTY);
        assertEquals(2, history.size(), HISTORY_SIZE_NOT_CORRECT);
        assertEquals(task, history.get(0), HISTORY_ORDER_NOT_CORRECT);
        assertEquals(epicTask, history.get(1), HISTORY_ORDER_NOT_CORRECT);
    }

    @Test
    void removeEmptyHistory() {
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, HISTORY_IS_EMPTY);
        assertEquals(0, history.size(), HISTORY_SIZE_NOT_CORRECT);

        historyManager.remove(epicTask.getTaskCode());
        history = historyManager.getHistory();

        assertNotNull(history, HISTORY_IS_EMPTY);
        assertEquals(0, history.size(), HISTORY_SIZE_NOT_CORRECT);
    }
    private void addHistoryTaskEpicTaskSubTask() {
        historyManager.addHistory(task);
        historyManager.addHistory(epicTask);
        historyManager.addHistory(subTask);
    }
}