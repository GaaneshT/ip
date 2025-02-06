package malt;

import java.io.File;


import malt.ui.Ui;
import malt.storage.Storage;
import malt.task.TaskList;
import malt.parser.Parser;
import malt.MaltException;


public class Malt {
    private static final String DATA_FILE_PATH = "data/malt.txt";

    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    /**
     * Constructs the Malt chatbot, initializing UI, storage, and tasks.
     *
     * @param filePath path to the data file storing tasks (e.g., "data/malt.txt")
     */
    public Malt(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        ensureDataDirectoryExists(filePath);

        tasks = new TaskList(storage.loadTasks());
    }



    private void ensureDataDirectoryExists(String filePath) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
    }

    /**
     * Runs the main loop of the Malt program.
     * Shows a welcome message, then continuously reads commands
     * until the 'bye' command is encountered.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String input = ui.readCommand();
            try {
                isExit = Parser.parseAndExecute(input, tasks, ui, storage);
            } catch (MaltException e) {
                ui.showError(e);
            }
        }
//        ui.showGoodbye();
    }

    /**
     * The main entry point for the Malt program.
     * Initializes a Malt instance with the default data file path, then runs it.
     */
    public static void main(String[] args) {
        new Malt(DATA_FILE_PATH).run();
    }
}
