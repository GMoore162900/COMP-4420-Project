package edu.westga.comp4420.it_asset_management;

import java.io.IOException;

import edu.westga.comp4420.it_asset_management.model.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Entry point for the simple admin login GUI.
 *
 * @author Comp 4420
 * @version Fall 2026
 */
public class Main extends Application {
    public static final String WINDOW_TITLE = "IT Asset Management Login";
    public static final String MANAGEMENT_WINDOW_RESOURCE = "view/Management.fxml";

    /**
     * JavaFX entry point.
     *
     * @precondition none
     * @postcondition none
     */
    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("Admin Login");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField emailField = new TextField();
        emailField.setPromptText("admin@admin.edu");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label statusLabel = new Label();
        statusLabel.setTextFill(Color.GRAY);
        statusLabel.setWrapText(true);

        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> {
            try {
                this.loadManagementScene(primaryStage);
            } catch (IllegalArgumentException exception) {
                statusLabel.setText("Credentials rejected: " + exception.getMessage());
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

    private void loadManagementScene(Stage primaryStage) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(Main.MANAGEMENT_WINDOW_RESOURCE));
            primaryStage.setTitle("Inventory Management Dashboard");
            primaryStage.setScene(new Scene(parent));
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load Management.fxml.", exception);
        }
    }
}
