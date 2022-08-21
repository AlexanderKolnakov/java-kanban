package taskTracker;

import java.time.LocalDateTime;

public class Task {
    protected String nameOfTask;
    protected String taskDescription;
    protected int taskCode;
    protected Status status;
    protected long duration = 0;
    protected LocalDateTime startTime = LocalDateTime.now();

    protected TypeOfTask type;

    public Task(String nameOfTask, String taskDescription, int taskCode, Status status, long duration, LocalDateTime startTime) {
        this.nameOfTask = nameOfTask;
        this.taskDescription = taskDescription;
        this.taskCode = taskCode;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
        this.type = TypeOfTask.TASK;
    }
    public Task(String nameOfTask, String taskDescription, int taskCode, Status status) {
        this.nameOfTask = nameOfTask;
        this.taskDescription = taskDescription;
        this.taskCode = taskCode;
        this.status = status;
        this.type = TypeOfTask.TASK;

    }

    @Override
    public String toString() {
        return type + " { id задачи - " + taskCode +
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
    public void renameTask(String newNameOfTask) {
        nameOfTask = newNameOfTask;
    }
    public void renameTaskDescription(String newTaskDescription) {
        taskDescription = newTaskDescription;
    }
    public void changeStatus(Status newStatus) {
        status = newStatus;
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

