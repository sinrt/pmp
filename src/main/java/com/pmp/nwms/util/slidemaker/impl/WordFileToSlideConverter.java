package com.pmp.nwms.util.slidemaker.impl;

/*import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.util.slidemaker.FileToSlideConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component("wordFileToSlideConverter")*/
public class WordFileToSlideConverter {}/*implements FileToSlideConverter {
    private final Logger log = LoggerFactory.getLogger(WordFileToSlideConverter.class);

    private static IConverter CONVERTER = null;

    private final PdfFileToSlideConverter pdfFileToSlideConverter;
    private final NwmsConfig nwmsConfig;

    public WordFileToSlideConverter(NwmsConfig nwmsConfig, PdfFileToSlideConverter pdfFileToSlideConverter) {
        this.nwmsConfig = nwmsConfig;
        this.pdfFileToSlideConverter = pdfFileToSlideConverter;
        File baseFolder = new File(nwmsConfig.getFileToSlideConvertTempFolder());
        if (!baseFolder.exists()) {
            baseFolder.mkdirs();
        }
        prepareConverter();
    }

    private void prepareConverter() {
        File baseFolder = new File(nwmsConfig.getFileToSlideConvertTempFolder());
        CONVERTER = LocalConverter.builder()
            .baseFolder(baseFolder)
            .workerPool(nwmsConfig.getConverterCorePoolSize(), nwmsConfig.getConverterMaxPoolSize(), nwmsConfig.getConverterKeepAliveTime(), TimeUnit.SECONDS)
            .processTimeout(5, TimeUnit.SECONDS)
            .build();
    }

    @Override
    public List<byte[]> convertFileToImages(byte[] fileContent) {
        if(!CONVERTER.isOperational()){
            prepareConverter();
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            CONVERTER
                .convert(inputStream).as(DocumentType.DOCX)
                .to(outputStream).as(DocumentType.PDF)
                .prioritizeWith(1000) // optional
                .execute();

            return pdfFileToSlideConverter.convertFileToImages(outputStream.toByteArray());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
            }
        }
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        try {
            CONVERTER.shutDown();
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }
}
*/
