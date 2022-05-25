package com.octo.captcha.component.image.fontgenerator;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RandomFontGenerator extends AbstractFontGenerator {
    private int[] STYLES = new int[] { 0, 2, 1, 3 };

    private String requiredCharacters = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static String[] defaultBadFontNamePrefixes = new String[] { "Courier", "Times Roman" };

    private String[] badFontNamePrefixes = defaultBadFontNamePrefixes;

    private static final int GENERATED_FONTS_ARRAY_SIZE = 3000;

    private boolean mixStyles = true;

    private Font[] generatedFonts = null;

    protected Random myRandom = new SecureRandom();

    public RandomFontGenerator(Integer minFontSize, Integer maxFontSize) {
        super(minFontSize, maxFontSize);
        initializeFonts(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts());
    }

    public RandomFontGenerator(Integer minFontSize, Integer maxFontSize, Font[] fontsList) {
        super(minFontSize, maxFontSize);
        if (fontsList == null || fontsList.length < 1)
            throw new IllegalArgumentException("fonts list cannot be null or empty");
        initializeFonts(fontsList);
    }

    public RandomFontGenerator(Integer minFontSize, Integer maxFontSize, Font[] fontsList, boolean mixStyles) {
        super(minFontSize, maxFontSize);
        if (fontsList == null || fontsList.length < 1)
            throw new IllegalArgumentException("fonts list cannot be null or empty");
        this.mixStyles = mixStyles;
        initializeFonts(fontsList);
    }

    public RandomFontGenerator(Integer minFontSize, Integer maxFontSize, String[] badFontNamePrefixes) {
        super(minFontSize, maxFontSize);
        this.badFontNamePrefixes = badFontNamePrefixes;
        initializeFonts(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts());
    }

    private void initializeFonts(Font[] fontList) {
        List fonts = cleanFontList(fontList);
        checkInitializedFontListSize(fonts);
        this.generatedFonts = generateCustomStyleFontArray(fonts);
    }

    private void checkInitializedFontListSize(List fontList) {
        if (fontList.size() < 1)
            throw new IllegalArgumentException("fonts list cannot be null or empty, some of your font are removed from the list by this class, Courrier and TimesRoman");
    }

    public Font getFont() {
        return this.generatedFonts[Math.abs(this.myRandom.nextInt(3000))];
    }

    private Font[] generateCustomStyleFontArray(List<Font> fontList) {
        Font[] generatedFonts = new Font[3000];
        for (int i = 0; i < 3000; i++) {
            Font font = fontList.get(this.myRandom.nextInt(fontList.size()));
            Font styled = applyStyle(font);
            generatedFonts[i] = applyCustomDeformationOnGeneratedFont(styled);
        }
        return generatedFonts;
    }

    protected Font applyStyle(Font font) {
        int fontSizeIncrement = 0;
        if (getFontSizeDelta() > 0)
            fontSizeIncrement = Math.abs(this.myRandom.nextInt(getFontSizeDelta()));
        Font styled = font.deriveFont(this.mixStyles ? this.STYLES[this.myRandom.nextInt(this.STYLES.length)] : font.getStyle(), (getMinFontSize() + fontSizeIncrement));
        return styled;
    }

    private int getFontSizeDelta() {
        return getMaxFontSize() - getMinFontSize();
    }

    protected Font applyCustomDeformationOnGeneratedFont(Font font) {
        return font;
    }

    protected List cleanFontList(Font[] uncheckFonts) {
        List goodFonts = new ArrayList(uncheckFonts.length);
        goodFonts.addAll(Arrays.asList(uncheckFonts));
        for (Iterator<Font> iter = goodFonts.iterator(); iter.hasNext(); ) {
            Font f = iter.next();
            if (!checkFontNamePrefix(iter, f))
                checkFontCanDisplayCharacters(iter, f);
        }
        return goodFonts;
    }

    private boolean checkFontNamePrefix(Iterator iter, Font f) {
        boolean removed = false;
        for (int i = 0; i < this.badFontNamePrefixes.length; i++) {
            String prefix = this.badFontNamePrefixes[i];
            if (prefix != null && !"".equals(prefix))
                if (f.getName().startsWith(prefix)) {
                    iter.remove();
                    removed = true;
                    break;
                }
        }
        return removed;
    }

    private boolean checkFontCanDisplayCharacters(Iterator iter, Font f) {
        boolean removed = false;
        for (int i = 0; i < this.requiredCharacters.length(); i++) {
            if (!f.canDisplay(this.requiredCharacters.charAt(i))) {
                iter.remove();
                removed = true;
                break;
            }
        }
        return removed;
    }

    public String getRequiredCharacters() {
        return this.requiredCharacters;
    }

    public void setRequiredCharacters(String requiredCharacters) {
        this.requiredCharacters = requiredCharacters;
    }

    public String[] getBadFontNamePrefixes() {
        return this.badFontNamePrefixes;
    }

    public void setBadFontNamePrefixes(String[] badFontNamePrefixes) {
        this.badFontNamePrefixes = badFontNamePrefixes;
    }
}
