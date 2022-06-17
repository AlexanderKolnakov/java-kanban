public class Main {
    public static void main(String[] args) {

        Managers manager = new Managers();
        manager.getDefault().addTask("Задача 1", "описание Задачи 1", Status.DONE);
        manager.getDefault().addTask("Задача 2", "описание Задачи 2", Status.DONE);

        manager.getDefault().addEpicTask("Эпик_Задача 1", "описание Эпик_Задачи 1");
        manager.getDefault().addEpicTask("Эпик_Задача 2", "описание Эпик_Задачи 2");

        manager.getDefault().addSubTask("Подзадача 1 Эпик_Задачи 1", "описание Подзадачи 1",
                1, Status.NEW);
        manager.getDefault().addSubTask("Подзадача 2 Эпик_Задачи 1", "описание Подзадачи 2",
                1, Status.NEW);
        manager.getDefault().addSubTask("Подзадача 3 Эпик_Задачи 1", "описание Подзадачи 3",
                1, Status.NEW);
        manager.getDefault().addSubTask("Подзадача 4 Эпик_Задачи 2", "описание Подзадачи 4",
                2, Status.NEW);
        manager.getDefault().addSubTask("Подзадача 5 Эпик_Задачи 2", "описание Подзадачи 4",
                2, Status.NEW);


//        проверка на изменение статуса епика при изменении статуса подзадач

        /*System.out.println(manager.getDefault().checkEpicStatus(1));
        System.out.println(manager.getDefault().subChangeStatus(2, Status.DONE));
        System.out.println(manager.getDefault().checkEpicStatus(1));
        System.out.println(" ");
        System.out.println(manager.getDefault().checkEpicStatus(1));
        System.out.println(manager.getDefault().subChangeStatus(1, Status.DONE));
        System.out.println(manager.getDefault().subChangeStatus(2, Status.DONE));
        System.out.println(manager.getDefault().subChangeStatus(3, Status.DONE));
        System.out.println(manager.getDefault().checkEpicStatus(1));*/


//        проверка списка подзадач у конкретного эпика
        /*System.out.println(manager.getDefault().showSubTaskToEpic(1));
        manager.getDefault().deleteSubTask(2);
        System.out.println(manager.getDefault().showSubTaskToEpic(1));*/


//        проверка метода getHistory()

        /*manager.getDefault().showTask(1);
        System.out.println(manager.getDefault().getHistory());
        System.out.println("/n");
        manager.getDefault().showEpicTask(1);
        manager.getDefault().showEpicTask(1);
        manager.getDefault().showSubTask(1);
        manager.getDefault().showEpicTask(1);
        manager.getDefault().showEpicTask(1);
        manager.getDefault().showEpicTask(1);
        manager.getDefault().showSubTask(1);
        manager.getDefault().showEpicTask(1);
        manager.getDefault().showEpicTask(1);
        manager.getDefault().showEpicTask(1);
        manager.getDefault().showSubTask(1);
        manager.getDefault().showEpicTask(1);
        System.out.println(manager.getDefault().getHistory());*/



    }
}
