package org.example;

import java.io.IOException;

public class GameManager {
    private GameBoard gameBoard;
    private ScoreManager scoreManager;
    private final UserInterface userInterface;

    private boolean gameOver = false;

    public GameManager() {
        this.userInterface = new UserInterface(this);
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void startGame() throws IOException {
        // Get user settings
        int gridSize = userInterface.getGridSizeFromUser();
        Difficulty difficulty = userInterface.getDifficultyFromUser();

        // Initialize game components
        this.gameBoard = new GameBoard(gridSize, difficulty);
        this.scoreManager = new ScoreManager(difficulty);

        // Game loop
        while (!gameOver) {
            userInterface.displayBoard(gameBoard);
            int[] move = userInterface.getUserMove();
            processMove(move);
            gameOver = gameBoard.isComplete();
        }

        // Game completion
        scoreManager.gameCompleted();
        userInterface.displayEndGameMessage(scoreManager.getCurrentScore());
        offerRestart();
    }

    private void processMove(int[] move) throws IOException {
        if (gameBoard.isValidMove(move[0], move[1], move[2])) {
            gameBoard.makeMove(move[0], move[1], move[2]);
            scoreManager.correctEntry();
        } else {
            scoreManager.incorrectEntry();
        }
        userInterface.displayBoard(gameBoard);
    }

    public int getScore() {
        return scoreManager.getCurrentScore();
    }

    private void offerRestart() throws IOException {
        if (userInterface.promptForRestart()) {
            restartGame();
        } else {
            System.exit(0);
        }
    }

    private void restartGame() throws IOException {
        gameOver = false;
        startGame();
    }
}
