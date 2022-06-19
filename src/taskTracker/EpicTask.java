package taskTracker;

import java.util.ArrayList;  // используется в строке для ведения листа с подзадачами у эпика (строка 5)

public class EpicTask extends Task {

     ArrayList<SubTask> listOfSubTasks = new ArrayList<>();


    public EpicTask(String nameOfTask, String taskDescription, int taskCode) {
        super(nameOfTask, taskDescription, taskCode, Status.NEW);
    }

    public void addSubTask(SubTask subTask) {
        listOfSubTasks.add(subTask);
    }

    public void deleteSubTask(SubTask subTask) {
        listOfSubTasks.remove(subTask);
    }

    @Override
    public String toString() {
        return "taskTracker.Managers.taskTracker.EpicTask {" +
                "Список задач :" + listOfSubTasks +
                "Имя задачи -'" + nameOfTask + '\'' +
                ", Описание задачи -'" + taskDescription + '\'' +
                ", Статус задачи -" + status +
                '}';
    }
}
