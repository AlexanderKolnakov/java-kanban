import java.util.*;

public class Manager {
    HashMap<Integer, Task> dataTask = new HashMap<>();
    HashMap<Integer, EpicTask> dataEpicTask = new HashMap<>();
    HashMap<Integer, SubTask> dataSubTask = new HashMap<>();

    public Task addTask(String nameOfTask, String taskDescription, int taskCode, String status) {
        Task task = new Task(nameOfTask, taskDescription, taskCode, status);
        dataTask.put(taskCode, task);
        return task;
    }

    public EpicTask addEpicTask(String nameOfTask, String taskDescription, int taskCode) {
        EpicTask epicTask = new EpicTask(nameOfTask, taskDescription, taskCode);
        dataEpicTask.put(taskCode, epicTask);
        return epicTask;
    }

    public SubTask addSubTask(String nameOfSubTask, String taskDescription, int taskCode, int codeOfEpicTask,
                              String status) {
        SubTask subTask = new SubTask(nameOfSubTask, taskDescription, taskCode, status, codeOfEpicTask);
        if (dataEpicTask.containsKey(codeOfEpicTask)) {
            dataEpicTask.get(codeOfEpicTask).addSubTask(subTask);
        }
        dataSubTask.put(taskCode, subTask);
        return subTask;
    }

    public List<String> showAllTusk() {
        List<String> tuskList = new ArrayList<>();
        for (Map.Entry<Integer, EpicTask> pair : dataEpicTask.entrySet()) {
            tuskList.add(pair.getValue().nameOfTask);
        }
        for (Map.Entry<Integer, SubTask> pair : dataSubTask.entrySet()) {
            tuskList.add(pair.getValue().nameOfTask);
        }
        for (Map.Entry<Integer, Task> pair : dataTask.entrySet()) {
            tuskList.add(pair.getValue().nameOfTask);
        }
        return tuskList;
    }

    public Task showTask(int codeOfTask) {
        return dataTask.get(codeOfTask);
    }

    public EpicTask showEpicTask(int codeOfTask) {
        return dataEpicTask.get(codeOfTask);
    }

    public SubTask showSubTask(int codeOfTask) {
        return dataSubTask.get(codeOfTask);
    }

    public HashMap<Integer, Task> deleteAllTask() {
        dataTask.clear();
        return dataTask;
    }

    public HashMap<Integer, EpicTask> deleteAllEpicTask() {
        dataEpicTask.clear();
        return dataEpicTask;
    }

    public HashMap<Integer, SubTask> deleteAllSubTask() {
        dataSubTask.clear();
        for (Map.Entry<Integer, EpicTask> pair : dataEpicTask.entrySet()) {
            pair.getValue().listOfSubTasks.clear();
        }
        return dataSubTask;
    }

    public HashMap<Integer, Task> deleteTask(int codeOfTask) {
        dataTask.remove(codeOfTask);
        return dataTask;
    }

    public HashMap<Integer, EpicTask> deleteEpicTask(int codeOfTask) {
        for (Map.Entry<Integer, SubTask> pair : dataSubTask.entrySet()) {
            if (pair.getValue().getCodeOfEpicTask() == codeOfTask) {
                dataSubTask.remove(pair.getKey());
                return deleteEpicTask(codeOfTask);
            }
        }
        dataEpicTask.remove(codeOfTask);
        return dataEpicTask;
    }

    public HashMap<Integer, SubTask> deleteSubTask(int codeOfTask) {
        for (Map.Entry<Integer, EpicTask> pair : dataEpicTask.entrySet()) {
            for (SubTask subTask : pair.getValue().listOfSubTasks) {
                if (subTask.taskCode == codeOfTask) {
                    pair.getValue().listOfSubTasks.remove(subTask);
                    return deleteSubTask(codeOfTask);
                }
            }
        }
        dataSubTask.remove(codeOfTask);
        return dataSubTask;
    }

    public Task updateTask(String nameOfTask, String taskDescription, int taskCode, String status) {
        dataTask.remove(taskCode);
        return addTask(nameOfTask, taskDescription, taskCode, status);
    }

    public EpicTask updateEpicTask(String nameOfTask, String taskDescription, int taskCode) {
        dataEpicTask.remove(taskCode);
        return addEpicTask(nameOfTask, taskDescription, taskCode);
    }

    public SubTask updateSubTask(String nameOfSubTask, String taskDescription, int taskCode, int codeOfEpicTask,
                                 String status) {
        if (dataEpicTask.containsKey(codeOfEpicTask)) {
            dataEpicTask.get(codeOfEpicTask).deleteSubTask(dataSubTask.get(taskCode));
        }
        dataSubTask.remove(taskCode);
        return addSubTask(nameOfSubTask, taskDescription, taskCode, codeOfEpicTask, status);
    }

    public List<SubTask> showSubTaskToEpic(int codeOfTask) {
        List<SubTask> subTuskList = new ArrayList<>();
        for (Map.Entry<Integer, EpicTask> pair : dataEpicTask.entrySet()) {
            if (pair.getKey() == codeOfTask) {
                subTuskList = pair.getValue().listOfSubTasks;
            }
        }
        return subTuskList;
    }

    public String taskChangeStatus(int codeOfTask, String status) {
        return dataTask.get(codeOfTask).status = status;
    }

    public String subChangeStatus(int codeOfTask, String newStatus) {
        dataSubTask.get(codeOfTask).status = newStatus;
        Set<String> statusList = new HashSet<>();
        Set<String> statusListNew = new HashSet<>(Collections.singleton("NEW"));
        Set<String> statusListDONE = new HashSet<>(Collections.singleton("DONE"));

        for (SubTask subTask : dataEpicTask.get(dataSubTask.get(codeOfTask).getCodeOfEpicTask()).listOfSubTasks) {
            statusList.add(subTask.status);
        }
        if (statusList.equals(statusListNew)) {
            dataEpicTask.get(dataSubTask.get(codeOfTask).getCodeOfEpicTask()).status = "NEW";
        } else if (statusList.equals(statusListDONE)) {
            dataEpicTask.get(dataSubTask.get(codeOfTask).getCodeOfEpicTask()).status = "DONE";
        } else {
            dataEpicTask.get(dataSubTask.get(codeOfTask).getCodeOfEpicTask()).status = "IN_PROGRESS";
        }
        statusList.clear();
        return dataSubTask.get(codeOfTask).status;
    }

    public String checkEpicStatus(int codeOfTask) {
        return dataEpicTask.get(codeOfTask).status;
    }
}
