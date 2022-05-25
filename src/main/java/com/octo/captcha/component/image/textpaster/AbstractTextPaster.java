package com.octo.captcha.component.image.textpaster;

import com.octo.captcha.component.image.color.ColorGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Random;

public  abstract class AbstractTextPaster implements TextPaster {
    public Random myRandom = new SecureRandom();

    private int max = 20;

    private int min = 6;

    private ColorGenerator colorGenerator = (ColorGenerator)new SingleColorGenerator(Color.black);

    private boolean manageColorPerGlyph = false;

    AbstractTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength) {
        this.max = (maxAcceptedWordLength != null) ? maxAcceptedWordLength.intValue() : this.max;
        this.min = (minAcceptedWordLength != null && minAcceptedWordLength.intValue() <= this.max) ? minAcceptedWordLength.intValue() : Math.min(this.min, this.max - 1);
    }

    AbstractTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength, Color textColor) {
        this(minAcceptedWordLength, maxAcceptedWordLength);
        if (textColor != null)
            this.colorGenerator = (ColorGenerator)new SingleColorGenerator(textColor);
    }

    AbstractTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength, ColorGenerator colorGenerator) {
        this(minAcceptedWordLength, maxAcceptedWordLength);
        if (colorGenerator == null)
            throw new RuntimeException("ColorGenerator is null");
        this.colorGenerator = colorGenerator;
    }

    AbstractTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength, ColorGenerator colorGenerator, Boolean manageColorPerGlyph) {
        this(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator);
        this.manageColorPerGlyph = (manageColorPerGlyph != null) ? manageColorPerGlyph.booleanValue() : this.manageColorPerGlyph;
    }

    public int getMaxAcceptedWordLenght() {
        return this.max;
    }

    public int getMinAcceptedWordLenght() {
        return this.min;
    }

    public int getMaxAcceptedWordLength() {
        return this.max;
    }

    public int getMinAcceptedWordLength() {
        return this.min;
    }

    protected ColorGenerator getColorGenerator() {
        return this.colorGenerator;
    }

    BufferedImage copyBackground(BufferedImage background) {
        BufferedImage out = new BufferedImage(background.getWidth(), background.getHeight(), background.getType());
        return out;
    }

    Graphics2D pasteBackgroundAndSetTextColor(BufferedImage out, BufferedImage background) {
        Graphics2D pie = (Graphics2D)out.getGraphics();
        pie.drawImage(background, 0, 0, out.getWidth(), out.getHeight(), null);
        pie.setColor(this.colorGenerator.getNextColor());
        return pie;
    }

    void customizeGraphicsRenderingHints(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    public boolean isManageColorPerGlyph() {
        return this.manageColorPerGlyph;
    }

    public void setColorGenerator(ColorGenerator colorGenerator) {
        this.colorGenerator = colorGenerator;
    }
}
