package taskTracker;
import java.util.ArrayList;

public class EpicTask extends Task {

     ArrayList<SubTask> listOfSubTasks = new ArrayList<>();


    public EpicTask(String nameOfTask, String taskDescription, int taskCode) {
        super(nameOfTask, taskDescription, taskCode, Status.NEW);
    }

    public void addSubTask(SubTask subTask) {
        listOfSubTasks.add(subTask);
    }

    @Override
    public String toString() {
        return "EpicTask {" +
                "Список задач :" + listOfSubTasks +
                "Имя задачи -'" + nameOfTask + '\'' +
                ", Описание задачи -'" + taskDescription + '\'' +
                ", Статус задачи -" + status +
                '}';
    }
    public void deleteSubTask(SubTask subTask) {
        listOfSubTasks.remove(subTask);
    }
    public ArrayList<SubTask> getListOfSubTasks() {
        return listOfSubTasks;
    }
    public  void removeAllListOfSubTasks() {
        listOfSubTasks.clear();
    }
    public void addSubTaskInListOfSubTasks(SubTask subTask) {
        listOfSubTasks.add(subTask);
    }
}
