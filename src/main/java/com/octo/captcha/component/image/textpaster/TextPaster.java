package com.octo.captcha.component.image.textpaster;

import java.awt.image.BufferedImage;
import java.text.AttributedString;

public interface TextPaster {
    int getMaxAcceptedWordLength();

    int getMinAcceptedWordLength();

    BufferedImage pasteText(BufferedImage paramBufferedImage, AttributedString paramAttributedString);
}
