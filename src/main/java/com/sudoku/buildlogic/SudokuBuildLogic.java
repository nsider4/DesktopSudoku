package com.sudoku.buildlogic;

import com.sudoku.gameutilities.Difficulty;
import com.sudoku.constants.GameState;
import com.sudoku.gamelogic.GameLogic;
import com.sudoku.gamelogic.SudokuSolver;
import com.sudoku.gameutilities.Mistakes;
import com.sudoku.storage.LocalStorageImpl;
import com.sudoku.gameutilities.IStorage;
import com.sudoku.gameutilities.SudokuGame;
import com.sudoku.userinterface.IUserInterfaceContract;
import com.sudoku.userinterface.GameListener;
import com.sudoku.userinterface.UserInterfaceImpl;

import java.io.IOException;

public class SudokuBuildLogic {


    /*
    Handles the build logic for the program

    If there is existent game data, it will try to load it with all its information
    In the case it fails, it will start a brand-new game

    @param IUserInterfaceContract.View userInterface     -    The class responsible for all interface-related changes
     */
    public static void build(IStorage storage, IUserInterfaceContract.View userInterface) throws IOException {
        IUserInterfaceContract.EventListener uiLogic
                = new GameListener(storage, userInterface);

        storage.getGameData().setGameState(GameState.NEW);
        userInterface.setListener(uiLogic);
        userInterface.updateBoard(storage.getGameData());
    }

    public static void loadGameData(IStorage storage) throws IOException {
        SudokuGame intialState;

        try {
            intialState = storage.getGameData();
            SudokuSolver.setSolvedGrid(intialState.getSolvedGrid());
            Mistakes.setMistakeCount(intialState.getMistakes());
            Difficulty.setCurrentDifficulty(intialState.getDifficulty());
        } catch (IOException e) {
            intialState = GameLogic.getNewGame(Difficulty.DifficultyEnum.EASY);
            storage.updateGameData(intialState);
        }
    }
}
