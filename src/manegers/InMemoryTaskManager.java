package manegers;

import interfaces.HistoryManager;
import interfaces.TaskManager;
import taskTracker.EpicTask;
import taskTracker.Status;
import taskTracker.SubTask;
import taskTracker.Task;

import java.io.File;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    HashMap<Integer, Task> dataTask = new HashMap<>();
    int taskScore = 0;
    HashMap<Integer, EpicTask> dataEpicTask = new HashMap<>();
    HashMap<Integer, SubTask> dataSubTask = new HashMap<>();
    HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public Task addTask(String nameOfTask, String taskDescription, Status status) {
        taskScore++;
        Task task = new Task(nameOfTask, taskDescription, taskScore, status);
        dataTask.put(taskScore, task);
        return task;
    }

    @Override
    public EpicTask addEpicTask(String nameOfTask, String taskDescription) {
        taskScore++;
        EpicTask epicTask = new EpicTask(nameOfTask, taskDescription, taskScore);
        dataEpicTask.put(taskScore, epicTask);
        return epicTask;
    }

    @Override
    public SubTask addSubTask(String nameOfSubTask, String taskDescription, int codeOfEpicTask,
                              Status status) {
        taskScore++;
        SubTask subTask = new SubTask(nameOfSubTask, taskDescription, taskScore, status, codeOfEpicTask);
        if (dataEpicTask.containsKey(codeOfEpicTask)) {
            dataEpicTask.get(codeOfEpicTask).addSubTask(subTask);
        }
        dataSubTask.put(taskScore, subTask);
        if(!dataEpicTask.containsKey(codeOfEpicTask)) {return null;}
        return subTask;
    }

    @Override
    public List<String> showAllTusk() {
        List<String> tuskList = new ArrayList<>();
        for (Map.Entry<Integer, EpicTask> pair : dataEpicTask.entrySet()) {
            tuskList.add(pair.getValue().getName());
        }
        for (Map.Entry<Integer, SubTask> pair : dataSubTask.entrySet()) {
            tuskList.add(pair.getValue().getName());
        }
        for (Map.Entry<Integer, Task> pair : dataTask.entrySet()) {
            tuskList.add(pair.getValue().getName());
        }
        return tuskList;
    }

    @Override
    public Task showTask(int codeOfTask) {
        if(!dataTask.containsKey(codeOfTask)) return null;
        historyManager.addHistory(dataTask.get(codeOfTask));
        return dataTask.get(codeOfTask);
    }

    @Override
    public EpicTask showEpicTask(int codeOfTask) {
        if(!dataEpicTask.containsKey(codeOfTask)) return null;
        historyManager.addHistory(dataEpicTask.get(codeOfTask));
        return dataEpicTask.get(codeOfTask);
    }

    @Override
    public SubTask showSubTask(int codeOfTask) {
        if(!dataSubTask.containsKey(codeOfTask)) return null;
        historyManager.addHistory(dataSubTask.get(codeOfTask));
        return dataSubTask.get(codeOfTask);
    }



    @Override
    public HashMap<Integer, Task> deleteAllTask() {
        for (Map.Entry<Integer, Task> task : dataTask.entrySet()) {
            historyManager.remove(task.getValue().getTaskCode());
        }
        dataTask.clear();
        return dataTask;
    }

    @Override
    public HashMap<Integer, EpicTask> deleteAllEpicTask() {
        for (Map.Entry<Integer, EpicTask> task : dataEpicTask.entrySet()) {
            historyManager.remove(task.getValue().getTaskCode());
        }
        dataEpicTask.clear();
        deleteAllSubTask();
        return dataEpicTask;
    }

    @Override
    public HashMap<Integer, SubTask> deleteAllSubTask() {
        for (Map.Entry<Integer, SubTask> task : dataSubTask.entrySet()) {
            historyManager.remove(task.getValue().getTaskCode());
        }
        dataSubTask.clear();
        for (Map.Entry<Integer, EpicTask> pair : dataEpicTask.entrySet()) {
            pair.getValue().removeAllListOfSubTasks();
        }
        return dataSubTask;
    }

    @Override
    public HashMap<Integer, Task> deleteTask(int codeOfTask) {
        if(!dataTask.containsKey(codeOfTask)) return null;
        historyManager.remove(codeOfTask);
        dataTask.remove(codeOfTask);
        return dataTask;
    }

    @Override
    public HashMap<Integer, EpicTask> deleteEpicTask(int codeOfTask) {
        if(!dataEpicTask.containsKey(codeOfTask)) return null;
        for (Map.Entry<Integer, SubTask> pair : dataSubTask.entrySet()) {
            if (pair.getValue().getCodeOfEpicTask() == codeOfTask) {
                dataSubTask.remove(pair.getKey());
                return deleteEpicTask(codeOfTask);
            }
        }
        historyManager.remove(codeOfTask);
        dataEpicTask.remove(codeOfTask);
        return dataEpicTask;
    }

    @Override
    public HashMap<Integer, SubTask> deleteSubTask(int codeOfTask) {
        if(!dataSubTask.containsKey(codeOfTask)) return null;
        for (Map.Entry<Integer, EpicTask> pair : dataEpicTask.entrySet()) {
            for (SubTask subTask : pair.getValue().getListOfSubTasks()) {
                if (subTask.getTaskCode() == codeOfTask) {
                    pair.getValue().deleteSubTask(subTask);;
                    return deleteSubTask(codeOfTask);
                }
            }
        }
        historyManager.remove(codeOfTask);
        dataSubTask.remove(codeOfTask);
        return dataSubTask;
    }

    @Override
    public Task updateTask(String newNameOfTask, String newTaskDescription, int taskCode, Status newStatus) {
        if(!dataTask.containsKey(taskCode)) return null;
        try {
            dataTask.get(taskCode).renameTask(newNameOfTask);
            dataTask.get(taskCode).renameTaskDescription(newTaskDescription);
            dataTask.get(taskCode).changeStatus(newStatus);
        } catch (NullPointerException e) {
        }
        return dataTask.get(taskCode);
    }

    @Override
    public EpicTask updateEpicTask(String newNameOfTask, String newTaskDescription, int taskCode) {
        if(!dataEpicTask.containsKey(taskCode)) return null;
        try {
            dataEpicTask.get(taskCode).renameTask(newNameOfTask);
            dataEpicTask.get(taskCode).renameTask(newTaskDescription);
        } catch (NullPointerException e) {
        }
        return dataEpicTask.get(taskCode);
    }

    @Override
    public SubTask updateSubTask(String newNameOfSubTask, String newTaskDescription, int taskCode, int codeOfEpicTask,
                                 Status newStatus) {
        if(!dataSubTask.containsKey(taskCode)) return null;
        try {
            dataSubTask.get(taskCode).renameTask(newNameOfSubTask);
            dataSubTask.get(taskCode).renameTaskDescription(newTaskDescription);
            dataSubTask.get(taskCode).changeStatus(newStatus);
        } catch (NullPointerException e) {
        }
        return dataSubTask.get(taskCode);
    }

    @Override
    public List<SubTask> showSubTaskToEpic(int codeOfTask) {
        if(!dataEpicTask.containsKey(codeOfTask)) return null;
        List<SubTask> subTuskList = new ArrayList<>();
        for (Map.Entry<Integer, EpicTask> pair : dataEpicTask.entrySet()) {
            if (pair.getKey() == codeOfTask) {
                subTuskList = pair.getValue().getListOfSubTasks();
            }
        }
        return subTuskList;
    }

    @Override
    public Task taskChangeStatus(int codeOfTask, Status status) {
        if(!dataTask.containsKey(codeOfTask)) return null;
        try {
            dataTask.get(codeOfTask).changeStatus(status);
        } catch (NullPointerException e) {
        }
        return dataTask.get(codeOfTask);
    }

    @Override
    public SubTask subChangeStatus(int codeOfTask, Status newStatus) {
        if(!dataSubTask.containsKey(codeOfTask)) return null;
        try {
            dataSubTask.get(codeOfTask).changeStatus(newStatus);
            Set<Status> statusList = new HashSet<>();
            for (SubTask subTask : dataEpicTask.get(dataSubTask.get(codeOfTask).getCodeOfEpicTask()).getListOfSubTasks()) {
                statusList.add(subTask.getStatus());
            }
            if (statusList.size() > 1) {
                dataEpicTask.get(dataSubTask.get(codeOfTask).getCodeOfEpicTask()).changeStatus(Status.IN_PROGRESS);
            } else {
                dataEpicTask.get(dataSubTask.get(codeOfTask).getCodeOfEpicTask()).
                        changeStatus(dataSubTask.get(codeOfTask).getStatus());
            }
            statusList.clear();
        } catch (NullPointerException e) {
        }
        return dataSubTask.get(codeOfTask);
    }

    @Override
    public EpicTask checkEpicStatus(int codeOfTask) {
        if(!dataEpicTask.containsKey(codeOfTask)) return null;
        return dataEpicTask.get(codeOfTask);
    }

    @Override
    public List<Integer> getHistory() {
        List<Integer> historyList = new ArrayList<>();
        for (Task task : historyManager.getHistory()) {
            historyList.add(task.getTaskCode());
        }
        return historyList;
    }
    @Override
    public void load() {
    }
    @Override
    public Task addTaskID(String nameOfTask, String taskDescription, Status status, int taskCode) {
        Task task = new Task(nameOfTask, taskDescription, taskCode, status);
        dataTask.put(taskCode, task);
        return task;
    }

    @Override
    public EpicTask addEpicTaskID(String nameOfTask, String taskDescription, int taskCode) {
        EpicTask epicTask = new EpicTask(nameOfTask, taskDescription, taskCode);
        dataEpicTask.put(taskCode, epicTask);
        return epicTask;
    }

    @Override
    public SubTask addSubTaskID(String nameOfSubTask, String taskDescription, int codeOfEpicTask,
                                Status status, int taskCode) {
        SubTask subTask = new SubTask(nameOfSubTask, taskDescription, taskCode, status, codeOfEpicTask);
        if (dataEpicTask.containsKey(codeOfEpicTask)) {
            dataEpicTask.get(codeOfEpicTask).addSubTask(subTask);
        }
        dataSubTask.put(taskCode, subTask);
        if(!dataEpicTask.containsKey(codeOfEpicTask)) {return null;}
        return subTask;
    }
}
