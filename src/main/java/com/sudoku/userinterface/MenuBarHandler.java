package com.sudoku.userinterface;

import com.sudoku.gameutilities.Difficulty;
import com.sudoku.constants.Messages;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/*
This class handles all the MenuBar content used in the program
 */
public class MenuBarHandler {

    /*
    Loads all Menu Bar Items into a MenuBar object

    @param IUserInterfaceContract.View view  - The class in charge of handling interface (UserInterfaceImpl)
     */
    private static MenuBar loadMenuBarItems(IUserInterfaceContract.View view) {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Game");

        MenuItem item1 = new MenuItem("New Game");

        item1.setOnAction(actionEvent -> {
            view.showDialog(Messages.NEW_GAME, Difficulty.getCurrentDifficulty());
        });

        menu.getItems().add(item1);

        Menu difficultyMenu = new Menu("Difficulty");
        MenuItem easyMenuItem = new MenuItem("Easy");
        MenuItem mediumMenuItem = new MenuItem("Medium");
        MenuItem hardMenuItem = new MenuItem("Hard");
        MenuItem expertMenuItem = new MenuItem("Expert");

        easyMenuItem.setOnAction( actionEvent -> {
            view.showDialog(Messages.CHANGE_DIFFICULTY_EASY, Difficulty.DifficultyEnum.EASY);
            Difficulty.setCurrentDifficulty(Difficulty.DifficultyEnum.EASY);
        });
        mediumMenuItem.setOnAction( actionEvent -> {
            view.showDialog(Messages.CHANGE_DIFFICULTY_MEDIUM, Difficulty.DifficultyEnum.MEDIUM);
            Difficulty.setCurrentDifficulty(Difficulty.DifficultyEnum.MEDIUM);
        });
        hardMenuItem.setOnAction( actionEvent -> {
            view.showDialog(Messages.CHANGE_DIFFICULTY_HARD, Difficulty.DifficultyEnum.HARD);
            Difficulty.setCurrentDifficulty(Difficulty.DifficultyEnum.HARD);
        });
        expertMenuItem.setOnAction( actionEvent -> {
            view.showDialog(Messages.CHANGE_DIFFICULTY_EXPERT, Difficulty.DifficultyEnum.EXPERT);
            Difficulty.setCurrentDifficulty(Difficulty.DifficultyEnum.EXPERT);
        });

        difficultyMenu.getItems().addAll(easyMenuItem, mediumMenuItem, hardMenuItem, expertMenuItem);

        menuBar.getMenus().addAll(menu, difficultyMenu);

        return menuBar;
    }

    /*
    Loads the MenuBar item into the JavaFX stage

    @param Stage primaryStage
    @param IUserInterfaceContract.View uiImpl
     */
    public static void loadMenuBar(Stage primaryStage, IUserInterfaceContract.View uiImpl) {
        MenuBar menuBar = loadMenuBarItems(uiImpl);
        ((BorderPane) primaryStage.getScene().getRoot()).setTop(menuBar);
        primaryStage.show();
    }
}
