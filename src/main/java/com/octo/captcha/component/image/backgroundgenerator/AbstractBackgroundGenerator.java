package com.octo.captcha.component.image.backgroundgenerator;


import java.security.SecureRandom;
import java.util.Random;

public abstract class AbstractBackgroundGenerator implements BackgroundGenerator {
    private int height = 100;

    private int width = 200;

    Random myRandom = new SecureRandom();

    AbstractBackgroundGenerator(Integer width, Integer height) {
        this.width = (width != null) ? width.intValue() : this.width;
        this.height = (height != null) ? height.intValue() : this.height;
    }

    public int getImageHeight() {
        return this.height;
    }

    public int getImageWidth() {
        return this.width;
    }
}
