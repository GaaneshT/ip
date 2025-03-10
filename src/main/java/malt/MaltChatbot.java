package malt;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import malt.parser.Parser;
import malt.storage.Storage;
import malt.task.TaskList;
import malt.ui.Ui;

public class MaltChatbot {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    public MaltChatbot() {
        ui = new Ui();
        storage = new Storage("data/malt.txt");
        tasks = new TaskList(storage.loadTasks());

        assert ui != null : "UI should be initialized!";
        assert storage != null : "Storage should be initialized!";
        assert tasks != null : "TaskList should be initialized!";
    }

    /**
     * Processes user input and returns Malt's response.
     *
     * @param input User input string.
     * @return Malt's response.
     */
    public String getResponse(String input) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            Parser.parseAndExecute(input, tasks, ui, storage);
        } catch (MaltException e) {
            // Restore original stream before returning error
            System.setOut(originalOut);
            return "Error: " + e.getMessage();
        } finally {
            System.setOut(originalOut);
        }

        String response = outputStream.toString().trim();
        assert response != null && !response.isEmpty() : "Response should not be null or empty!";
        return response;
    }
}
