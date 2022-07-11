import manegers.Managers;
import taskTracker.Status;
import interfaces.TaskManager;

public class Main {
    public static void main(String[] args) {

        TaskManager manager = new Managers().getDefault();

        manager.addTask("Задача 1", "описание Задачи 1", Status.DONE);  // taskCode = 1
        manager.addTask("Задача 2", "описание Задачи 2", Status.DONE);  // taskCode = 2

        manager.addEpicTask("Эпик_Задача 1", "описание Эпик_Задачи 3");  // taskCode = 3
        manager.addEpicTask("Эпик_Задача 2", "описание Эпик_Задачи 4");  // taskCode = 4

        manager.addSubTask("Подзадача 5 Эпик_Задачи 3", "описание Подзадачи 5",
                3, Status.NEW);  // taskCode = 5
        manager.addSubTask("Подзадача 6 Эпик_Задачи 3", "описание Подзадачи 6",
                3, Status.NEW);  // taskCode = 6
        manager.addSubTask("Подзадача 7 Эпик_Задачи 3", "описание Подзадачи 7",
                3, Status.NEW);  // taskCode = 7
        manager.addSubTask("Подзадача 8 Эпик_Задачи 4", "описание Подзадачи 8",
                4, Status.NEW);  // taskCode = 8
        manager.addSubTask("Подзадача 9 Эпик_Задачи 4", "описание Подзадачи 9",
                4, Status.NEW);  // taskCode = 9

        







//        проверка на изменение статуса епика при изменении статуса подзадач

        /*System.out.println(manager.checkEpicStatus(3));
        System.out.println(manager.subChangeStatus(6, Status.DONE));
        System.out.println(manager.checkEpicStatus(3));
        System.out.println(" ");
        System.out.println(manager.checkEpicStatus(3));
        System.out.println(manager.subChangeStatus(5, Status.DONE));
        System.out.println(manager.subChangeStatus(6, Status.DONE));
        System.out.println(manager.subChangeStatus(7, Status.DONE));
        System.out.println(manager.checkEpicStatus(3));*/


//        проверка списка подзадач у конкретного эпика

        /*System.out.println(manager.showSubTaskToEpic(3));
        manager.deleteSubTask(6);
        System.out.println(manager.showSubTaskToEpic(3));*/


//        проверка на изменение имени, описания и статуса задач

        /*System.out.println(manager.updateTask("новое имя", "новое описание",
                122, Status.DONE));
        System.out.println(manager.updateTask("новое имя", "новое описание",
                1, Status.DONE));
        System.out.println(manager.updateEpicTask("новое имя", "новое описание",
                122));
        System.out.println(manager.updateEpicTask("новое имя", "новое описание",
                3));
        System.out.println(manager.updateSubTask("новое имя", "новое описание",
                122, 122, Status.DONE));
        System.out.println(manager.showSubTaskToEpic(3));
        System.out.println(manager.updateSubTask("новое имя", "новое описание",
                6, 3, Status.DONE));
        System.out.println(manager.showSubTaskToEpic(3));*/


//        проверка на изменение статуса задач

        /*System.out.println(manager.taskChangeStatus(122, Status.IN_PROGRESS));
        System.out.println(manager.taskChangeStatus(1, Status.IN_PROGRESS));
        System.out.println(manager.subChangeStatus(122, Status.DONE));
        System.out.println(manager.subChangeStatus(6, Status.DONE));*/


//        проверка метода getHistory()

        /*manager.showTask(1);
        System.out.println(manager.getHistory());
        System.out.println(manager.getHistory().size());
        System.out.println("\n");
        manager.showEpicTask(3);
        manager.showEpicTask(3);
        manager.showSubTask(6);
        manager.showEpicTask(3);
        manager.showEpicTask(3);
        manager.showEpicTask(3);
        manager.showSubTask(6);
        manager.showEpicTask(3);
        manager.showEpicTask(3);
        manager.showEpicTask(3);
        manager.showSubTask(6);
        manager.showEpicTask(3);
        manager.showEpicTask(3);
        manager.showEpicTask(3);
        manager.showSubTask(6);
        System.out.println(manager.getHistory());
        System.out.println(manager.getHistory().size());*/

//        System.out.println(manager.showTask(111));


//        проверка метода getHistory() и удаление задач delete

        /*manager.showTask(1);
        System.out.println(manager.getHistory());
        System.out.println(manager.getHistory().size());
        manager.showEpicTask(3);
        manager.showEpicTask(3);
        manager.showSubTask(6);
        System.out.println(manager.getHistory());
        System.out.println(manager.getHistory().size());
        manager.deleteSubTask(6);
        System.out.println(manager.getHistory());
        System.out.println(manager.getHistory().size());*/
    }
}
