package com.octo.captcha.component.image.backgroundgenerator;


import com.octo.captcha.component.image.color.ColorGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class GradientBackgroundGenerator extends AbstractBackgroundGenerator {
    ColorGenerator firstColor = null;

    ColorGenerator secondColor = null;

    public GradientBackgroundGenerator(Integer width, Integer height, Color firstColor, Color secondColor) {
        super(width, height);
        if (firstColor == null || secondColor == null)
            throw new RuntimeException("Color is null");
        this.firstColor = (ColorGenerator)new SingleColorGenerator(firstColor);
        this.secondColor = (ColorGenerator)new SingleColorGenerator(secondColor);
    }

    public GradientBackgroundGenerator(Integer width, Integer height, ColorGenerator firstColorGenerator, ColorGenerator secondColorGenerator) {
        super(width, height);
        if (firstColorGenerator == null || secondColorGenerator == null)
            throw new RuntimeException("ColorGenerator is null");
        this.firstColor = firstColorGenerator;
        this.secondColor = secondColorGenerator;
    }

    public BufferedImage getBackground() {
        BufferedImage bi = new BufferedImage(getImageWidth(), getImageHeight(), 1);
        Graphics2D pie = (Graphics2D)bi.getGraphics();
        pie.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0.0F, getImageHeight(), this.firstColor.getNextColor(), getImageWidth(), 0.0F, this.secondColor.getNextColor());
        pie.setPaint(gp);
        pie.fillRect(0, 0, getImageWidth(), getImageHeight());
        pie.dispose();
        return bi;
    }
}
