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

    private String summary;

    private Set<TrialSite> trialSites;

    private TrialDirectImage trialLogo;

    private TrialFullDescription trialFullDescription;

    public TrialInfo(){}

    public TrialInfo(Trial trial, String summary) {
        this.trial = trial;
        this.summary = summary;
    }

    @ManyToOne
    @JoinColumn(name = "trial_id")
    public Trial getTrial() {
        return trial;
    }

    public void setTrial(Trial trial) {
        this.trial = trial;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @OneToMany(mappedBy = "trialInfo", cascade = CascadeType.ALL,
            orphanRemoval = true,fetch = FetchType.EAGER)
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

    @OneToOne
    public TrialFullDescription getTrialFullDescription() {
        return trialFullDescription;
    }

    public void setTrialFullDescription(TrialFullDescription trialFullDescription) {
        this.trialFullDescription = trialFullDescription;
    }
}
