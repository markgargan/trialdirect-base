package com.tekenable.model.trial;

import com.tekenable.model.BaseEntity;
import com.tekenable.model.Trial;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by mark on 30/03/2016.
 */
@Entity
public class TrialInfo extends BaseEntity {

    private Trial trial;

    private String description;

    private Set<TrialSite> trialSites;

    private TrialDirectImage trialLogo;

    public TrialInfo(){}

    public TrialInfo(Trial trial, String description) {
        this.trial = trial;
        this.description = description;
    }

    @ManyToOne
    @JoinColumn(name = "trial_id")
    public Trial getTrial() {
        return trial;
    }

    public void setTrial(Trial trial) {
        this.trial = trial;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "trialInfo", cascade = CascadeType.ALL,
            orphanRemoval = true,fetch = FetchType.LAZY)
    public Set<TrialSite> getTrialSites() {
        return trialSites;
    }

    public void setTrialSites(Set<TrialSite> trialSites) {
        this.trialSites = trialSites;
    }

    @Embedded
    public TrialDirectImage getTrialLogo() {
        return trialLogo;
    }

    public void setTrialLogo(TrialDirectImage trialLogo) {
        this.trialLogo = trialLogo;
    }
}
