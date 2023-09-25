package com.sudoku.userinterface;

import com.sudoku.constants.Difficulty;
import com.sudoku.constants.GameState;
import com.sudoku.constants.Messages;
import com.sudoku.gamelogic.SudokuUtilities;
import com.sudoku.gameutilities.Mistakes;
import com.sudoku.gameutilities.Coordinates;
import com.sudoku.gameutilities.SudokuGame;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;

public class UserInterfaceImpl implements IUserInterfaceContract.View, EventHandler<KeyEvent> {


    private final Stage stage;
    private final AnchorPane anchorPane1;
    private final AnchorPane anchorPane2;
    private final BorderPane root;
    private static Text mistakesText;
    private final HashMap<Coordinates, SudokuTextField> textFieldCoordinates;
    private IUserInterfaceContract.EventListener listener;

    private static final double WINDOW_Y = 700;
    private static final double WINDOW_X = 700;
    private static final double BOARD_PADDING = 50;
    private static final double BOARD_X_AND_Y = 576;

    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0, 150, 136);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224, 242, 241);
    private static final String SUDOKU = "SUDOKU";

    /*
    Initializing all scene related utilities
     */
    public UserInterfaceImpl(Stage stage) {
        this.stage = stage;
        this.root = new BorderPane();
        this.anchorPane1 = new AnchorPane();
        this.anchorPane2 = new AnchorPane();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
    }

    /*
    Draws the whole UI
     */
    private void initializeUserInterface() {
        drawBackground(root);
        drawTitle(anchorPane1);
        drawSudokuBoard(anchorPane1);
        drawTextFields(anchorPane1);
        drawGridLines(anchorPane1);
        drawUI();
    }

    /*
    @returns mistakesText
     */
    public static Text getMistakesText() {
        return mistakesText;
    }

    /*
    Draws basic UI
     */
    private void drawUI() {
        mistakesText = new Text("Mistakes: ");

        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(event -> showDialog(Messages.NEW_GAME, Difficulty.EASY));

        AnchorPane.setTopAnchor(mistakesText, 10.0);
        AnchorPane.setLeftAnchor(mistakesText, 10.0);
        AnchorPane.setTopAnchor(newGameButton, 560.0);
        AnchorPane.setLeftAnchor(newGameButton, 15.0);
        AnchorPane.setRightAnchor(newGameButton, 15.0);
        AnchorPane.setBottomAnchor(newGameButton, 40.0);
        newGameButton.setCenterShape(true);
        anchorPane2.getChildren().addAll(mistakesText, newGameButton);

        anchorPane1.setMaxWidth(670);
        anchorPane2.setMinWidth(200); anchorPane2.setMaxWidth(230);
        SplitPane splitPane = new SplitPane(anchorPane1, anchorPane2);

        root.setCenter(splitPane);
    }

    /*
    Draws the grid lines in the Sudoku board

    @param AnchorPane root
     */
    private void drawGridLines(AnchorPane root) {
        int xAndY = 114;
        int index = 0;
        while (index < 8) {
            int thickness;
            if(index == 2 || index == 5) {
                thickness = 3;
            } else {
                thickness = 2;
            }

            Rectangle verticalLine = getLine(
                    xAndY + 64 * index,
                    BOARD_PADDING,
                    BOARD_X_AND_Y,
                    thickness
            );

            Rectangle horizontalLine = getLine(
                    BOARD_PADDING,
                    xAndY + 64 * index,
                    thickness,
                    BOARD_X_AND_Y
            );

            root.getChildren().addAll(
                    verticalLine,
                    horizontalLine
            );

            index++;
        }
    }

    /*
    Helper method for drawing grid lines
     */
    private Rectangle getLine(double x,
                              double y,
                              double height,
                              double width) {
        Rectangle line = new Rectangle();

        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.BLACK);
        return line;
    }

    /*
    Creates all the text fields for boxes that can be edited

    @param AnchorPane root
     */
    private void drawTextFields(AnchorPane root) {
        final int xOrigin = 50;
        final int yOrigin = 50;

        final int xAndYDelta = 64;

        //O(n^2) Runtime Complexity
        for(int xIndex = 0; xIndex < 9; xIndex++) {
            for(int yIndex = 0; yIndex < 9; yIndex++) {
                int x = xOrigin + xIndex * xAndYDelta;
                int y = yOrigin + yIndex * xAndYDelta;

                SudokuTextField tile = new SudokuTextField(xIndex, yIndex);

                styleSudokuTile(tile, x, y);

                tile.setOnKeyPressed(this);

                textFieldCoordinates.put(new Coordinates(xIndex, yIndex), tile);

                root.getChildren().add(tile);
            }
        }
    }

    /*
    Helper method for drawTextFields that stiles the tile and text used
     */
    private void styleSudokuTile(SudokuTextField tile, int x, int y) {
        Font numberFont = new Font(32);

        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);

        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(64);
        tile.setPrefWidth(64);

        tile.setBackground(Background.EMPTY);
    }

    /*
    Draws the basic board

    @param AnchorPane root
     */
    private void drawSudokuBoard(AnchorPane root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);

        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setHeight(BOARD_X_AND_Y);

        boardBackground.setFill(BOARD_BACKGROUND_COLOR);

        root.getChildren().addAll(boardBackground);
    }

    /*
    Creates the title

    @param AnchorPane root
     */
    private void drawTitle(AnchorPane root) {
        Text title = new Text(255, 40, SUDOKU);
        title.setFill(Color.DARKSLATEBLUE);
        Font titleFont = new Font(43);
        title.setFont(titleFont);
        root.getChildren().add(title);
    }

    /*
    Customizes the window's background

    @param BorderPane root
     */
    private void drawBackground(BorderPane root) {
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }

    /*
    Below are all the runtime-related methods which will handle various tasks to achieve the desired functionality
     */

    @Override
    public void setListener(IUserInterfaceContract.EventListener listener) {
        this.listener = listener;
    }


    @Override
    public void updateSquare(int x, int y, int input) {
        TextField tile = textFieldCoordinates.get(new Coordinates(x, y));
        String value = (input == 0) ? "" : String.valueOf(input);

        Platform.runLater(() -> tile.textProperty().setValue(value));
    }

    @Override
    public void updateBoard(SudokuGame game) {
        for(int xIndex = 0; xIndex < 9; xIndex++) {
            for(int yIndex = 0; yIndex < 9; yIndex++) {
                TextField tile = textFieldCoordinates.get(new Coordinates(xIndex, yIndex));

                String value = Integer.toString(
                        game.getCopyOfGridState()[xIndex][yIndex]
                );

                if(value.equals("0")) value = "";

                tile.setText(
                        value
                );

                if(game.getGameState() == GameState.NEW) {
                    if(value.isEmpty()) {
                        tile.setStyle("-fx-opacity: 1;");
                        tile.setDisable(false);
                    } else {
                        tile.setStyle("-fx-opacity: 0.8;");
                        tile.setDisable(true);
                    }
                }
            }
        }
    }

    @Override
    public void showDialog(String message, Difficulty difficulty) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        ButtonType result = alert.getResult();
        if (result == ButtonType.YES) {
            listener.onDialogClick(difficulty);
        }
    }

    @Override
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.show();
    }

    @Override
    public void handle(KeyEvent event) {
        if(event.getEventType() == KeyEvent.KEY_PRESSED) {
            if(event.getText().matches("[0-9]")) {

                int value = Integer.parseInt(event.getText());
                handleInput(value, event.getSource());

            } else if (event.getText().matches(".*\\D.*")) {
                handleInput(0, event.getSource());
            } else {
                handleInput(0, event.getSource());
            }
        }
    }

    private void handleInput(int value, Object source) {
        listener.onSudokuInput(
                (SudokuTextField) source,
                value
        );
    }
}
