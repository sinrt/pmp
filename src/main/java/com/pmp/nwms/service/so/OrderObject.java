package com.pmp.nwms.service.so;

import java.io.Serializable;

public class OrderObject implements Serializable {
    /**
     * This is the field name that the sort must be done based on it.
     */
    private String sortField;
    /**
     * This shows the sort mst be ascending or descending
     * The valid values here are : ASC|DESC. This is not case sensitive.
     */
    private String sortOrder;

    public OrderObject() {
    }

    public OrderObject(String sortField, String sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
