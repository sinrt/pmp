package com.octo.captcha.component.image.textpaster;

import com.octo.captcha.component.image.color.ColorGenerator;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

public class RandomTextPaster  extends AbstractTextPaster {
    protected final int kerning = 20;

    protected Color[] textColors = null;

    public RandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength, Color textColor) {
        super(minAcceptedWordLength, maxAcceptedWordLength, textColor);
    }

    public RandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength, Color[] textColors) {
        super(minAcceptedWordLength, maxAcceptedWordLength);
        this.textColors = textColors;
    }

    public RandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength, ColorGenerator colorGenerator) {
        super(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator);
    }

    public RandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength, ColorGenerator colorGenerator, Boolean manageColorPerGlyph) {
        super(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator, manageColorPerGlyph);
    }

    public BufferedImage pasteText(BufferedImage background, AttributedString attributedString) {
        BufferedImage out = copyBackground(background);
        Graphics2D g2 = pasteBackgroundAndSetTextColor(out, background);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        MutableAttributedString newAttrString = new MutableAttributedString(g2, attributedString, 20);
        newAttrString.useMinimumSpacing(20.0D);
        newAttrString.moveToRandomSpot(background);
        if (isManageColorPerGlyph()) {
            newAttrString.drawString(g2, getColorGenerator());
        } else {
            newAttrString.drawString(g2);
        }
        g2.dispose();
        return out;
    }
}
