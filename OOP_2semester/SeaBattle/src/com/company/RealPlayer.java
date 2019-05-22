package com.company;

import static java.lang.Math.abs;

public class RealPlayer extends Player implements IPlayer {
    public RealPlayer() {
        gameField = new PlayerGameField();
    }
    public boolean[][] GetShips() {
        boolean[][] field = new boolean[SIZE_OF_FIELD][SIZE_OF_FIELD];
        for (int i = 0; i < SIZE_OF_FIELD; i++) {
            for (int j = 0; j < SIZE_OF_FIELD; j++) {
                field[i][j] = false;
            }
        }
        byte[] tailPosition, nosePosition, tmp;
        boolean flag;
        boolean mistakeFlag;
        GameClient.print("Now you need to place your ships on the field in the following format: \nx y\nx' y'\nwhere x and y coordinates of the beginning and x' y' are coordinates of the end of the ship");
        for (int j = MAX_SHIP_SIZE; j > 0; j--) {   //go throw all types of ships
            for (int k = MAX_SHIP_SIZE + 1 - j; k > 0; k--) { // read enough numbers of this type of ship
                GameClient.print("Enter coordinates of " + Integer.toString(j) + "-squared ship");
                mistakeFlag = true;
                while (mistakeFlag) { //wait for not wrong coords
                    nosePosition = GameClient.readPosition();
                    tailPosition = GameClient.readPosition();
                    if ((tailPosition[0] == nosePosition[0] && abs(nosePosition[1] - tailPosition[1]) == j - 1) || (tailPosition[1] == nosePosition[1] && abs(nosePosition[0] - tailPosition[0]) == j - 1)) {
                        mistakeFlag = false;
                        if (tailPosition[0] == nosePosition[0]) {
                            if (nosePosition[1] > tailPosition[1]) {
                                tmp = nosePosition;
                                nosePosition = tailPosition;
                                tailPosition = tmp;
                            }
                            for (byte i = nosePosition[1]; i <= tailPosition[1]; i++) {
                                flag = checkPoint(nosePosition[0], i, field); //check if other ship too close
                                if (!flag) {
                                    mistakeFlag = true;
                                    GameClient.print("Other ships too close. Try again");
                                    break;
                                }
                            }
                            if (!mistakeFlag) {
                                gameField.SetShip(nosePosition[0], nosePosition[0], nosePosition[1], tailPosition[1]);
                                for (byte i = nosePosition[1]; i <= tailPosition[1]; i++) {
                                    field[nosePosition[0]][i] = true;
                                }
                            }
                        } else {
                            if (nosePosition[0] > tailPosition[0]) {
                                tmp = nosePosition;
                                nosePosition = tailPosition;
                                tailPosition = tmp;
                            }
                            for (byte i = nosePosition[0]; i <= tailPosition[0]; i++) {
                                flag = checkPoint(i, nosePosition[1], field); //check if other ship too close
                                if (!flag) {
                                    mistakeFlag = true;
                                    GameClient.print("Other ships too close. Try again");
                                    break;
                                }
                            }
                            if (!mistakeFlag) {
                                gameField.SetShip(nosePosition[0], tailPosition[0], nosePosition[1], nosePosition[1]);
                                for (byte i = nosePosition[0]; i <= tailPosition[0]; i++) {
                                    field[i][nosePosition[1]] = true;
                                }
                            }
                        }
                    } else {
                        GameClient.print("Wrong coordinates. Try again");
                    }
                }
            }
        }
        return field;
    }

    public byte[] Shoot() {
        byte[] position = {0, 0};
        boolean flag = false;
        while (!flag) {
            GameClient.print("Enter coordinates for shoot");
            position = GameClient.readPosition();
            if (gameField.getPoint(position[0], position[1]) != 0) {
                GameClient.print("You have shot there earlier. Choose another coordinates");
            } else {
                flag = true;
            }
        }
        return position;
    }
}
