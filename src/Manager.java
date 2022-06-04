import java.util.*;

public class Manager {
    HashMap<Integer, EpicTask> dataEpicTask = new HashMap<>();
    HashMap<Integer, SubTask> dataSubTask = new HashMap<>();

    public EpicTask addEpicTask(String nameOfTask, String taskDescription, int taskCode) {
        EpicTask task = new EpicTask(nameOfTask, taskDescription, taskCode);
        dataEpicTask.put(taskCode, task);
        return task;
    }

    public SubTask addSubTask(String nameOfSubTask, String taskDescription, int taskCode, int codeOfEpicTask, String status) {
        SubTask subTask = new SubTask(nameOfSubTask, taskDescription, taskCode, codeOfEpicTask, status);
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
        return tuskList;
    }

    public EpicTask showEpicTask(int codeOfTask) {
        return dataEpicTask.get(codeOfTask);
    }

    public SubTask showSubTask(int codeOfTask) {
        return dataSubTask.get(codeOfTask);
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

    public HashMap<Integer, EpicTask> deleteEpicTask(int codeOfTask) {
        for (Map.Entry<Integer, SubTask> pair : dataSubTask.entrySet()) {
            if (pair.getValue().codeOfEpicTask == codeOfTask) {
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

    public EpicTask updateEpicTask(String nameOfTask, String taskDescription, int taskCode) {
        dataEpicTask.remove(taskCode);
        return addEpicTask(nameOfTask, taskDescription, taskCode);
    }

    public SubTask updateSubTask(String nameOfSubTask, String taskDescription, int taskCode, int codeOfEpicTask, String status) {
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

    public String subChangeStatus(int codeOfTask, String newStatus) {
        dataSubTask.get(codeOfTask).status = newStatus;

        Set<String> statusList = new HashSet<>();
        Set<String> statusListNew = new HashSet<>(Collections.singleton("NEW"));
        Set<String> statusListDONE = new HashSet<>(Collections.singleton("DONE"));

        for (SubTask subTask : dataEpicTask.get(dataSubTask.get(codeOfTask).codeOfEpicTask).listOfSubTasks) {
            statusList.add(subTask.status);
        }

        if (statusList.equals(statusListNew)) {
            dataEpicTask.get(dataSubTask.get(codeOfTask).codeOfEpicTask).status = "NEW";
        } else if (statusList.equals(statusListDONE)) {
            dataEpicTask.get(dataSubTask.get(codeOfTask).codeOfEpicTask).status = "DONE";
        } else {
            dataEpicTask.get(dataSubTask.get(codeOfTask).codeOfEpicTask).status = "IN_PROGRESS";
        }
        statusList.clear();
        return dataSubTask.get(codeOfTask).status;
    }

    public String checkEpicStatus(int codeOfTask) {
        return dataEpicTask.get(codeOfTask).status;
    }
}
