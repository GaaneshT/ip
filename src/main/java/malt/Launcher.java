package malt;

import javafx.application.Application;

/**
 * A launcher class to avoid JavaFX classpath issues.
 */
public class Launcher {
    /**
     * The main method that launches the JavaFX application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        Application.launch(Malt.class, args);
    }
}
