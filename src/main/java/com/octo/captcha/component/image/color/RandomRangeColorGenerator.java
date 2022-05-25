package com.octo.captcha.component.image.color;

import java.awt.Color;
import java.security.SecureRandom;
import java.util.Random;

public class RandomRangeColorGenerator implements ColorGenerator {
    public static final int MIN_COLOR_COMPONENT_VALUE = 0;

    public static final int MAX_COLOR_COMPONENT_VALUE = 255;

    public static final int TRANSPARENT_ALPHA_COMPONENT_VALUE = 0;

    public static final int OPAQUE_ALPHA_COMPONENT_VALUE = 255;

    private int[] redComponentRange;

    private int[] greenComponentRange;

    private int[] blueComponentRange;

    private int[] alphaComponentRange;

    private Random random = new SecureRandom();

    public RandomRangeColorGenerator(int[] redComponentRange, int[] greenComponentRange, int[] blueComponentRange, int[] alphaComponentRange) {
        validateColorComponentRange(redComponentRange);
        setRedComponentRange(redComponentRange);
        validateColorComponentRange(greenComponentRange);
        setGreenComponentRange(greenComponentRange);
        validateColorComponentRange(blueComponentRange);
        setBlueComponentRange(blueComponentRange);
        validateColorComponentRange(alphaComponentRange);
        setAlphaComponentRange(alphaComponentRange);
    }

    public RandomRangeColorGenerator(int[] redComponentRange, int[] greenComponentRange, int[] blueComponentRange) {
        this(redComponentRange, greenComponentRange, blueComponentRange, new int[] { 255, 255 });
    }

    private void validateColorComponentRange(int[] colorComponentRange)  {
        if (colorComponentRange.length != 2)
            throw new RuntimeException("Range length must be 2");
        if (colorComponentRange[0] > colorComponentRange[1])
            throw new RuntimeException("Start value of color component range is greater than end value");
        validateColorComponentValue(colorComponentRange[0]);
        validateColorComponentValue(colorComponentRange[1]);
    }

    private void validateColorComponentValue(int colorComponentValue) {
        if (colorComponentValue < 0 || colorComponentValue > 255)
            throw new RuntimeException("Color component value is always between 0 and 255");
    }

    public Color getNextColor() {
        int red = getRandomInRange(this.redComponentRange[0], this.redComponentRange[1]);
        int green = getRandomInRange(this.greenComponentRange[0], this.greenComponentRange[1]);
        int blue = getRandomInRange(this.blueComponentRange[0], this.blueComponentRange[1]);
        int alpha = getRandomInRange(this.alphaComponentRange[0], this.alphaComponentRange[1]);
        System.out.println("red   = " + red);
        System.out.println("green = " + green);
        System.out.println("blue  = " + blue);
        System.out.println("alpha = " + alpha);
        return new Color(red, green, blue, alpha);
    }

    private int getRandomInRange(int start, int end) {
        if (start == end)
            return start;
        return this.random.nextInt(end - start) + start;
    }

    private void setAlphaComponentRange(int[] alphaComponentRange) {
        this.alphaComponentRange = alphaComponentRange;
    }

    private void setBlueComponentRange(int[] blueComponentRange) {
        this.blueComponentRange = blueComponentRange;
    }

    private void setGreenComponentRange(int[] greenComponentRange) {
        this.greenComponentRange = greenComponentRange;
    }

    private void setRedComponentRange(int[] redComponentRange) {
        this.redComponentRange = redComponentRange;
    }
}
