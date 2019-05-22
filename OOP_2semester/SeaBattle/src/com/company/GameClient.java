package com.company;

import java.util.Scanner;
import static com.company.Player.SIZE_OF_FIELD;

public class GameClient {
    private static Scanner sc = new Scanner(System.in);

    public static void print(String s) {
        System.out.println(s);
    }

    public static byte[] readPosition() {
        byte[] position = new byte[2];
        String[] input = sc.nextLine().split(" ");
        position[0] = (byte)Integer.parseInt(input[0]);
        position[1] = (byte)Integer.parseInt(input[1]);
        if (position[0] > SIZE_OF_FIELD || position[0] < 0 || position[1] > SIZE_OF_FIELD || position[1] < 0)
        {
            print("Wrong format of coordinates. Enter right ones");
            position = readPosition();
        }
        return position;
    }

    public static void drawField(byte[][] field) {
        for (int i = 0; i < SIZE_OF_FIELD; i++) {
            for (int j = 0; j < SIZE_OF_FIELD; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("----------------------------");
    }
}
