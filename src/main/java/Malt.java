import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Malt {

    private static final List<String> tasks = new ArrayList<>();

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
     * Adds a task (the user's input) to the list of tasks and prints a confirmation message.
     *
     * @param task The user's input string to be added as a task.
     */
    private static void addTask(String task) {
        tasks.add(task);
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
            if (isExitCommand(input)) {
                goodbyeMessage();
                break;
            } else if (input.equalsIgnoreCase("list")) {
                listTasks();
            }
            else{
                addTask(input);
            }
        }
    }
}
