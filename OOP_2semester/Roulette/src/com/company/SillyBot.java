package com.company;

import java.util.Random;

import static com.company.Bet.*;
import static com.company.Casino.AMOUNT_OF_NUMBERS;

public class SillyBot extends Player {
    public static final int SILLY_BOT_BET = 20;

    public SillyBot(int cash) {
        this.cash = cash;
    }

    public Bet makeBet() {
        Random random = new Random();
        return new Bet(BET_ON_NUMBER, random.nextInt(AMOUNT_OF_NUMBERS), SILLY_BOT_BET);
    }
}
