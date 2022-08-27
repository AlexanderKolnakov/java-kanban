package manegers;

import client.KVTaskClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import taskTracker.EpicTask;
import taskTracker.SubTask;
import taskTracker.Task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HTTPTaskManager extends FileBackedTasksManager {
    private KVTaskClient client;
    private Gson gson;
    private static final String TASK_KEY = "task";
    private static final String SUBTASK_KEY = "subtasks";
    private static final String EPIC_TASK_KEY = "epic";
    private static final String HISTORY_KEY = "history";



    public HTTPTaskManager(int port) {
        super(null);
        gson = Managers.getGsons();
        client = new KVTaskClient(port);

    }

    @Override
    public void save() {
        String tasksJson = gson.toJson(getTask());
        client.put(TASK_KEY, tasksJson);    // tasks

        String epicJson = gson.toJson(getEpicTask());
        client.put(EPIC_TASK_KEY , epicJson);

        String subTaskJson = gson.toJson(getSubTask());
        client.put(SUBTASK_KEY, subTaskJson);

        String historyString = gson.toJson(getHistory());
        client.put(HISTORY_KEY, historyString);
    }


    @Override
    public void load()  {
        Type tasksType = new TypeToken<ArrayList<Task>>() {
        }.getType();
        List<Task> tasks = gson.fromJson(client.load(TASK_KEY), tasksType);
        tasks.forEach(task -> {
            int id = task.getTaskCode();
            this.dataTask.put(id, task);
            this.prioritizedTasks.add(task);
            if (id > taskScore) {
                taskScore = id;
            }
        });

        Type subTasksType = new TypeToken<ArrayList<SubTask>>() {
        }.getType();
        List<SubTask> subTasks = gson.fromJson(client.load(SUBTASK_KEY), subTasksType);
        subTasks.forEach(subTask -> {
            int id = subTask.getTaskCode();
            this.dataSubTask.put(id, subTask);
            this.prioritizedTasks.add(subTask);
            if (id > taskScore) {
                taskScore = id;
            }
        });

        Type epicTasksType = new TypeToken<ArrayList<EpicTask>>() {
        }.getType();
        List<EpicTask> epicTasks = gson.fromJson(client.load(EPIC_TASK_KEY), epicTasksType);
        epicTasks.forEach(epicTask -> {
            int id = epicTask.getTaskCode();
            this.dataEpicTask.put(id, epicTask);
            this.prioritizedTasks.add(epicTask);
            if (id > taskScore) {
                taskScore = id;
            }
        });

        Type historyType = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        List<Integer> historys = gson.fromJson(client.load(HISTORY_KEY), historyType);
        for (Integer taskID : historys) {
            historyManager.addHistory(findTask(taskID));
        }
    }

}
