package com.company;

import java.util.Random;

import static com.company.Casino.AMOUNT_OF_NUMBERS;

public class Roulette {
    public int spin() {
        Random random = new Random();
        return random.nextInt(AMOUNT_OF_NUMBERS);
    }
}
