package SudokuSolver;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Fileworker {

    public static List<Triple> getInput(String filename) {
        List<Triple> res = new LinkedList<>();
        int lineCounter = 0;
        int columnCounter = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    if (Character.isDigit(line.charAt(i))) {
                        res.add(new Triple(lineCounter, columnCounter, line.charAt(i) - '0'));
                    }
                    if (Character.isDigit(line.charAt(i)) || line.charAt(i) == '_') {
                        columnCounter++;
                    }
                }
                columnCounter = 0;
                lineCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void printResult(int[] res, String filename) {
        int[][] matrix = new int[9][9];
        Triple tmp;
        for (int i = 0; i < res.length; i++) {
            tmp = Triple.decode(res[i]);
            if (tmp != null) {
                //System.out.println(tmp.getFirst() + " " + tmp.getSecond() + " " + (tmp.getThird() == 0 ? 9 : tmp.getThird()));
                matrix[tmp.getFirst()][tmp.getSecond()] = (tmp.getThird() == 0 ? 9 : tmp.getThird());
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)))) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    writer.write(matrix[i][j] + " ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
