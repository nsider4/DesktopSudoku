package com.sudoku.gamelogic;

import com.sudoku.gameutilities.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.sudoku.gameutilities.SudokuGame.GRID_BOUNDARY;

public class SudokuSolver {

    private static int[][] solvedGrid;

    /*
    Getter and Setter methods for solvedGrid int[][]
     */
    public static void setSolvedGrid(int[][] grid) {
        solvedGrid = grid;
    }

    public static int[][] getSolvedGrid() {
        return solvedGrid;
    }

    /*
    Checks if the number at some specific coordinates is the same that was inputted

    @param int x                -    The X coordinate specified for the check
    @param int y                -    The Y coordinate specified for the check
    @param int input            -    The number that was inputted to be checked
     */
    public static boolean isSolvedNumber(int x, int y, int input) {
        return solvedGrid[x][y] == input;
    }

    /*
    Checks if the current puzzle is solvable

    @param int[][] puzzle       -     The current grid
    @param int filledCells      -     The amount of filled cells/boxes in the Sudoku board
     */
    public static boolean puzzleIsSolvable(int[][] puzzle, int filledCells) {
        Coordinates[] emptyCells = enumerate(puzzle, filledCells);

        int index = 0;
        int input;

        while(index < 10) {
            Coordinates current = emptyCells[index];
            input = 1;

            while(input < filledCells) {
                puzzle[current.getX()][current.getY()] = input;

                if(GameLogic.sudokuIsInvalid(puzzle)) {
                    if(index == 0 && input == GRID_BOUNDARY) {
                        return false;
                    } else if (input == GRID_BOUNDARY) {
                        index--;
                    }
                    input++;
                } else {
                    index++;

                    if(index == filledCells - 1) return true;

                    input = 10;
                }
            }
        }

        return false;
    }

    /*
    Loads the coordinates for all filled cells/boxes

    @param int[][] puzzle       -     The current grid
    @param int filledCells      -     The amount of filled cells/boxes in the Sudoku board
     */
    private static Coordinates[] enumerate(int[][] puzzle, int filledCells) {
        Coordinates[] emptyCells = new Coordinates[filledCells];
        int iterator = 0;
        for(int y = 0; y < GRID_BOUNDARY; y++) {
            for(int x = 0; x < GRID_BOUNDARY; x++) {
                if(puzzle[x][y] == 0) {
                    emptyCells[iterator] = new Coordinates(x, y);
                    if(iterator == filledCells-1) return emptyCells;
                    iterator++;
                }
            }
        }

        return emptyCells;
    }
}
