package com.sudoku;

import com.sudoku.buildlogic.SudokuBuildLogic;
import com.sudoku.gameutilities.IStorage;
import com.sudoku.storage.LocalStorageImpl;
import com.sudoku.userinterface.IUserInterfaceContract;
import com.sudoku.userinterface.MenuBarHandler;
import com.sudoku.userinterface.UserInterfaceImpl;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SudokuApplication extends Application {
    private IUserInterfaceContract.View uiImpl;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(700);
        primaryStage.setResizable(false);

        IStorage storage = new LocalStorageImpl();
        SudokuBuildLogic.loadGameData(storage);

        uiImpl = new UserInterfaceImpl(primaryStage);
        MenuBarHandler.loadMenuBar(primaryStage, uiImpl);

        try {
            SudokuBuildLogic.build(storage, uiImpl);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}