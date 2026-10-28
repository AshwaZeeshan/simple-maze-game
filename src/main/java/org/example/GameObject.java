package org.example;

/**
 * Abstract base class for all game objects in the maze game.
 * Provides common position coordinates and type identification for game entities.
 *
 * @author Mahnoor Khan, Shifa Zeeshan
 * @version 1.0
 */
public abstract class GameObject {
    /** The x-coordinate position of the game object in the maze grid */
    int x;

    /** The y-coordinate position of the game object in the maze grid */
    int y;

    /**
     * Constructs a GameObject with specified coordinates.
     *
     * @param x The initial x-coordinate position
     * @param y The initial y-coordinate position
     */
    GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the type identifier for this game object.
     * Each concrete implementation must provide its own type string.
     *
     * @return A string representing the type of this game object
     */
    abstract String getType();
}

/**
 * Represents the player character in the maze game.
 * Extends GameObject to provide movement capabilities and player identification.
 * The player navigates through the maze to collect coins and reach the exit.
 *
 * @author Mahnoor Khan, Shifa Zeeshan
 * @version 1.0
 */
class Player extends GameObject {

    /**
     * Constructs a Player object at the specified coordinates.
     *
     * @param x The initial x-coordinate position in the maze
     * @param y The initial y-coordinate position in the maze
     */
    Player(int x, int y) {
        super(x, y);
    }

    /**
     * Moves the player by the specified displacement values.
     * Updates the player's position by adding the delta values to current coordinates.
     *
     * @param dx The change in x-coordinate (positive for right, negative for left)
     * @param dy The change in y-coordinate (positive for down, negative for up)
     */
    void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /**
     * Returns the type identifier for the player object.
     *
     * @return The string "Player" identifying this object type
     */
    @Override
    String getType() {
        return "Player";
    }
}

/**
 * Represents a collectible coin object in the maze game.
 * Extends GameObject to provide position-based collision detection.
 * Coins are placed throughout the maze for the player to collect for points.
 *
 * @author Mahnoor Khan
 * @version 1.0
 */
class Coin extends GameObject {

    /**
     * Constructs a Coin object at the specified coordinates.
     *
     * @param x The x-coordinate position of the coin in the maze
     * @param y The y-coordinate position of the coin in the maze
     */
    Coin(int x, int y) {
        super(x, y);
    }

    /**
     * Returns the type identifier for the coin object.
     *
     * @return The string "Coin" identifying this object type
     */
    @Override
    String getType() {
        return "Coin";
    }

    /**
     * Determines if this coin is equal to another object.
     * Two coins are considered equal if they have the same coordinates.
     *
     * @param obj The object to compare with this coin
     * @return true if the objects are equal (same position), false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coin coin = (Coin) obj;
        return x == coin.x && y == coin.y;
    }

    /**
     * Generates a hash code for this coin based on its position.
     * Uses a standard formula combining x and y coordinates for hash distribution.
     *
     * @return An integer hash code representing this coin's position
     */
    @Override
    public int hashCode() {
        return 31 *x+y;
}
}
