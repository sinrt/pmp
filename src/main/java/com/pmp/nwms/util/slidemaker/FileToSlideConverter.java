package com.pmp.nwms.util.slidemaker;

import java.util.List;

public interface FileToSlideConverter {
    List<byte[]> convertFileToImages(byte[] fileContent);
}
