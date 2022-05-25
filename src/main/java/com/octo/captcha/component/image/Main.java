package com.octo.captcha.component.image;

import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.MultipleShapeBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import org.apache.commons.lang.RandomStringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            String colorStr = "00aff0";
            WordToImage wordToImage = new ComposedWordToImage(
                new RandomFontGenerator(20, 35),
                new GradientBackgroundGenerator(250, 75, Color.WHITE, new Color(188, 255, 191)),
                new RandomTextPaster(6, 8, new RandomListColorGenerator(new Color[]{
                 /*   new Color(39, 149, 192),
                    new Color(6, 141, 196),*/
                 new Color(Integer.valueOf( colorStr.substring(0,2), 16 ),
                     Integer.valueOf( colorStr.substring(2,4), 16 ),
                     Integer.valueOf( colorStr.substring(4), 16 ))
//                    new Color(52, 132, 177),
//                    new Color(41, 129, 170)
 /*                   new Color(63, 170, 160),
                    new Color(99, 133, 195),
                    new Color(23, 166, 157)*/
                }))
            );
            for (int i = 25; i < 30; i++) {
                System.out.println("-------------------" + i);
                BufferedImage buffer = wordToImage.getImage("sQaBbtKp");
                File outputfile = new File("c:\\captcha\\ccc\\image.bb"+i+".jpg");
                ImageIO.write(buffer, "jpg", outputfile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
