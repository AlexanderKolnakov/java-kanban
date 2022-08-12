package taskTracker;

public class SubTask extends Task {
    private final int CODE_OF_EPIC_TASK;
    protected TypeOfTask type = TypeOfTask.SUBTASK;

    public int getCodeOfEpicTask() {
        return CODE_OF_EPIC_TASK;
    }

    public SubTask(String nameOfTask, String taskDescription, int taskCode, Status status, int codeOfEpicTask) {
        super(nameOfTask, taskDescription, taskCode, status);
        this.CODE_OF_EPIC_TASK = codeOfEpicTask;
    }

    @Override
    public String toString() {
        return "SubTask { id подзадачи - " + taskCode +
                ", Имя подзадачи - '" + nameOfTask + '\'' +
                ", Описание подзадачи - '" + taskDescription + '\'' +
                ", Статус подзадачи - " + status +
                '}';
    }
    public TypeOfTask getType() { return type; }
}
