package org.example;

import java.util.Random;

public class GameBoard {
    private final int[][] board;
    private final int gridSize;
    private final int subGridSize;
    private final Random random;

    public int getSize() {
        return gridSize;
    }

    public GameBoard(int gridSize, Difficulty difficulty) {
        this.gridSize = gridSize;
        this.subGridSize = (int) Math.sqrt(gridSize);
        this.board = new int[gridSize][gridSize];
        this.random = new Random();
        if (generateBoard(0, 0)) {
            removeCells(difficulty);
        }
    }

    public int getValueAt(int row, int col) {
        return board[row][col];
    }

    private boolean generateBoard(int row, int col) {
        int nextCol = col + 1;
        int nextRow = row;
        if (nextCol == gridSize) {
            nextCol = 0;
            nextRow += 1;
        }
        if (nextRow == gridSize) {
            return true;
        }

        int[] numbers = getRandomNumbers(gridSize);
        for (int num : numbers) {
            if (isSafe(row, col, num)) {
                board[row][col] = num;
                if (generateBoard(nextRow, nextCol)) {
                    return true;
                }
                board[row][col] = 0;
            }
        }
        return false;
    }

    private int[] getRandomNumbers(int size) {
        int[] numbers = new int[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = i + 1;
        }
        for (int i = 0; i < size; i++) {
            int j = random.nextInt(size);
            int temp = numbers[i];
            numbers[i] = numbers[j];
            numbers[j] = temp;
        }
        return numbers;
    }

    private boolean isSafe(int row, int col, int num) {
        if (isInRow(row, num) || isInCol(col, num) || isInSubgrid(row, col, num))
        {
            return false;
        }
        else {
            return true;
        }
    }

    private boolean isInRow(int row, int num) {
        for (int i = 0; i < gridSize; i++) {
            if (board[row][i] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInCol(int col, int num) {
        for (int i = 0; i < gridSize; i++) {
            if (board[i][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInSubgrid(int row, int col, int num) {
        int startRow = row - row % subGridSize;
        int startCol = col - col % subGridSize;
        for (int i = startRow; i < startRow + subGridSize; i++) {
            for (int j = startCol; j < startCol + subGridSize; j++) {
                if (board[i][j] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    private void removeCells(Difficulty difficulty) {
        int cellsToRemove = getCellsToRemove(difficulty);
        while (cellsToRemove > 0) {
            int row = random.nextInt(gridSize);
            int col = random.nextInt(gridSize);
            if (board[row][col] != 0) {
                board[row][col] = 0;
                cellsToRemove--;
            }
        }
    }

    private int getCellsToRemove(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> gridSize * gridSize / 4;
            case NORMAL -> gridSize * gridSize / 2;
            case HARD -> gridSize * gridSize * 3 / 4;
        };
    }

    public boolean isValidMove(int row, int col, int num) {
        return board[row][col] == 0 && isSafe(row, col, num);
    }

    public void makeMove(int row, int col, int num) {
        board[row][col] = num;
    }

    public boolean isComplete() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                builder.append(board[i][j] == 0 ? ". " : board[i][j] + " ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
