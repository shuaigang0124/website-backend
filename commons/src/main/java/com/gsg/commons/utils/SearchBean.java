package com.gsg.commons.utils;

import java.io.Serializable;

public class SearchBean<T> implements Serializable {

    private static final long serialVersionUID = 1952937982288236093L;

    private Page page;

    private T entity;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public SearchBean(Page page, T entity) {
        this.page = page;
        this.entity = entity;
    }

    public SearchBean() {
    }

    public SearchBean(Page page) {
        this.page = page;
    }

    public SearchBean(T entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "SearchBean{" +
                "page=" + page +
                ", entity=" + entity +
                '}';
    }
}
