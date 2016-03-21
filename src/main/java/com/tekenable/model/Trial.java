package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Trial{

    protected int id;

    private String trialSummary;

//    private Set<QuestionEntry> questionEntries;
    private Set<TrialSelectorEntry> trialSelectorEntries;

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

    public Trial(String trialSummary, Set<TrialSelectorEntry> questionEntries) {
        this.trialSummary = trialSummary;
        this.trialSelectorEntries = trialSelectorEntries;
    }

    @OneToMany(mappedBy = "trial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<TrialSelectorEntry> getTrialSelectorEntries() {
        return trialSelectorEntries;
    }

    public void setTrialSelectorEntries(Set<TrialSelectorEntry> trialSelectorEntries) {
        this.trialSelectorEntries = trialSelectorEntries;
    }
}
