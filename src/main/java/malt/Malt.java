package malt;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import malt.ui.MainWindow;

public class Malt extends Application {
    private final MaltChatbot maltChatbot = new MaltChatbot();

    /**
     * Starts the JavaFX application by loading the FXML layout and setting up the scene.
     *
     * @param stage The primary stage for the application.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
            AnchorPane rootLayout = fxmlLoader.load();
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setMaltChatbot(maltChatbot);
            stage.setTitle("Malt Chatbot");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main entry point of the application. This method launches the JavaFX application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
