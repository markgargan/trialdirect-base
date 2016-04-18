package com.tekenable.model.trial;

import com.tekenable.model.BaseEntity;
import org.hibernate.loader.entity.plan.AbstractLoadPlanBasedEntityLoader;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by mark on 15/04/2016.
 */
public class TrialFullDescription {

    String fullDescription;

    TrialInfo trialInfo;

    public TrialFullDescription() {
    }

    public TrialFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    @Column(length = 1000)
    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }
}
