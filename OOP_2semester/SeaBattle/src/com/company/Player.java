package com.company;

public abstract class Player {
    static final int SIZE_OF_FIELD = 10;
    static  final int MAX_SHIP_SIZE = 4;
    PlayerGameField gameField;
    protected boolean checkPoint(byte x, byte y, boolean[][] field) {
        boolean flag = true;
        if (field[x][y]) {
            flag = false;
        }
        if (checkRange(x + 1) && checkRange(y) && field[x + 1][y]) {
            flag = false;
        }
        if (checkRange(x) && checkRange(y + 1) && field[x][y + 1]) {
            flag = false;
        }
        if (checkRange(x + 1) && checkRange(y + 1) && field[x + 1][y + 1]) {
            flag = false;
        }
        if (checkRange(x - 1) && checkRange(y) && field[x - 1][y]) {
            flag = false;
        }
        if (checkRange(x) && checkRange(y - 1) && field[x][y - 1]) {
            flag = false;
        }
        if (checkRange(x - 1) && checkRange(y - 1) && field[x - 1][y - 1]) {
            flag = false;
        }
        if (checkRange(x + 1) && checkRange(y - 1) && field[x + 1][y - 1]) {
            flag = false;
        }
        if (checkRange(x - 1) && checkRange(y + 1) && field[x - 1][y + 1]) {
            flag = false;
        }
        return flag;
    }

    public void ShootResult(byte i, byte[] position) {
        gameField.setPoint(position[0], position[1], i);
    }

    public void Attacked(byte[] position) {
        gameField.Attacked(position[0], position[1]);
    }

    public void drawSituation() {
        GameClient.drawField(gameField.getYourField());
        GameClient.drawField(gameField.getEnemyField());
    }

    protected boolean checkRange(int i) {
        return (i >= 0 && i < SIZE_OF_FIELD);
    }
}
/* 0 - empty
   1 - empty shotted
   2 - ship shotted
   3 - ship without problems
 */