package SudokuSolver;

import java.io.*;
import java.util.List;

public class BoolGenerator {

    private List<Triple> input;
    private static final int NUM_OF_CLAUSES = 14661;

    public BoolGenerator(List<Triple> input) {
        this.input = input;
    }

    public void generate() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("boolLanguage.txt")))) {
            //head of file with number of variables and clauses
            writer.write("c This file is machine generated! Don't change it!");
            writer.newLine();
            writer.write("p cnf 729 " + (NUM_OF_CLAUSES + input.size()));
            writer.newLine();
            //no more than one in every
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    for (int k = 1; k < 10; k++) {
                        for (int t = 1; t < 10; t++) {
                            if (t == k) {
                                break;
                            } else {
                                writer.write("-" + Triple.convert(i, j, k) + " " + "-" + Triple.convert(i, j, t) + " 0");
                                writer.newLine();
                            }
                        }
                    }
                }
            }
            //-----------------------------

            //at least one num
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    for (int k = 1; k < 10; k++) {
                        writer.write(Triple.convert(i, j, k) + " ");
                    }
                    writer.write("0");
                    writer.newLine();
                }
            }
            //-----------------------------
            //no repeats in line
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    for (int t = 0; t < 9; t++) {
                        if (j == t) {
                            break;
                        }
                        for (int k = 1; k < 10; k++) {
                            writer.write("-" + Triple.convert(i, j, k) + " " + "-" + Triple.convert(i, t, k) + " 0");
                            writer.newLine();
                        }
                    }
                }
            }
            //------------------------------

            //no repeats in column
            for (int i = 0; i < 9; i++) {
                for (int t = 0; t < 9; t++) {
                    if (i == t) {
                        break;
                    }
                    for (int j = 0; j < 9; j++) {
                        for (int k = 1; k < 10; k++) {
                            writer.write("-" + Triple.convert(i, j, k) + " " + "-" + Triple.convert(t, j, k) + " 0");
                            writer.newLine();
                        }
                    }
                }
            }
            //------------------------------
            //no repeats in squares
            int q = 0, d = 0;
            while (q <= 6) {
                while (d <= 6) {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            for (int t = 0; t < 3; t++) {
                                for (int p = 0; p < 3; p++) {
                                    for (int k = 1; k < 10; k++) {
                                        if ((i + q) != (t + q) || (j + d) != (p + d)) {
                                            //System.out.println((i + q) + " " + (j + d) + " " + k + " -> not " + (t + q) + " " + (p + d) + " " + k );
                                            writer.write("-" + Triple.convert(i + q, j + d, k) + " " + "-" + Triple.convert(t + q, p + d, k) + " 0");
                                            writer.newLine();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    d += 3;
                }
                d = 0;
                q += 3;
            }
            //------------------------------

            //adding inputData
            for (Triple triple : input) {
                writer.write(triple.convertThis() + " 0");
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

