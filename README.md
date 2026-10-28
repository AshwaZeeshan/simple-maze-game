# Simple Maze Game

A JavaFX maze game created as a second-semester Object-Oriented Programming university project by **Shifa Zeeshan** and **Mahnoor Khan**.

## Features

- Ten file-based maze levels with progressive unlocking
- Arrow-key movement and wall collision detection
- Randomly placed collectible coins, scoring, and move tracking
- Login and signup screens
- SQLite storage for local accounts and unlocked levels
- JavaFX screens for instructions, level selection, and gameplay

## Technologies

Java 23, JavaFX 17, SQLite, JDBC, and Maven.

## Run locally

Install JDK 23 and Maven. From the project root, run:

```sh
mvn clean javafx:run
```

Keep the `levels` folder in the project root; the game reads its maze files relative to the working directory. Create a local account through the signup screen before playing. The game creates `users.db` automatically on first launch.

## Controls

- **Arrow keys:** move the player
- **Escape:** return to the menu
- **Quit button:** return to the menu
- **Coins:** collect for 10 points each
- **Exit:** reach the bottom-right cell to complete a level

## Structure

```text
src/main/java/org/example/
  MazeGame.java        Application entry point and shared state
  UIManager.java       JavaFX screens and rendering
  GameLogic.java       Movement, scoring, and progression
  Maze.java            Level loading, walls, and coin placement
  GameObject.java      Base game object, player, and coin classes
  DatabaseManager.java Local accounts and progression storage
levels/                Ten maze layout files
pom.xml                Maven dependencies and launch configuration
```

## Project scope

This is an educational desktop project. Its local login system stores passwords in plain text and is intended only for demonstration; use a disposable password. Existing account data and generated build files are excluded from this repository.

## Authors
- Shifa Zeeshan
- Mahnoor Khan 
