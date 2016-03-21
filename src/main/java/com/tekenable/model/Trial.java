package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Trial extends BaseEntity{

    private String title;

    private Set<TrialSelectorQuestionEntry> trialSelectorEntries;

    public Trial() {}

    public Trial(String title) {
        this.title = title;
    }

    public Trial(String title, Set<TrialSelectorQuestionEntry> questionEntries) {
        this.title = title;
        this.trialSelectorEntries = questionEntries;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "trial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<TrialSelectorQuestionEntry> getTrialSelectorEntries() {
        return trialSelectorEntries;
    }

    public void setTrialSelectorEntries(Set<TrialSelectorQuestionEntry> trialSelectorEntries) {
        this.trialSelectorEntries = trialSelectorEntries;
    }
}
