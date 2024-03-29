package interfaces;

import manegers.InMemoryTaskManager;
import taskTracker.EpicTask;
import taskTracker.Status;
import taskTracker.SubTask;
import taskTracker.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {
    Task addTask(Task task) throws InMemoryTaskManager.IntersectionDataException;
    Task addSubTask(SubTask subTask) throws InMemoryTaskManager.IntersectionDataException;
    Task addEpicTask(EpicTask epicTask) throws InMemoryTaskManager.IntersectionDataException;

    void putTaskInData(Integer ID, Task task) throws InMemoryTaskManager.IntersectionDataException;

    List<String> showAllTusk();

    Task showTask(int codeOfTask);

    EpicTask showEpicTask(int codeOfTask);

    SubTask showSubTask(int codeOfTask);

    Task addTaskID(String nameOfTask, String taskDescription, Status status, int taskCode) throws InMemoryTaskManager.IntersectionDataException;

    EpicTask addEpicTaskID(String nameOfTask, String taskDescription, int taskCode) throws InMemoryTaskManager.IntersectionDataException;

    SubTask addSubTaskID(String nameOfSubTask, String taskDescription, int codeOfEpicTask,
                         Status status, int taskCode) throws InMemoryTaskManager.IntersectionDataException;

    HashMap<Integer, Task> deleteAllTask();

    HashMap<Integer, EpicTask> deleteAllEpicTask();

    HashMap<Integer, SubTask> deleteAllSubTask();

    HashMap<Integer, Task> deleteTask(int codeOfTask);

    HashMap<Integer, EpicTask> deleteEpicTask(int codeOfTask);

    HashMap<Integer, SubTask> deleteSubTask(int codeOfTask);

    Task updateTask(String newNameOfTask, String newTaskDescription, int taskCode, Status newStatus);
    Task updateTask (String newNameOfTask, String newTaskDescription, int taskCode, Status newStatus,
                     long duration, LocalDateTime startTime) throws InMemoryTaskManager.IntersectionDataException;
    Task updateTask (Task task);
    EpicTask updateEpicTask(String newNameOfTask, String newTaskDescription, int taskCode);
    EpicTask updateEpicTask(String newNameOfTask, String newTaskDescription, int taskCode,
                            long duration, LocalDateTime startTime) throws InMemoryTaskManager.IntersectionDataException;
    EpicTask updateEpicTask(EpicTask epicTask);
    SubTask updateSubTask(String newNameOfSubTask, String newTaskDescription, int taskCode, int codeOfEpicTask,
                          Status newStatus);
    SubTask updateSubTask(String newNameOfSubTask, String newTaskDescription, int taskCode, int codeOfEpicTask,
                          Status newStatus, long duration, LocalDateTime startTime) throws InMemoryTaskManager.IntersectionDataException;
    SubTask updateSubTask(SubTask subTask);

    List<SubTask> showSubTaskToEpic(int codeOfTask);

    Task taskChangeStatus(int codeOfTask, Status status);

    SubTask subChangeStatus(int codeOfTask, Status newStatus);

    EpicTask checkEpicStatus(int codeOfTask);

    List<Integer> getHistory();
    ArrayList<Task> getPrioritizedTasks();

    void load() throws InMemoryTaskManager.IntersectionDataException;
    List<Task> getTasks();


}
