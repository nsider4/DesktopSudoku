package com.sudoku.storage;

import com.sudoku.gameutilities.IStorage;
import com.sudoku.gameutilities.SudokuGame;

import java.io.*;

public class LocalStorageImpl implements IStorage {
    //Gets the file 'gamedata.txt' file
    private static File GAME_DATA = new File(
            System.getProperty("user.home"),
            "gamedata.txt"
    );

    /*
    Updates the game data inside the file

    @param SudokuGame game             -     SudokuGame object containing the updated game data/information
     */
    @Override
    public void updateGameData(SudokuGame game) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(GAME_DATA);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(game);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new IOException("Unable to access Game Data");
        }
    }

    /*
    Gets the SudokuGame object for the existing game data

    @return The SudokuGame object with the data obtained from the file
     */
    @Override
    public SudokuGame getGameData() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(GAME_DATA);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            SudokuGame gameState = (SudokuGame) objectInputStream.readObject();
            objectInputStream.close();
            return gameState;
        } catch (ClassNotFoundException e) {
            throw new IOException("File not found");
        }
    }
}
