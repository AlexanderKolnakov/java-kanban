package manegers;

import interfaces.HistoryManager;
import interfaces.TaskManager;
import taskTracker.EpicTask;
import taskTracker.Status;
import taskTracker.SubTask;
import taskTracker.Task;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    HashMap<Integer, Task> dataTask = new HashMap<>();
    int taskScore = 1;
    HashMap<Integer, EpicTask> dataEpicTask = new HashMap<>();
    HashMap<Integer, SubTask> dataSubTask = new HashMap<>();
    HistoryManager historyManager = Managers.getDefaultHistory();
    TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));

    @Override
    public Task addTask(Task task) throws IntersectionDataException {
        if (task.getTaskCode() != 0) {
            dataTask.put(task.getTaskCode(), task);
        } else { dataTask.put(taskScore, task);}
        taskScore++;
        validate(task);
        return task;
    }

    @Override
    public Task addSubTask(SubTask subTask) throws IntersectionDataException {
        if(!dataEpicTask.containsKey(subTask.getCodeOfEpicTask())) {return null;}
        if (subTask.getTaskCode() != 0) {
            dataSubTask.put(subTask.getTaskCode(), subTask);
        } else { dataSubTask.put(taskScore, subTask); }
        taskScore++;
        validate(subTask);
        if (dataEpicTask.containsKey(subTask.getCodeOfEpicTask())) {
            dataEpicTask.get(subTask.getCodeOfEpicTask()).addSubTask(subTask);
            updateEpicDurationAndStartTime(subTask.getCodeOfEpicTask());
        }
        return subTask;
    }

    @Override
    public Task addEpicTask(EpicTask epicTask) throws IntersectionDataException {
        if (epicTask.getTaskCode() != 0) {
            dataEpicTask.put(epicTask.getTaskCode(), epicTask);
        } else { dataEpicTask.put(taskScore, epicTask);}
        taskScore++;
        validate(epicTask);
        return epicTask;
    }

    public Task addTask(String nameOfTask, String taskDescription, Status status) throws IntersectionDataException {
        Task task = new Task(nameOfTask, taskDescription, taskScore, status);
        addTask(task);
        return task;
    }


    public Task addTask (String nameOfTask, String taskDescription, Status status,
                         long duration, LocalDateTime startTime) throws IntersectionDataException {
        Task task = new Task(nameOfTask, taskDescription, taskScore, status, duration, startTime);
        addTask(task);
        return task;
    }


    public EpicTask addEpicTask(String nameOfTask, String taskDescription) throws IntersectionDataException {
        EpicTask epicTask = new EpicTask(nameOfTask, taskDescription, taskScore);
        addEpicTask(epicTask);
        return epicTask;
    }


    public EpicTask addEpicTask (String nameOfTask, String taskDescription, long duration, LocalDateTime startTime) throws IntersectionDataException {
        EpicTask epicTask = new EpicTask(nameOfTask, taskDescription, taskScore, duration, startTime);
        addEpicTask(epicTask);
        return epicTask;
    }


    public SubTask addSubTask(String nameOfSubTask, String taskDescription, int codeOfEpicTask,
                              Status status) throws IntersectionDataException {
        SubTask subTask = new SubTask(nameOfSubTask, taskDescription, taskScore, status, codeOfEpicTask);
        addSubTask(subTask);
        return subTask;
    }


    public SubTask addSubTask(String nameOfSubTask, String taskDescription, int codeOfEpicTask,
                              Status status, long duration, LocalDateTime startTime) throws IntersectionDataException {
        SubTask subTask = new SubTask(nameOfSubTask, taskDescription, taskScore, status, codeOfEpicTask,
                duration, startTime);
        addSubTask(subTask);
        return subTask;
    }
    @Override
    public void putTaskInData(Integer ID, Task task) throws IntersectionDataException {
        dataTask.put(ID, task);
        taskScore++;
        validate(task);
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
    public List<Task> getTasks() {
        List<Task> tuskList = new ArrayList<>();
        for (Map.Entry<Integer, EpicTask> pair : dataEpicTask.entrySet()) {
            tuskList.add(pair.getValue());
        }
        for (Map.Entry<Integer, SubTask> pair : dataSubTask.entrySet()) {
            tuskList.add(pair.getValue());
        }
        for (Map.Entry<Integer, Task> pair : dataTask.entrySet()) {
            tuskList.add(pair.getValue());
        }
        return tuskList;
    }

    public List<Task> getTask() {
        return new ArrayList<>(dataTask.values());
    }
    public List<Task> getSubTask() {
        return new ArrayList<>(dataSubTask.values());
    }
    public List<Task> getEpicTask() {
        return new ArrayList<>(dataEpicTask.values());
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
            prioritizedTasks.remove(task.getValue());
        }
        dataTask.clear();
        return dataTask;
    }

    @Override
    public HashMap<Integer, EpicTask> deleteAllEpicTask() {
        for (Map.Entry<Integer, EpicTask> task : dataEpicTask.entrySet()) {
            historyManager.remove(task.getValue().getTaskCode());
            prioritizedTasks.remove(task.getValue());
        }
        dataEpicTask.clear();
        deleteAllSubTask();
        return dataEpicTask;
    }

    @Override
    public HashMap<Integer, SubTask> deleteAllSubTask() {
        for (Map.Entry<Integer, SubTask> task : dataSubTask.entrySet()) {
            historyManager.remove(task.getValue().getTaskCode());
            prioritizedTasks.remove(task.getValue());
        }
        dataSubTask.clear();
        for (Map.Entry<Integer, EpicTask> pair : dataEpicTask.entrySet()) {
            pair.getValue().removeAllListOfSubTasks();
            updateEpicDurationAndStartTime(pair.getKey());
        }
        return dataSubTask;
    }

    @Override
    public HashMap<Integer, Task> deleteTask(int codeOfTask) {
        if(!dataTask.containsKey(codeOfTask)) return null;
        historyManager.remove(codeOfTask);
        prioritizedTasks.remove(dataTask.get(codeOfTask));
        dataTask.remove(codeOfTask);
        return dataTask;
    }

    @Override
    public HashMap<Integer, EpicTask> deleteEpicTask(int codeOfTask) {
        if(!dataEpicTask.containsKey(codeOfTask)) return null;
        for (Map.Entry<Integer, SubTask> pair : dataSubTask.entrySet()) {
            if (pair.getValue().getCodeOfEpicTask() == codeOfTask) {
                prioritizedTasks.remove(pair.getValue());
                dataSubTask.remove(pair.getKey());
                return deleteEpicTask(codeOfTask);
            }
        }
        historyManager.remove(codeOfTask);
        prioritizedTasks.remove(dataEpicTask.get(codeOfTask));
        dataEpicTask.remove(codeOfTask);
        return dataEpicTask;
    }

    @Override
    public HashMap<Integer, SubTask> deleteSubTask(int codeOfTask) {
        if(!dataSubTask.containsKey(codeOfTask)) return null;
        for (Map.Entry<Integer, EpicTask> pair : dataEpicTask.entrySet()) {
            for (SubTask subTask : pair.getValue().getListOfSubTasks()) {
                if (subTask.getTaskCode() == codeOfTask) {
                    pair.getValue().deleteSubTask(subTask);
                    updateEpicDurationAndStartTime(pair.getKey());
                    return deleteSubTask(codeOfTask);
                }
            }
        }
        historyManager.remove(codeOfTask);
        prioritizedTasks.remove(dataSubTask.get(codeOfTask));
        dataSubTask.remove(codeOfTask);
        return dataSubTask;
    }

    @Override
    public Task updateTask(String newNameOfTask, String newTaskDescription, int taskCode, Status newStatus) {
        if(!dataTask.containsKey(taskCode)) {return null;}
        dataTask.get(taskCode).renameTask(newNameOfTask);
        dataTask.get(taskCode).renameTaskDescription(newTaskDescription);
        dataTask.get(taskCode).changeStatus(newStatus);
        return dataTask.get(taskCode);
    }

    @Override
    public Task updateTask (String newNameOfTask, String newTaskDescription, int taskCode, Status newStatus,
                            long duration, LocalDateTime startTime) throws IntersectionDataException {
        if(!dataTask.containsKey(taskCode)) {return null;}
        updateTask(newNameOfTask, newTaskDescription, taskCode, newStatus);
        dataTask.get(taskCode).setDuration(duration);
        dataTask.get(taskCode).setStartTime(startTime);
        validate(dataTask.get(taskCode));
        return dataTask.get(taskCode);
    }

    @Override
    public Task updateTask(Task task) {
        if(!dataTask.containsKey(task.getTaskCode())) {return null;}
        return dataTask.put(task.getTaskCode(), task);
    }

    @Override
    public EpicTask updateEpicTask(String newNameOfTask, String newTaskDescription, int taskCode) {
        if(!dataEpicTask.containsKey(taskCode)) {return null;}
        dataEpicTask.get(taskCode).renameTask(newNameOfTask);
        dataEpicTask.get(taskCode).renameTask(newTaskDescription);
        return dataEpicTask.get(taskCode);
    }

    @Override
    public EpicTask updateEpicTask(String newNameOfTask, String newTaskDescription, int taskCode,
                                   long duration, LocalDateTime startTime) throws IntersectionDataException {
        if(!dataEpicTask.containsKey(taskCode)) {return null;}
        updateEpicTask(newNameOfTask, newTaskDescription, taskCode);
        dataEpicTask.get(taskCode).setDuration(duration);
        dataEpicTask.get(taskCode).setStartTime(startTime);
        validate(dataEpicTask.get(taskCode));
        return dataEpicTask.get(taskCode);
    }

    @Override
    public EpicTask updateEpicTask(EpicTask epicTask) {
        if(!dataEpicTask.containsKey(epicTask.getTaskCode())) {return null;}
        return dataEpicTask.put(epicTask.getTaskCode(), epicTask);
    }

    @Override
    public SubTask updateSubTask(String newNameOfSubTask, String newTaskDescription, int taskCode, int codeOfEpicTask,
                                 Status newStatus) {
        if(!dataSubTask.containsKey(taskCode)) {return null;}
            dataSubTask.get(taskCode).renameTask(newNameOfSubTask);
            dataSubTask.get(taskCode).renameTaskDescription(newTaskDescription);
            dataSubTask.get(taskCode).changeStatus(newStatus);
        return dataSubTask.get(taskCode);
    }

    @Override
    public SubTask updateSubTask(String newNameOfSubTask, String newTaskDescription, int taskCode, int codeOfEpicTask,
                                 Status newStatus, long duration, LocalDateTime startTime) throws IntersectionDataException {
        if(!dataSubTask.containsKey(taskCode)) {return null;}
        updateSubTask(newNameOfSubTask, newTaskDescription, taskCode, codeOfEpicTask, newStatus);
        dataSubTask.get(taskCode).setDuration(duration);
        dataSubTask.get(taskCode).setStartTime(startTime);
        updateEpicDurationAndStartTime(codeOfEpicTask );
        validate(dataSubTask.get(taskCode));
        return dataSubTask.get(taskCode);
    }

    @Override
    public SubTask updateSubTask(SubTask subTask) {
        if(!dataSubTask.containsKey(subTask.getTaskCode())) {return null;}
        updateEpicDurationAndStartTime(subTask.getCodeOfEpicTask());
        return dataSubTask.put(subTask.getTaskCode(), subTask);
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
        if(!dataTask.containsKey(codeOfTask)) {return null;}
        dataTask.get(codeOfTask).changeStatus(status);
        return dataTask.get(codeOfTask);
    }

    @Override
    public SubTask subChangeStatus(int codeOfTask, Status newStatus) {
        if(!dataSubTask.containsKey(codeOfTask)) {return null;}
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

    public Task addTaskID(String nameOfTask, String taskDescription, Status status, int taskCode) throws IntersectionDataException {
        Task task = new Task(nameOfTask, taskDescription, taskCode, status);
        addTask(task);
        return task;
    }

    public Task addTaskID(String nameOfTask, String taskDescription, Status status, int taskCode,
                          long duration, LocalDateTime startTime) throws IntersectionDataException {
        Task task = new Task(nameOfTask, taskDescription, taskCode, status, duration, startTime);
        addTask(task);
        return task;
    }

    public EpicTask addEpicTaskID(String nameOfTask, String taskDescription, int taskCode) throws IntersectionDataException {
        EpicTask epicTask = new EpicTask(nameOfTask, taskDescription, taskCode);
        addEpicTask(epicTask);
        return epicTask;
    }

    public EpicTask addEpicTaskID(String nameOfTask, String taskDescription, int taskCode,
                                  long duration, LocalDateTime startTime) throws IntersectionDataException {
        EpicTask epicTask = new EpicTask(nameOfTask, taskDescription, taskCode, duration, startTime);
        addEpicTask(epicTask);
        return epicTask;
    }

    public SubTask addSubTaskID(String nameOfSubTask, String taskDescription, int codeOfEpicTask,
                                Status status, int taskCode) throws IntersectionDataException {
        SubTask subTask = new SubTask(nameOfSubTask, taskDescription, taskCode, status, codeOfEpicTask);
        addSubTask(subTask);
        return subTask;
    }

    public SubTask addSubTaskID(String nameOfSubTask, String taskDescription, int codeOfEpicTask,
                                Status status, int taskCode, long duration, LocalDateTime startTime) throws IntersectionDataException {
        SubTask subTask = new SubTask(nameOfSubTask, taskDescription, taskCode, status, codeOfEpicTask,
                duration, startTime);
        addSubTask(subTask);
        return subTask;
    }

    private void updateEpicDurationAndStartTime(int epicTaskID) {
        EpicTask epicTask = dataEpicTask.get(epicTaskID);
        ArrayList<SubTask> subList = epicTask.getListOfSubTasks();
        if (subList.isEmpty()) {
            epicTask.setDuration(0L);
            return;
        }
        LocalDateTime startEpicTask = LocalDateTime.MAX;
        LocalDateTime endEpicTask = LocalDateTime.MIN;
        long duration = 0L;

        for (SubTask subTask : subList) {
            LocalDateTime startTime = subTask.getStartTime();
            LocalDateTime endTime = subTask.getEndTime();
            if (startTime.isBefore(startEpicTask)) {
                startEpicTask = startTime;
            }
            if (endTime.isAfter(endEpicTask)) {
                endEpicTask = endTime;
            }
            duration += subTask.getDuration();
        }
        epicTask.setDuration(duration);
        epicTask.setStartTime(startEpicTask);
        epicTask.setEndTime(endEpicTask);
    }


    @Override
    public ArrayList<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    @Override
    public void load() throws IntersectionDataException {

    }

    public void validate(Task addTask) throws IntersectionDataException {
        for (Task task : prioritizedTasks) {
            if (addTask.getStartTime().isBefore(task.getStartTime()) && addTask.getEndTime().isAfter(task.getStartTime())) {
                throw new IntersectionDataException("Обнаружено пересечение с уже существующими задачами : \n " +
                        "новая задача началась раньше существующей и заканчивается позже начала существующей"); // исключение что новая задача заканчивается позже начала текущей задачи
            }
            if (addTask.getStartTime().isAfter(task.getStartTime()) && addTask.getStartTime().isBefore(task.getEndTime())) {
                throw new IntersectionDataException("Обнаружено пересечение с уже существующими задачами : \n " +
                        "новая задача началась до завершения уже текущей задачи"); // искл пересечение задач
            }
        }
        prioritizedTasks.add(addTask);
    }
    public static class IntersectionDataException extends Exception {
        public IntersectionDataException(String message) {
            super(message);
        }
    }


}
