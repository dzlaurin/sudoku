package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        GameManager gameManager = new GameManager(ui);
        ui.setGameManager(gameManager);
        try {
            ui.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
