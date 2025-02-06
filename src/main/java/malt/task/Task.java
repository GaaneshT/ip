package malt.task;

import malt.MaltException;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
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

    /**
     * Each subclass (Todo, Deadline, Event) must implement this method
     * to produce a string in the correct format, e.g.,
     * - T | 1 | read book
     * - D | 0 | return book | Sunday
     * - E | 1 | project meeting | Monday 2pm | 4pm
     */
    public abstract String toFileFormat();

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Parses a single line from the data file and constructs the
     * corresponding Task object (Todo, Deadline, or Event).
     *
     * @param line A string in the format, e.g.:
     *             "T | 1 | read book"
     *             "D | 0 | return book | Sunday"
     *             "E | 1 | project meeting | Monday 2pm | 4pm"
     * @return a Task object (Todo, Deadline, or Event)
     * @throws MaltException if the line is corrupted or unrecognized
     */
    public static Task fromFileFormat(String line) throws MaltException {
        // Example line: "T | 1 | read book"
        // We split on the " | " delimiter. Using regex: "\\s*\\|\\s*"
        // means "split on '|' and allow for optional spaces around it."
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
            // Format: D | 0/1 | <description> | <by>
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
            // Format: E | 0/1 | <description> | <from> | <to>
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
