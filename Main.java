package com.company;

public class Main {

    public static void main(String[] args) {
        String savePath = "C:\\Games\\savegames\\";

        GameProgress gameProgress1 = new GameProgress(80, 10, 2, 100.5);
        GameProgress gameProgress2 = new GameProgress(50, 20, 10, 401.2);
        GameProgress gameProgress3 = new GameProgress(10, 2, 15, 1401.0);

        GameProgress[] games = new GameProgress[]{gameProgress1, gameProgress2};

        GameProgress.saveGame(gameProgress1, savePath);
        GameProgress.saveGame(gameProgress2, savePath);
        GameProgress.saveGame(gameProgress3, savePath);

        GameProgress.zipFile(games, savePath);

        GameProgress.deleteFile(gameProgress3, savePath);

        String source = "C:\\Games\\savegames\\saves.zip";
        String extract = "C:\\Games\\savegames\\unpack\\";
        GameProgress.openZip(source, extract);
        GameProgress.openProgress("C:\\Games\\savegames\\unpack\\100.5.dat");
    }
}
