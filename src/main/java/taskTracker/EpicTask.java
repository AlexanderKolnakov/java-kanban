package taskTracker;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EpicTask extends Task {

    ArrayList<SubTask> listOfSubTasks = new ArrayList<>();

    protected LocalDateTime endTime;


    public EpicTask(String nameOfTask, String taskDescription, int taskCode) {
        super(nameOfTask, taskDescription, taskCode, Status.NEW);
        this.type = TypeOfTask.EPIC;
    }

    public EpicTask(String nameOfTask, String taskDescription, int taskCode, long duration, LocalDateTime startTime) {
        super(nameOfTask, taskDescription, taskCode, Status.NEW, duration, startTime);
        this.type = TypeOfTask.EPIC;
    }

    public void addSubTask(SubTask subTask) {
        listOfSubTasks.add(subTask);
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
        return super.toString() + "+ { Список подзадач : " + listOfSubTasks + "}";
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
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
