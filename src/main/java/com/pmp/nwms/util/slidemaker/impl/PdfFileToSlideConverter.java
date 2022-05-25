package com.pmp.nwms.util.slidemaker.impl;

import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.util.slidemaker.FileToSlideConverter;
import com.pmp.nwms.web.rest.errors.InvalidDocumentPageCountForSlidingException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Component("pdfFileToSlideConverter")
public class PdfFileToSlideConverter implements FileToSlideConverter {
    @Autowired
    private NwmsConfig nwmsConfig;
    @Override
    public List<byte[]> convertFileToImages(byte[] fileContent) {
        List<byte[]> result = new ArrayList<>();
        PDDocument document = null;
        ExecutorService es = null;
        try {
            document = PDDocument.load(fileContent);
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            {
                int numberOfPages = document.getNumberOfPages();
                if(numberOfPages > nwmsConfig.getSlidingMaxPageCount()){
                    throw new InvalidDocumentPageCountForSlidingException(numberOfPages, nwmsConfig.getSlidingMaxPageCount());
                }
                es = Executors.newFixedThreadPool(numberOfPages);
                Map<Integer, Future<byte[]>> futures = new HashMap<>();
                for (int page = 0; page < numberOfPages; ++page) {
                    final int pageNo = page;
                    futures.put(page, es.submit(() -> {
                        ByteArrayOutputStream baos = null;
                        byte[] imageInByte;
                        try {
                            BufferedImage bim = pdfRenderer.renderImageWithDPI(pageNo, nwmsConfig.getSlidingImageDPI(), ImageType.RGB);
                            baos = new ByteArrayOutputStream();
                            ImageIO.write(bim, "jpg", baos);
                            baos.flush();
                            imageInByte = baos.toByteArray();
                        } finally {
                            if (baos != null) {
                                try {
                                    baos.close();
                                } catch (Exception ignored) {
                                }
                            }

                        }
                        return imageInByte;
                    }));
                }
                for (Map.Entry<Integer, Future<byte[]>> entry : futures.entrySet()) {
                    try {
                        Integer index = entry.getKey();
                        byte[] bytes = entry.getValue().get(30, TimeUnit.MINUTES);
                        result.add(index, bytes);
                    } catch (Exception e) {
                        throw new RuntimeException(e);//todo
                    }
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);//todo
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (Exception ignored) {
                }
            }
            if (es != null && !es.isTerminated()) {
                es.shutdown();
                try {
                    es.awaitTermination(30, TimeUnit.SECONDS);
                    es.shutdownNow();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return result;
    }

   /* public static void main(String[] args) throws Exception {
        File pdf = new File("C:\\Users\\hossein\\Desktop\\FarzanehMohandespour1.pdf");
        byte[] bytesArray = new byte[(int) pdf.length()];

        FileInputStream fis = new FileInputStream(pdf);
        fis.read(bytesArray); //read file into bytes[]
        fis.close();

        PdfFileToSlideConverter converter = new PdfFileToSlideConverter();
        List<byte[]> bytes = converter.convertFileToImages(bytesArray);
        int counter = 1;
        for (byte[] image : bytes) {
            ImageIcon imageIcon = new ImageIcon(image);
            Image image1 = imageIcon.getImage();
            FileUtils.writeByteArrayToFile(new File("D:\\aaaaaa\\rubru\\page3_" + counter + ".png"), image);
            counter++;
        }
    }*/

}
