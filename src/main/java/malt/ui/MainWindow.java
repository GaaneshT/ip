package malt.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import malt.MaltChatbot;

public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private MaltChatbot maltChatbot;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image maltImage = new Image(this.getClass().getResourceAsStream("/images/malt.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(
                DialogBox.getMaltDialog("Hello! I'm Malt, your chatbot.\nHow can I assist you today?", maltImage)
        );
    }

    /** Injects the chatbot instance */
    public void setMaltChatbot(MaltChatbot maltChatbot) {
        this.maltChatbot = maltChatbot;
    }

    /** Handles user input */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) return;

        String response = maltChatbot.getResponse(input); // Now uses MaltChatbot for responses

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMaltDialog(response, maltImage)
        );
        userInput.clear();
    }
}
