package com.tekenable.model;

import javax.persistence.*;

/**
 * Created by mark on 30/03/2016.
 */
@Entity
public class TrialInfo extends BaseEntity{

    private Trial trial;

    private String filename;

    private String description;

    private String trialDirector;

    private String type;

    private byte[] content;

    public TrialInfo(Trial trial) {
        this.trial = trial;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "trial_id")
    public Trial getTrial() {
        return trial;
    }

    public void setTrial(Trial trial) {
        this.trial = trial;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrialDirector() {
        return trialDirector;
    }

    public void setTrialDirector(String trialDirector) {
        this.trialDirector = trialDirector;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="blob")
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
