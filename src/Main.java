public class Main {
    public static void main(String[] args) {

        TaskManager manager = new Managers().getDefault();

        manager.addTask("Задача 1", "описание Задачи 1", Status.DONE);
        manager.addTask("Задача 2", "описание Задачи 2", Status.DONE);

        manager.addEpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1");
        manager.addEpicTask("Эпик_Задача 2", "описание Эпик_Задачи 2");

        manager.addSubTask("Подзадача 1 Эпик_Задачи 1", "описание Подзадачи 1",
                1, Status.NEW);
        manager.addSubTask("Подзадача 2 Эпик_Задачи 1", "описание Подзадачи 2",
                1, Status.NEW);
        manager.addSubTask("Подзадача 3 Эпик_Задачи 1", "описание Подзадачи 3",
                1, Status.NEW);
        manager.addSubTask("Подзадача 4 Эпик_Задачи 2", "описание Подзадачи 4",
                2, Status.NEW);
        manager.addSubTask("Подзадача 5 Эпик_Задачи 2", "описание Подзадачи 4",
                2, Status.NEW);


//        проверка на изменение статуса епика при изменении статуса подзадач

        /*System.out.println(manager.checkEpicStatus(1));
        System.out.println(manager.subChangeStatus(2, Status.DONE));
        System.out.println(manager.checkEpicStatus(1));
        System.out.println(" ");
        System.out.println(manager.checkEpicStatus(1));
        System.out.println(manager.subChangeStatus(1, Status.DONE));
        System.out.println(manager.subChangeStatus(2, Status.DONE));
        System.out.println(manager.subChangeStatus(3, Status.DONE));
        System.out.println(manager.checkEpicStatus(1));*/


//        проверка списка подзадач у конкретного эпика
        /*System.out.println(manager.showSubTaskToEpic(1));
        manager.deleteSubTask(2);
        System.out.println(manager.showSubTaskToEpic(1));*/


//        проверка метода getHistory()

        /*manager.showTask(1);
        System.out.println(manager.getHistory());
        System.out.println("/n");
        manager.showEpicTask(1);
        manager.showEpicTask(1);
        manager.showSubTask(1);
        manager.showEpicTask(1);
        manager.showEpicTask(1);
        manager.showEpicTask(1);
        manager.showSubTask(1);
        manager.showEpicTask(1);
        manager.showEpicTask(1);
        manager.showEpicTask(1);
        manager.showSubTask(1);
        manager.showEpicTask(1);
        manager.showEpicTask(1);
        manager.showEpicTask(1);
        manager.showSubTask(1);
        System.out.println(manager.getHistory());
        System.out.println(manager.getHistory().size());*/



    }
}
