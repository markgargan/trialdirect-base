package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Trial{

    protected int id;

    private String trialSummary;

    private Set<TrialSelectorQuestionEntry> trialSelectorQuestionEntries;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Trial() {}

    public Trial(String trialSummary) {
        this.trialSummary = trialSummary;
    }

    public Trial(String trialSummary, Set<TrialSelectorQuestionEntry> trialSelectorQuestionEntries) {
        this.trialSummary = trialSummary;
        this.trialSelectorQuestionEntries = trialSelectorQuestionEntries;
    }

    @OneToMany(mappedBy = "trial", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    public Set<TrialSelectorQuestionEntry> getTrialSelectorQuestionEntries() {
        return trialSelectorQuestionEntries;
    }

    public void setTrialSelectorQuestionEntries(Set<TrialSelectorQuestionEntry> trialSelectorQuestionEntries) {
        this.trialSelectorQuestionEntries = trialSelectorQuestionEntries;
    }
}
