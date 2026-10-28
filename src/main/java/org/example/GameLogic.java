package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;

/**
 * GameLogic handles the core game mechanics and flow for the maze game.
 * This class manages level progression, maze loading, player movement,
 * coin collection, and win conditions.
 *
 * @author Mahnoor Khan, Shifa Zeeshan
 * @version 1.0
 */
public class GameLogic {
    private MazeGame mainApp;

    /**
     * Constructs a GameLogic instance with a reference to the main application.
     *
     * @param mainApp The main application instance that contains shared game state
     */
    public GameLogic(MazeGame mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Starts a new game by loading the first level.
     * Resets the current level to 1 and attempts to load the corresponding level file.
     *
     * @param stage The primary stage for displaying the game
     */
    public void startNewGame(Stage stage) {
        mainApp.setCurrentLevel(1);
        File levelFile = new File(mainApp.getLevelsDir(), "level_" + mainApp.getCurrentLevel() + ".txt");
        if (levelFile.exists()) {
            Maze loadedMaze = Maze.loadFromFile(levelFile);
            if (loadedMaze != null) {
                loadMaze(stage, loadedMaze);
            } else {
                mainApp.getUiManager().showAlert("Error", "Failed to load level 1.");
            }
        } else {
            mainApp.getUiManager().showAlert("Missing File", "Level 1 file not found.");
        }
    }

    /**
     * Loads a specific level by its number.
     *
     * @param stage The primary stage for displaying the game
     * @param level The level number to load (must correspond to an existing level file)
     */
    public void loadLevel(Stage stage, int level) {
        File levelFile = new File(mainApp.getLevelsDir(), "level_" + level + ".txt");
        Maze loadedMaze = Maze.loadFromFile(levelFile);
        if (loadedMaze != null) {
            mainApp.setCurrentLevel(level);
            loadMaze(stage, loadedMaze);

        } else {
            mainApp.getUiManager().showAlert("Error", "Failed to load level " + level);
        }
    }

    /**
     * Loads and initializes a maze for gameplay.
     * Sets up the player, coins, score, moves counter, and UI elements.
     * Creates the game scene with maze visualization and control handlers.
     *
     * @param stage The primary stage for displaying the game
     * @param m The maze object to load and display
     */
    public void loadMaze(Stage stage, Maze m) {
        mainApp.setMaze(m);
        mainApp.setPlayer(new Player(0, 0));
        mainApp.setCoins(m.placeCoins(mainApp.getSize() / 5));

        mainApp.setScore(0);
        mainApp.setMoves(0);
        mainApp.setScoreLabel(new Label("Score: 0"));
        mainApp.setMovesLabel(new Label("Moves: 0"));

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> mainApp.getUiManager().showMenu(stage));

        mainApp.setMazePane(new Pane());
        mainApp.getUiManager().drawMaze();

        BorderPane root = new BorderPane();
        HBox infoBox = new HBox(20, mainApp.getScoreLabel(), mainApp.getMovesLabel(), quitButton);
        infoBox.setPadding(new Insets(10));
        infoBox.setAlignment(Pos.CENTER_LEFT);
        root.setTop(infoBox);
        root.setCenter(mainApp.getMazePane());

        Scene scene = new Scene(root, mainApp.getSize() * 30, mainApp.getSize() * 30 + 40);
        scene.setOnKeyPressed(e -> handleKey(e.getCode(), stage));
        stage.setScene(scene);
        scene.getRoot().requestFocus();
        stage.setTitle("Maze Level " + mainApp.getCurrentLevel());
        stage.show();
    }

    /**
     * Handles keyboard input for player movement and game controls.
     * Processes arrow keys for movement, validates moves against walls,
     * updates game state, checks for coin collection, and detects win conditions.
     *
     * @param code The KeyCode representing the pressed key
     * @param stage The primary stage for potential scene changes
     */
    public void handleKey(KeyCode code, Stage stage) {
        int dx = 0, dy = 0;
        switch (code) {
            case UP -> dy = -1;
            case DOWN -> dy = 1;
            case LEFT -> dx = -1;
            case RIGHT -> dx = 1;
            case ESCAPE -> {
                mainApp.getUiManager().showMenu(stage);
                return;
            }
        }

        if (dx != 0 || dy != 0) {
            Player player = mainApp.getPlayer();
            Maze maze = mainApp.getMaze();

            if (!maze.isWall(player.x + dx, player.y + dy)) {
                player.move(dx, dy);
                mainApp.setMoves(mainApp.getMoves() + 1);
                mainApp.getMovesLabel().setText("Moves: " + mainApp.getMoves());
            }

            // Check for coin collection
            mainApp.getCoins().removeIf(coin -> {
                if (coin.x == player.x && coin.y == player.y) {
                    mainApp.setScore(mainApp.getScore() + 10);
                    return true;
                }
                return false;
            });
            mainApp.getScoreLabel().setText("Score: " + mainApp.getScore());

            mainApp.getUiManager().drawMaze();

            // Check win condition
            if (player.x == maze.cols - 1 && player.y == maze.rows - 1) {
                handleLevelComplete(stage);
            }
        }
    }

    /**
     * Handles the completion of a level.
     * Shows completion message, updates unlocked levels in database,
     * and either loads the next level or returns to menu if no more levels exist.
     *
     * @param stage The primary stage for scene transitions
     */
    private void handleLevelComplete(Stage stage) {
        mainApp.getUiManager().showAlert("You Win", "Completed Level " + mainApp.getCurrentLevel() + "! Score: " + mainApp.getScore());

        int currentLevel = mainApp.getCurrentLevel();
        int unlockedLevel = mainApp.getDbManager().getUnlockedLevel(mainApp.getCurrentUser());

        if (currentLevel >= unlockedLevel && currentLevel < mainApp.getMaxLevels()) {
            mainApp.getDbManager().updateUnlockedLevel(mainApp.getCurrentUser(), currentLevel + 1);
        }

        mainApp.setCurrentLevel(currentLevel + 1);
        if (mainApp.getCurrentLevel() <= mainApp.getMaxLevels()) {
            File nextLevel = new File(mainApp.getLevelsDir(), "level_" + mainApp.getCurrentLevel() + ".txt");
            if (nextLevel.exists()) {
                Maze nextMaze = Maze.loadFromFile(nextLevel);
                if (nextMaze != null) {
                    loadMaze(stage, nextMaze);
                    return;
                }
            }
            mainApp.getUiManager().showAlert("Done", "No more levels available.");
        }
        mainApp.getUiManager().showMenu(stage);

    }
}
