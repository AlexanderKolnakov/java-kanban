public class SubTask extends Task{
    String nameOfTask;
    String taskDescription;
    int taskCode;
    int codeOfEpicTask;
    String status;

    public SubTask(String nameOfSubTask, String taskDescription, int taskCode, int codeOfEpicTask, String status) {
        this.nameOfTask = nameOfSubTask;
        this.taskDescription = taskDescription;
        this.taskCode = taskCode;
        this.status = status;
        this.codeOfEpicTask = codeOfEpicTask;
    }
}
