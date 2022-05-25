package com.octo.captcha.component.image.wordtoimage;


import java.awt.image.BufferedImage;

public interface WordToImage {
    int getMaxAcceptedWordLength();

    int getMinAcceptedWordLength();

    int getImageHeight();

    int getImageWidth();

    int getMinFontSize();

    BufferedImage getImage(String paramString);
}
