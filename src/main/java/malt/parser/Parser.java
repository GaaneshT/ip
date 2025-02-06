package malt.parser;
import java.util.List;
import malt.task.Task;
import malt.task.Todo;
import malt.task.Deadline;
import malt.task.Event;
import malt.task.TaskList;
import malt.ui.Ui;
import malt.storage.Storage;
import malt.MaltException;


public class Parser {

    /**
     * Parses the user's input string and executes the corresponding command.
     *
     * @param input   The raw command string, e.g. "todo read book"
     * @param tasks   The TaskList to operate on (add, delete, mark, etc.)
     * @param ui      The Ui to handle user interactions (printing messages, etc.)
     * @param storage The Storage to save/load tasks
     * @return true if the command indicates the app should exit, false otherwise
     * @throws MaltException if there's a problem parsing or executing the command
     */
    public static boolean parseAndExecute(String input, TaskList tasks, Ui ui, Storage storage) throws MaltException {
        // Split the first word as 'command', the rest as 'arguments'
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        String argument = (parts.length > 1) ? parts[1].trim() : "";

        switch (command) {
            case "bye":
                // User wants to exit
                return handleBye(ui);

            case "list":
                handleList(tasks, ui);
                return false;

            case "mark":
                handleMark(argument, tasks, ui, storage);
                return false;

            case "unmark":
                handleUnmark(argument, tasks, ui, storage);
                return false;

            case "delete":
                handleDelete(argument, tasks, ui, storage);
                return false;

            case "todo":
                handleTodo(argument, tasks, ui, storage);
                return false;

            case "deadline":
                handleDeadline(argument, tasks, ui, storage);
                return false;

            case "event":
                handleEvent(argument, tasks, ui, storage);
                return false;

            default:
                throw new MaltException("I'm sorry, but I don't know what that means!");
        }
    }

    // --------------------------------------------------
    // COMMAND HANDLERS
    // --------------------------------------------------

    /**
     * Handles the "bye" command by displaying a goodbye message.
     *
     * @param ui The UI handler for user interaction.
     * @return true indicating that the application should exit.
     */
    private static boolean handleBye(Ui ui) {
        ui.showGoodbye();
        // Returning true signals the main loop to exit
        return true;
    }

    /**
     * Handles the "list" command by displaying all tasks.
     *
     * @param tasks The list of tasks.
     * @param ui    The UI handler for user interaction.
     */
    private static void handleList(TaskList tasks, Ui ui) {
        ui.showLine();
        if (tasks.size() == 0) {
            System.out.println(" You haven't added any tasks yet!");
        } else {
            List<Task> allTasks = tasks.getAllTasks();
            for (int i = 0; i < allTasks.size(); i++) {
                System.out.println((i + 1) + ". " + allTasks.get(i));
            }
        }
        ui.showLine();
    }

    /**
     * Handles the "mark" command by marking a task as done.
     *
     * @param arg     The index of the task to be marked.
     * @param tasks   The task list.
     * @param ui      The UI handler for user interaction.
     * @param storage The storage handler for saving tasks.
     * @throws MaltException If the index is invalid.
     */
    private static void handleMark(String arg, TaskList tasks, Ui ui, Storage storage) throws MaltException {
        try {
            int index = Integer.parseInt(arg);
            // Convert 1-based to 0-based if needed; depends on your TaskList design
            Task task = tasks.getTask(index - 1);
            task.markAsDone();

            ui.showLine();
            System.out.println(" Perfect, marking this task as done now:");
            System.out.println("   " + task);
            ui.showLine();

            // Save after modifying tasks
            storage.saveTasks(tasks.getAllTasks());
        } catch (NumberFormatException e) {
            throw new MaltException(" Invalid index for mark command!");
        }
    }

    /**
     * Handles the "unmark" command by marking a task as not done.
     *
     * @param arg     The index of the task to be unmarked.
     * @param tasks   The task list.
     * @param ui      The UI handler for user interaction.
     * @param storage The storage handler for saving tasks.
     * @throws MaltException If the index is invalid.
     */
    private static void handleUnmark(String arg, TaskList tasks, Ui ui, Storage storage) throws MaltException {
        try {
            int index = Integer.parseInt(arg);
            Task task = tasks.getTask(index - 1);
            task.markAsNotDone();

            ui.showLine();
            System.out.println(" OK, I've unmarked this task:");
            System.out.println("   " + task);
            ui.showLine();

            // Save after modifying tasks
            storage.saveTasks(tasks.getAllTasks());
        } catch (NumberFormatException e) {
            throw new MaltException(" Invalid index for unmark command!");
        }
    }

    /**
     * Handles the "delete" command by removing a task.
     *
     * @param arg     The index of the task to be deleted.
     * @param tasks   The task list.
     * @param ui      The UI handler for user interaction.
     * @param storage The storage handler for saving tasks.
     * @throws MaltException If the index is invalid.
     */
    private static void handleDelete(String arg, TaskList tasks, Ui ui, Storage storage) throws MaltException {
        try {
            int index = Integer.parseInt(arg);
            Task removed = tasks.removeTask(index - 1);

            ui.showLine();
            System.out.println(" Noted. I've removed this task:");
            System.out.println("   " + removed);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list. Get working :(");
            ui.showLine();

            storage.saveTasks(tasks.getAllTasks());
        } catch (NumberFormatException e) {
            throw new MaltException(" Invalid index for delete command!");
        }
    }

    /**
     * Handles the "todo" command by adding a new todo task.
     *
     * @param arg     The description of the todo task.
     * @param tasks   The task list.
     * @param ui      The UI handler for user interaction.
     * @param storage The storage handler for saving tasks.
     * @throws MaltException If the description is empty.
     */
    private static void handleTodo(String arg, TaskList tasks, Ui ui, Storage storage) throws MaltException {
        if (arg.isBlank()) {
            throw new MaltException(" OOPS!!! The description of a todo cannot be empty.");
        }

        Todo todo = new Todo(arg);
        tasks.addTask(todo);

        // Save
        storage.saveTasks(tasks.getAllTasks());

        // Print confirmation
        ui.showLine();
        System.out.println(" Adding this task:");
        System.out.println("   " + todo);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list! Get working :(");
        ui.showLine();
    }


    private static void handleDeadline(String arg, TaskList tasks, Ui ui, Storage storage) throws MaltException {
        // E.g.: "return book /by 2023-10-15"
        String[] parts = arg.split("/by", 2);
        if (parts.length < 2) {
            throw new MaltException("""
                    OOPS!!! Please specify the deadline in the format:
                       deadline <description> /by <yyyy-MM-dd>
                    """);
        }
        String description = parts[0].trim();
        String byInput = parts[1].trim();

        if (description.isEmpty() || byInput.isEmpty()) {
            throw new MaltException(" OOPS!!! Both description and /by part cannot be empty.");
        }

        Deadline deadline = new Deadline(description, byInput);
        tasks.addTask(deadline);
        storage.saveTasks(tasks.getAllTasks());

        ui.showLine();
        System.out.println(" Adding this task:");
        System.out.println("   " + deadline);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list! Get working :(");
        ui.showLine();
    }


    private static void handleEvent(String arg, TaskList tasks, Ui ui, Storage storage) throws MaltException {
        // e.g.: "project meeting /from Monday 2pm /to 4pm"
        String[] fromSplit = arg.split("/from", 2);
        if (fromSplit.length < 2) {
            throw new MaltException("""
                    OOPS!!! Please specify the event in the format:
                       event <description> /from <start> /to <end>
                    """);
        }
        String description = fromSplit[0].trim();

        String[] toSplit = fromSplit[1].split("/to", 2);
        if (toSplit.length < 2) {
            throw new MaltException(" OOPS!!! An event must have both /from and /to time frames.");
        }
        String fromTime = toSplit[0].trim();
        String toTime = toSplit[1].trim();

        if (description.isEmpty() || fromTime.isEmpty() || toTime.isEmpty()) {
            throw new MaltException(" OOPS!!! Make sure description, /from, and /to parts are not empty.");
        }

        Event event = new Event(description, fromTime, toTime);
        tasks.addTask(event);
        storage.saveTasks(tasks.getAllTasks());

        ui.showLine();
        System.out.println(" Adding this task:");
        System.out.println("   " + event);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list! Get working :(");
        ui.showLine();
    }
}
