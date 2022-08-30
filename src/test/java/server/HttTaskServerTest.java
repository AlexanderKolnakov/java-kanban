package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import interfaces.TaskManager;
import manegers.InMemoryTaskManager;
import manegers.Managers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import taskTracker.EpicTask;
import taskTracker.Status;
import taskTracker.SubTask;
import taskTracker.Task;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttTaskServerTest {
    private HttpTaskServer server;
    private TaskManager taskManager;

    private Task task;
    private EpicTask epicTask;
    private SubTask subTask;
    private final static String STATUS_200 = "Возвращен код состояния не 200";
    private final static String STATUS_403 = "Возвращен код состояния не 403";
    private final static String STATUS_404 = "Возвращен код состояния не 404";

    private Gson gson = Managers.getGsons();

    @BeforeEach
    void init() throws IOException, InMemoryTaskManager.IntersectionDataException {
        taskManager = new InMemoryTaskManager();

        server = new HttpTaskServer(taskManager);

        task = new Task("Задача 1", "Описание задачи 1", 1,
                Status.DONE, 10, LocalDateTime.of(2022, 12, 30, 12, 0));
        epicTask = new EpicTask("Эпик Задача 1", "Описание эпик задачи 1", 2, 10,
                LocalDateTime.of(2022, 12, 30, 12, 40));
        subTask = new SubTask("Подзадача 1", "Описание задачи 1", 3,
                Status.DONE,  2,10, LocalDateTime.of(2022, 12, 30, 12, 20));
        taskManager.addTask(task);
        taskManager.addEpicTask(epicTask);
        taskManager.addSubTask(subTask);
        server.start();
    }

    @AfterEach
    void stop() {
        server.clear();
        server.stop();
    }

    @Test
    void getTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), STATUS_200);

        Type userType = new TypeToken<ArrayList<String>>() {
        }.getType();

        List<String> tasks = gson.fromJson(response.body(), userType);

        assertNotNull(tasks, "Список задач пустой");
        assertEquals(3, tasks.size(), "Не верное количество задач");
    }

    @ParameterizedTest
    @ValueSource(strings = { "task?1", "subtask?3", "epic?2" })
    void getTasksByID (String s) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/"+ s);
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), STATUS_200);

        Type userType = new TypeToken<Task>() {
        }.getType();

        Task taskResponse = gson.fromJson(response.body(), userType);

        assertNotNull(taskResponse, "Задача не возвращена");
        assertEquals(taskResponse.getTaskCode(), Integer.parseInt(String.valueOf(s.charAt((s.length() - 1))))
                , "Возвращена не верная задача");
    }

    @ParameterizedTest
    @ValueSource(strings = { "task?2222", "subtask?2222", "epic?222", "subtask/epic?9999"})
    void getTasksByNotExistID(String s) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/"+ s);
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode(), STATUS_404);
    }

    @Test
    void deleteAllTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), STATUS_200);

        Type userType = new TypeToken<HashMap<Integer, Task>>() {
        }.getType();

        HashMap<Integer, Task> taskResponse = gson.fromJson(response.body(), userType);

        assertNotNull(taskResponse, "Задачи не возвращены");
        assertEquals(0, taskResponse.size(), "Задача не удалена");
    }

    @ParameterizedTest
    @ValueSource(strings = { "task?1", "subtask?3", "epic?2" })
    void deleteTasksByID11(String s) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/"+ s);
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), STATUS_200);
    }

    @ParameterizedTest
    @ValueSource(strings = { "task?2222", "subtask?22222", "epic?222" })
    void deleteTasksByNotExistID(String s) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/"+ s);
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode(), STATUS_404);
    }

    @Test
    void postTasksWithBody() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        Task newTask = new Task("Задача 2", "Описание задачи 2", 4, Status.NEW);
        String json = gson.toJson(newTask);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), STATUS_200);

        Type userType = new TypeToken<Task>() {
        }.getType();

        Task taskResponse = gson.fromJson(response.body(), userType);

        assertNotNull(taskResponse, "Задачи не добавлена");
        assertEquals(4, taskResponse.getTaskCode(), "Задача не добавлена");
    }

    @Test
    void postSubTasksWithBody() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        SubTask newTask = new SubTask("Подзадача 2", "Описание подзадачи 2",
                4, Status.NEW, 2);
        String json = gson.toJson(newTask);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), STATUS_200);

        Type userType = new TypeToken<SubTask>() {
        }.getType();

        SubTask taskResponse = gson.fromJson(response.body(), userType);

        assertNotNull(taskResponse, "Подзадачи не добавлена");
        assertEquals(4, taskResponse.getTaskCode(), "Подзадача не добавлена");
    }
    @Test
    void postEpicTasksWithBody() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        EpicTask newTask = new EpicTask("Эпик задача 2", "Описание Эпик задачи 2",4);
        String json = gson.toJson(newTask);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), STATUS_200);

        Type userType = new TypeToken<EpicTask>() {
        }.getType();

        EpicTask taskResponse = gson.fromJson(response.body(), userType);

        assertNotNull(taskResponse, "Эпик задачи не добавлена");
        assertEquals(4, taskResponse.getTaskCode(), "Эпик задача не добавлена");
    }

    @ParameterizedTest
    @ValueSource(strings = { "task", "subtask", "epic" })
    void postTasksByNotExistBody(String s) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/"+ s);
        String json = "Тут нет задачи";
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode(), STATUS_404);
    }
    @Test
    void postTasksUpdate() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        Task newTask = new Task("Новое имя задачи", "Новое описание задачи", 1, Status.NEW);
        String json = gson.toJson(newTask);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), STATUS_200);

        Type userType = new TypeToken<Task>() {
        }.getType();

        Task taskResponse = gson.fromJson(response.body(), userType);

        assertNotNull(taskResponse, "Задачи не добавлена");
        assertEquals(1, taskResponse.getTaskCode(), "Задача не обновлена");
        assertEquals("Новое имя задачи", taskResponse.getName(), "Задача не обновлена");
    }
    @Test
    void postSubTasksUpdate() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        SubTask newTask = new SubTask("Новое имя подзадачи", "Новое описание подзадачи",
                3, Status.NEW, 2);
        String json = gson.toJson(newTask);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), STATUS_200);

        Type userType = new TypeToken<SubTask>() {
        }.getType();

        SubTask taskResponse = gson.fromJson(response.body(), userType);

        assertNotNull(taskResponse, "Задачи не добавлена");
        assertEquals(3, taskResponse.getTaskCode(), "Подзадача не обновлена");
        assertEquals("Новое имя подзадачи", taskResponse.getName(), "Подзадача не обновлена");
    }
    @Test
    void postEpicTasksUpdate() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        EpicTask newTask = new EpicTask("Новое имя Эпик задачи", "Новое описание Эпик задачи",
                2);
        String json = gson.toJson(newTask);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), STATUS_200);

        Type userType = new TypeToken<EpicTask>() {
        }.getType();

        EpicTask taskResponse = gson.fromJson(response.body(), userType);

        assertNotNull(taskResponse, "Задачи не добавлена");
        assertEquals(2, taskResponse.getTaskCode(), "Эпик задача не обновлена");
        assertEquals("Новое имя Эпик задачи", taskResponse.getName(), "Эпик задача не обновлена");
    }
    @ParameterizedTest
    @ValueSource(strings = { "task", "subtask", "epic" })
    void putTasks(String s) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/"+ s);
        String json = "Тут могла бы быть ваша задача...";
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).PUT(body).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(403, response.statusCode(), STATUS_403);
    }

    @Test
    void getSubTaskOfEpicTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/epic?2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), STATUS_200);

        Type userType = new TypeToken<ArrayList<SubTask>>() {
        }.getType();

        List<SubTask> tasks = gson.fromJson(response.body(), userType);

        assertNotNull(tasks, "Список задач пустой");
        assertEquals(1, tasks.size(), "Не верное количество задач");
    }

    @Test
    void deleteSubTaskByNotExistEpicTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/epic?2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(403, response.statusCode(), STATUS_403);
    }

    @Test
    void getHistory() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/history");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), STATUS_200);

        Type userType = new TypeToken<List<Integer>>() {
        }.getType();

        List<Integer> history = gson.fromJson(response.body(), userType);

        assertNotNull(history, "Список задач пустой");
    }

    @Test
    void deleteHistory() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/history");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(403, response.statusCode(), STATUS_403);
    }

    @Test
    void getPrioritizedTasks() throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), STATUS_200);

        Type userType = new TypeToken<ArrayList<Task>>() {
        }.getType();

        ArrayList<Task> history = gson.fromJson(response.body(), userType);

        assertNotNull(history, "Список задач пустой");
        assertEquals(3, history.size(), "Не верное количество задач");
        assertEquals(history.get(0).getTaskCode(), task.getTaskCode(), "Не верный порядок задач");
        assertEquals(history.get(2).getTaskCode(), epicTask.getTaskCode(), "Не верный порядок задач");
    }

    @Test
    void deletePrioritizedTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(403, response.statusCode(), STATUS_403);
    }
}