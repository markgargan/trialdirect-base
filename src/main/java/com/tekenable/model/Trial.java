package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Trial {

    protected int id;

    private String title;

    private Set<TrialSelectorEntry> trialSelectorEntries;

    public Trial() {}

    public Trial(String title) {
        this.title = title;
    }

    public Trial(String title, Set<TrialSelectorEntry> questionEntries) {
        this.title = title;
        this.trialSelectorEntries = questionEntries;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "trial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<TrialSelectorEntry> getTrialSelectorEntries() {
        return trialSelectorEntries;
    }

    public void setTrialSelectorEntries(Set<TrialSelectorEntry> trialSelectorEntries) {
        this.trialSelectorEntries = trialSelectorEntries;
    }
}
