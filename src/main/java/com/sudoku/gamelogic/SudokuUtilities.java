package com.sudoku.gamelogic;

import com.sudoku.userinterface.SudokuTextField;

import java.util.ArrayList;
import java.util.List;

import static com.sudoku.gameutilities.SudokuGame.GRID_BOUNDARY;

/*
Class with small utilities to optimize code in external class' methods
 */
public class SudokuUtilities {
    public static void copySudokuArrayValues(int[][] oldArray, int[][] newArray) {
        for(int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for(int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
            }
        }
    }

    public static int[][] copyToNewArray(int[][] oldArray) {
        int[][] newArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        for(int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for(int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
            }
        }

        return newArray;
    }
}
