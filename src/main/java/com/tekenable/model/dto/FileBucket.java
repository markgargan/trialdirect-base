package com.tekenable.model.dto;

import com.tekenable.model.trial.TrialInfo;
import org.springframework.web.multipart.MultipartFile;
 
public class FileBucket {

    String description;

    Integer trialId;

    MultipartFile file;
     
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
}