package org.example;

import java.io.IOException;

public class GameManager {
    private GameBoard gameBoard;
    private final UserInterface userInterface;
    private boolean gameOver = false;

    public GameManager(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void startGame(int gridSize, Difficulty difficulty) {
        this.gameBoard = new GameBoard(gridSize, difficulty);
        userInterface.displayBoard(gameBoard);
    }

    public void processMove(int[] move) throws IOException {
        if (gameBoard.isValidMove(move[0], move[1], move[2])) {
            gameBoard.makeMove(move[0], move[1], move[2]);
        } else {
            System.out.println("Invalid move. Try again.");
        }
        userInterface.displayBoard(gameBoard);
    }

    public int getScore() {
        return 0;
    }

    public void checkGameStatus() throws IOException {
        if (gameBoard.isComplete()) {
            System.out.println("Congratulations! You completed the board.");
            offerRestart();
        }
    }

    private void offerRestart() throws IOException {
        if (userInterface.promptForRestart()) {
            userInterface.start();
        } else {
            System.exit(0);
        }
    }
}
