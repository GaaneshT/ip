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
     * appropriate methods based on the command (e.g., "list", "mark", "unmark").</p>
     *
     * @param input The raw user input string, e.g., "mark 2" or "read book".
     * @return {@code true} if the command signals the program should exit, or {@code false} otherwise.
     */
    private static boolean handleCommand(String input) {
        if (isExitCommand(input)) {
            goodbyeMessage();
            return true; // signals main() to exit the loop
        }

        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
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
            default:
                addTask(input);
                break;
        }

        return false;
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
