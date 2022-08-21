package taskTracker;

import java.time.LocalDateTime;

public class SubTask extends Task {
    private final int CODE_OF_EPIC_TASK;

    public int getCodeOfEpicTask() {
        return CODE_OF_EPIC_TASK;
    }

    public SubTask(String nameOfTask, String taskDescription, int taskCode, Status status, int codeOfEpicTask) {
        super(nameOfTask, taskDescription, taskCode, status);
        this.CODE_OF_EPIC_TASK = codeOfEpicTask;
        this.type = TypeOfTask.SUBTASK;
    }
    public SubTask(String nameOfTask, String taskDescription, int taskCode, Status status, int codeOfEpicTask,
                   long duration, LocalDateTime startTime) {
        super(nameOfTask, taskDescription, taskCode, status, duration, startTime);
        this.CODE_OF_EPIC_TASK = codeOfEpicTask;
        this.type = TypeOfTask.SUBTASK;
    }
}
