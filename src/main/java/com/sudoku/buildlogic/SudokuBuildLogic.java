package com.sudoku.buildlogic;

import com.sudoku.constants.Difficulty;
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
    public static void build(IUserInterfaceContract.View userInterface) throws IOException {
        SudokuGame intialState;
        IStorage storage = new LocalStorageImpl();

        try {
            intialState = storage.getGameData();
            SudokuSolver.setSolvedGrid(intialState.getSolvedGrid());
            Mistakes.setMistakeCount(intialState.getMistakes());
            UserInterfaceImpl.getMistakesText().setText("Mistakes: " + Mistakes.getMistakeCount());
        } catch (IOException e) {
            intialState = GameLogic.getNewGame(Difficulty.EASY);
            storage.updateGameData(intialState);
        }


        IUserInterfaceContract.EventListener uiLogic
                = new GameListener(storage, userInterface);

        intialState.setGameState(GameState.NEW);
        userInterface.setListener(uiLogic);
        userInterface.updateBoard(intialState);
    }
}
