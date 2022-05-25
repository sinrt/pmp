package com.pmp.nwms.web.rest.errors;

public class InvalidDocumentPageCountForSlidingException extends BadRequestAlertException {
    public InvalidDocumentPageCountForSlidingException(int actualPageCount, int maxPageCount) {
        super(ErrorConstants.INVALID_DOCUMENT_PAGE_COUNT_FOR_SLIDING_TYPE, "Document page count is " + actualPageCount + " witch is more than max accepted page count " + maxPageCount, "ClassroomFileSlide", "invalid.document.page.count.for.sliding");
    }
}
