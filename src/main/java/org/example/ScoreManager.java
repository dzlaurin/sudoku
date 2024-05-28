package org.example;

public class ScoreManager {
    private int score;
    private final Difficulty difficulty;

    public ScoreManager(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.score = 0;
    }

    public void correctEntry() {
        switch (difficulty) {
            case EASY -> score += 10;
            case NORMAL -> score += 20;
            case HARD -> score += 30;
        }
    }

    public void incorrectEntry() {
        switch (difficulty) {
            case EASY -> score -= 5;
            case NORMAL -> score -= 10;
            case HARD -> score -= 15;
        }
    }

    public void gameCompleted() {
        switch (difficulty) {
            case EASY -> score += 100;
            case NORMAL -> score += 200;
            case HARD -> score += 300;
        }
    }

    public int getCurrentScore() {
        return score;
    }
}
