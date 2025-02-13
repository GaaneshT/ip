package malt;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import malt.parser.Parser;
import malt.storage.Storage;
import malt.task.TaskList;
import malt.ui.Ui;

public class MaltGui extends Application {
    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    private TextArea chatArea;
    private TextField inputField;

    @Override
    public void start(Stage primaryStage) {
        ui = new Ui();
        storage = new Storage("data/malt.txt");
        tasks = new TaskList(storage.loadTasks());

        // GUI Components
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);

        inputField = new TextField();
        inputField.setPromptText("Type a command...");
        inputField.setOnAction(event -> handleUserInput());

        VBox layout = new VBox(10, chatArea, inputField);
        layout.setStyle("-fx-padding: 10;");

        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setTitle("Malt Chatbot");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Show Welcome Message
        chatArea.appendText("Hey! I'm Malt, your chatbot.\nHow can I assist you today?\n");
    }

    private void handleUserInput() {
        String userInput = inputField.getText().trim();
        if (userInput.isEmpty()) return;

        // Display user input in GUI
        chatArea.appendText("> " + userInput + "\n");
        inputField.clear();

        System.out.println("Processing command: " + userInput); // Debugging

        try {
            // Capture system output from Malt
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outputStream));  // Redirect output

            boolean isExit = Parser.parseAndExecute(userInput, tasks, ui, storage);

            System.setOut(originalOut);  // Restore normal output
            String botResponse = outputStream.toString().trim();

            // Display Malt's response in GUI
            if (!botResponse.isEmpty()) {
                chatArea.appendText(botResponse + "\n");
            }

            if (isExit) {
                chatArea.appendText("Goodbye! See you next time.\n");
                inputField.setDisable(true);
            }
        } catch (MaltException e) {
            chatArea.appendText("Error: " + e.getMessage() + "\n");
            System.out.println("Error processing command: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
