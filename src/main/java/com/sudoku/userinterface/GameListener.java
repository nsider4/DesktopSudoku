package com.sudoku.userinterface;

import com.sudoku.constants.Difficulty;
import com.sudoku.gamelogic.GameLogic;
import com.sudoku.constants.GameState;
import com.sudoku.constants.Messages;
import com.sudoku.gameutilities.Mistakes;
import com.sudoku.gamelogic.SudokuSolver;
import com.sudoku.gameutilities.IStorage;
import com.sudoku.gameutilities.SudokuGame;

import java.io.IOException;

/*
This class handles all game-related events that occur throughout the run time of the program
 */

public class GameListener implements IUserInterfaceContract.EventListener {
    private final IStorage storage;
    private final IUserInterfaceContract.View view;

    public GameListener(IStorage storage, IUserInterfaceContract.View view) {
        this.storage = storage;
        this.view = view;
    }

    /*
    Handles each time a number is inputted inside the Sudoku board and does all necessary checks

    @param SudokuTextField source   -    The text tile that was changed
    @param int input                -    The number inputted
     */
    @Override
    public void onSudokuInput(SudokuTextField source, int input) {
        try {
            SudokuGame gameData = storage.getGameData();
            int[][] newGridState = gameData.getCopyOfGridState();
            int x = source.getX(); int y = source.getY();

            newGridState[x][y] = input;

            view.updateSquare(x, y, input);

            if(input == 0) {
                return;
            }

            handleMistake(source, x, y, input, gameData);

            if(!source.getStyle().matches("-fx-text-fill: red;")) {
                updateGameData(newGridState);
            }

            if (gameData.getGameState() == GameState.COMPLETE) {
                view.showDialog(Messages.GAME_COMPLETE, Difficulty.EASY);
            }
        } catch (IOException e) {
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }
    }

    /*
    Method in charge of handling all the changes in order to create a new game

    @param Difficulty difficulty      -     The difficulty of the game to be started
     */
    @Override
    public void onDialogClick(Difficulty difficulty) {
        try {
            storage.updateGameData(
                    GameLogic.getNewGame(difficulty)
            );

            UserInterfaceImpl.getMistakesText().setText("Mistakes: " + Mistakes.getMistakeCount());

            view.updateBoard(storage.getGameData());
        } catch (IOException e) {
            view.showError(Messages.ERROR);
        }
    }

    /*
    Updates all game data

    @param int[][] newGridState - The updated grid for the Sudoku game
     */
    private void updateGameData(int[][] newGridState) throws IOException {
        SudokuGame gameData = new SudokuGame(
                GameLogic.checkForCompletion(newGridState),
                newGridState,
                SudokuSolver.getSolvedGrid(),
                Mistakes.getMistakeCount()
        );
        storage.updateGameData(gameData);
    }

    /*
    Handles all checks and actions when a user enters the wrong number into a certain coordinate inside the board

    @param SudokuTextField source     -    The source tile that was interacted with when the event was fired
    @param int x                      -    The X coordinate for the specific spot in the board
    @param int y                      -    The Y coordinate for the specific spot in the board
    @param input                      -    The number that was inputted into the board
    @param SudokuGame gameData        -    The current SudokuGame object which contains all data on the current game
     */
    private void handleMistake(SudokuTextField source, int x, int y, int input, SudokuGame gameData) throws IOException {
        if (!SudokuSolver.isSolvedNumber(x, y, input)) {
            Mistakes.setMistakeCount(Mistakes.getMistakeCount() + 1);
            gameData.setMistakes(Mistakes.getMistakeCount());
            storage.updateGameData(gameData);

            UserInterfaceImpl.getMistakesText().setText("Mistakes: " + Mistakes.getMistakeCount());
            source.setStyle("-fx-text-fill: red;");
            view.showError(Messages.MISTAKE);
        } else {
            source.setStyle("");
        }
    }
}
