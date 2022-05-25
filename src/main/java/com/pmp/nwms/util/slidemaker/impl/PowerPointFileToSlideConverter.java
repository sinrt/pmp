package com.pmp.nwms.util.slidemaker.impl;

/*
import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.util.slidemaker.FileToSlideConverter;
import com.pmp.nwms.web.rest.errors.InvalidDocumentPageCountForSlidingException;
import com.spire.presentation.FileFormat;
import com.spire.presentation.Presentation;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("powerPointFileToSlideConverter")
*/
public class PowerPointFileToSlideConverter {}/*implements FileToSlideConverter {
    @Autowired
    private NwmsConfig nwmsConfig;

    @Override
    public List<byte[]> convertFileToImages(byte[] fileContent) {
        *//*try {
            XMLSlideShow ppt = new XMLSlideShow(new ByteArrayInputStream(fileContent));
            List<XSLFSlide> slides = ppt.getSlides();
            if (slides.size() > nwmsConfig.getSlidingMaxPageCount()) {
                throw new InvalidDocumentPageCountForSlidingException(slides.size(), nwmsConfig.getSlidingMaxPageCount());
            }
            Dimension pgsize = ppt.getPageSize();
            BufferedImage img = null;
            ArrayList<byte[]> result = new ArrayList<>();
            for (XSLFSlide slide : slides) {


                XSLFTextShape[] phs = slide.getPlaceholders();
                for (XSLFTextShape ts : phs) {
                    java.util.List<XSLFTextParagraph> tpl = ts.getTextParagraphs();
                    for(XSLFTextParagraph tp: tpl) {
                        java.util.List<XSLFTextRun> trs = tp.getTextRuns();
                        for(XSLFTextRun tr: trs) {
                            tr.setFontFamily("BNazanin");
                        }
                    }
                }


                img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();

                //clear the drawing area
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                //render
                slide.draw(graphics);

                ByteOutputStream out = new ByteOutputStream();
                javax.imageio.ImageIO.write(img, "png", out);
                result.add(out.getBytes());
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);//todo
        }*//*

        try {
            ArrayList<byte[]> bytes = new ArrayList<>();
            //create a Presentation object
            Presentation presentation = new Presentation();

            //load an example PPTX file
            presentation.loadFromStream(new ByteArrayInputStream(fileContent), FileFormat.PPTX_2007);

            //loop through the slides
            for (int i = 0; i < presentation.getSlides().getCount(); i++) {

                //save each slide as a BufferedImage
                BufferedImage image = presentation.getSlides().get(i).saveAsImage();

                //save BufferedImage as PNG file format
                ByteOutputStream output = new ByteOutputStream();
                ImageIO.write(image, "PNG", output);
                bytes.add(output.getBytes());
            }
            presentation.dispose();
            return bytes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}*/
