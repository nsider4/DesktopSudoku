package com.sudoku.gameutilities;

public class Difficulty {
    public enum DifficultyEnum {
        EASY,
        MEDIUM,
        HARD,
        EXPERT
    }

    private static DifficultyEnum currentDifficulty;

    public static DifficultyEnum getCurrentDifficulty() {
        return currentDifficulty;
    }

    public static void setCurrentDifficulty(DifficultyEnum currentDifficulty) {
        Difficulty.currentDifficulty = currentDifficulty;
    }


    /*
    Determines the amount of tiles to be removed/hidden depending on the difficulty

    @param Difficulty difficulty        -     The difficulty of the game

    @return The amount of tiles to be hidden
     */
    public static int getDifficultyTileCount(Difficulty.DifficultyEnum difficulty) {
        switch (difficulty) {
            case MEDIUM:
                return 45;
            case HARD:
                return 50;
            case EXPERT:
                return 55;
            default:
                return 40;
        }
    }


}
