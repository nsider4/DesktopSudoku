package com.sudoku.gamelogic;

import com.sudoku.constants.Difficulty;
import com.sudoku.constants.GameState;
import com.sudoku.constants.Rows;
import com.sudoku.gameutilities.Mistakes;
import com.sudoku.gameutilities.SudokuGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sudoku.gameutilities.SudokuGame.GRID_BOUNDARY;

public class GameLogic {

    /*
    Creates a new game in the specified difficulty

    @return The new SudokuGame object after resetting
     */
    public static SudokuGame getNewGame(Difficulty difficulty) {
        Mistakes.resetMistakes();
        return new SudokuGame(
                GameState.NEW,
                GameGenerator.getNewGameGrid(difficulty),
                SudokuSolver.getSolvedGrid(),
                Mistakes.getMistakeCount()
        );
    }

    /*
    Checks if the grid specified is completed

    @param int[][] grid       -    Grid to be checked

    @return The current state of the game (ACTIVE or COMPLETE)
     */
    public static GameState checkForCompletion(int[][] grid) {
        if(sudokuIsInvalid(grid)) return GameState.ACTIVE;
        if(tilesAreNotFilled(grid)) return GameState.ACTIVE;
        return GameState.COMPLETE;
    }

    /*
    Checks if current Sudoku grid is invalid

    @param int[][] grid       -    Grid to be checked

    @return True if it's invalid, false otherwise
     */
    public static boolean sudokuIsInvalid(int[][] grid) {
        if(rowsOrColumnsAreInvalid(grid, true)) return true;
        if(rowsOrColumnsAreInvalid(grid, false)) return true;
        if(squaresAreInvalid(grid)) return true;
        else return false;
    }

    /*
    Helper method to check for valid rows and columns

    @param int[][] grid       -    Grid to be checked
    @param boolean checkRows  -    Weather to check rows or columns, if false it will be columns

    @return True if one of the rows or columns are invalid for the current grid, false otherwise
     */
    private static boolean rowsOrColumnsAreInvalid(int[][] grid, boolean checkRows) {
        int boundary = GRID_BOUNDARY;
        for (int i = 0; i < boundary; i++) {
            List<Integer> values = new ArrayList<>();
            for (int j = 0; j < boundary; j++) {
                if (checkRows) {
                    values.add(grid[i][j]); //Check rows
                } else {
                    values.add(grid[j][i]); //Check columns
                }
            }

            if (collectionHasRepeats(values)) {
                return true;
            }
        }

        return false;
    }

    /*
    Helper method to check for valid squares in the Sudoku board

    @param int[][] grid       -    Grid to be checked

    @return True if one of the squares is invalid, false otherwise
     */
    private static boolean squaresAreInvalid(int[][] grid) {
        if(rowOfSquaresIsInvalid(Rows.TOP, grid)) return true;

        if(rowOfSquaresIsInvalid(Rows.MIDDLE, grid)) return true;

        if(rowOfSquaresIsInvalid(Rows.BOTTOM, grid)) return true;

        return false;
    }

    /*
    Helper method to check for 'squaresAreInvalid' that checks each square in the grid
     */
    private static boolean rowOfSquaresIsInvalid(Rows rows, int[][] grid) {
        switch(rows) {
            case TOP:
                if(squareIsInvalid(0, 0, grid)) return true;
                if(squareIsInvalid(0, 3, grid)) return true;
                if(squareIsInvalid(0, 6, grid)) return true;
                return false;
            case MIDDLE:
                if(squareIsInvalid(3, 0, grid)) return true;
                if(squareIsInvalid(3, 3, grid)) return true;
                if(squareIsInvalid(3, 6, grid)) return true;
                return false;
            case BOTTOM:
                if(squareIsInvalid(6, 0, grid)) return true;
                if(squareIsInvalid(6, 3, grid)) return true;
                if(squareIsInvalid(6, 6, grid)) return true;
                return false;
            default:
                return false;
        }
    }

    /*
    Algorithm that analyzes a 3x3 square in the current Sudoku grid

    @param int xIndex               -    The X coordinate to begin with
    @param int yIndex               -    The Y coordinate to begin with
    @param int[][] grid             -    The current Sudoku grid

    @return True if the square is invalid, false otherwise
     */
    private static boolean squareIsInvalid(int xIndex, int yIndex, int[][] grid) {
        int yIndexEnd = yIndex + 3;
        int xIndexEnd = xIndex + 3;

        List<Integer> square = new ArrayList<>();

        while(yIndex < yIndexEnd) {
            while(xIndex < xIndexEnd) {
                square.add(
                        grid[xIndex][yIndex]
                );

                xIndex++;
            }

            xIndex -= 3;

            yIndex++;
        }

        if(collectionHasRepeats(square)) {
            return true;
        }
        return false;
    }

    /*
    Utility method used to check if there are any numbers repeated in a list

    @param List<Integer> collection        -    The list of numbers to check

    @return True if there are repeats, false otherwise
     */
    public static boolean collectionHasRepeats(List<Integer> collection) {
        for(int index = 1; index <= GRID_BOUNDARY; index++) {
            if(Collections.frequency(collection, index) > 1) return true;
        }

        return false;
    }

    /*
    Checks if a certain tile/box is not currently filled

    @param int[][] grid           -    The current Sudoku grid
     */
    public static boolean tilesAreNotFilled(int[][] grid) {
        for(int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for(int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                if(grid[xIndex][yIndex] == 0) return true;
            }
        }

        return false;
    }
}
