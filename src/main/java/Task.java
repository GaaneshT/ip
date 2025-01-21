public class Task {
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
        // Return "X" if done, or " " otherwise
        return (isDone ? "X" : " ");
    }

    @Override
    public String toString() {
        // Example: [X] read book
        return "[" + getStatusIcon() + "] " + description;
    }
}
