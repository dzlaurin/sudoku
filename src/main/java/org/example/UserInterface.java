package org.example;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;
import java.util.Scanner;

public class UserInterface {
    private Screen screen;
    private Terminal terminal;
    private GameManager gameManager;
    private int cursorX = 0;
    private int cursorY = 0;

    private final Scanner scanner;

    public UserInterface(GameManager gameManager) {
        this.scanner = new Scanner(System.in);
        this.gameManager = gameManager;
        try {
            terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.setCursorPosition(new TerminalPosition(0, 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayBoard(GameBoard board) throws IOException {
        screen.clear();
        TextGraphics textGraphics = screen.newTextGraphics();

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                String value = board.getValueAt(row, col) == 0 ? " " : Integer.toString(board.getValueAt(row, col));
                textGraphics.putString(col * 2, row, value);
            }
        }

        moveCursor();
        screen.refresh();
    }

    public void moveCursor() throws IOException {
        screen.setCursorPosition(new TerminalPosition(cursorX * 2, cursorY));
    }

    public int getGridSizeFromUser() throws IOException {
        int[] options = {4, 9, 16}; // Grid sizes
        int selectedOption = 0; // Default to the first option

        while (true) {
            screen.clear();
            for (int i = 0; i < options.length; i++) {
                String line = (i == selectedOption ? "> " : "  ") + options[i] + "x" + options[i];
                screen.newTextGraphics().putString(1, i + 1, line);
            }
            screen.refresh();

            KeyStroke keyStroke = screen.readInput();
            if (keyStroke.getKeyType() == KeyType.ArrowUp && selectedOption > 0) {
                selectedOption--;
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown && selectedOption < options.length - 1) {
                selectedOption++;
            } else if( keyStroke.getKeyType() == KeyType.Enter) {
                break;
            }

        }

        return options[selectedOption];
    }

    public void run() throws IOException {
        displayBoard(gameManager.getGameBoard());
        boolean running = true;
        while (running) {
            KeyStroke keyStroke = screen.readInput();
            switch (keyStroke.getKeyType()) {
                case ArrowUp:
                    cursorY = Math.max(0, cursorY - 1);
                    break;
                case ArrowDown:
                    cursorY = Math.min(gameManager.getGameBoard().getSize() - 1, cursorY + 1);
                    break;
                case ArrowLeft:
                    cursorX = Math.max(0, cursorX - 1);
                    break;
                case ArrowRight:
                    cursorX = Math.min(gameManager.getGameBoard().getSize() - 1, cursorX + 1);
                    break;
                case Enter:
                    handleCellAction(cursorY, cursorX);
                    break;
                case Escape:
                    running = false;
                    break;
            }
            moveCursor();
            displayBoard(gameManager.getGameBoard());
        }
        close();
    }

    private void handleCellAction(int row, int col) {
        // Handle cell action, e.g., entering a number or requesting a hint
        // This is a placeholder for interaction logic
        System.out.println("Action at " + row + ", " + col);
    }

    public boolean promptForRestart() throws IOException {
        screen.clear();
        screen.newTextGraphics().putString(0, 0, "Do you want to restart? (Y/N)");
        screen.refresh();

        while (true) {
            KeyStroke keyStroke = screen.readInput();
            if (keyStroke.getKeyType() == KeyType.Character) {
                if (keyStroke.getCharacter() == 'Y' || keyStroke.getCharacter() == 'y') {
                    return true;
                } else if (keyStroke.getCharacter() == 'N' || keyStroke.getCharacter() == 'n') {
                    return false;
                }
            }
        }
    }

    public Difficulty getDifficultyFromUser() throws IOException {
        Difficulty[] difficulties = Difficulty.values();
        int selectedOption = 0; // Default to the first option

        while (true) {
            screen.clear();
            for (int i = 0; i < difficulties.length; i++) {
                String line = (i == selectedOption ? "> " : "  ") + difficulties[i].toString();
                screen.newTextGraphics().putString(1, i + 1, line);
            }
            screen.refresh();

            KeyStroke keyStroke = screen.readInput();
            if (keyStroke.getKeyType() == KeyType.ArrowUp && selectedOption > 0) {
                selectedOption--;
            } else if (keyStroke.getKeyType() == KeyType.ArrowDown && selectedOption < difficulties.length - 1) {
                selectedOption++;
            } else if (keyStroke.getKeyType() == KeyType.Enter) {
                break;
            }

        }

        return difficulties[selectedOption];
    }


    public void close() throws IOException {
        screen.close();
        terminal.close();
    }
}