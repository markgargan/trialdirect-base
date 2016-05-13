package com.tekenable.model.trial;

import com.tekenable.model.SortEntity;
import com.tekenable.model.common.TrialDirectAddress;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by mark on 30/03/2016.
 */
@Entity
public class TrialSite extends SortEntity {

    private TrialInfo trialInfo;

    private String facilityName;

    private String facilityDescription;

    private String principalInvestigator;

    private String siteMap;

    private TrialDirectAddress trialDirectAddress;

    private TrialDirectImage trialSiteImage;

    public TrialSite() {
    }

    public TrialSite(TrialInfo trialInfo, String facilityName, String facilityDescription,
                     String address1, String address2, String address3, String address4,
                     String address5, String country, String siteMap, String principalInvestigator, Integer sortOrder) {
        this.trialInfo = trialInfo;
        this.facilityName = facilityName;
        this.facilityDescription = facilityDescription;
        this.principalInvestigator = principalInvestigator;

        trialDirectAddress = new TrialDirectAddress(address1, address2, address3, address4, address5, country);
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

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    @Column(length=500)
    public String getFacilityDescription() {
        return facilityDescription;
    }

    public void setFacilityDescription(String facilityDescription) {
        this.facilityDescription = facilityDescription;
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

    public String getPrincipalInvestigator() {
        return principalInvestigator;
    }

    public void setPrincipalInvestigator(String principalInvestigator) {
        this.principalInvestigator = principalInvestigator;
    }

    public String getFacilityName() {
        return facilityName;
    }
}
