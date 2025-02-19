package malt.task;

public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructs an Event task with a description, start time, and end time.
     *
     * @param description The description of the event.
     * @param from        The starting time of the event.
     * @param to          The ending time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        assert from != null && !from.trim().isEmpty() : "Event 'from' time cannot be null or empty!";
        assert to != null && !to.trim().isEmpty() : "Event 'to' time cannot be null or empty!";
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the Event task.
     *
     * @return A string in the format: [E][status] description (from: start time to: end time)
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    /**
     * Returns the file format representation of the Event task.
     *
     * @return A formatted string for file storage, containing task type, status, description, start, and end time.
     */
    @Override
    public String toFileFormat() {
        return String.format("E | %d | %s | %s | %s", (isDone ? 1 : 0), description, from, to);
    }
}
