package taskTracker;

public class Task {
    protected String nameOfTask;
    protected String taskDescription;
    protected int taskCode;
    protected Status status;

    public Task(String nameOfTask, String taskDescription, int taskCode, Status status) {
        this.nameOfTask = nameOfTask;
        this.taskDescription = taskDescription;
        this.taskCode = taskCode;
        this.status = status;
    }

    @Override
    public String toString() {
        return "taskTracker.Task {" +
                "Имя задачи'" + nameOfTask + '\'' +
                ", Описание задачи - '" + taskDescription + '\'' +
                ", Статус задачи - " + status +
                '}';
    }
}

