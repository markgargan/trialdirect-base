package com.tekenable.model.common;

import com.tekenable.model.BaseEntity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

/**
 * Created by mark on 15/04/2016.
 */
@Embeddable
public class TrialDirectAddress{

    private String address1;

    private String address2;

    private String address3;

    private String address4;

    private String address5;

    private String country;

    public TrialDirectAddress() {
    }

    public TrialDirectAddress(String address1, String address2, String address3, String address4, String address5, String country) {
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.address4 = address4;
        this.address5 = address5;
        this.country = country;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getAddress5() {
        return address5;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
