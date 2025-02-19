package malt.ui;

import java.util.Scanner;

import malt.MaltException;


public class Ui {
    private static final String DIVIDER = "____________________________________________";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }


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
     * Prints a horizontal line divider for visual clarity.
     */
    public void showLine() {
        System.out.println(DIVIDER);
    }

    /**
     * Displays the welcome message, including the Malt ASCII art logo.
     * This method is called when the program starts to greet the user.
     */
    public void showWelcome() {
        displayLogo();
        showLine();
        System.out.println(" Hey! I'm Malt, like the chocolate Maltesers hehe");
        System.out.println(" What can I help you with?");
        showLine();
    }

    /**
     * Reads a line of user input from the console.
     *
     * @return the user input as a String.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a farewell message to the user upon exiting.
     */
    public void showGoodbye() {
        showLine();
        System.out.println(" Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Displays an error message.
     *
     * @param message the error message to be displayed.
     */
    public void showError(String message) {
        showLine();
        System.out.println(" Error: " + message);
        showLine();
    }

    /**
     * Overloaded: Prints any error message from a MaltException directly.
     */
    public void showError(MaltException e) {
        showError(e.getMessage());
    }

    /**
     * Displays a general message, surrounded by dividers for readability.
     *
     * @param message the message to be displayed.
     */
    public void showMessage(String message) {
        showLine();
        System.out.println(" " + message);
        showLine();
    }
}
