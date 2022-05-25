package com.octo.captcha.component.image.wordtoimage;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.textpaster.TextPaster;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

public class ComposedWordToImage extends AbstractWordToImage {
    private FontGenerator fontGenerator;

    private BackgroundGenerator background;

    private TextPaster textPaster;

    public ComposedWordToImage(FontGenerator fontGenerator, BackgroundGenerator background, TextPaster textPaster) {
        this.background = background;
        this.fontGenerator = fontGenerator;
        this.textPaster = textPaster;
    }

    public ComposedWordToImage(boolean manageFontByCharacter, FontGenerator fontGenerator, BackgroundGenerator background, TextPaster textPaster) {
        super(manageFontByCharacter);
        this.fontGenerator = fontGenerator;
        this.background = background;
        this.textPaster = textPaster;
    }

    public int getMaxAcceptedWordLenght() {
        return this.textPaster.getMaxAcceptedWordLength();
    }

    public int getMinAcceptedWordLenght() {
        return this.textPaster.getMinAcceptedWordLength();
    }

    public int getMaxAcceptedWordLength() {
        return this.textPaster.getMaxAcceptedWordLength();
    }

    public int getMinAcceptedWordLength() {
        return this.textPaster.getMinAcceptedWordLength();
    }

    public int getImageHeight() {
        return this.background.getImageHeight();
    }

    public int getImageWidth() {
        return this.background.getImageWidth();
    }

    public int getMinFontSize() {
        return this.fontGenerator.getMinFontSize();
    }

    Font getFont() {
        return this.fontGenerator.getFont();
    }

    BufferedImage getBackground() {
        return this.background.getBackground();
    }

    BufferedImage pasteText(BufferedImage background, AttributedString attributedWord) {
        return this.textPaster.pasteText(background, attributedWord);
    }
}
