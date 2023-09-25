package com.sudoku.gameutilities;

public class Mistakes {

    private static int mistakeCount;

    public static void resetMistakes() {
        mistakeCount = 0;
    }

    public static int getMistakeCount() {
        return mistakeCount;
    }

    public static void setMistakeCount(int mistakeAmount) {
        mistakeCount = mistakeAmount;
    }
}
