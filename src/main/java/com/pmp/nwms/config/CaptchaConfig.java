package com.pmp.nwms.config;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.pmp.nwms.NwmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

@Configuration
public class CaptchaConfig {
    @Autowired
    private NwmsConfig nwmsConfig;

    @Bean
    public Font fontArial() {
        return new Font("Arial", Font.PLAIN, 10);
    }

    @Bean
    public Font fontTahoma() {
        return new Font("Tahoma", Font.PLAIN, 10);
    }

    @Bean
    public Font fontVerdana() {
        return new Font("Verdana", Font.PLAIN, 10);
    }

    @Bean
    public FontGenerator fontGenerator() {
        return new RandomFontGenerator(nwmsConfig.getCaptchaMinFontSize(), nwmsConfig.getCaptchaMaxFontSize(), new Font[]{fontArial(), fontTahoma(), fontVerdana()});
    }

    @Bean
    public BackgroundGenerator backgroundGenerator() {
        String firstColorRGB = nwmsConfig.getCaptchaBackgroundFirstColorRGB();
        String secondColorRGB = nwmsConfig.getCaptchaBackgroundSecondColorRGB();
        return new GradientBackgroundGenerator(nwmsConfig.getCaptchaImageWidth(), nwmsConfig.getCaptchaImageHeight(),
            getColorFromRGB(firstColorRGB),
            getColorFromRGB(secondColorRGB)
        );
    }

    @Bean
    public TextPaster textPaster() {
        String textColorRGB = nwmsConfig.getCaptchaTextColorRGB();
        return new RandomTextPaster(nwmsConfig.getCaptchaMinAcceptedWordLength(), nwmsConfig.getCaptchaMaxAcceptedWordLength(), getColorFromRGB(textColorRGB));
    }

    private Color getColorFromRGB(String colorRGB) {
        return new Color(Integer.valueOf(colorRGB.substring(0, 2), 16),
            Integer.valueOf(colorRGB.substring(2, 4), 16),
            Integer.valueOf(colorRGB.substring(4), 16));
    }

    @Bean
    public WordToImage wordToImage() {
        return new ComposedWordToImage(fontGenerator(), backgroundGenerator(), textPaster());
    }
}
