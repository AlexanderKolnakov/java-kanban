package taskTracker;

import java.time.LocalDateTime;

public class Task {
    protected String nameOfTask;
    protected String taskDescription;
    protected int taskCode;
    protected Status status;
    protected long duration = 0;
    protected LocalDateTime startTime = LocalDateTime.now();

    protected TypeOfTask type = TypeOfTask.TASK;

    public Task(String nameOfTask, String taskDescription, int taskCode, Status status, long duration, LocalDateTime startTime) {
        this.nameOfTask = nameOfTask;
        this.taskDescription = taskDescription;
        this.taskCode = taskCode;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }
    public Task(String nameOfTask, String taskDescription, int taskCode, Status status) {
        this.nameOfTask = nameOfTask;
        this.taskDescription = taskDescription;
        this.taskCode = taskCode;
        this.status = status;

    }

    @Override
    public String toString() {
        return "Task { id задачи - " + taskCode +
                ", Имя задачи'" + nameOfTask + '\'' +
                ", Описание задачи - '" + taskDescription + '\'' +
                ", Статус задачи - " + status +
                '}';
    }

    public int getTaskCode() {
        return taskCode;
    }
    public Status getStatus() {
        return status;
    }
    public String getName() {
        return nameOfTask;
    }
    public String renameTask(String newNameOfTask) {
        return nameOfTask = newNameOfTask;
    }
    public String renameTaskDescription(String newTaskDescription) {
        return taskDescription = newTaskDescription;
    }
    public Status changeStatus(Status newStatus) {
        return status = newStatus;
    }
    public String getTaskDescription() { return taskDescription; }
    public TypeOfTask getType() { return type; }
    public LocalDateTime getEndTime () {
        return startTime.plusMinutes(duration);
    }

    public long getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}

