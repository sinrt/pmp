package com.octo.captcha.component.image.backgroundgenerator;

import com.octo.captcha.component.image.color.ColorGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class MultipleShapeBackgroundGenerator extends AbstractBackgroundGenerator {
    private ColorGenerator firstEllipseColorGenerator;

    private ColorGenerator secondEllipseColorGenerator;

    private ColorGenerator firstRectangleColorGenerator;

    private ColorGenerator secondRectangleColorGenerator;

    private Integer spaceBetweenLine;

    private Integer spaceBetweenCircle;

    private Integer ellipseHeight;

    private Integer ellipseWidth;

    private Integer rectangleWidth;

    public MultipleShapeBackgroundGenerator(Integer width, Integer height) {
        super(width, height);
        this.firstEllipseColorGenerator = new SingleColorGenerator(new Color(210, 210, 210));
        this.secondEllipseColorGenerator = new SingleColorGenerator(new Color(0, 0, 0));
        this.firstRectangleColorGenerator = new SingleColorGenerator(new Color(210, 210, 210));
        this.secondRectangleColorGenerator = new SingleColorGenerator(new Color(0, 0, 0));
        this.spaceBetweenLine = new Integer(10);
        this.spaceBetweenCircle = new Integer(10);
        this.ellipseHeight = new Integer(8);
        this.ellipseWidth = new Integer(8);
        this.rectangleWidth = new Integer(3);
    }

    public MultipleShapeBackgroundGenerator(Integer width, Integer height, Color firstEllipseColor, Color secondEllipseColor, Integer spaceBetweenLine, Integer spaceBetweenCircle, Integer ellipseHeight, Integer ellipseWidth, Color firstRectangleColor, Color secondRectangleColor, Integer rectangleWidth) {
        super(width, height);
        this.firstEllipseColorGenerator = new SingleColorGenerator(new Color(210, 210, 210));
        this.secondEllipseColorGenerator = new SingleColorGenerator(new Color(0, 0, 0));
        this.firstRectangleColorGenerator = new SingleColorGenerator(new Color(210, 210, 210));
        this.secondRectangleColorGenerator = new SingleColorGenerator(new Color(0, 0, 0));
        this.spaceBetweenLine = new Integer(10);
        this.spaceBetweenCircle = new Integer(10);
        this.ellipseHeight = new Integer(8);
        this.ellipseWidth = new Integer(8);
        this.rectangleWidth = new Integer(3);
        if (firstEllipseColor != null)
            this.firstEllipseColorGenerator = new SingleColorGenerator(firstEllipseColor);
        if (secondEllipseColor != null)
            this.secondEllipseColorGenerator = new SingleColorGenerator(secondEllipseColor);
        if (spaceBetweenLine != null)
            this.spaceBetweenLine = spaceBetweenCircle;
        if (spaceBetweenCircle != null)
            this.spaceBetweenCircle = spaceBetweenCircle;
        if (ellipseHeight != null)
            this.ellipseHeight = ellipseHeight;
        if (ellipseWidth != null)
            this.ellipseWidth = ellipseWidth;
        if (firstRectangleColor != null)
            this.firstRectangleColorGenerator = new SingleColorGenerator(firstRectangleColor);
        if (secondRectangleColor != null)
            this.secondRectangleColorGenerator = new SingleColorGenerator(secondRectangleColor);
        if (rectangleWidth != null)
            this.rectangleWidth = rectangleWidth;
    }

    public BufferedImage getBackground() {
        BufferedImage bi = new BufferedImage(getImageWidth(), getImageHeight(), 1);
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setBackground(Color.white);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int variableOnWidth = 0; variableOnWidth < getImageWidth(); variableOnWidth += getSpaceBetweenLine()) {
            Color firstEllipseColor = this.firstEllipseColorGenerator.getNextColor();
            Color secondEllipseColor = this.secondEllipseColorGenerator.getNextColor();
            Color firstRectangleColor = this.firstRectangleColorGenerator.getNextColor();
            Color secondRectangleColor = this.secondRectangleColorGenerator.getNextColor();
            int variableOnHeight;
            for (variableOnHeight = 0; variableOnHeight < getImageHeight(); variableOnHeight += getSpaceBetweenCircle()) {
                Ellipse2D e2 = new Ellipse2D.Double(variableOnWidth, variableOnHeight, getEllipseWidth(), getEllipseHeight());
                GradientPaint gp = new GradientPaint(0.0F, getEllipseHeight(), firstEllipseColor, getEllipseWidth(), 0.0F, secondEllipseColor, true);
                g2.setPaint(gp);
                g2.fill(e2);
            }
            GradientPaint gp2 = new GradientPaint(0.0F, getImageHeight(), firstRectangleColor, getRectangleWidth(), 0.0F, secondRectangleColor, true);
            g2.setPaint(gp2);
            Rectangle2D r2 = new Rectangle2D.Double(variableOnWidth, 0.0D, getRectangleWidth(), getImageHeight());
            g2.fill(r2);
        }
        g2.dispose();
        return bi;
    }

    protected int getSpaceBetweenLine() {
        return this.spaceBetweenLine.intValue();
    }

    protected int getSpaceBetweenCircle() {
        return this.spaceBetweenCircle.intValue();
    }

    protected int getEllipseHeight() {
        return this.ellipseHeight.intValue();
    }

    protected int getEllipseWidth() {
        return this.ellipseWidth.intValue();
    }

    protected int getRectangleWidth() {
        return this.rectangleWidth.intValue();
    }
}
