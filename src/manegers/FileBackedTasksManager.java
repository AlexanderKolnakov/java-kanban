package manegers;
import interfaces.HistoryManager;
import interfaces.TaskManager;
import taskTracker.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public String taskToString(Task task) {
        String codeOfEpicTask = "";
        if (task.getType() == TypeOfTask.SUBTASK) {
            SubTask taskType = (SubTask) task;
            codeOfEpicTask = Integer.toString(taskType.getCodeOfEpicTask());
        }
        return task.getTaskCode() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + "," +
                task.getTaskDescription() + "," + codeOfEpicTask;
    }  // преобразование задачи в строку

    public void save() {
        try (Writer fileWriter = new FileWriter(file)) {
            fileWriter.write("id,type,name,status,description,epic" + "\n");
            for (Task task : dataTask.values()) {
                fileWriter.write(taskToString(task) + "\n");
            }
            for (EpicTask task : dataEpicTask.values()) {
                fileWriter.write(taskToString(task) + "\n");
            }
            for (SubTask task : dataSubTask.values()) {
                fileWriter.write(taskToString(task) + "\n");
            }
            fileWriter.write("" + "\n");
            fileWriter.write(historyToString(historyManager));
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время записи файла.");
        }
    }

    private Task fromString(String value) {
        String[] taskString = value.split(",");
        switch (taskString[1]) {
            case "TASK":
                return addTaskID(taskString[2], taskString[4], checkStatus(taskString[3]),
                        Integer.parseInt(taskString[0]));
            case "EPIC":
                return addEpicTaskID(taskString[2], taskString[4], Integer.parseInt(taskString[0]));
            case "SUBTASK":
                return addSubTaskID(taskString[2], taskString[4], Integer.parseInt(taskString[5]),
                        checkStatus(taskString[3]), Integer.parseInt(taskString[0]));
        }
        return null;
    }   // создание задачи из строки

    private static Status checkStatus(String statusString) {
        Status status = Status.NEW;
        switch (statusString) {
            case "IN_PROGRESS":
                status = Status.IN_PROGRESS;
                break;
            case "DONE":
                status = Status.DONE;
                break;
        }
        return status;
    }

    public FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        try (Reader fileReader = new FileReader(file)) {
            BufferedReader br = new BufferedReader(fileReader);
            while (br.ready()) {
                String line = br.readLine();
                if (line.isEmpty()) {
                    String historyLine = br.readLine();
                    historyFromString(historyLine);
                    break;
                }
                fromString(line);
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время чтения файла.");
        }
        return fileBackedTasksManager;
    }

    private static String historyToString(HistoryManager manager) {
        List<Task> history = manager.getHistory();
        int count = 0;
        StringBuilder numberHistory = new StringBuilder();
        for (Task task : history) {
            numberHistory.append(task.getTaskCode());
            count++;
            if (!(history.size() == count)) {
                numberHistory.append(",");
            }
        }
        numberHistory.append("\n");
        return numberHistory.toString();
    }  // сохранение истории в строку

    void historyFromString(String value) {
        if (!(value == null)) {
            List<Integer> integerList = new ArrayList<>();
            String[] historyString = value.split(",");
            for (String s : historyString) {
                integerList.add(Integer.valueOf(s));
                if (dataTask.containsKey(Integer.valueOf(s))) {
                    showTask(Integer.parseInt(s));
                }
                if (dataEpicTask.containsKey(Integer.valueOf(s))) {
                    showEpicTask(Integer.parseInt(s));
                }
                if (dataSubTask.containsKey(Integer.valueOf(s))) {
                    showSubTask(Integer.parseInt(s));
                }
            }
        }
    }  // восстановление истории из строки

    @Override
    public Task addTaskID(String nameOfTask, String taskDescription, Status status, int taskCode) {
        Task task = new Task(nameOfTask, taskDescription, taskCode, status);
        dataTask.put(taskCode, task);
        taskScore ++;
        save();
        return task;
    }

    @Override
    public EpicTask addEpicTaskID(String nameOfTask, String taskDescription, int taskCode) {
        EpicTask epicTask = new EpicTask(nameOfTask, taskDescription, taskCode);
        dataEpicTask.put(taskCode, epicTask);
        taskScore ++;
        save();
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
        taskScore ++;
        save();
        return subTask;
    }

    @Override
    public HashMap<Integer, Task> deleteAllTask() {
        HashMap<Integer, Task> hashMap = super.deleteAllTask();
        save();
        return hashMap;
    }

    @Override
    public HashMap<Integer, EpicTask> deleteAllEpicTask() {
        HashMap<Integer, EpicTask> hashMap = super.deleteAllEpicTask();
        save();
        return hashMap;
    }

    @Override
    public HashMap<Integer, SubTask> deleteAllSubTask() {
        HashMap<Integer, SubTask> hashMap = super.deleteAllSubTask();
        save();
        return hashMap;
    }

    @Override
    public HashMap<Integer, Task> deleteTask(int codeOfTask) {
        HashMap<Integer, Task> hashMap = super.deleteTask(codeOfTask);
        save();
        return hashMap;
    }

    @Override
    public HashMap<Integer, EpicTask> deleteEpicTask(int codeOfTask) {
        HashMap<Integer, EpicTask> hashMap = super.deleteEpicTask(codeOfTask);
        save();
        return hashMap;
    }

    @Override
    public HashMap<Integer, SubTask> deleteSubTask(int codeOfTask) {
        HashMap<Integer, SubTask> hashMap = super.deleteSubTask(codeOfTask);
        save();
        return hashMap;
    }

    @Override
    public Task updateTask(String newNameOfTask, String newTaskDescription, int taskCode, Status newStatus) {
        Task task = super.updateTask(newNameOfTask, newTaskDescription, taskCode, newStatus);
        save();
        return task;
    }

    @Override
    public EpicTask updateEpicTask(String newNameOfTask, String newTaskDescription, int taskCode) {
        EpicTask epicTask = super.updateEpicTask(newNameOfTask, newTaskDescription, taskCode);
        save();
        return epicTask;
    }

    @Override
    public SubTask updateSubTask(String newNameOfSubTask, String newTaskDescription, int taskCode,
                                 int codeOfEpicTask, Status newStatus) {
        SubTask subTask = super.updateSubTask(newNameOfSubTask, newTaskDescription, taskCode, codeOfEpicTask, newStatus);
        save();
        return subTask;
    }

    @Override
    public Task taskChangeStatus(int codeOfTask, Status status) {
        Task task = super.taskChangeStatus(codeOfTask, status);
        save();
        return task;
    }

    @Override
    public SubTask subChangeStatus(int codeOfTask, Status newStatus) {
        SubTask subTask = super.subChangeStatus(codeOfTask, newStatus);
        save();
        return subTask;
    }

    @Override
    public void load() {
        loadFromFile(file);
    }

    @Override
    public List<String> showAllTusk() {
        List<String> list = super.showAllTusk();
        save();
        return list;
    }

    @Override
    public Task showTask(int codeOfTask) {
        Task task = super.showTask(codeOfTask);
        save();
        return task;
    }

    @Override
    public EpicTask showEpicTask(int codeOfTask) {
        EpicTask epicTask = super.showEpicTask(codeOfTask);
        save();
        return epicTask;
    }

    @Override
    public SubTask showSubTask(int codeOfTask) {
        SubTask subTask = super.showSubTask(codeOfTask);
        save();
        return subTask;
    }

    @Override
    public List<SubTask> showSubTaskToEpic(int codeOfTask) {
        List<SubTask> subTasks = super.showSubTaskToEpic(codeOfTask);
        save();
        return subTasks;
    }
}
