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
    public static boolean parseAndExecute(String input, TaskList tasks, Ui ui, Storage storage)
            throws MaltException {
        assert input != null : "Command input should never be null!";
        assert tasks != null : "TaskList should never be null!";
        assert ui != null : "UI should never be null!";
        assert storage != null : "Storage should never be null!";

        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        String argument = (parts.length > 1) ? parts[1].trim() : "";

        switch (command) {
        case "bye":
            return handleBye(ui);
        case "list":
            handleList(tasks, ui);
            break;
        case "mark":
            handleMark(argument, tasks, ui, storage);
            break;
        case "unmark":
            handleUnmark(argument, tasks, ui, storage);
            break;
        case "delete":
            handleDelete(argument, tasks, ui, storage);
            break;
        case "todo":
            handleTodo(argument, tasks, ui, storage);
            break;
        case "deadline":
            handleDeadline(argument, tasks, ui, storage);
            break;
        case "event":
            handleEvent(argument, tasks, ui, storage);
            break;
        case "find":
            handleFind(argument, tasks, ui);
            break;
        default:
            throw new MaltException("I'm sorry, but I don't know what that means!");
        }
        return false;
    }

    // ----------------------
    // COMMAND HANDLERS
    // ----------------------

    private static boolean handleBye(Ui ui) {
        ui.showGoodbye();
        return true;
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

    private static void handleMark(String arg, TaskList tasks, Ui ui, Storage storage)
            throws MaltException {
        int index = parseTaskIndex(arg);
        Task task = tasks.getTask(index - 1);
        task.markAsDone();
        printTaskConfirmation(ui, "Perfect, marking this task as done now:", task);
        storage.saveTasks(tasks.getAllTasks());
    }

    private static void handleUnmark(String arg, TaskList tasks, Ui ui, Storage storage)
            throws MaltException {
        int index = parseTaskIndex(arg);
        Task task = tasks.getTask(index - 1);
        task.markAsNotDone();
        printTaskConfirmation(ui, "OK, I've unmarked this task:", task);
        storage.saveTasks(tasks.getAllTasks());
    }

    private static void handleDelete(String arg, TaskList tasks, Ui ui, Storage storage)
            throws MaltException {
        int index = parseTaskIndex(arg);
        Task removed = tasks.removeTask(index - 1);
        ui.showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + tasks.size() + " tasks in the list. Get working :(");
        ui.showLine();
        storage.saveTasks(tasks.getAllTasks());
    }

    private static void handleTodo(String arg, TaskList tasks, Ui ui, Storage storage)
            throws MaltException {
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

    private static void handleDeadline(String arg, TaskList tasks, Ui ui, Storage storage)
            throws MaltException {
        String[] parts = arg.split("/by", 2);
        if (parts.length < 2) {
            throw new MaltException(
                    "OOPS!!! Please specify the deadline in the format:\n" +
                            "   deadline <description> /by <yyyy-MM-dd>");
        }
        String description = parts[0].trim();
        String byInput = parts[1].trim();
        if (description.isEmpty() || byInput.isEmpty()) {
            throw new MaltException("OOPS!!! Both description and /by part cannot be empty.");
        }
        Deadline deadline = new Deadline(description, byInput);
        tasks.addTask(deadline);
        storage.saveTasks(tasks.getAllTasks());
        printTaskConfirmation(ui, "Adding this task:", deadline);
        System.out.println("Now you have " + tasks.size() + " tasks in the list! Get working :(");
        ui.showLine();
    }

    private static void handleEvent(String arg, TaskList tasks, Ui ui, Storage storage)
            throws MaltException {
        String[] fromSplit = arg.split("/from", 2);
        if (fromSplit.length < 2) {
            throw new MaltException(
                    "OOPS!!! Please specify the event in the format:\n" +
                            "   event <description> /from <start> /to <end>");
        }
        String description = fromSplit[0].trim();
        String[] toSplit = fromSplit[1].split("/to", 2);
        if (toSplit.length < 2) {
            throw new MaltException("OOPS!!! An event must have both /from and /to time frames.");
        }
        String fromTime = toSplit[0].trim();
        String toTime = toSplit[1].trim();
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
}
