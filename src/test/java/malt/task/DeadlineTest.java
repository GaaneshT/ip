package malt.task;

import org.junit.jupiter.api.Test;
import malt.MaltException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {

    @Test
    public void testDeadlineValid() throws MaltException {
        // Create a Deadline with a valid date
        Deadline deadline = new Deadline("return book", "2023-10-15");

        // Check that the toString() method produces the expected output.
        // Expected output: [D][ ] return book (by: Oct 15 2023)
        String expectedToString = "[D][ ] return book (by: Oct 15 2023)";
        assertEquals(expectedToString, deadline.toString());

        // Optionally, you can also test the file format output.
        String expectedFileFormat = "D | 0 | return book | 2023-10-15";
        assertEquals(expectedFileFormat, deadline.toFileFormat());
    }
}
