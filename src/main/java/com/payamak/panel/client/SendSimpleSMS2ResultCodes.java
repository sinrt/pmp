package com.payamak.panel.client;

public enum SendSimpleSMS2ResultCodes {
    Unknown("-1"),
    AuthenticationError("0"),
    SuccessfullyRequested("1"),
    NotEnoughCredit("2"),
    DailyQuotaExceeded("3"),
    VolumeQuotaExceeded("4"),
    InvalidSenderNumber("5"),
    ServiceIsDownDueToUpdate("6"),
    UnacceptableTextContent("7"),
    SendingFromGlobalNumbersIsNotSupported("9"),
    UserIsInactive("10"),
    NotSent("11"),
    UserDocsNotComplete("12"),
    ;

    private String code;

    SendSimpleSMS2ResultCodes(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static SendSimpleSMS2ResultCodes findByCode(String code) {
        for (SendSimpleSMS2ResultCodes value : values()) {
            if (value.code.equalsIgnoreCase(code)) {
                return value;
            }
        }
        return null;
    }

}
