package interfaces;

import taskTracker.EpicTask;
import taskTracker.Status;
import taskTracker.SubTask;
import taskTracker.Task;

import java.util.HashMap;
import java.util.List;

public interface TaskManager {
    Task addTask(String nameOfTask, String taskDescription, Status status);

    EpicTask addEpicTask(String nameOfTask, String taskDescription);

    SubTask addSubTask(String nameOfSubTask, String taskDescription, int codeOfEpicTask, Status status);

    List<String> showAllTusk();

    Task showTask(int codeOfTask);

    EpicTask showEpicTask(int codeOfTask);

    SubTask showSubTask(int codeOfTask);

    HashMap<Integer, Task> deleteAllTask();

    HashMap<Integer, EpicTask> deleteAllEpicTask();

    HashMap<Integer, SubTask> deleteAllSubTask();

    HashMap<Integer, Task> deleteTask(int codeOfTask);

    HashMap<Integer, EpicTask> deleteEpicTask(int codeOfTask);

    HashMap<Integer, SubTask> deleteSubTask(int codeOfTask);

    Task updateTask(String newNameOfTask, String newTaskDescription, int taskCode, Status newStatus);

    EpicTask updateEpicTask(String newNameOfTask, String newTaskDescription, int taskCode);

    SubTask updateSubTask(String newNameOfSubTask, String newTaskDescription, int taskCode, int codeOfEpicTask,
                          Status newStatus);

    List<SubTask> showSubTaskToEpic(int codeOfTask);

    Task taskChangeStatus(int codeOfTask, Status status);

    SubTask subChangeStatus(int codeOfTask, Status newStatus);

    EpicTask checkEpicStatus(int codeOfTask);

    List<Task> getHistory();
}
