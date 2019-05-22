package com.company;

import java.io.*;

public class Printer {
    private static PrintWriter fr = null;
    public static void open(String path) {
        try {
            fr = new PrintWriter(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void println(String string) {
            fr.println(string);
    }
    public static void close() {
        fr.close();
    }
}
