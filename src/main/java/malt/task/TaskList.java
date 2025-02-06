package malt.task;

import java.util.ArrayList;
import java.util.List;
import malt.MaltException;

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

    /**
     * Finds tasks that contain the given keyword in their description.
     * @param keyword The keyword to search for.
     * @return A list of matching tasks.
     */
    public List<Task> findTasks(String keyword) {
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.description.contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }


    public int size() {
        return tasks.size();
    }

    public List<Task> getAllTasks() {
        return tasks;
    }
}
