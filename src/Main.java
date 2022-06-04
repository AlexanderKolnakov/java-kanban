public class Main {
    private static final String STATUS_NEW = "NEW";
    private static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    private static final String STATUS_DONE = "DONE";

    public static void main(String[] args) {

        Manager manager = new Manager();
        manager.addEpicTask("эпик 1", "что-то 1", 11);
        manager.addEpicTask("эпик 2", "что-то 1", 22);

        manager.addSubTask("саб 1", "что-то 3", 1, 11, STATUS_NEW);
        manager.addSubTask("саб 2", "что-то 4",2, 11, STATUS_NEW);
        manager.addSubTask("саб 3", "что-то 5",3, 22, STATUS_NEW);



//        проверка на изменение статуса епика при изменении статуса подзадач

        /*System.out.println(manager.checkEpicStatus(11));
        System.out.println(manager.subChangeStatus(2, STATUS_DONE));
        System.out.println(manager.checkEpicStatus(11));
        System.out.println(" ");
        System.out.println(manager.checkEpicStatus(11));
        System.out.println(manager.subChangeStatus(1, STATUS_DONE));
        System.out.println(manager.subChangeStatus(2, STATUS_DONE));
        System.out.println(manager.checkEpicStatus(11));*/


//        проверка списка подзадач у конкретного эпика

        /*System.out.println(manager.showSubTaskToEpic(11));
        manager.deleteSubTask(2);
        System.out.println(manager.showSubTaskToEpic(11));*/




//        System.out.println(manager.showEpicTask(11));
//        System.out.println(manager.showSubTask(1));
//        manager.deleteEpicTask(11);
//
//        System.out.println(" ");
//
//        System.out.println(manager.showAllTusk());
//        manager.deleteSubTask(3);
//        System.out.println(manager.showAllTusk());
//        System.out.println(manager.updateSubTask("саб нов", 1, 11, "In"));





//        System.out.println(manager.showAllTusk());

    }
}
