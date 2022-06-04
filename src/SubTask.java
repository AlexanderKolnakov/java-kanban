public class SubTask extends Task {
    private final int CODE_OF_EPIC_TASK;

    public int getCodeOfEpicTask() {
        return CODE_OF_EPIC_TASK;
    }

    public SubTask(String nameOfTask, String taskDescription, int taskCode, String status, int codeOfEpicTask) {
        super(nameOfTask, taskDescription, taskCode, status);
        this.CODE_OF_EPIC_TASK = codeOfEpicTask;
    }
}
