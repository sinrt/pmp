package com.pmp.nwms.util.captcha;

import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.pmp.nwms.NwmsConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class CaptchaUtil {
    private static final Logger log = LoggerFactory.getLogger(CaptchaUtil.class);

    @Autowired
    private NwmsConfig nwmsConfig;
    @Autowired
    private WordToImage wordToImage;

    private Map<String, String> generatedCaptchas = new HashMap<>();

    public CaptchaInfoDto generateCaptcha() {
        ByteArrayOutputStream baos = null;
        try {
            CaptchaInfoDto captchaInfoDto = new CaptchaInfoDto();
            int captchaLength = getRandomNumberUsingInts(nwmsConfig.getCaptchaMinAcceptedWordLength(), nwmsConfig.getCaptchaMaxAcceptedWordLength());
            String captchaText = RandomStringUtils.random(captchaLength, "23456789abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ");
            BufferedImage image = wordToImage.getImage(captchaText);
            String clientKey = UUID.randomUUID().toString().replace("-", "");
            captchaInfoDto.setClientKey(clientKey);
            baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(bytes);
            captchaInfoDto.setCaptchaImageFileContent(encodeImage);
            generatedCaptchas.put(clientKey, captchaText);
            return captchaInfoDto;
        } catch (IOException e) {
            throw new RuntimeException(e);//todo this should never happen!
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
            }
        }
    }

    public boolean validateCaptcha(String clientKey, String captchaValue) {
        String captchaOriginalValue = null;
        if (generatedCaptchas.containsKey(clientKey)) {
            captchaOriginalValue = generatedCaptchas.remove(clientKey);
        }
        return captchaValue.equalsIgnoreCase(captchaOriginalValue);
    }

    private int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
            .findFirst()
            .getAsInt();
    }
}
