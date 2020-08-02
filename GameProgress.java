package com.company;

import java.io.*;
import java.util.zip.*;

public class GameProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }

    public static void saveGame(GameProgress gameProgress, String savePath) {
        File folder = new File(savePath);
        if (!folder.exists()) folder.mkdirs();

        File file = new File(folder, gameProgress.getDistance() + ".dat");
        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipFile(GameProgress gameProgress, String savePath) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(savePath + "saves.zip"));
             FileInputStream fis = new FileInputStream(savePath + gameProgress.getDistance() + ".dat")
        ) {
            ZipEntry entry = new ZipEntry(gameProgress.getDistance() + ".dat");
            zout.putNextEntry(entry);

            // считываем содержимое файла в массив байтов, потому что input stream работает побайтово
            byte[] buffer = new byte[fis.available()];

            fis.read(buffer);

            zout.write(buffer);
            // закрываем этот ентри, а не весь поток
            zout.closeEntry();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zipFile(GameProgress[] gameProgress, String savePath) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(savePath + "saves.zip"))) {

            for (GameProgress tmp : gameProgress) {

                try (FileInputStream fis = new FileInputStream(savePath + tmp.getDistance() + ".dat")) {
                    ZipEntry entry = new ZipEntry(tmp.getDistance() + ".dat");
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.write(buffer);
                    zos.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(GameProgress gameProgress, String savePath) {
        File folder = new File(savePath);
        File file = new File(folder, gameProgress.getDistance() + ".dat");

        if (folder.exists() && file.exists()) {
            file.delete();
        } else {
            System.out.println("Такого файла или папки не существует");
        }

    }

    // Задание 3

    public static void openZip(String source, String extract) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source))) {

            ZipEntry entry;
            String name;

            while ((entry = zis.getNextEntry()) != null) {

                name = entry.getName();

                File extractFolder = new File(extract);

                if (!extractFolder.exists()) extractFolder.mkdir();

                FileOutputStream fos = new FileOutputStream(extract + name);

                int c;
                while ((c = zis.read()) != -1) {
                    fos.write(c);
                }

                fos.flush();
                zis.closeEntry();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openProgress(String source) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(source);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(gameProgress);
    }

}
