package com.company;

public interface IPlayer {
    boolean[][] GetShips();
    byte[] Shoot();
    void Attacked(byte[] position);
    void ShootResult(byte i, byte[] position);
    void drawSituation();
}
