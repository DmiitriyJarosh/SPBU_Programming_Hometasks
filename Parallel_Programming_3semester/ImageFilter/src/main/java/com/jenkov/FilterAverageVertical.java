package com.jenkov;

import java.awt.image.BufferedImage;


import static com.jenkov.Filter.AVERAGE_9X9_HALF_SIZE;
import static com.jenkov.Filter.AVERAGE_9X9_SQUARE;

public class FilterAverageVertical implements  Runnable {
    private int widthStart;
    private int widthFinish;
    private int width;
    private int height;
    private boolean flag;
    private BufferedImage image;
    private BufferedImage newImage;


    public FilterAverageVertical(int widthStart, int widthFinish, int width, int height, BufferedImage image, BufferedImage newImage) {
        this.image = image;
        this.height = height;
        this.widthStart = widthStart;
        this.widthFinish = widthFinish;
        this.newImage = newImage;
        this.width = width;
    }
    public void run() {
        int colorRed = 0;
        int colorGreen = 0;
        int color;
        int colorBlue = 0;
        for (int i = widthStart; i < widthFinish; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = -AVERAGE_9X9_HALF_SIZE; k < AVERAGE_9X9_HALF_SIZE; k++) {
                    for (int u = -AVERAGE_9X9_HALF_SIZE; u < AVERAGE_9X9_HALF_SIZE; u++) {
                        if (!(i + k < 0 || i + k >= width || j + u < 0 || j + u >= height)) {
                            colorRed += image.getRGB(i + k, j + u) & 0xFF;
                            colorGreen += (image.getRGB(i + k, j + u) & 0xFF00) >> 8;
                            colorBlue += (image.getRGB(i + k, j + u) & 0xFF0000) >> 16;
                        }
                    }
                }
                colorRed /= AVERAGE_9X9_SQUARE;
                colorGreen /= AVERAGE_9X9_SQUARE;
                colorBlue /= AVERAGE_9X9_SQUARE;
                color = image.getRGB(i, j) & 0xFF000000;
                color |= colorRed;
                color |= colorGreen << 8;
                color |= colorBlue << 16;
                newImage.setRGB(i, j, color);
                colorRed = 0;
                colorGreen = 0;
                colorBlue = 0;
            }
        }
      // System.out.println("Thread № " + Thread.currentThread().getId() + " finished!");
    }
}