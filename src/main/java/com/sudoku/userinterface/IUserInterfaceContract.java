package com.sudoku.userinterface;

import com.sudoku.constants.Difficulty;
import com.sudoku.gameutilities.SudokuGame;

public interface IUserInterfaceContract {
    interface EventListener {
        void onSudokuInput(SudokuTextField source, int input);
        void onDialogClick(Difficulty difficulty);
    }

    interface View {
        void setListener(IUserInterfaceContract.EventListener listener);
        void updateSquare(int x, int y, int input);
        void updateBoard(SudokuGame game);
        void showDialog(String message, Difficulty difficulty);
        void showError(String message);
    }
}
