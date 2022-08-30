package manegers;

import client.KVTaskClient;
import interfaces.TaskManager;
import taskTracker.EpicTask;
import taskTracker.SubTask;
import taskTracker.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HTTPTaskManager extends FileBackedTasksManager {
    private static KVTaskClient client;

    public HTTPTaskManager(String URL) throws IOException, InterruptedException {
        super(URL);
        client = new KVTaskClient(super.name);

    }

    @Override
    public void save() {
        StringBuilder builder = new StringBuilder();
        builder.append("id,type,name,status,description,epic,startTime,Duration\n");
        for (Task task : getTasks()) {
            builder.append(task.toString()).append("\n");
        }

        List<Integer> history = getHistory();

        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        for (int hist : history) {
            stringBuilder.append(String.valueOf(hist));
            count++;
            if (count != history.size()) {
                stringBuilder.append(",");
            }
        }
        String historyString = stringBuilder.toString();


        builder.append('\n').append(historyString);
        try {
            client.put("manager", builder.toString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при сохранении " + e.getMessage());
        }
    }

    public TaskManager load(String URL) {
        String key = "manager";
        TaskManager manager = null;
        String json = null;
        try {
            manager = new HTTPTaskManager(URL);

            json = client.load(key);
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при загрузка " + e.getMessage());
        }

        for (String s : readTaskFromJson(json)) {
            fillInMaps(s);
        }
        List<Integer> taskList=  getHistory();
        for (Integer task : taskList) {
            historyManager.remove(task);
        }
        for (Integer id : readHistoryFromJson(json)) {
            historyManager = new InMemoryHistoryManager();
            if (dataTask.containsKey(id)) {
                historyManager.addHistory(dataTask.get(id));
            } else if (dataEpicTask.containsKey(id)) {
                historyManager.addHistory(dataEpicTask.get(id));
            } else if (dataSubTask.containsKey(id)) {
                historyManager.addHistory(dataSubTask.get(id));
            }
        }
        return manager;
    }
    public static List<String> readTaskFromJson(String json) {
        List<String> tasks = new ArrayList<>(List.of(json.split("\n")));
        tasks.remove(0);
        tasks.remove(tasks.size()-1);
        tasks.remove(tasks.size()-1);
        return tasks;
    }

    protected void fillInMaps(String string) {
        switch (string.split(",")[1]) {
            case "Task":
                Task task = fromStringer(string);
                dataTask.put(task.getTaskCode(), task);
                break;
            case "EpicTask":
                task = fromStringer(string);
                dataEpicTask.put(task.getTaskCode(), (EpicTask) task);
                break;
            case "SubTask":
                task = fromStringer(string);
                dataSubTask.put(task.getTaskCode(), (SubTask) task);
                dataEpicTask.get(((SubTask) task).getCodeOfEpicTask()).getListOfSubTasks().add((SubTask) task);
                break;
        }
    }

    public static List<Integer> readHistoryFromJson(String json){
        List<String> strings = List.of(json.split("\n"));
        return Stream.of(strings.get(strings.size()-1).split(","))
                .map(Integer::parseInt).collect(Collectors.toList());
    }

    @Override
    public String taskToString(Task task) {
        return super.taskToString(task);
    }



    public static Task fromStringer (String value) {
        String[] taskString = value.split(",");
        switch (taskString[1]) {
            case "TASK":
                return new Task(taskString[2], taskString[4], Integer.parseInt(taskString[0]), checkStatus(taskString[3]),
                        Integer.parseInt(taskString[5]), stringToData(taskString[6]));
            case "EPIC":
                return new EpicTask(taskString[2], taskString[4], Integer.parseInt(taskString[0]),
                        Integer.parseInt(taskString[5]), stringToData(taskString[6]));
            case "SUBTASK":
                return new SubTask(taskString[2], taskString[4], Integer.parseInt(taskString[7]),
                        checkStatus(taskString[3]), Integer.parseInt(taskString[0]), Integer.parseInt(taskString[5]),
                        stringToData(taskString[6]));
        }
        return null;
    }
}
