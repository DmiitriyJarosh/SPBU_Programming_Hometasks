package com.company;
import java.io.*;

public class Main {

    private static String readFrom(FileReader reader)
    throws java.io.IOException {
        boolean flag = false;
        boolean first = true;
        boolean second = true;
        String string  = "";
        char c;
        while ((c = (char)reader.read()) != -1 && c != '\n') {
            if (c == '"' && flag) {
                flag = false;
                string += '\n';
            } else if (flag) {
                string += c;
            } else if (c == '"' && !flag && !second) {
                flag = true;
            } else if (c == '"' && first) {
                first = false;
            } else if (c == '"') {
                second = false;
            }
        }
        return string;
    }

    public static void main(String[] args) {
        String[] words = {""}, files = {""};
        String file = "", code = "";
        try (FileReader reader = new FileReader("..\\SourceMapEncoder\\src\\com\\company\\SourceMap.txt")) {
            file = Main.readFrom(reader);
            file = file.substring(0, file.length() - 1);
            words = Main.readFrom(reader).split("\n");
            files = Main.readFrom(reader).split("\n");
            code = Main.readFrom(reader);
            code = code.substring(0, code.length() - 1);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        Decoder decoder = new Decoder(code);
        Printer.open("..\\SourceMapEncoder\\src\\com\\company\\decoded.txt");
        Printer.println("For file: " + file);
        Decoder.PrintSourceMap(decoder.Decode(), words, files);
        Printer.close();
    }
}
