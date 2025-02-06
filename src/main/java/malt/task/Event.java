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
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    /**
     * Converts the event task into a formatted string for file storage.
     *
     * @return A formatted string representing the event task, in the format "E | status | description | from | to".
     */
    @Override
    public String toFileFormat() {
        return String.format("E | %d | %s | %s | %s", (isDone ? 1 : 0), description, from, to);
    }
}
