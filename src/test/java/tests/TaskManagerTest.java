package tests;

import interfaces.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public abstract class TaskManagerTest <T extends TaskManager> {
    protected T taskManage;

    @AfterEach
    void tearDown() {
    }

    @Test
    void addTask() {
    }

    @Test
    void addEpicTask() {
    }

    @Test
    void addSubTask() {
    }

    @Test
    void showAllTusk() {
    }

    @Test
    void showTask() {
    }

    @Test
    void showEpicTask() {
    }

    @Test
    void showSubTask() {
    }

    @Test
    void addTaskID() {
    }

    @Test
    void addEpicTaskID() {
    }

    @Test
    void addSubTaskID() {
    }

    @Test
    void deleteAllTask() {
    }

    @Test
    void deleteAllEpicTask() {
    }

    @Test
    void deleteAllSubTask() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void deleteEpicTask() {
    }

    @Test
    void deleteSubTask() {
    }

    @Test
    void updateTask() {
    }

    @Test
    void updateEpicTask() {
    }

    @Test
    void updateSubTask() {
    }

    @Test
    void showSubTaskToEpic() {
    }

    @Test
    void taskChangeStatus() {
    }

    @Test
    void subChangeStatus() {
    }

    @Test
    void checkEpicStatus() {
    }

    @Test
    void getHistory() {
    }

    @Test
    void load() {
    }
}