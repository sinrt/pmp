package com.octo.captcha.component.image.fontgenerator;

public abstract class AbstractFontGenerator implements FontGenerator {
    private int minFontSize = 10;

    private int maxFontSize = 14;

    AbstractFontGenerator(Integer minFontSize, Integer maxFontSize) {
        this.minFontSize = (minFontSize != null) ? minFontSize.intValue() : this.minFontSize;
        this.maxFontSize = (maxFontSize != null && maxFontSize.intValue() >= this.minFontSize) ? maxFontSize.intValue() : Math.max(this.maxFontSize, this.minFontSize + 1);
    }

    public int getMinFontSize() {
        return this.minFontSize;
    }

    public int getMaxFontSize() {
        return this.maxFontSize;
    }
}
