package com.tekenable.model.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class TrialInfoBucket {

    String description;

    Integer trialId;

    MultipartFile file;

    List<TrialSiteBucket> trialSites;

    public MultipartFile getFile() {
        return file;
    }
 
    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTrialId() {
        return trialId;
    }

    public void setTrialId(Integer trialId) {
        this.trialId = trialId;
    }

    public List<TrialSiteBucket> getTrialSites() {
        return trialSites;
    }

    public void setTrialSites(List<TrialSiteBucket> trialSites) {
        this.trialSites = trialSites;
    }
}