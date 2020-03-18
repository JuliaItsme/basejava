package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    static String counterDir = "";
    static String str = "";
    static String counterFile = "";
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File("C:\\basejava");
        System.out.println(dir.isDirectory());
        String[] str = dir.list();
        if (str != null) {
            for (String name : str) {
                System.out.println(name);
            }
        }
        /*
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
         */
        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        printDirectory(dir);
    }

    public static void printDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println(counterDir + "Directory: " + file.getName());
                    counterDir += "   ";
                    str = counterDir;
                    printDirectory(file);
                    counterFile = "";
                } else if (file.isFile()) {
                    counterFile = str;
                    System.out.println(counterFile + "File: " + file.getName());
                    counterDir = "";
                }
            }
        }
    }
}
