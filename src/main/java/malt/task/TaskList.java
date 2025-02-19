package malt.task;

import java.util.ArrayList;
import java.util.List;

import malt.MaltException;

public class TaskList {
    private final List<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with an existing list of tasks.
     *
     * @param existingTasks The list of existing tasks to initialize the TaskList.
     */
    public TaskList(List<Task> existingTasks) {
        this.tasks = new ArrayList<>(existingTasks);
    }

    /**
     * Adds a task to the TaskList.
     *
     * @param t The task to be added.
     */
    public void addTask(Task t) {
        assert t != null : "Task being added should not be null!";
        tasks.add(t);
    }

    /**
     * Removes a task from the TaskList by index.
     *
     * @param index The index of the task to remove.
     * @return The removed task.
     * @throws MaltException If the index is invalid.
     */
    public Task removeTask(int index) throws MaltException {
//        if (index < 0 || index >= tasks.size()) {
//            throw new MaltException("Invalid index for delete command!");
//        }
        assert index >= 0 && index < tasks.size() : "Invalid index removal attempt!";
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the TaskList by index.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the specified index.
     * @throws MaltException If the index is invalid.
     */
    public Task getTask(int index) throws MaltException {
//        if (index < 0 || index >= tasks.size()) {
//            throw new MaltException("Invalid index!");
//        }
        assert index >= 0 && index < tasks.size() : "Invalid index access in TaskList!";

        return tasks.get(index);
    }

    /**
     * Finds tasks that contain the given keyword in their description.
     *
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

    /**
     * Returns the number of tasks in the TaskList.
     *
     * @return The number of tasks.
     */

    public int size() {
        return tasks.size();
    }


    /**
     * Returns a list of all tasks.
     *
     * @return The list of tasks.
     */
    public List<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Removes all tasks from the task list.
     * This action cannot be undone as it will clear all stored tasks.
     */
    public void clear() {
        tasks.clear();
    }

}

