package com.pmp.nwms.util.excel;

public class ExportCellData  {
    private String value;
    private boolean longNumeric;
    private boolean doubleNumeric;

    public String getValue() {
        return value;
    }

    public Double getDoubleValue() {
        if (value != null && !value.isEmpty()) {
            return Double.valueOf(value);
        }
        return null;
    }

    public Long getLongValue() {
        if (value != null && !value.isEmpty()) {
            return Long.valueOf(value);
        }
        return null;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValue(Number value) {
        if (value != null) {
            this.value = value.toString();
        } else {
            this.value = "";
        }
        if (value instanceof Double) {
            this.doubleNumeric = true;
        } else {
            this.longNumeric = true;
        }
    }

    public boolean isLongNumeric() {
        return longNumeric;
    }

    public void setLongNumeric(boolean longNumeric) {
        this.longNumeric = longNumeric;
    }

    public boolean isDoubleNumeric() {
        return doubleNumeric;
    }

    public void setDoubleNumeric(boolean doubleNumeric) {
        this.doubleNumeric = doubleNumeric;
    }
}
