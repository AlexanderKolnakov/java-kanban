import java.util.ArrayList;

public class EpicTask extends Task {

    ArrayList<SubTask> listOfSubTasks = new ArrayList<>();
    String nameOfTask;
    String taskDescription;
    int taskCode;
    String status;

    public EpicTask(String nameOfTask, String taskDescription, int taskCode) {
        this.nameOfTask = nameOfTask;
        this.taskDescription = taskDescription;
        this.status = "NEW";
        this.taskCode = taskCode;
    }

    public void addSubTask(SubTask subTask) {
        listOfSubTasks.add(subTask);
    }

    public void deleteSubTask(SubTask subTask) {
        listOfSubTasks.remove(subTask);
    }
}
