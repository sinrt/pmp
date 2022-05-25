package com.octo.captcha.component.image.wordtoimage;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

public class SimpleWordToImage extends AbstractWordToImage {
    public int getMaxAcceptedWordLength() {
        return 10;
    }

    public int getMinAcceptedWordLength() {
        return 1;
    }

    public int getMaxAcceptedWordLenght() {
        return 10;
    }

    public int getMinAcceptedWordLenght() {
        return 1;
    }

    public int getImageHeight() {
        return 50;
    }

    public int getImageWidth() {
        return 100;
    }

    public int getMinFontSize() {
        return 10;
    }

    public Font getFont() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[0];
    }

    public BufferedImage getBackground() {
        BufferedImage background = new BufferedImage(getImageWidth(), getImageHeight(), 1);
        return background;
    }

    BufferedImage pasteText(BufferedImage background, AttributedString attributedWord) {
        Graphics graph = background.getGraphics();
        int x = (getImageWidth() - getMaxAcceptedWordLength()) / 2;
        int y = (getImageHeight() - getMinFontSize()) / 2;
        graph.drawString(attributedWord.getIterator(), x, y);
        graph.dispose();
        return background;
    }
}
