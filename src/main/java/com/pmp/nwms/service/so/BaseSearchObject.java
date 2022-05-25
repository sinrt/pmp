package com.pmp.nwms.service.so;

import com.pmp.nwms.config.Constants;

import java.io.Serializable;
import java.util.List;

public abstract class BaseSearchObject implements Serializable{
    private int pageNumber = Constants.DEFAULT_FIRST_RESULT;

    private int pageSize = Constants.DEFAULT_PAGE_SIZE;

    private List<OrderObject> orderObjects;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<OrderObject> getOrderObjects() {
        return orderObjects;
    }

    public void setOrderObjects(List<OrderObject> orderObjects) {
        this.orderObjects = orderObjects;
    }

    public abstract boolean isClean();

}
