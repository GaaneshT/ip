import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Malt {

    private static final List<Task> tasks = new ArrayList<>();

    private static void displayLogo() {
        String logo = """
                          _____                    _____                    _____        _____         \s
                         /\\    \\                  /\\    \\                  /\\    \\      /\\    \\        \s
                        /::\\____\\                /::\\    \\                /::\\____\\    /::\\    \\       \s
                       /::::|   |               /::::\\    \\              /:::/    /    \\:::\\    \\      \s
                      /:::::|   |              /::::::\\    \\            /:::/    /      \\:::\\    \\     \s
                     /::::::|   |             /:::/\\:::\\    \\          /:::/    /        \\:::\\    \\    \s
                    /:::/|::|   |            /:::/__\\:::\\    \\        /:::/    /          \\:::\\    \\   \s
                   /:::/ |::|   |           /::::\\   \\:::\\    \\      /:::/    /           /::::\\    \\  \s
                  /:::/  |::|___|______    /::::::\\   \\:::\\    \\    /:::/    /           /::::::\\    \\ \s
                 /:::/   |::::::::\\    \\  /:::/\\:::\\   \\:::\\    \\  /:::/    /           /:::/\\:::\\    \\\s
                /:::/    |:::::::::\\____\\/:::/  \\:::\\   \\:::\\____\\/:::/____/           /:::/  \\:::\\____\\
                \\::/    / ~~~~~/:::/    /\\::/    \\:::\\  /:::/    /\\:::\\    \\          /:::/    \\::/    /
                 \\/____/      /:::/    /  \\/____/ \\:::\\/:::/    /  \\:::\\    \\        /:::/    / \\/____/\s
                             /:::/    /            \\::::::/    /    \\:::\\    \\      /:::/    /         \s
                            /:::/    /              \\::::/    /      \\:::\\    \\    /:::/    /          \s
                           /:::/    /               /:::/    /        \\:::\\    \\   \\::/    /           \s
                          /:::/    /               /:::/    /          \\:::\\    \\   \\/____/            \s
                         /:::/    /               /:::/    /            \\:::\\    \\                     \s
                        /:::/    /               /:::/    /              \\:::\\____\\                    \s
                        \\::/    /                \\::/    /                \\::/    /                    \s
                         \\/____/                  \\/____/                  \\/____/                     \s
                                                                                                       \s
                
                """;
        System.out.println(logo);
    }

    /**
     * Prints the greeting message to the user.
     */
    private static void greetings() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hey! I'm Malt, like the chocolate Maltesers hehe");
        System.out.println(" What can I help you with?");
        System.out.println("____________________________________________________________");
    }

    /**
     * Checks if the user wants to exit the program.
     *
     * @param input The user input to check.
     * @return True if the input matches "bye" (case-insensitive), false otherwise.
     */
    private static boolean isExitCommand(String input) {
        return input.equalsIgnoreCase("bye");
    }

    /**
     * Reads user input from the console.
     *
     * @param scanner The Scanner instance for reading input.
     * @return The user-provided input as a string.
     */
    private static String getUserInput(Scanner scanner) {
        return scanner.nextLine();
    }

    /**
     * Displays a farewell message to the user when the program exits.
     * The message is framed with decorative lines for visual clarity.
     */
    private static void goodbyeMessage() {
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints the user input back to them (echo functionality).
     *
     * @param input The user input to echo.
     */
    private static void echoInput(String input) {
        System.out.println("____________________________________________________________");
        System.out.println(" " + input);
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints a list of all tasks the user has added so far.
     */
    private static void listTasks() {
        System.out.println("____________________________________________________________");
        if (tasks.isEmpty()) {
            System.out.println(" You haven't added any tasks yet!");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
        System.out.println("____________________________________________________________");
    }


    /**
     * Handles a single user command by parsing and executing it.
     *
     * <p>If the command is "bye", this method will print a goodbye message and return {@code true},
     * signaling the caller (usually {@code main()}) to exit. Otherwise, it delegates to the
     * appropriate methods based on the command (e.g., "list", "mark", "unmark", "todo")
     *
     * @param input The raw user input string, e.g., "mark 2" or "todo borrow book".
     * @return {@code true} if the command signals the program should exit, or {@code false} otherwise.
     */
    private static boolean handleCommand(String input) {
        if (isExitCommand(input)) {
            goodbyeMessage();
            return true; // signals main() to exit the loop
        }

        try {
            // split into 2 parts: the first word is the command, the rest is the argument
            String[] parts = input.split(" ", 2);
            String command = parts[0].toLowerCase();  // e.g. "todo", "deadline", "event", etc.
            String rest = (parts.length > 1) ? parts[1] : "";

            switch (command) {
                case "list":
                    listTasks();
                    break;
                case "mark":
                    markTask(rest);
                    break;
                case "unmark":
                    unmarkTask(rest);
                    break;
                case "delete":
                    deleteTask(rest);
                    break;
                case "todo":
                    handleTodo(rest);
                    break;
                case "deadline":
                    handleDeadline(rest);
                    break;
                case "event":
                    handleEvent(rest);
                    break;
                default:
                    // For unrecognized commands, throw a custom exception
                    throw new MaltException(" I'm sorry, but I don't know what that means!");
            }
        } catch (MaltException e) {
            // Catches any MaltException thrown by the methods called above
            System.out.println("____________________________________________________________");
            System.out.println(e.getMessage());
            System.out.println("____________________________________________________________");
        }

        return false;
    }


    /**
     * Handles creation of a Todo task.
     *
     * @param description The full string after the "todo" command.
     */
    private static void handleTodo(String description) throws MaltException {
        if (description.trim().isEmpty()) {
            throw new MaltException(" OOPS!!! The description of a todo cannot be empty.");
        }
        Todo todo = new Todo(description.trim());
        tasks.add(todo);
        printAddedTaskMessage(todo);
    }


    /**
     * Handles creation of a Deadline task, e.g. "deadline return book /by Sunday".
     *
     * @param input The string after "deadline", e.g. "return book /by Sunday".
     */
    private static void handleDeadline(String input) throws MaltException {
        String[] parts = input.split("/by", 2);
        if (parts.length < 2) {
            throw new MaltException("""
            OOPS!!! Please specify the deadline in the format:
               deadline <description> /by <time>
            """);
        }
        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new MaltException(" OOPS!!! Both description and /by part cannot be empty.");
        }

        Deadline deadline = new Deadline(description, by);
        tasks.add(deadline);
        printAddedTaskMessage(deadline);
    }


    /**
     * Handles creation of an Event task, e.g. "event project meeting /from Mon 2pm /to 4pm".
     *
     * @param input The string after "event", e.g. "project meeting /from Mon 2pm /to 4pm".
     */
    private static void handleEvent(String input) throws MaltException {
        String[] fromSplit = input.split("/from", 2);
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
        tasks.add(event);
        printAddedTaskMessage(event);
    }

    /**
     * Deletes the specified task (by its 1-based index) from the tasks list.
     *
     * <p>If the index is invalid (out of range or non-numeric), throws a MaltException.</p>
     *
     * @param indexString A string representing the 1-based task index to be deleted.
     *                    For example, "3" means the third task in the list.
     * @throws MaltException if the index is not a valid integer or is out of bounds.
     */
    private static void deleteTask(String indexString) throws MaltException {
        try {
            int index = Integer.parseInt(indexString) - 1; // Convert 1-based to 0-based
            Task removed = tasks.remove(index);            // remove returns the removed element

            System.out.println("____________________________________________________________");
            System.out.println(" Noted. I've removed this task:");
            System.out.println("   " + removed);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list. Get working :(");
            System.out.println("____________________________________________________________");
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            // If indexString is not an integer or index is out-of-range, throw a MaltException
            throw new MaltException(" Invalid index for delete command!");
        }
    }




    /**
     * Prints the "Got it. I've added this task" message and shows how many tasks are in the list.
     *
     * @param task The newly created Task object.
     */
    private static void printAddedTaskMessage(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println(" Adding this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list! Get working :(");
        System.out.println("____________________________________________________________");
    }



    /**
     * Marks the specified task (by its 1-based index in the tasks list) as done.
     *
     * <p>If the index is invalid (out of range or non-numeric), prints an error message.</p>
     *
     * @param indexString A string representing the 1-based task index to be marked as done.
     *                    For example, "2" means the second task in the list.
     */
    private static void markTask(String indexString) throws MaltException {
        try {
            int index = Integer.parseInt(indexString) - 1;
            Task task = tasks.get(index);
            task.markAsDone();
            System.out.println("____________________________________________________________");
            System.out.println(" Perfect,marking this task as done now:");
            System.out.println("   " + task);
            System.out.println("____________________________________________________________");
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new MaltException(" Invalid index for mark command!");
        }
    }

    /**
     * Unmarks the specified task (by its 1-based index in the tasks list) as not done.
     *
     * <p>If the index is invalid (out of range or non-numeric), prints an error message.</p>
     *
     * @param indexString A string representing the 1-based task index to be marked as not done.
     *                    For example, "2" means the second task in the list.
     */
    private static void unmarkTask(String indexString) throws MaltException {
        try {
            int index = Integer.parseInt(indexString) - 1;
            Task task = tasks.get(index);
            task.markAsNotDone();
            System.out.println("____________________________________________________________");
            System.out.println(" OK, I've unmarked this task:");
            System.out.println("   " + task);
            System.out.println("____________________________________________________________");
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new MaltException(" Invalid index for unmark command!");
        }
    }

    /**
     * Adds a task (the user's input) to the list of tasks and prints a confirmation message.
     *
     * @param task The user's input string to be added as a task.
     */
    private static void addTask(String task) {
        Task newTask = new Task(task);
        tasks.add(newTask);
        System.out.println("____________________________________________________________");
        System.out.println(" added: " + task);
        System.out.println("____________________________________________________________");
    }

    /**
     * The main entry point for the Malt program.
     * This method initializes the program, displays a logo and greetings, processes user input
     * through an interactive loop, and exits when the user types a termination command.
     *
     * @param args Command-line arguments. This program does not utilize any command-line arguments.
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
//        displayLogo();
        greetings();

        while (true) {
            String input = getUserInput(in);
            // Pass the input to your handleCommand function
            boolean shouldExit = handleCommand(input);

            if (shouldExit) {
                break;
            }
        }
    }

}
