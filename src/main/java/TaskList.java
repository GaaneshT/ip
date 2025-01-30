import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> existingTasks) {
        this.tasks = new ArrayList<>(existingTasks);
    }

    public void addTask(Task t) {
        tasks.add(t);
    }

    public Task removeTask(int index) throws MaltException {
        if (index < 0 || index >= tasks.size()) {
            throw new MaltException("Invalid index for delete command!");
        }
        return tasks.remove(index);
    }

    public Task getTask(int index) throws MaltException {
        if (index < 0 || index >= tasks.size()) {
            throw new MaltException("Invalid index!");
        }
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public List<Task> getAllTasks() {
        return tasks;
    }
}
