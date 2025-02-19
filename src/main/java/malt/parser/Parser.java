package malt.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import malt.MaltException;
import malt.storage.Storage;
import malt.task.Deadline;
import malt.task.Event;
import malt.task.Task;
import malt.task.TaskList;
import malt.task.Todo;
import malt.ui.Ui;

public class Parser {

    /**
     * A map of command aliases to their "real" commands.
     * E.g., "t" -> "todo", "dl" -> "deadline", etc.
     */
    private static final Map<String, String> COMMAND_ALIASES = new HashMap<>();

    static {
        COMMAND_ALIASES.put("t", "todo");
        COMMAND_ALIASES.put("dl", "deadline");
        COMMAND_ALIASES.put("ev", "event");
        COMMAND_ALIASES.put("b", "bye");
        COMMAND_ALIASES.put("c", "clear");
    }

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

        assert input != null : "Command input should never be null!";
        assert tasks != null : "TaskList should never be null!";
        assert ui != null : "UI should never be null!";
        assert storage != null : "Storage should never be null!";

        // Split the input by whitespace
        String[] tokens = input.trim().split("\\s+");
        if (tokens.length == 0) {
            throw new MaltException("No command provided!");
        }

        String rawCommand = tokens[0].toLowerCase();
        String command = COMMAND_ALIASES.getOrDefault(rawCommand, rawCommand);

        String[] argTokens = Arrays.copyOfRange(tokens, 1, tokens.length);

        switch (command) {
        case "bye":
            return handleBye(ui);

        case "list":
            handleList(tasks, ui);
            break;

        case "mark":
            handleMark(joinArgs(argTokens), tasks, ui, storage);
            break;

        case "unmark":
            handleUnmark(joinArgs(argTokens), tasks, ui, storage);
            break;

        case "delete":
            handleDelete(joinArgs(argTokens), tasks, ui, storage);
            break;

        case "todo":
            // Minimal approach: no flags needed; just treat everything as description
            handleTodo(joinArgs(argTokens), tasks, ui, storage);
            break;

        case "deadline":
            // Flexible approach with /by anywhere in the input
            handleDeadlineFlexible(argTokens, tasks, ui, storage);
            break;

        case "event":
            // Flexible approach with /from and /to anywhere in the input
            handleEventFlexible(argTokens, tasks, ui, storage);
            break;

        case "find":
            handleFind(joinArgs(argTokens), tasks, ui);
            break;

        case "clear":
            handleClear(tasks, ui, storage);
            break;

        default:
            throw new MaltException("I'm sorry, but I don't know what that means!");
        }
        return false;
    }

    // ----------------------------------------------------------------------
    // COMMAND HANDLERS (unchanged from your original, except for flexible ones)
    // ----------------------------------------------------------------------

    private static boolean handleBye(Ui ui) {
        ui.showGoodbye();
        return true;
    }

    private static void handleClear(TaskList tasks, Ui ui, Storage storage) throws MaltException {
        tasks.clear();
        storage.saveTasks(tasks.getAllTasks());
        ui.showLine();
        System.out.println("All tasks have been cleared!");
        ui.showLine();
    }


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

    private static void handleFind(String keyword, TaskList tasks, Ui ui) {
        ui.showLine();
        List<Task> matchingTasks = tasks.findTasks(keyword);
        if (matchingTasks.isEmpty()) {
            System.out.println("No matching tasks found.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println((i + 1) + ". " + matchingTasks.get(i));
            }
        }
        ui.showLine();
    }

    private static int parseTaskIndex(String arg) throws MaltException {
        try {
            return Integer.parseInt(arg.trim());
        } catch (NumberFormatException e) {
            throw new MaltException("Invalid task index provided!");
        }
    }

    private static void handleMark(String arg, TaskList tasks, Ui ui, Storage storage) throws MaltException {
        int index = parseTaskIndex(arg);
        Task task = tasks.getTask(index - 1);
        task.markAsDone();
        printTaskConfirmation(ui, "Perfect, marking this task as done now:", task);
        storage.saveTasks(tasks.getAllTasks());
    }

    private static void handleUnmark(String arg, TaskList tasks, Ui ui, Storage storage) throws MaltException {
        int index = parseTaskIndex(arg);
        Task task = tasks.getTask(index - 1);
        task.markAsNotDone();
        printTaskConfirmation(ui, "OK, I've unmarked this task:", task);
        storage.saveTasks(tasks.getAllTasks());
    }

    private static void handleDelete(String arg, TaskList tasks, Ui ui, Storage storage) throws MaltException {
        int index = parseTaskIndex(arg);
        Task removed = tasks.removeTask(index - 1);
        ui.showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + tasks.size() + " tasks in the list. Get working :(");
        ui.showLine();
        storage.saveTasks(tasks.getAllTasks());
    }

    private static void handleTodo(String arg, TaskList tasks, Ui ui, Storage storage) throws MaltException {
        if (arg.isBlank()) {
            throw new MaltException("OOPS!!! The description of a todo cannot be empty.");
        }
        Todo todo = new Todo(arg);
        tasks.addTask(todo);
        storage.saveTasks(tasks.getAllTasks());
        printTaskConfirmation(ui, "Adding this task:", todo);
        System.out.println("Now you have " + tasks.size() + " tasks in the list! Get working :(");
        ui.showLine();
    }

    // ----------------------------------------------------------------------
    // FLEXIBLE SYNTAX HANDLERS
    // ----------------------------------------------------------------------

    /**
     * Flexible parsing for 'deadline' command:
     * Allows /by <date> to appear anywhere in the user input tokens.
     * e.g. "deadline /by 2023-10-15 return book"
     * "deadline return book /by 2023-10-15"
     */
    private static void handleDeadlineFlexible(String[] tokens, TaskList tasks, Ui ui, Storage storage) throws MaltException {
        String byDate = "";
        StringBuilder descriptionBuilder = new StringBuilder();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("/by")) {
                // The next token should be the date
                if (i + 1 < tokens.length) {
                    byDate = tokens[++i];
                } else {
                    throw new MaltException("Please provide a date after /by.");
                }
            } else {
                // Part of the description
                descriptionBuilder.append(tokens[i]).append(" ");
            }
        }

        String description = descriptionBuilder.toString().trim();
        if (description.isEmpty() || byDate.isEmpty()) {
            throw new MaltException("OOPS!!! Both description and /by part cannot be empty.");
        }

        Deadline deadline = new Deadline(description, byDate);
        tasks.addTask(deadline);
        storage.saveTasks(tasks.getAllTasks());
        printTaskConfirmation(ui, "Adding this task:", deadline);
        System.out.println("Now you have " + tasks.size() + " tasks in the list! Get working :(");
        ui.showLine();
    }

    /**
     * Flexible parsing for 'event' command:
     * Allows /from <start> and /to <end> to appear in any order.
     * e.g. "event project meeting /from Monday 2pm /to 4pm"
     * "event /from Monday 2pm /to 4pm project meeting"
     */
    private static void handleEventFlexible(String[] tokens, TaskList tasks, Ui ui, Storage storage) throws MaltException {
        String fromTime = "";
        String toTime = "";
        StringBuilder descriptionBuilder = new StringBuilder();

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.equals("/from")) {
                if (i + 1 < tokens.length) {
                    fromTime = tokens[++i];
                } else {
                    throw new MaltException("Please provide a start time after /from.");
                }
            } else if (token.equals("/to")) {
                if (i + 1 < tokens.length) {
                    toTime = tokens[++i];
                } else {
                    throw new MaltException("Please provide an end time after /to.");
                }
            } else {
                descriptionBuilder.append(token).append(" ");
            }
        }

        String description = descriptionBuilder.toString().trim();
        if (description.isEmpty() || fromTime.isEmpty() || toTime.isEmpty()) {
            throw new MaltException("OOPS!!! Make sure description, /from, and /to parts are not empty.");
        }

        Event event = new Event(description, fromTime, toTime);
        tasks.addTask(event);
        storage.saveTasks(tasks.getAllTasks());
        printTaskConfirmation(ui, "Adding this task:", event);
        System.out.println("Now you have " + tasks.size() + " tasks in the list! Get working :(");
        ui.showLine();
    }

    /**
     * Prints a confirmation message for a task-related command.
     *
     * @param ui      The UI handler.
     * @param message The confirmation message.
     * @param task    The task involved.
     */
    private static void printTaskConfirmation(Ui ui, String message, Task task) {
        ui.showLine();
        System.out.println(message);
        System.out.println("  " + task);
        ui.showLine();
    }

    /**
     * Utility method to join an array of tokens into a single space-separated string.
     *
     * @param tokens The tokens to join.
     * @return A single string containing all tokens separated by spaces.
     */
    private static String joinArgs(String[] tokens) {
        return String.join(" ", tokens).trim();
    }
}
