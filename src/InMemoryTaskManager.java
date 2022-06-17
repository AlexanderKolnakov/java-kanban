import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    HashMap<Integer, Task> dataTask = new HashMap<>();
    private int taskScore = 0;
    HashMap<Integer, EpicTask> dataEpicTask = new HashMap<>();
    private int epicTaskScore = 0;
    HashMap<Integer, SubTask> dataSubTask = new HashMap<>();
    private int subTaskScore = 0;

    @Override
    public Task addTask(String nameOfTask, String taskDescription, Status status) {
        taskScore++;
        Task task = new Task(nameOfTask, taskDescription, taskScore, status);
        dataTask.put(taskScore, task);
        return task;
    }
    @Override
    public EpicTask addEpicTask(String nameOfTask, String taskDescription) {
        epicTaskScore++;
        EpicTask epicTask = new EpicTask(nameOfTask, taskDescription, epicTaskScore);
        dataEpicTask.put(epicTaskScore, epicTask);
        return epicTask;
    }
    @Override
    public SubTask addSubTask(String nameOfSubTask, String taskDescription, int codeOfEpicTask,
                              Status status) {
        subTaskScore++;
        SubTask subTask = new SubTask(nameOfSubTask, taskDescription, subTaskScore, status, codeOfEpicTask);
        if (dataEpicTask.containsKey(codeOfEpicTask)) {
            dataEpicTask.get(codeOfEpicTask).addSubTask(subTask);
        }
        dataSubTask.put(subTaskScore, subTask);
        return subTask;
    }
    @Override
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
    @Override
    public Task showTask(int codeOfTask) {
        Managers.getDefaultHistory().addHistory(dataTask.get(codeOfTask));
        return dataTask.get(codeOfTask);
    }
    @Override
    public EpicTask showEpicTask(int codeOfTask) {
        Managers.getDefaultHistory().addHistory(dataEpicTask.get(codeOfTask));
        return dataEpicTask.get(codeOfTask);
    }
    @Override
    public SubTask showSubTask(int codeOfTask) {
        Managers.getDefaultHistory().addHistory(dataSubTask.get(codeOfTask));
        return dataSubTask.get(codeOfTask);
    }
    @Override
    public HashMap<Integer, Task> deleteAllTask() {
        dataTask.clear();
        return dataTask;
    }
    @Override
    public HashMap<Integer, EpicTask> deleteAllEpicTask() {
        dataEpicTask.clear();
        return dataEpicTask;
    }
    @Override
    public HashMap<Integer, SubTask> deleteAllSubTask() {
        dataSubTask.clear();
        for (Map.Entry<Integer, EpicTask> pair : dataEpicTask.entrySet()) {
            pair.getValue().listOfSubTasks.clear();
        }
        return dataSubTask;
    }
    @Override
    public HashMap<Integer, Task> deleteTask(int codeOfTask) {
        dataTask.remove(codeOfTask);
        return dataTask;
    }
    @Override
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
    @Override
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
    @Override
    public Task updateTask(String newNameOfTask, String newTaskDescription, int taskCode, Status newStatus) {
        dataTask.get(taskCode).nameOfTask = newNameOfTask;
        dataTask.get(taskCode).taskDescription = newTaskDescription;
        dataTask.get(taskCode).status = newStatus;
        return dataTask.get(taskCode);
    }
    @Override
    public EpicTask updateEpicTask(String newNameOfTask, String newTaskDescription, int taskCode) {
        dataEpicTask.get(taskCode).nameOfTask = newNameOfTask;
        dataEpicTask.get(taskCode).taskDescription = newTaskDescription;
        return dataEpicTask.get(taskCode);
    }
    @Override
    public SubTask updateSubTask(String newNameOfSubTask, String newTaskDescription, int taskCode, int codeOfEpicTask,
                                 Status newStatus) {
        dataSubTask.get(taskCode).nameOfTask = newNameOfSubTask;
        dataSubTask.get(taskCode).taskDescription = newTaskDescription;
        dataSubTask.get(taskCode).status = newStatus;
        if (dataEpicTask.containsKey(codeOfEpicTask)) {
            dataEpicTask.get(codeOfEpicTask).deleteSubTask(dataSubTask.get(taskCode));
            dataEpicTask.get(codeOfEpicTask).listOfSubTasks.add(dataSubTask.get(taskCode));
        }
        return dataSubTask.get(taskCode);
    }
    @Override
    public List<SubTask> showSubTaskToEpic(int codeOfTask) {
        List<SubTask> subTuskList = new ArrayList<>();
        for (Map.Entry<Integer, EpicTask> pair : dataEpicTask.entrySet()) {
            if (pair.getKey() == codeOfTask) {
                subTuskList = pair.getValue().listOfSubTasks;
            }
        }
        return subTuskList;
    }
    @Override
    public Status taskChangeStatus(int codeOfTask, Status status) {
        return dataTask.get(codeOfTask).status = status;
    }
    @Override
    public Status subChangeStatus(int codeOfTask, Status newStatus) {
        dataSubTask.get(codeOfTask).status = newStatus;
        Set<Status> statusList = new HashSet<>();
        for (SubTask subTask : dataEpicTask.get(dataSubTask.get(codeOfTask).getCodeOfEpicTask()).listOfSubTasks) {
            statusList.add(subTask.status);
        }
        if (statusList.size() > 1) {
            dataEpicTask.get(dataSubTask.get(codeOfTask).getCodeOfEpicTask()).status = Status.IN_PROGRESS;
        } else {
            dataEpicTask.get(dataSubTask.get(codeOfTask).getCodeOfEpicTask()).status = dataSubTask.get(codeOfTask).status;
        }
        statusList.clear();
        return dataSubTask.get(codeOfTask).status;
    }
    @Override
    public Status checkEpicStatus(int codeOfTask) {
        return dataEpicTask.get(codeOfTask).status;
    }
    @Override
    public List<Task> getHistory() {
        return Managers.getDefaultHistory().getHistory();
    }
}
