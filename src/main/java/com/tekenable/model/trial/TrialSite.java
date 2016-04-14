package com.tekenable.model.trial;

import com.tekenable.model.BaseEntity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by mark on 30/03/2016.
 */
@Entity
public class TrialSite extends BaseEntity {

    private TrialInfo trialInfo;

    private String siteDirector;

    private String siteSummary;

    private String siteDescription;

    private String siteMap;

    private TrialDirectImage trialSiteImage;

    public TrialSite() {
    }

    public TrialSite(TrialInfo trialInfo, String siteDirector, String siteSummary, String siteDescription, String siteMap) {
        this.trialInfo = trialInfo;
        this.siteDirector = siteDirector;
        this.siteSummary = siteSummary;
        this.siteDescription = siteDescription;
        this.siteMap = siteMap;
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

    public String getSiteSummary() {
        return siteSummary;
    }

    public void setSiteSummary(String siteSummary) {
        this.siteSummary = siteSummary;
    }

    public String getSiteDescription() {
        return siteDescription;
    }

    public void setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription;
    }

    public String getSiteMap() {
        return siteMap;
    }

    public void setSiteMap(String siteMap) {
        this.siteMap = siteMap;
    }
}
