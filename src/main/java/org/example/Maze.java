package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a maze structure with walls and navigable paths.
 * Handles maze loading from files, wall collision detection, and coin placement.
 * The maze uses a 2D boolean array where true represents walls and false represents open paths.
 *
 * @author Mahnoor Khan, Shifa Zeeshan
 * @version 1.0
 */
public class Maze {
    /** The number of columns in the maze grid */
    int cols;

    /** The number of rows in the maze grid */
    int rows;

    /** 2D array representing walls in the maze (true = wall, false = open path) */
    boolean[][] walls;

    /**
     * Constructs a Maze with specified dimensions and wall configuration.
     *
     * @param c The number of columns in the maze
     * @param r The number of rows in the maze
     * @param w The 2D boolean array representing wall positions
     */
    public Maze(int c, int r, boolean[][] w) {
        cols = c;
        rows = r;
        walls = w;
    }

    /**
     * Loads a maze from a text file.
     * The file format expects the first line to contain columns and rows,
     * followed by rows of strings where '1' represents walls and '0' represents open paths.
     *
     * @param file The file containing the maze data
     * @return A new Maze object loaded from the file, or null if loading fails
     */
    public static Maze loadFromFile(File file) {
        try (Scanner sc = new Scanner(file)) {
            int c = sc.nextInt(), r = sc.nextInt();
            boolean[][] w = new boolean[c][r];
            for (int y = 0; y < r; y++) {
                String line = sc.next();
                for (int x = 0; x < c; x++)
                    w[x][y] = line.charAt(x) == '1';
            }
            return new Maze(c, r, w);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Checks if the specified position contains a wall or is outside maze boundaries.
     * Returns true for positions that are out of bounds or contain walls,
     * effectively preventing movement to invalid locations.
     *
     * @param x The x-coordinate to check
     * @param y The y-coordinate to check
     * @return true if the position is a wall or out of bounds, false if it's a valid open path
     */
    public boolean isWall(int x, int y) {
        return x < 0 || y < 0 || x >= cols || y >= rows || walls[x][y];
    }

    /**
     * Randomly places coins throughout the maze in valid positions.
     * Coins are placed only on open paths, avoiding walls, start position (0,0),
     * and end position (cols-1, rows-1). Prevents duplicate coin placement at the same position.
     *
     * @param count The desired number of coins to place in the maze
     * @return A list of Coin objects representing the placed coins
     */
    public List<Coin> placeCoins(int count) {
        List<Coin> coins = new ArrayList<>();
        Random rand = new Random();
        int attempts = 0;
        int maxAttempts = count * 10; // Prevent infinite loop

        while (coins.size() < count && attempts < maxAttempts) {
            int x = rand.nextInt(cols);
            int y = rand.nextInt(rows);

            // Check if position is valid (not a wall, not start, not end)
            if (!walls[x][y] && (x != 0 || y != 0) && (x != cols - 1 || y != rows - 1)) {
                Coin newCoin = new Coin(x, y);
                // Check if coin already exists at this position
                boolean exists = false;
                for (Coin existingCoin : coins) {
                    if (existingCoin.x == x && existingCoin.y == y) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    coins.add(newCoin);
                }
            }
            attempts++;
        }
        return coins;
}
}
