import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements  HistoryManager {
    static List<Task> dataRequest = new ArrayList<>();
    @Override
    public void addHistory(Task task) {
        dataRequest.add(task);
        if (dataRequest.size() > 10) {
            dataRequest.remove(0);
        }
    }
    @Override
    public List<Task> getHistory() {
        return dataRequest;
    }
}
