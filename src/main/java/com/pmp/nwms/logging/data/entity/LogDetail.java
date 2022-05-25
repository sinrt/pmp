package com.pmp.nwms.logging.data.entity;

import com.pmp.nwms.logging.data.enums.InputParamType;

public class LogDetail {
    private String paramName;
    private InputParamType paramType;
    private Object paramValue;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public InputParamType getParamType() {
        return paramType;
    }

    public void setParamType(InputParamType paramType) {
        this.paramType = paramType;
    }

    public Object getParamValue() {
        return paramValue;
    }

    public void setParamValue(Object paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogDetail logDetail = (LogDetail) o;

        if (paramName != null ? !paramName.equals(logDetail.paramName) : logDetail.paramName != null) return false;
        if (paramType != logDetail.paramType) return false;
        return paramValue != null ? paramValue.equals(logDetail.paramValue) : logDetail.paramValue == null;
    }

    @Override
    public int hashCode() {
        int result = paramName != null ? paramName.hashCode() : 0;
        result = 31 * result + (paramType != null ? paramType.hashCode() : 0);
        result = 31 * result + (paramValue != null ? paramValue.toString().hashCode() : 0);
        return result;
    }
}
