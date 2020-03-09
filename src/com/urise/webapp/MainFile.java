package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File("C:.\\.gitignore");
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File("C:.\\src\\com\\urise\\webapp");
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
        try (FileInputStream f = new FileInputStream(filePath)) {
            System.out.println(f.read());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        print(new File("C:\\basejava"));
    }

    public static void print(File dir) {
        File[] file = dir.listFiles();
        if (file != null) {
            for (File files : file) {
                if (files.isDirectory()) {
                    print(files);
                } else {
                    System.out.println(files.getName());
                }
            }
        }
    }
}
