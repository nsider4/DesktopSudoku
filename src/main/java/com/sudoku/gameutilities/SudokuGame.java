package com.sudoku.gameutilities;

import com.sudoku.gamelogic.SudokuUtilities;
import com.sudoku.constants.GameState;

import java.io.Serializable;

public class SudokuGame implements Serializable {
    private GameState gameState;
    private final int[][] gridState;
    private final int[][] solvedGrid;
    private int mistakes;

    public static final int GRID_BOUNDARY = 9;

    public SudokuGame(GameState gameState, int[][] gridState, int[][] solvedGrid, int mistakes) {
        this.gameState = gameState;
        this.gridState = gridState;
        this.solvedGrid = solvedGrid;
        this.mistakes = mistakes;
    }

    public GameState getGameState() {
        return gameState;
    }
    public void setGameState(GameState state) { this.gameState = state; }

    public int getMistakes() {
        return this.mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }
    public int[][] getSolvedGrid() {
        return this.solvedGrid;
    }

    public int[][] getCopyOfGridState() {
        return SudokuUtilities.copyToNewArray(gridState);
    }


}
