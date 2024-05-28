package org.example;

public class HintSystem {
    private final GameBoard gameBoard;

    public HintSystem(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public int[] getHint() {
        for (int row = 0; row < gameBoard.getSize(); row++) {
            for (int col = 0; col < gameBoard.getSize(); col++) {
                if (gameBoard.getValueAt(row, col) == 0) { // Find the first empty cell
                    for (int num = 1; num <= gameBoard.getSize(); num++) {
                        if (gameBoard.isValidMove(row, col, num)) {
                            return new int[]{row, col, num}; // Provide a valid move
                        }
                    }
                }
            }
        }
        return null; // No hints available
    }
}
