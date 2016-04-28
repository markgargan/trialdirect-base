package com.tekenable.model.trial;

import com.tekenable.model.BaseEntity;
import com.tekenable.model.SortEntity;
import com.tekenable.model.common.TrialDirectAddress;

import javax.persistence.*;
import javax.swing.*;

/**
 * Created by mark on 30/03/2016.
 */
@Entity
public class TrialSite extends SortEntity {

    private TrialInfo trialInfo;

    private String siteDirector;

    private String siteDirectorBio;

    private String siteMap;

    private TrialDirectAddress trialDirectAddress;

    private TrialDirectImage trialSiteImage;

    public TrialSite() {
    }

    public TrialSite(TrialInfo trialInfo, String siteDirector, String siteDirectorBio,
                     String address1, String address2,String address3,String address4,
                     String address5, String country, String siteMap, Integer sortOrder) {

        this.trialInfo = trialInfo;
        this.siteDirector = siteDirector;
        this.siteDirectorBio = siteDirectorBio;
        this.trialDirectAddress = new TrialDirectAddress(address1, address2, address3, address4, address5, country);
        this.siteMap = siteMap;
        this.sortOrder = sortOrder;
    }

    @ManyToOne
    public TrialInfo getTrialInfo() {
        return trialInfo;
    }

    public void setTrialInfo(TrialInfo trialInfo) {
        this.trialInfo = trialInfo;
    }

    @Embedded
    public TrialDirectImage getTrialSiteImage() {
        return trialSiteImage;
    }

    public void setTrialSiteImage(TrialDirectImage trialSiteImage) {
        this.trialSiteImage = trialSiteImage;
    }

    public String getSiteDirector() {
        return siteDirector;
    }

    public void setSiteDirector(String siteDirector) {
        this.siteDirector = siteDirector;
    }

    @Column(length=500)
    public String getSiteDirectorBio() {
        return siteDirectorBio;
    }

    public void setSiteDirectorBio(String siteDirectorBio) {
        this.siteDirectorBio = siteDirectorBio;
    }

    public String getSiteMap() {
        return siteMap;
    }

    public void setSiteMap(String siteMap) {
        this.siteMap = siteMap;
    }

    @Embedded
    public TrialDirectAddress getTrialDirectAddress() {
        return trialDirectAddress;
    }

    public void setTrialDirectAddress(TrialDirectAddress trialDirectAddress) {
        this.trialDirectAddress = trialDirectAddress;
    }
}
