package edu.westga.comp4420.grocery_list;

import edu.westga.comp4420.grocery_list.model.Login;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Entry point for the simple login GUI.
 *
 * @author Comp 4420
 * @version Fall 2026
 */
public class Main extends Application {
    public static final String WINDOW_TITLE = "IT Asset Management Login";

    /**
     * JavaFX entry point.
     *
     * @precondition none
     * @postcondition none
     */
    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("University Login");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField emailField = new TextField();
        emailField.setPromptText("name@westga.edu");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label statusLabel = new Label();
        statusLabel.setTextFill(Color.GRAY);

        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> {
            try {
                Login login = new Login(emailField.getText(), passwordField.getText());
                statusLabel.setText("Welcome, " + login.getEmail());
                statusLabel.setTextFill(Color.DARKGREEN);
            } catch (IllegalArgumentException exception) {
                statusLabel.setText(exception.getMessage());
                statusLabel.setTextFill(Color.DARKRED);
            }
        });

        VBox root = new VBox(12);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(25));
        root.getChildren().addAll(titleLabel, emailField, passwordField, loginButton, statusLabel);

        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setScene(new Scene(root, 420, 260));
        primaryStage.show();
    }

    /**
     * Primary Java entry point.
     *
     * @precondition none
     * @postcondition none
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Main.launch(args);
    }
}
