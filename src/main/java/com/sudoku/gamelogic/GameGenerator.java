package com.sudoku.gamelogic;

import com.sudoku.gameutilities.Difficulty;
import com.sudoku.gameutilities.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.sudoku.gameutilities.SudokuGame.GRID_BOUNDARY;
/*
Class in charge of generating the new game, and handling difficulty
 */
public class GameGenerator {

    /*
    Gets a new Sudoku game grid, and saves the solved one for later checks

    @param Difficulty difficulty       -     The difficulty of the game to start

    @return new int[][] grid containing the new game
     */
    public static int[][] getNewGameGrid(Difficulty.DifficultyEnum difficulty) {
        int filledCells = Difficulty.getDifficultyTileCount(difficulty);
        int[][] solvedGame = getSolvedGame();
        SudokuSolver.setSolvedGrid(solvedGame);

        return unsolveGame(solvedGame, filledCells);
    }

    /*
    Generates a new 9x9 2D Array (solved game).
    For each value in the range 1 to 9, distribute that value 9 times according to the following criteria:
    Choose a random coordinate on the grid. If it is unoccupied, assign a random value.
    Ensure that the resulting assignment does not result in invalid rows, columns, or squares.

   @return new int[][] with the solved game
    */
    public static int[][] getSolvedGame() {
        Random random = new Random(System.currentTimeMillis());
        int[][] newGrid = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        for(int value = 1; value <= GRID_BOUNDARY; value++) {
            int allocations = 0;
            int interrupt = 0;

            List<Coordinates> allocTracker = new ArrayList<>();

            int attempts = 0;

            while(allocations < GRID_BOUNDARY) {
                if(interrupt > 200) {
                    allocTracker.forEach(coord -> {
                        newGrid[coord.getX()][coord.getY()] = 0;
                    });

                    interrupt = 0;
                    allocations = 0;
                    allocTracker.clear();
                    attempts++;

                    if(attempts > 500) {
                        clearArray(newGrid);
                        attempts = 0;
                        value = 1;
                    }
                }

                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if(newGrid[xCoordinate][yCoordinate] == 0) {
                    newGrid[xCoordinate][yCoordinate] = value;

                    if(GameLogic.sudokuIsInvalid(newGrid)) {
                        newGrid[xCoordinate][yCoordinate] = 0;
                        interrupt++;
                    } else {
                        allocTracker.add(new Coordinates(xCoordinate, yCoordinate));
                        allocations++;
                    }
                }
            }
        }

        return newGrid;
    }

    /*
    Unsolves the game depending on the amount of tiles to remove/hide

    @param int[][] solvedGame              -     The grid containing the fully solved game
    @param int filledTiles                 -     The tiles to remove/hide

    @return new int[][] grid with the unsolved game
     */
    private static int[][] unsolveGame(int[][] solvedGame, int filledTiles) {
        Random random = new Random(System.currentTimeMillis());

        boolean solvable = false;

        int[][] solvableArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        while (!solvable){

            //Take values from solvedGame and write to new unsolved; i.e. reset to initial state
            SudokuUtilities.copySudokuArrayValues(solvedGame, solvableArray);

            int index = 0;
            while (index < filledTiles) {
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if (solvableArray[xCoordinate][yCoordinate] != 0) {
                    solvableArray[xCoordinate][yCoordinate] = 0;
                    index++;
                }
            }

            int[][] toBeSolved = new int[GRID_BOUNDARY][GRID_BOUNDARY];
            SudokuUtilities.copySudokuArrayValues(solvableArray, toBeSolved);

            solvable = SudokuSolver.puzzleIsSolvable(toBeSolved, filledTiles);
        }

        return solvableArray;
    }

    /*
    Small utility to clear the grid array
     */
    private static void clearArray(int[][] newGrid) {
        for(int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for(int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                newGrid[xIndex][yIndex] = 0;
            }
        }
    }
}
