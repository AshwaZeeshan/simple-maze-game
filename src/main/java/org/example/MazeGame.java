package org.example;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * Main application class for the Maze Game.
 * This JavaFX application implements a maze game where players navigate through
 * levels, collect coins, and try to reach the exit.
 *
 * <p>Features include:
 * <ul>
 * <li>User authentication system</li>
 * <li>Multiple levels with progression tracking</li>
 * <li>Score and moves tracking</li>
 * <li>Coin collection system</li>
 * <li>SQLite database for user data persistence</li>
 * </ul>
 *
 * @author Mahnoor Khan, Shifa Zeeshan
 * @version 1.0
 * @since 1.0
 */
public class MazeGame extends Application {
    /** Default maze size for the game grid */
    private static final int SIZE = 21;

    /** Maximum number of levels available in the game */
    private static final int MAX_LEVELS = 10;

    /** Directory containing level files */
    private final File levelsDir = new File("levels");

    /** Pane containing the visual maze representation */
    private Pane mazePane;

    /** Labels for displaying current score and move count */
    private Label scoreLabel, movesLabel;

    /** Current maze instance */
    private Maze maze;

    /** Player character in the game */
    private Player player;

    /** List of coins currently in the maze */
    private List<Coin> coins;

    /** Current game statistics */
    private int score = 0, moves = 0;

    /** Current level being played */
    private int currentLevel = 1;

    /** Currently logged-in user */
    private String currentUser = "";

    /** Manager for database operations */
    private DatabaseManager dbManager;

    /** Manager for UI operations */
    private UIManager uiManager;

    /** Manager for game logic operations */
    private GameLogic gameLogic;

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and starts the JavaFX application.
     * Sets up the levels directory, initializes managers, connects to database,
     * and shows the login screen.
     *
     * @param stage the primary stage for this application
     */
    @Override
    public void start(Stage stage) {
        if (!levelsDir.exists()) levelsDir.mkdirs();

        dbManager = new DatabaseManager();
        uiManager = new UIManager(this);
        gameLogic = new GameLogic(this);

        dbManager.connectDatabase();
        uiManager.showWelcome(stage);
    }

    // Getters for other classes to access
    /** @return the database manager instance */
    public DatabaseManager getDbManager() { return dbManager; }

    /** @return the UI manager instance */
    public UIManager getUiManager() { return uiManager; }

    /** @return the game logic manager instance */
    public GameLogic getGameLogic() { return gameLogic; }

    /** @return the levels directory */
    public File getLevelsDir() { return levelsDir; }

    /** @return the maximum number of levels */
    public int getMaxLevels() { return MAX_LEVELS; }

    /** @return the default maze size */
    public int getSize() { return SIZE; }

    // Current state getters/setters
    /** @return the current user's username */
    public String getCurrentUser() { return currentUser; }

    /** @param user the username to set as current user */
    public void setCurrentUser(String user) { this.currentUser = user; }

    /** @return the current level number */
    public int getCurrentLevel() { return currentLevel; }

    /** @param level the level number to set as current */
    public void setCurrentLevel(int level) { this.currentLevel = level; }

    /** @return the current score */
    public int getScore() { return score; }

    /** @param score the score to set */
    public void setScore(int score) { this.score = score; }

    /** @return the current number of moves */
    public int getMoves() { return moves; }

    /** @param moves the number of moves to set */
    public void setMoves(int moves) { this.moves = moves; }

    // Game objects getters/setters
    /** @return the current maze instance */
    public Maze getMaze() { return maze; }

    /** @param maze the maze to set as current */
    public void setMaze(Maze maze) { this.maze = maze; }

    /** @return the player instance */
    public Player getPlayer() { return player; }

    /** @param player the player to set */
    public void setPlayer(Player player) { this.player = player; }

    /** @return the list of coins in the current maze */
    public List<Coin> getCoins() { return coins; }

    /** @param coins the list of coins to set */
    public void setCoins(List<Coin> coins) { this.coins = coins; }

    // UI components getters/setters
    /** @return the maze display pane */
    public Pane getMazePane() { return mazePane; }

    /** @param pane the pane to set for maze display */
    public void setMazePane(Pane pane) { this.mazePane = pane; }

    /** @return the score display label */
    public Label getScoreLabel() { return scoreLabel; }

    /** @param label the label to set for score display */
    public void setScoreLabel(Label label) { this.scoreLabel = label; }

    /** @return the moves display label */
    public Label getMovesLabel() { return movesLabel; }

    /** @param label the label to set for moves display */
    public void setMovesLabel(Label label) { this.movesLabel=label;}
}
