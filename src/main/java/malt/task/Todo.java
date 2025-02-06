package malt.task;

public class Todo extends Task {
    /**
     * Constructs a new Todo task with the given description.
     *
     * @param description The description of the to-do task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of the to-do task, including its type.
     *
     * @return A string representation of the to-do task in the format [T] followed by the task details.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Converts the to-do task into a formatted string for file storage.
     *
     * @return A formatted string representing the to-do task, in the format "T | status | description".
     */
    @Override
    public String toFileFormat() {
        return String.format("T | %d | %s", (isDone ? 1 : 0), description);
    }
}
