package org.example;

import java.io.IOException;

public class GameManager {
    private GameBoard gameBoard;
    private ScoreManager scoreManager;
    private final UserInterface userInterface;
    private HintSystem hintSystem;
    private boolean gameOver = false;

    public GameManager(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void startGame(int gridSize, Difficulty difficulty) {
        this.gameBoard = new GameBoard(gridSize, difficulty);
        this.scoreManager = new ScoreManager(difficulty);
        this.hintSystem = new HintSystem(gameBoard);
        userInterface.displayBoard(gameBoard);
    }

    public void processMove(int[] move) throws IOException {
        if (gameBoard.isValidMove(move[0], move[1], move[2])) {
            gameBoard.makeMove(move[0], move[1], move[2]);
            scoreManager.correctEntry();
        } else {
            scoreManager.incorrectEntry();
            System.out.println("Invalid move. Try again.");
        }
        userInterface.displayBoard(gameBoard);
    }

    public int getScore() {
        return scoreManager.getCurrentScore();
    }

    public void checkGameStatus() throws IOException {
        if (gameBoard.isComplete()) {
            scoreManager.gameCompleted();
            System.out.println("Congratulations! You completed the board.");
            System.out.println("Your final score is: " + getScore());
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

    public int[] getHint() {
        return hintSystem.getHint();
    }
}
