package malt.task;

import malt.MaltException;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with the given description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        assert description != null && !description.trim().isEmpty() : "Task description cannot be null or empty!";
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public abstract String toFileFormat();

    /**
     * Returns a string representation of the Event task.
     *
     * @return A string in the format: [E][status] description (from: start time to: end time)
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Parses a single line from the data file and constructs the corresponding Task object.
     *
     * @param line A string representing a task in file format.
     * @return A Task object.
     * @throws MaltException if the line is corrupted or unrecognized.
     */
    public static Task fromFileFormat(String line) throws MaltException {
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            throw new MaltException("Corrupted line (not enough parts): " + line);
        }

        String taskType = parts[0];
        int doneStatus;
        try {
            doneStatus = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new MaltException("Corrupted line (invalid doneStatus): " + line);
        }
        String description = parts[2];

        switch (taskType) {
        case "T":
            if (parts.length != 3) {
                throw new MaltException("Corrupted Todo line: " + line);
            }
            Todo todo = new Todo(description);
            if (doneStatus == 1) {
                todo.markAsDone();
            }
            return todo;
        case "D":
            if (parts.length != 4) {
                throw new MaltException("Corrupted Deadline line: " + line);
            }
            String by = parts[3];
            Deadline deadline = new Deadline(description, by);
            if (doneStatus == 1) {
                deadline.markAsDone();
            }
            return deadline;
        case "E":
            if (parts.length != 5) {
                throw new MaltException("Corrupted Event line: " + line);
            }
            String from = parts[3];
            String to = parts[4];
            Event event = new Event(description, from, to);
            if (doneStatus == 1) {
                event.markAsDone();
            }
            return event;
        default:
            throw new MaltException("Unrecognized task type: " + taskType);
        }
    }
}
