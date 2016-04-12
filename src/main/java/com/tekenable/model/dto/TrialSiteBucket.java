package com.tekenable.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class TrialSiteBucket {

    private String siteDirector;

    private String siteSummary;

    private String siteDescription;

    private String siteMap;

    Integer trialInfoId;

    MultipartFile trialSiteFile;
     
    public MultipartFile getTrialSiteFile() {
        return trialSiteFile;
    }

    public void setTrialSiteFile(MultipartFile trialSiteFile) {
        this.trialSiteFile = trialSiteFile;
    }

    public Integer getTrialInfoId() {
        return trialInfoId;
    }

    public void setTrialInfoId(Integer trialInfoId) {
        this.trialInfoId = trialInfoId;
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