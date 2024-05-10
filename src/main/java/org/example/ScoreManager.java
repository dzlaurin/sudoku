package org.example;

public class ScoreManager {
    private int score;
    private int hintPenalty;
    private int incorrectPenalty;
    private Difficulty difficulty;

    public ScoreManager(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.score = 0;
        initializeScoringRules();
    }

    private void initializeScoringRules() {
        switch (difficulty) {
            case EASY:
                this.hintPenalty = 5;
                this.incorrectPenalty = 10;
                break;
            case NORMAL:
                this.hintPenalty = 10;
                this.incorrectPenalty = 20;
                break;
            case HARD:
                this.hintPenalty = 15;
                this.incorrectPenalty = 30;
                break;
            default:
                // Default case can be set to NORMAL or any custom rules
                this.hintPenalty = 10;
                this.incorrectPenalty = 20;
        }
    }

    public void correctEntry() {
        // Increment score by 1 for every correct entry, or more based on custom rules
        score += 10;
    }

    public void incorrectEntry() {
        // Decrease score for every incorrect entry
        if (score > 0) {
            score -= incorrectPenalty;
        }
    }

    public void usedHint() {
        // Decrease score when a hint is used
        if (score > 0) {
            score -= hintPenalty;
        }
    }

    public void gameCompleted() {
        // Add bonus points on game completion
        score += 100; // Arbitrary bonus value
    }

    public int getCurrentScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
    }
}