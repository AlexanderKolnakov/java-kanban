import java.util.ArrayList;

public class EpicTask extends Task {

    ArrayList<SubTask> listOfSubTasks = new ArrayList<>();


    public EpicTask(String nameOfTask, String taskDescription, int taskCode) {
        super(nameOfTask, taskDescription, taskCode, "NEW");
    }

    public void addSubTask(SubTask subTask) {
        listOfSubTasks.add(subTask);
    }

    public void deleteSubTask(SubTask subTask) {
        listOfSubTasks.remove(subTask);
    }
}
