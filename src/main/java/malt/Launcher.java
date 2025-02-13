package malt;

import javafx.application.Application;

/**
 * A launcher class to avoid JavaFX classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Malt.class, args);
    }
}
