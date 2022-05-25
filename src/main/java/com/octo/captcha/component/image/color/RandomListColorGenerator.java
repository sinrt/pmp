package com.octo.captcha.component.image.color;


import java.awt.Color;
import java.security.SecureRandom;
import java.util.Random;

public class RandomListColorGenerator implements ColorGenerator {
    private Color[] colorsList = null;

    private Random random = new SecureRandom();

    public RandomListColorGenerator(Color[] colorsList) {
        if (colorsList == null)
            throw new RuntimeException("Color list cannot be null");
        for (int i = 0; i < colorsList.length; i++) {
            if (colorsList[i] == null)
                throw new RuntimeException("One or several color is null");
        }
        this.colorsList = colorsList;
    }

    public Color getNextColor() {
        int index = this.random.nextInt(this.colorsList.length);
        return this.colorsList[index];
    }
}
