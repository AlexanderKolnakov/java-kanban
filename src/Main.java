public class Main {
    private static final String STATUS_NEW = "NEW";
    private static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    private static final String STATUS_DONE = "DONE";

    public static void main(String[] args) {

        Manager manager = new Manager();
        manager.addTask("Задача 1", "описание Задачи 1", 111, STATUS_DONE);
        manager.addTask("Задача 2", "описание Задачи 2", 222, STATUS_NEW);

        manager.addEpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1", 11);
        manager.addEpicTask("Эпик_Задача 2", "описание Эпик_Задачи 2", 22);

        manager.addSubTask("Подзадача 1 Эпик_Задачи 1", "описание Подзадачи 1", 1,
                11, STATUS_NEW);
        manager.addSubTask("Подзадача 2 Эпик_Задачи 1", "описание Подзадачи 2",
                2, 11, STATUS_NEW);
        manager.addSubTask("Подзадача 3 Эпик_Задачи 1", "описание Подзадачи 3",
                3, 11, STATUS_NEW);
        manager.addSubTask("Подзадача 4 Эпик_Задачи 2", "описание Подзадачи 4",
                4, 22, STATUS_NEW);
        manager.addSubTask("Подзадача 5 Эпик_Задачи 2", "описание Подзадачи 4",
                5, 22, STATUS_NEW);


//        проверка на изменение статуса епика при изменении статуса подзадач

        System.out.println(manager.checkEpicStatus(11));
        System.out.println(manager.subChangeStatus(2, STATUS_DONE));
        System.out.println(manager.checkEpicStatus(11));
        System.out.println(" ");
        System.out.println(manager.checkEpicStatus(11));
        System.out.println(manager.subChangeStatus(1, STATUS_DONE));
        System.out.println(manager.subChangeStatus(2, STATUS_DONE));
        System.out.println(manager.checkEpicStatus(11));


//        проверка списка подзадач у конкретного эпика

        /*System.out.println(manager.showSubTaskToEpic(11));
        manager.deleteSubTask(2);
        System.out.println(manager.showSubTaskToEpic(11));*/

    }
}
