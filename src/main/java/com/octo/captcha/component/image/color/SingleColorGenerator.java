package com.octo.captcha.component.image.color;


import java.awt.Color;

public class SingleColorGenerator implements ColorGenerator {
    public Color color = null;

    public SingleColorGenerator(Color color) {
        if (color == null)
            throw new RuntimeException("Color is null");
        this.color = color;
    }

    public Color getNextColor() {
        return this.color;
    }
}
