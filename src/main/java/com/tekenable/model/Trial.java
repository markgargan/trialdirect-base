package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Trial extends BaseEntity{

    private String title;

    private Set<TrialSelectorQuestionnaireEntry> trialselectorquestionnaireentries;

    public Trial() {}

    public Trial(String title) {
        this.title = title;
    }

    public Trial(String title, Set<TrialSelectorQuestionnaireEntry> trialselectorquestionnaireentries) {
        this.title = title;
        this.trialselectorquestionnaireentries = trialselectorquestionnaireentries;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "trial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<TrialSelectorQuestionnaireEntry> getTrialSelectorQuestionnaireEntries() {
        return trialselectorquestionnaireentries;
    }

    public void setTrialSelectorQuestionnaireEntries(Set<TrialSelectorQuestionnaireEntry> trialselectorquestionnaireentries) {
        this.trialselectorquestionnaireentries = trialselectorquestionnaireentries;
    }
}
