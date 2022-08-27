package server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import interfaces.TaskManager;
import manegers.InMemoryTaskManager;
import manegers.Managers;
import taskTracker.EpicTask;
import taskTracker.SubTask;
import taskTracker.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {
    public static final int PORT = 8080;
    private HttpServer server;
    private Gson gson;
    private TaskManager taskManager;

    public HttpTaskServer() throws IOException {
        this(Managers.getDefault());
    }

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        gson = Managers.getGsons();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", this::handler);
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();
    }

    private void handler(HttpExchange httpExchange) throws IOException {
        try {
            System.out.println("\n /tasks" + httpExchange.getRequestURI());
            String requestMethod = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath().replaceFirst("/tasks", "");
            switch (path) {
                case "/task": {
                    System.out.println("запрос /task");
                    handlerTask(httpExchange);
                    break;
                }
                case "/subtask": {
                    System.out.println("запрос /subtask");
                    handlerSubTask(httpExchange);
                    break;
                }
                case "/epic": {
                    System.out.println("запрос /epic");
                    handlerEpicTask(httpExchange);
                    break;
                }
                case "/subtask/epic": {
                    System.out.println("запрос /subtask/epic");
                    handlerSubTaskToEpicTask(httpExchange);
                    break;
                }
                case "/history": {
                    System.out.println("запрос /history");
                    handlerHistory(httpExchange);
                    break;
                } case "": {
                    System.out.println("пустой запрос");
                    handlerPriority(httpExchange);
                    break;
                }
                default: {
                    System.out.println("/ ждем /task , /subtask , /epic или /history , а получили - " + path);
                    httpExchange.sendResponseHeaders(405, 0);
                }
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Передано не корректное тело метода" );
            System.out.println("Передайте задачу в формате JSON" );
            httpExchange.sendResponseHeaders(404, 0);
        }catch (Exception e) {
            System.out.println("Ошибка при обработке запроса");
        } finally {
            httpExchange.close();
        }
    }


    private void handlerHistory(HttpExchange httpExchange) throws IOException {
        String requestMethod = httpExchange.getRequestMethod();
        if ("GET".equals(requestMethod)) {
            System.out.println("вызван метод GET");
            final String response = gson.toJson(taskManager.getHistory());
            System.out.println("История успешно возвращена");
            sendText(httpExchange, response);
        } else {
            System.out.println("Ожидался метод GET, а получили: " + requestMethod);
            httpExchange.sendResponseHeaders(403, 0);
        }
    }


    private void handlerTask(HttpExchange httpExchange) throws IOException, InMemoryTaskManager.IntersectionDataException {
        String requestMethod = httpExchange.getRequestMethod();
        String query = httpExchange.getRequestURI().getQuery();
        switch (requestMethod) {
            case "GET": {
                System.out.println("вызван метод GET");
                if (Objects.nonNull(query)) {
                    int id = Integer.parseInt(query);
                    if (taskManager.showTask(id) != null) {
                        final String response = gson.toJson(taskManager.showTask(id));
                        sendText(httpExchange, response);
                        return;
                    } else {
                        System.out.println("Задачи с ID:" + id + " не существует");
                        httpExchange.sendResponseHeaders(404, 0);
                    }
                } else {
                    final String response = gson.toJson(taskManager.showAllTusk());
                    sendText(httpExchange, response);
                    return;
                }
                break;
            }
            case "DELETE" : {
                System.out.println("вызван метод DELETE");
                if (Objects.nonNull(query)) {
                    int id = Integer.parseInt(query);
                    if (taskManager.showTask(id) != null) {
                        taskManager.deleteTask(id);
                        System.out.println("Задача по ID: " + query + " удалена");
                        httpExchange.sendResponseHeaders(200, 0);
                        return;
                    } else {
                        System.out.println("Задачи с ID:" + id + " не существует");
                        httpExchange.sendResponseHeaders(404, 0);
                    }
                } else {
                    System.out.println("Все задачи удалены");
                    final String response = gson.toJson(taskManager.deleteAllTask());
                    sendText(httpExchange, response);
                    return;
                }
                break;
            } case "POST": {
                System.out.println("вызван метод POST");
                String taskInString = readText(httpExchange);
                System.out.println("тело метода: " + taskInString);

                Task task = gson.fromJson(taskInString, Task.class);
                if (taskManager.showTask(task.getTaskCode()) == null) {
                    taskManager.addTask(task);
                    System.out.println("Задача типа " + task.getType() + " успешно добавлена");
                    final String response = gson.toJson(taskManager.addTask(task));
                    sendText(httpExchange, response);
                } else {
                    taskManager.updateTask(task);
                    System.out.println("Так как задача с ID: " + task.getTaskCode() + " уже есть, она была обновлена");
                    final String response = gson.toJson(taskManager.updateTask(task));
                    sendText(httpExchange, response);
                }
                break;
            }
            default: {
                System.out.println("Ожидали метод GET, DELETE или POST, а получили: " + requestMethod);
                httpExchange.sendResponseHeaders(403, 0);

            }
        }
    }

    private void handlerSubTask(HttpExchange httpExchange) throws IOException, InMemoryTaskManager.IntersectionDataException {
        String requestMethod = httpExchange.getRequestMethod();
        String query = httpExchange.getRequestURI().getQuery();
        switch (requestMethod) {
            case "GET": {
                System.out.println("вызван метод GET");
                if (Objects.nonNull(query)) {
                    int id = Integer.parseInt(query);
                    if (taskManager.showSubTask(id) != null) {
                        final String response = gson.toJson(taskManager.showSubTask(id));
                        sendText(httpExchange, response);
                        return;
                    } else {
                        System.out.println("Подзадачи с ID:" + id + " не существует");
                        httpExchange.sendResponseHeaders(404, 0);
                    }
                } else {
                    final String response = gson.toJson(taskManager.showAllTusk());
                    sendText(httpExchange, response);
                    return;
                }
                break;
            }
            case "DELETE" : {
                System.out.println("вызван метод DELETE");
                if (Objects.nonNull(query)) {
                    int id = Integer.parseInt(query);
                    if (taskManager.showSubTask(id) != null) {
                        System.out.println("Подзадача по ID: " + query + " удалена");
                        final String response = gson.toJson(taskManager.deleteSubTask(id));
                        sendText(httpExchange, response);
                        return;
                    } else {
                        System.out.println("Подзадачи с ID:" + id + " не существует");
                        httpExchange.sendResponseHeaders(404, 0);
                    }
                } else {
                    System.out.println("Все задачи удалены");
                    final String response = gson.toJson(taskManager.deleteAllTask());
                    sendText(httpExchange, response);
                    return;
                }
                break;
            } case "POST": {
                System.out.println("вызван метод POST");
                String taskInString = readText(httpExchange);
                System.out.println("тело метода: " + taskInString);

                SubTask subTask = gson.fromJson(taskInString, SubTask.class);
                if (taskManager.showSubTask(subTask.getTaskCode()) == null) {
                    taskManager.addSubTask(subTask);
                    System.out.println("Задача типа " + subTask.getType() + " успешно добавлена");
                    final String response = gson.toJson(taskManager.addSubTask(subTask));
                    sendText(httpExchange, response);
                } else {
                    taskManager.updateSubTask(subTask);
                    System.out.println("Так как подзадача с ID: " + subTask.getTaskCode() + " уже есть, она была обновлена");
                    final String response = gson.toJson(taskManager.updateSubTask(subTask));
                    sendText(httpExchange, response);
                }
                break;
            }
            default: {
                System.out.println("Ожидали метод GET, DELETE или POST, а получили: " + requestMethod);
                httpExchange.sendResponseHeaders(403, 0);
            }
        }
    }

    private void handlerEpicTask(HttpExchange httpExchange) throws InMemoryTaskManager.IntersectionDataException, IOException {
        String requestMethod = httpExchange.getRequestMethod();
        String query = httpExchange.getRequestURI().getQuery();
        switch (requestMethod) {
            case "GET": {
                System.out.println("вызван метод GET");
                if (Objects.nonNull(query)) {
                    int id = Integer.parseInt(query);
                    if (taskManager.showEpicTask(id) != null) {
                        final String response = gson.toJson(taskManager.showEpicTask(id));
                        sendText(httpExchange, response);
                        return;
                    } else {
                        System.out.println("Эпик задачи с ID:" + id + " не существует");
                        httpExchange.sendResponseHeaders(404, 0);
                    }
                } else {
                    final String response = gson.toJson(taskManager.showAllTusk());
                    sendText(httpExchange, response);
                    return;
                }
                break;
            }
            case "DELETE" : {
                System.out.println("вызван метод DELETE");
                if (Objects.nonNull(query)) {
                    int id = Integer.parseInt(query);
                    if (taskManager.showEpicTask(id) != null) {
                        System.out.println("Эпик задача по ID: " + query + " удалена");
                        final String response = gson.toJson(taskManager.deleteEpicTask(id));
                        sendText(httpExchange, response);
                        return;
                    } else {
                        System.out.println("Эпик задачи с ID:" + id + " не существует");
                        httpExchange.sendResponseHeaders(404, 0);
                    }
                } else {
                    System.out.println("Все задачи удалены");
                    final String response = gson.toJson(taskManager.deleteAllTask());
                    sendText(httpExchange, response);
                    return;
                }
                break;
            } case "POST": {
                System.out.println("вызван метод POST");
                String taskInString = readText(httpExchange);
                System.out.println("тело метода: " + taskInString);

                EpicTask epicTask = gson.fromJson(taskInString, EpicTask.class);
                if (taskManager.showEpicTask(epicTask.getTaskCode()) == null) {
                    taskManager.addEpicTask(epicTask);
                    System.out.println("Задача типа " + epicTask.getType() + " успешно добавлена");
                    final String response = gson.toJson(taskManager.addEpicTask(epicTask));
                    sendText(httpExchange, response);
                } else {
                    taskManager.updateEpicTask(epicTask);
                    System.out.println("Так как Эпик задача с ID: " + epicTask.getTaskCode() + " уже есть, она была обновлена");
                    final String response = gson.toJson(taskManager.updateEpicTask(epicTask));
                    sendText(httpExchange, response);
                }
                break;
            }
            default: {
                System.out.println("Ожидали метод GET, DELETE или POST, а получили: " + requestMethod);
                httpExchange.sendResponseHeaders(403, 0);
            }
        }
    }

    private void handlerSubTaskToEpicTask(HttpExchange httpExchange) throws IOException {
        String requestMethod = httpExchange.getRequestMethod();
        String query = httpExchange.getRequestURI().getQuery();
        if ("GET".equals(requestMethod)) {
            System.out.println("вызван метод GET");
            if (Objects.nonNull(query)) {
                int id = Integer.parseInt(query);
                if (taskManager.showEpicTask(id) != null) {
                    final String response = gson.toJson(taskManager.showSubTaskToEpic(id));
                    System.out.println("Список подзадачи Эпик задачи с ID:" + id + " успешно возвращен");
                    sendText(httpExchange, response);
                    return;
                } else {
                    System.out.println("Эпик задачи с ID:" + id + " не существует");
                    httpExchange.sendResponseHeaders(404, 0);
                }
            }
        } else {
            System.out.println("Ожидался метод GET, а получили: " + requestMethod);
            httpExchange.sendResponseHeaders(403, 0);
        }
    }

    private void handlerPriority(HttpExchange httpExchange) throws IOException {
        String requestMethod = httpExchange.getRequestMethod();
        if ("GET".equals(requestMethod)) {
            final String response = gson.toJson(taskManager.getPrioritizedTasks());
            System.out.println("Отсортированный по дате список задач возвращен");
            sendText(httpExchange, response);
        } else {
            System.out.println("Ожидался метод GET, а получили: " + requestMethod);
            httpExchange.sendResponseHeaders(403, 0);
        }
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("http://localhost:" + PORT + "/tasks");
        server.start();
    }

    public void stop() {
        server.stop(0);
        System.out.println("Остановили сервер на порту " + PORT);
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=uft-8");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }

}
