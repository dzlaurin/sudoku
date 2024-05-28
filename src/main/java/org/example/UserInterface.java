package org.example;

import java.io.IOException;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    private GameManager gameManager;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void displayBoard(GameBoard board) {
        System.out.println(board);
        System.out.println("Current score: " + gameManager.getScore());
    }

    public void start() throws IOException {
        int gridSize = getGridSizeFromUser();
        Difficulty difficulty = getDifficultyFromUser();
        gameManager.startGame(gridSize, difficulty);
        run();
    }

    public int getGridSizeFromUser() {
        System.out.println("Select grid size:");
        System.out.println("1. 4x4");
        System.out.println("2. 9x9");
        System.out.println("3. 16x16");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                return 4;
            case 2:
                return 9;
            case 3:
                return 16;
            default:
                System.out.println("Invalid choice. Defaulting to 9x9.");
                return 9;
        }
    }

    public Difficulty getDifficultyFromUser() {
        System.out.println("Select difficulty:");
        System.out.println("1. EASY");
        System.out.println("2. NORMAL");
        System.out.println("3. HARD");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                return Difficulty.EASY;
            case 2:
                return Difficulty.NORMAL;
            case 3:
                return Difficulty.HARD;
            default:
                System.out.println("Invalid choice. Defaulting to NORMAL.");
                return Difficulty.NORMAL;
        }
    }

    public void run() throws IOException {
        while (!gameManager.getGameBoard().isComplete()) {
            System.out.println("Enter your move (format: row col number) or type 'hint' for a hint:");
            String input = scanner.next();
            if (input.equalsIgnoreCase("hint")) {
                int[] hint = gameManager.getHint();
                if (hint != null) {
                    System.out.println("Hint: place " + hint[2] + " at (" + (hint[0] + 1) + ", " + (hint[1] + 1) + ")");
                } else {
                    System.out.println("No hints available.");
                }
            } else {
                int row = Integer.parseInt(input) - 1;
                int col = scanner.nextInt() - 1;
                int num = scanner.nextInt();
                int[] move = new int[]{row, col, num};
                gameManager.processMove(move);
                gameManager.checkGameStatus();
            }
        }
    }

    public boolean promptForRestart() {
        System.out.println("Do you want to restart? (Y/N)");
        String input = scanner.next();
        return input.equalsIgnoreCase("Y");
    }
}
