package taskTracker;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EpicTask extends Task {

     ArrayList<SubTask> listOfSubTasks = new ArrayList<>();
    protected TypeOfTask type = TypeOfTask.EPIC;


    public EpicTask(String nameOfTask, String taskDescription, int taskCode) {
        super(nameOfTask, taskDescription, taskCode, Status.NEW);
    }

    public void addSubTask(SubTask subTask) {
        listOfSubTasks.add(subTask);
        Set<Status> statusList = new HashSet<>();
        for (SubTask sub : listOfSubTasks) {
            statusList.add(sub.getStatus());
        }



        checkStatus();
    }
    public void checkStatus () {
        Set<Status> statusList = new HashSet<>();
        for (SubTask sub : listOfSubTasks) {
            statusList.add(sub.getStatus());
        }
        if (statusList.size() > 1) {
            status = Status.IN_PROGRESS;
        } else {
            for (Status s : statusList) {
                status = s;
            }
        }
    }

    @Override
    public String toString() {
        return "EpicTask { id Epic задачи - " + taskCode +
                ", Имя Epic задачи -'" + nameOfTask + '\'' +
                ", Описание Epic задачи -'" + taskDescription + '\'' +
                ", Статус Epic задачи -" + status +
                ", Список подзадач :" + listOfSubTasks +
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
    public TypeOfTask getType() { return type; }
}
