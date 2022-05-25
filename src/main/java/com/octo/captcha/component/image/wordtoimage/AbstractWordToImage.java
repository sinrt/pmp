package com.octo.captcha.component.image.wordtoimage;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.text.AttributedString;
public abstract class AbstractWordToImage implements WordToImage {
    private boolean manageFontByCharacter = true;

    protected AbstractWordToImage() {}

    protected AbstractWordToImage(boolean manageFontByCharacter) {
        this.manageFontByCharacter = manageFontByCharacter;
    }

    public BufferedImage getImage(String word) {
        int wordLength = checkWordLength(word);
        AttributedString attributedWord = getAttributedString(word, wordLength);
        BufferedImage background = getBackground();
        return pasteText(background, attributedWord);
    }

    AttributedString getAttributedString(String word, int wordLength) {
        AttributedString attributedWord = new AttributedString(word);
        Font font = getFont();
        for (int i = 0; i < wordLength; i++) {
            attributedWord.addAttribute(TextAttribute.FONT, font, i, i + 1);
            if (this.manageFontByCharacter)
                font = getFont();
        }
        return attributedWord;
    }

    int checkWordLength(String word) {
        if (word == null)
            throw new RuntimeException("null word");//todo
        int wordLength = word.length();
        if (wordLength > getMaxAcceptedWordLength() || wordLength < getMinAcceptedWordLength())
            throw new RuntimeException("invalid length word");//todo
        return wordLength;
    }

    abstract Font getFont();

    abstract BufferedImage getBackground();

    abstract BufferedImage pasteText(BufferedImage paramBufferedImage, AttributedString paramAttributedString);
}
