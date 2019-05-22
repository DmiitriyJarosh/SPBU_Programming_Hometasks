package com.company;

import static com.company.Player.MAX_SHIP_SIZE;

public class GameServer {
    private IPlayer firstPlayer;
    private IPlayer secondPlayer;
    public GameServer(IPlayer firstPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = new CleverBotPlayer(); // always plays with clever one
    }
    public void Game(int typeOfOutput) {
        boolean[][] firstPlayerField, secondPlayerField;
        int sizeOfShip = 1, amountOfShips = MAX_SHIP_SIZE;
        int firstPlayerLiveCount = 0, secondPlayerLiveCount = 0;
        for (int i = 0 ; i < MAX_SHIP_SIZE; i++) {
            firstPlayerLiveCount += sizeOfShip * amountOfShips;
            sizeOfShip++;
            amountOfShips--;
        }
        secondPlayerLiveCount = firstPlayerLiveCount;
        byte[] position;
        int turn = 1;
        boolean winFlag = false;
        firstPlayerField = firstPlayer.GetShips();
        secondPlayerField = secondPlayer.GetShips();
        while (!winFlag) {
            if (typeOfOutput == 0) {
                firstPlayer.drawSituation();
                secondPlayer.drawSituation();
            } else if (typeOfOutput == 1) {
                secondPlayer.drawSituation();
            } else {
                firstPlayer.drawSituation();
            }

            if (turn == 1) {
                position = firstPlayer.Shoot();
                if (secondPlayerField[position[0]][position[1]]) {
                    secondPlayer.Attacked(position);
                    firstPlayer.ShootResult((byte)2, position);

                    secondPlayerLiveCount--;
                } else {
                    secondPlayer.Attacked(position);
                    firstPlayer.ShootResult((byte)1, position);
                    turn = 2;
                }
            } else {
                position = secondPlayer.Shoot();
                if (firstPlayerField[position[0]][position[1]]) {
                    firstPlayer.Attacked(position);
                    secondPlayer.ShootResult((byte)2, position);
                    firstPlayerLiveCount--;
                } else {
                    firstPlayer.Attacked(position);
                    secondPlayer.ShootResult((byte)1, position);
                    turn = 1;
                }
            }
            if (firstPlayerLiveCount == 0 || secondPlayerLiveCount == 0) {
                winFlag = true;
            }
        }
        if (firstPlayerLiveCount == 0) {
            GameClient.print("WIN OF THE SECOND PLAYER!!!");
        } else {
            GameClient.print("WIN OF THE FIRST PLAYER!!!");
        }
    }
}
