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
                // If no recognized command, you can decide:
                // 1) treat it as a normal text -> add a plain Task, or
                // 2) print an error message
                // We'll show an error message here:
                System.out.println("____________________________________________________________");
                System.out.println(" I'm sorry, but I don't know what that means!");
                System.out.println("____________________________________________________________");
                break;
        }
        return false;
    }

    /**
     * Handles creation of a Todo task.
     *
     * @param description The full string after the "todo" command.
     */
    private static void handleTodo(String description) {
        if (description.trim().isEmpty()) {
            System.out.println("____________________________________________________________");
            System.out.println(" OOPS!!! The description of a todo cannot be empty.");
            System.out.println("____________________________________________________________");
            return;
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
    private static void handleDeadline(String input) {
        // We expect something like: "<desc> /by <time>"
        String[] parts = input.split("/by", 2);
        if (parts.length < 2) {
            System.out.println("____________________________________________________________");
            System.out.println(" OOPS!!! Please specify the deadline in the format:");
            System.out.println("   deadline <description> /by <time>");
            System.out.println("____________________________________________________________");
            return;
        }
        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty() || by.isEmpty()) {
            System.out.println("____________________________________________________________");
            System.out.println(" OOPS!!! Both description and /by part cannot be empty.");
            System.out.println("____________________________________________________________");
            return;
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
    private static void handleEvent(String input) {
        // We expect something like "<desc> /from <start> /to <end>"
        String[] fromSplit = input.split("/from", 2);
        if (fromSplit.length < 2) {
            System.out.println("____________________________________________________________");
            System.out.println(" OOPS!!! Please specify the event in the format:");
            System.out.println("   event <description> /from <start> /to <end>");
            System.out.println("____________________________________________________________");
            return;
        }
        String description = fromSplit[0].trim();

        // now split the second part by "/to"
        String[] toSplit = fromSplit[1].split("/to", 2);
        if (toSplit.length < 2) {
            System.out.println("____________________________________________________________");
            System.out.println(" OOPS!!! An event must have both /from and /to time frames.");
            System.out.println("____________________________________________________________");
            return;
        }

        String fromTime = toSplit[0].trim();
        String toTime = toSplit[1].trim();

        if (description.isEmpty() || fromTime.isEmpty() || toTime.isEmpty()) {
            System.out.println("____________________________________________________________");
            System.out.println(" OOPS!!! Make sure description, /from, and /to parts are not empty.");
            System.out.println("____________________________________________________________");
            return;
        }

        Event event = new Event(description, fromTime, toTime);
        tasks.add(event);
        printAddedTaskMessage(event);
    }


    /**
     * Prints the "Got it. I've added this task" message and shows how many tasks are in the list.
     *
     * @param task The newly created Task object.
     */
    private static void printAddedTaskMessage(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
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
    private static void markTask(String indexString) {
        try {
            int index = Integer.parseInt(indexString) - 1;
            Task task = tasks.get(index);
            task.markAsDone();
            System.out.println("____________________________________________________________");
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   " + task);
            System.out.println("____________________________________________________________");
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("____________________________________________________________");
            System.out.println(" Invalid index for mark command!");
            System.out.println("____________________________________________________________");
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
    private static void unmarkTask(String indexString) {
        try {
            int index = Integer.parseInt(indexString) - 1;
            Task task = tasks.get(index);
            task.markAsNotDone();
            System.out.println("____________________________________________________________");
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   " + task);
            System.out.println("____________________________________________________________");
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("____________________________________________________________");
            System.out.println(" Invalid index for unmark command!");
            System.out.println("____________________________________________________________");
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
        displayLogo();
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
