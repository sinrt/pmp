package com.octo.captcha.component.image.deformation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Random;

public class PuzzleImageDeformation implements ImageDeformation {
    private int colNum = 6;

    private int rowNum = 4;

    private double maxAngleRotation = 0.3D;

    private Random random = new SecureRandom();

    public PuzzleImageDeformation(int colNum, int rowNum, double maxAngleRotation) {
        this.colNum = colNum;
        this.rowNum = rowNum;
        this.maxAngleRotation = maxAngleRotation;
    }

    public BufferedImage deformImage(BufferedImage image) {
        int height = image.getHeight();
        int width = image.getWidth();
        int xd = width / this.colNum;
        int yd = height / this.rowNum;
        BufferedImage backround = new BufferedImage(width, height, image.getType());
        Graphics2D pie = (Graphics2D)backround.getGraphics();
        pie.setColor(Color.white);
        pie.setBackground(Color.white);
        pie.fillRect(0, 0, width, height);
        pie.dispose();
        Graphics2D g = (Graphics2D)image.getGraphics();
        g.setBackground(Color.white);
        BufferedImage smallPart = new BufferedImage(xd, yd, image.getType());
        Graphics2D gSmall = smallPart.createGraphics();
        for (int i = 0; i < this.colNum; i++) {
            for (int j = 0; j < this.rowNum; j++) {
                gSmall.drawImage(image, 0, 0, xd, yd, xd * i, yd * j, xd * i + xd, yd * j + yd, null);
                smallPart.getGraphics().dispose();
                g.drawImage(smallPart, xd * i, yd * j, null, null);
            }
        }
        return image;
    }
}
