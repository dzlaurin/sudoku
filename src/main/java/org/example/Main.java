package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        GameManager gameManager = new GameManager(); // Make sure GameManager can interact properly with this UI
        UserInterface ui = new UserInterface(gameManager);
        try {
            ui.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}