public class Task {
    protected String nameOfTask;
    protected String taskDescription;
    protected int taskCode = 0;
    protected String status;

    public Task(String nameOfTask, String taskDescription, String status) {
        this.nameOfTask = nameOfTask;
        this.taskDescription = taskDescription;
        this.taskCode = ++taskCode;
        this.status = status;
    }
}

