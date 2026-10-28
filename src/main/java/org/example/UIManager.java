package org.example;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import java.io.File;

/**
 * UIManager handles all UI screens for the Maze Escape game using JavaFX.
 * <p>
 * It manages the welcome screen, login/signup screens, main menu, instructions, level selection,
 * maze drawing, and alerts. It also handles basic authentication logic by interacting with
 * the database via the Main class.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>Render and navigate between game screens</li>
 *     <li>Authenticate users via login and signup</li>
 *     <li>Visualize maze, coins, and player position</li>
 *     <li>Provide user feedback via styled alerts</li>
 * </ul>
 *
 * @author Shifa Zeeshan, Mahnoor Khan
 * @version 1.0
 */
public class UIManager {
    private final MazeGame mainApp;

    /**
     *
     * @param mainApp the main application instance for accessing logic and data
     */
    public UIManager(MazeGame mainApp) {
        this.mainApp = mainApp;
    }

    /**
     *
     * @param stage the primary JavaFX stage to render the screen
     */
    public void showWelcome(Stage stage) {
        Label welcomeLabel = new Label("🌟 Welcome to\nMaze Escape 🌟");
        welcomeLabel.setStyle("""
        -fx-font-size: 26px;
        -fx-font-weight: bold;
        -fx-text-fill: #2A4C76;
        -fx-font-family: 'Segoe UI Semibold';
        -fx-effect: dropshadow(gaussian, #B0C4DE, 3, 0.5, 2, 2);
    """);

        Label loginText = new Label("Already have an account?");
        Button loginBtn = new Button("Login");

        Label signupText = new Label("Create a new account?");
        Button signupBtn = new Button("Signup");

        for (Button btn : List.of(loginBtn, signupBtn)) {
            btn.setStyle("""
            -fx-background-color: #ADD8E6;
            -fx-text-fill: #2A4C76;
            -fx-font-size: 14px;
            -fx-font-family: 'Segoe UI';
            -fx-background-radius: 15;
            -fx-padding: 8 16;
            -fx-cursor: hand;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 2, 0.5, 1, 1);
        """);
        }

        loginBtn.setOnAction(e -> showLogin(stage));
        signupBtn.setOnAction(e -> showSignup(stage));

        HBox loginBox = new HBox(10, loginText, loginBtn);
        loginBox.setAlignment(Pos.CENTER);

        HBox signupBox = new HBox(10, signupText, signupBtn);
        signupBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(25, welcomeLabel, loginBox, signupBox);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.setStyle("""
        -fx-background-color: linear-gradient(to bottom right, #e0f7ff, #d6efff);
        -fx-background-radius: 25;
        -fx-border-radius: 25;
        -fx-border-color: #87CEFA;
        -fx-border-width: 2;
    """);

        Scene scene = new Scene(layout, 450, 300);
        stage.setScene(scene);
        stage.setTitle("Maze Escape - Welcome");
        stage.show();
    }

    /**
     * Displays the login screen where users can enter their credentials.
     *
     * @param stage the primary JavaFX stage to render the screen
     */
    public void showLogin(Stage stage) {
        Label title = new Label("🏁 Maze Login Portal");
        title.setStyle("""
        -fx-font-size: 24px;
        -fx-font-weight: bold;
        -fx-text-fill: #2A4C76;
        -fx-font-family: 'Segoe UI Semibold';
        -fx-effect: dropshadow(gaussian, #B0C4DE, 3, 0.5, 2, 2);
    """);

        TextField userField = new TextField();
        userField.setPromptText("Enter your username");

        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter your password");

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> loginUser(stage, userField.getText(), passField.getText()));

        Button backBtn = new Button("⬅ Back");
        backBtn.setOnAction(e -> showWelcome(stage));

        for (Button btn : List.of(loginBtn, backBtn)) {
            btn.setStyle("""
            -fx-background-color: #ADD8E6;
            -fx-text-fill: #2A4C76;
            -fx-font-size: 14px;
            -fx-font-family: 'Segoe UI';
            -fx-background-radius: 15;
            -fx-padding: 8 16;
            -fx-cursor: hand;
        """);
        }

        VBox box = new VBox(15, title, userField, passField, loginBtn, backBtn);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(30));
        box.setStyle("""
        -fx-background-color: linear-gradient(to bottom right, #e0f7ff, #d6efff);
        -fx-background-radius: 25;
        -fx-border-color: #87CEFA;
        -fx-border-width: 2;
        """);

        stage.setScene(new Scene(box, 380, 330));
    }

    /**
     *  * Displays the signup screen where users can register a new account.
     *
     * @param stage the primary JavaFX stage to render the screen
     */
    public void showSignup(Stage stage) {
        Label title = new Label("📝 Create New Account");
        title.setStyle("""
        -fx-font-size: 24px;
        -fx-font-weight: bold;
        -fx-text-fill: #2A4C76;
        -fx-font-family: 'Segoe UI Semibold';
        -fx-effect: dropshadow(gaussian, #B0C4DE, 3, 0.5, 2, 2);
        """);

        TextField userField = new TextField();
        userField.setPromptText("Choose a username");

        PasswordField passField = new PasswordField();
        passField.setPromptText("Choose a password");

        Button signupBtn = new Button("Sign Up");
        signupBtn.setOnAction(e -> signupUser(stage, userField.getText(), passField.getText()));

        Button backBtn = new Button("⬅ Back");
        backBtn.setOnAction(e -> showWelcome(stage));

        for (Button btn : List.of(signupBtn, backBtn)) {
            btn.setStyle("""
            -fx-background-color: #ADD8E6;
            -fx-text-fill: #2A4C76;
            -fx-font-size: 14px;
            -fx-font-family: 'Segoe UI';
            -fx-background-radius: 15;
            -fx-padding: 8 16;
            -fx-cursor: hand;
            """);
        }

        VBox box = new VBox(15, title, userField, passField, signupBtn, backBtn);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(30));
        box.setStyle("""
        -fx-background-color: linear-gradient(to bottom right, #e0f7ff, #d6efff);
        -fx-background-radius: 25;
        -fx-border-color: #87CEFA;
        -fx-border-width: 2;
    """);

        stage.setScene(new Scene(box, 380, 330));
    }

    /**
     *Attempts to log in a user with the given credentials.
     *If successful, proceeds to the main menu.
     *
     * @param stage    the JavaFX stage
     * @param username the entered username
     * @param password the entered password
     */
    private void loginUser(Stage stage, String username, String password) {
        if (mainApp.getDbManager().loginUser(username, password)) {
            mainApp.setCurrentUser(username);
            mainApp.setCurrentLevel(1);
            showMenu(stage);
        } else {
            showAlert("Login Failed", "Invalid username or password");
        }
    }

    /**
     *Attempts to register a new user with the given credentials.
     *If successful, proceeds to the main menu.
     *
     * @param stage    the JavaFX stage
     * @param username the desired username
     * @param password the desired password
     */
    private void signupUser(Stage stage, String username, String password) {
        if (mainApp.getDbManager().signupUser(username, password)) {
            mainApp.setCurrentUser(username);
            mainApp.setCurrentLevel(1);
            showMenu(stage);
        } else {
            showAlert("Signup Failed", "Username may already exist.");
        }
    }

    /**
     * Displays the main menu after successful login/signup.
     *
     * @param stage the primary JavaFX stage to render the menu
     */
    public void showMenu(Stage stage) {
        Label title = new Label("🌿 MAZE ESCAPE 🌿");
        title.setStyle("""
        -fx-font-size: 28px;
        -fx-font-weight: bold;
        -fx-text-fill: #2e7d32;
        -fx-font-family: 'Comic Sans MS';
        -fx-effect: dropshadow(gaussian, #a5d6a7, 5, 0.5, 2, 2);
    """);

        Button startGame = new Button("Start Game");
        Button levelsBtn = new Button("Levels");
        Button instructionsBtn = new Button("Instructions");

        List<Button> buttons = List.of(startGame, levelsBtn, instructionsBtn);
        for (Button btn : buttons) {
            btn.setPrefSize(200, 45);
            btn.setStyle("""
            -fx-background-color: #81c784;
            -fx-text-fill: black;
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-background-radius: 10;
            -fx-cursor: hand;
        """);
        }

        startGame.setOnAction(e -> mainApp.getGameLogic().startNewGame(stage));
        levelsBtn.setOnAction(e -> showLevels(stage));
        instructionsBtn.setOnAction(e -> showInstructions(stage));

        VBox menuBox = new VBox(20, title, startGame, levelsBtn, instructionsBtn);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(new Insets(40));
        menuBox.setBackground(new Background(new BackgroundFill(Color.web("#f1f8e9"), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(menuBox, 400, 350);
        stage.setScene(scene);
        stage.setTitle("Maze Game Menu - " + mainApp.getCurrentUser());
        stage.show();
    }

    /**
     * Displays the instruction screen explaining how to play the game.
     *
     * @param stage the primary JavaFX stage to render the instructions
     */
    public void showInstructions(Stage stage) {
        Label title = new Label("INSTRUCTIONS");
        title.setStyle("""
        -fx-font-size: 26px;
        -fx-font-weight: bold;
        -fx-text-fill: #2F4F4F;
        -fx-font-family: 'Segoe UI Semibold';
        -fx-effect: dropshadow(gaussian, #a0cfa0, 3, 0.5, 2, 2);
    """);

        Label line1 = new Label("🔼 Use arrow keys to navigate.");
        Label line2 = new Label("🏁 Reach the green square to finish.");
        Label line3 = new Label("💰 Collect coins to increase your score!");
        Label line4 = new Label("🌈 Enjoy the adventure!");

        for (Label label : List.of(line1, line2, line3, line4)) {
            label.setStyle("""
            -fx-font-size: 16px;
            -fx-text-fill: #2e4d2e;
            -fx-font-family: 'Segoe UI';
        """);
        }

        Label goodLuck = new Label("GOOD LUCK :)");
        goodLuck.setStyle("""
        -fx-font-size: 18px;
        -fx-font-weight: bold;
        -fx-text-fill: #3CB371;
        -fx-font-family: 'Segoe UI';
    """);

        Button backBtn = new Button("Back to Menu");
        backBtn.setStyle("""
        -fx-background-color: #98FB98;
        -fx-text-fill: #2F4F4F;
        -fx-font-size: 14px;
        -fx-font-family: 'Segoe UI';
        -fx-background-radius: 20;
        -fx-padding: 8 20;
        -fx-cursor: hand;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 3, 0.5, 1, 1);
        """);
        backBtn.setOnAction(e -> showMenu(stage));

        VBox vbox = new VBox(15, title, line1, line2, line3, line4, goodLuck, backBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(30));
        vbox.setStyle("""
        -fx-background-color: linear-gradient(to bottom right, #dfffe0, #b9f8c5);
        -fx-background-radius: 25;
        -fx-border-radius: 25;
        -fx-border-color: #a0d0a0;
        -fx-border-width: 2;
        """);

        Scene scene = new Scene(vbox, 400, 400);
        stage.setScene(scene);
    }

    /**
     * Displays the level selection screen with all unlocked levels.
     *
     * @param stage the primary JavaFX stage to render the levels
     */
    public void showLevels(Stage stage) {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        int col = 0, row = 0;
        int unlockedLevel = mainApp.getDbManager().getUnlockedLevel(mainApp.getCurrentUser());

        for (int i = 1; i <= mainApp.getMaxLevels(); i++) {
            File levelFile = new File(mainApp.getLevelsDir(), "level_" + i + ".txt");
            if (levelFile.exists()) {
                Button b = getStyledLevelButton(stage, i, unlockedLevel);
                grid.add(b, col, row);
                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            }
        }

        Label title = new Label("Select Level");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");

        Button backBtn = new Button("⬅ Back");
        backBtn.setStyle("-fx-background-color: #aed581; -fx-font-size: 14px; -fx-padding: 8px 20px; -fx-background-radius: 10;");
        backBtn.setOnAction(e -> showMenu(stage));

        VBox layout = new VBox(20, title, grid, backBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setBackground(new Background(new BackgroundFill(Color.web("#f1f8e9"), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(layout, 400, 450);
        stage.setScene(scene);
    }

    /**
     * Creates and styles a button for a specific level.
     *
     * @param stage         the JavaFX stage
     * @param level         the level number
     * @param unlockedLevel the maximum level the user has unlocked
     * @return a styled Button for the level
     */
    private Button getStyledLevelButton(Stage stage, int level, int unlockedLevel) {
        Button b = new Button("Level " + level);
        b.setPrefSize(100, 40);
        b.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8;");

        if (level <= unlockedLevel) {
            b.setStyle(b.getStyle() + "-fx-background-color: #81c784; -fx-text-fill: black;");
            b.setOnAction(e -> mainApp.getGameLogic().loadLevel(stage, level));
        } else {
            b.setDisable(true);
            b.setStyle(b.getStyle() + "-fx-background-color: #ccc; -fx-text-fill: #666;");
        }

        return b;
    }

    /**
     * Draws the maze, player, and coins on the game's pane.
     */
    public void drawMaze() {
        mainApp.getMazePane().getChildren().clear();
        double cs = 30;
        Maze maze = mainApp.getMaze();

        for (int y = 0; y < maze.rows; y++) {
            for (int x = 0; x < maze.cols; x++) {
                Rectangle r = new Rectangle(x * cs, y * cs, cs, cs);
                r.setStroke(Color.LIGHTGRAY);
                if (x == maze.cols - 1 && y == maze.rows - 1) r.setFill(Color.LIGHTGREEN);
                else r.setFill(maze.isWall(x, y) ? Color.BLACK : Color.WHITE);
                mainApp.getMazePane().getChildren().add(r);
            }
        }

        for (Coin coin : mainApp.getCoins()) {
            Circle c = new Circle((coin.x + 0.5) * cs, (coin.y + 0.5) * cs, cs / 4, Color.GOLD);
            mainApp.getMazePane().getChildren().add(c);
        }

        Player player = mainApp.getPlayer();
        Rectangle p = new Rectangle(player.x * cs, player.y * cs, cs, cs);
        p.setFill(Color.BLUE);
        mainApp.getMazePane().getChildren().add(p);
    }

    /**
     *
     * @param title the title of the alert dialog
     * @param msg the message content of the alert
     */
    public void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    /**
     * Applies a standard style to main menu buttons.
     *
     * @param button the button to style
     */
    private void styleMainButton(Button button) {
        button.setMinWidth(220);
        button.setMinHeight(50);
        button.setStyle(
                "-fx-background-color: #d1ecf1;" +
                        "-fx-text-fill: #1a237e;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: #81d4fa;" +
                        "-fx-border-width: 1.5;"
                );
    }

}
