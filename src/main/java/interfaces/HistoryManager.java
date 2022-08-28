package interfaces;
import taskTracker.Task;
import java.util.List;

public interface HistoryManager {
    void addHistory(Task task);
    void remove(int id);
    List<Task> getHistory();

    void deleteAllHistory();
}
