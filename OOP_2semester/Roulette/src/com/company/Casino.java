package com.company;

import static com.company.Bet.*;

public class Casino {

    private int spinResult;
    private BetWorker betWorker;
    public static final int COLOUR_WIN_COEF = 2;
    public static final int DOZEN_WIN_COEF = 3;
    public static final int SIZE_WIN_COEF = 2;
    public static final int ONE_NUMBER_WIN_COEF = 36;
    public static final int AMOUNT_OF_NUMBERS = 37;
    public static final int[] NULL_NUMBERS = new int[]{0}; //if you want to add 00, just change AMOUNT_OF_NUMBERS and classify 00 as the next number after old AMOUNT_OF_NUMBERS and add it to this array

    public void setBetWorker(BetWorker betWorker) {
        this.betWorker = betWorker;
    }

    public void setSpinResult(int spinResult) {
        this.spinResult = spinResult;
    }

    public boolean notNull(int spinResult) {
        for (int i : NULL_NUMBERS) {
            if (spinResult == i) {
                return false;
            }
        }
        return true;
    }

    public boolean isRed(int spinResult) {
        return (spinResult % 2 != 0);
    }

    public boolean isBlack(int spinResult) {
        return (spinResult % 2 == 0);
    }

    public void payBets() {
        int winCoef;
        if (notNull(spinResult)) {
            for (int i = 0; i < betWorker.getNumOfPlayers(); i++) {
                winCoef = getWinCoef(betWorker.getBet(i), spinResult);
                betWorker.pay(i, winCoef);
            }
        }
    }

    public int getWinCoef (Bet bet, int spinResult) {
        switch (bet.getTypeOfBet()) {
            case BET_ON_COLOUR:
                if (checkColourBet(bet, spinResult)) {
                    return COLOUR_WIN_COEF;
                }
                break;
            case BET_ON_DOZEN:
                if (checkDozenBet(bet, spinResult)) {
                    return DOZEN_WIN_COEF;
                }
                break;
            case BET_ON_SIZE:
                if (checkSizeBet(bet, spinResult)) {
                    return SIZE_WIN_COEF;
                }
                break;
            case BET_ON_NUMBER:
                if (checkNumberBet(bet, spinResult)) {
                    return ONE_NUMBER_WIN_COEF;
                }
                break;
        }
        return 0;
    }

    public boolean checkColourBet (Bet bet, int spinResult) {
        if ((bet.getBetChoice() == RED_BET && isRed(spinResult)) || (bet.getBetChoice() == BLACK_BET && isBlack(spinResult))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkDozenBet (Bet bet, int spinResult) {
        if ((bet.getBetChoice() == FIRST_DOZEN_BET && spinResult <= 12) || (bet.getBetChoice() == SECOND_DOZEN_BET && spinResult > 12 && spinResult <= 24) || (bet.getBetChoice() == THIRD_DOZEN_BET && spinResult > 24)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkSizeBet (Bet bet, int spinResult) {
        if ((bet.getBetChoice() == LITTLE_NUMBER_BET && spinResult <= 18) ||(bet.getBetChoice() == BIG_NUMBER_BET && spinResult > 18)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkNumberBet (Bet bet, int spinResult) {
        if (bet.getBetChoice() == spinResult) {
            return true;
        } else {
            return false;
        }
    }

}
