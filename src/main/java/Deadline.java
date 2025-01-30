import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDate byDate;

    private static final DateTimeFormatter INPUT_OUTPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");

    public Deadline(String description, String byInput) throws MaltException {
        super(description);
        try {
            this.byDate = LocalDate.parse(byInput.trim(), INPUT_OUTPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new MaltException("Invalid date format! Please use yyyy-MM-dd (e.g., 2023-10-15)");
        }
    }

    @Override
    public String toString() {
        String dateStr = (byDate == null)
                ? "???"
                : byDate.format(DISPLAY_FORMAT);
        return "[D]" + super.toString() + " (by: " + dateStr + ")";
    }

    @Override
    public String toFileFormat() {
        String dateStr = (byDate == null)
                ? ""
                : byDate.format(INPUT_OUTPUT_FORMAT);
        return String.format("D | %d | %s | %s",
                (isDone ? 1 : 0),
                description,
                dateStr);
    }
}
