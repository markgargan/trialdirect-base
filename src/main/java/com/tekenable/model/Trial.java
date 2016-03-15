package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Trial{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "trial", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TrialSelector> trialSelector;

    private String trialSummary;

    public Trial() {

    }

    public Trial(String trialSummary) {
        this.trialSummary = trialSummary;
    }

    public Trial(Set<TrialSelector> trialSelector, String trialSummary) {
        this.trialSelector = trialSelector;
        this.trialSummary = trialSummary;
    }

    public String getTrialSummary() {
        return trialSummary;
    }

    public void setTrialSummary(String trialSummary) {
        this.trialSummary = trialSummary;
    }

    public Set<TrialSelector> getTrialSelector() {
        return trialSelector;
    }

    public void setTrialSelector(Set<TrialSelector> trialSelector) {
        this.trialSelector = trialSelector;
    }
}
