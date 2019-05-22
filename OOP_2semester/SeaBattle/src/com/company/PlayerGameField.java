package com.company;

import static com.company.Player.SIZE_OF_FIELD;

public class PlayerGameField {
    private byte[][] enemyField;
    private byte[][] yourField;

    public PlayerGameField() {
        enemyField = new byte[SIZE_OF_FIELD][SIZE_OF_FIELD];
        yourField = new byte[SIZE_OF_FIELD][SIZE_OF_FIELD];
        for (int i = 0; i < SIZE_OF_FIELD; i++) {
            for (int j = 0; j < SIZE_OF_FIELD; j++) {
                enemyField[i][j] = 0;
                yourField[i][j] = 0;
            }
        }
    }

    public void SetShip(byte coordXbeg, byte coordXend, byte coordYbeg, byte coordYend) {
        for (byte i = coordYbeg; i <= coordYend; i++) {
            for (byte j = coordXbeg; j <= coordXend; j++)
            {
                yourField[j][i] = 3;
            }
        }
    }

    public byte getPoint(int i, int j) {
        return enemyField[i][j];
    }

    public void setPoint(int i, int j, byte result) {
        enemyField[i][j] = result;
    }

    public void Attacked(int i, int j) {
        if (yourField[i][j] == 0) {
            yourField[i][j] = 1;
        } else {
            yourField[i][j] = 2;
        }
    }

    public byte[][] getEnemyField() {
        return enemyField;
    }

    public byte[][] getYourField() {
        return yourField;
    }
}
