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

    private String trialSiteSummary;

    private String googleMapURL;

    private TrialDirectImage trialSiteImage;

    public TrialSite() {
    }

    public TrialSite(TrialInfo trialInfo, String trialSiteSummary, String googleMapURL) {
        this.trialInfo = trialInfo;
        this.trialSiteSummary = trialSiteSummary;
        this.googleMapURL = googleMapURL;
    }

    @ManyToOne
    public TrialInfo getTrialInfo() {
        return trialInfo;
    }

    public void setTrialInfo(TrialInfo trialInfo) {
        this.trialInfo = trialInfo;
    }

    public String getTrialSiteSummary() {
        return trialSiteSummary;
    }

    public void setTrialSiteSummary(String trialSiteSummary) {
        this.trialSiteSummary = trialSiteSummary;
    }

    public String getGoogleMapURL() {
        return googleMapURL;
    }

    public void setGoogleMapURL(String googleMapURL) {
        this.googleMapURL = googleMapURL;
    }

    @Embedded
    public TrialDirectImage getTrialSiteImage() {
        return trialSiteImage;
    }

    public void setTrialSiteImage(TrialDirectImage trialSiteImage) {
        this.trialSiteImage = trialSiteImage;
    }
}
