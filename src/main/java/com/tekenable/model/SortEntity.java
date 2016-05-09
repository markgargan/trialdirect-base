package com.tekenable.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class SortEntity extends BaseEntity {

    protected Integer sortOrder;

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

}