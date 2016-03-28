package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@EntityListeners(TrialListener.class)
public class Trial extends BaseEntity{

    private String title;

    private Set<TrialSelectorQuestionnaireEntry> trialselectorquestionnaireentries;

    private TherapeuticArea therapeuticArea;

    public Trial() {}

    public Trial( String title, TherapeuticArea therapeuticArea) {
        this.title = title;
        this.therapeuticArea = therapeuticArea;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "trial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<TrialSelectorQuestionnaireEntry> getTrialselectorquestionnaireentries() {
        return trialselectorquestionnaireentries;
    }

    public void setTrialselectorquestionnaireentries(Set<TrialSelectorQuestionnaireEntry> trialselectorquestionnaireentries) {
        this.trialselectorquestionnaireentries = trialselectorquestionnaireentries;
    }

    @ManyToOne
    @JoinColumn(nullable = true, name = "therapeutic_area_id")
    public TherapeuticArea getTherapeuticArea() {
        return therapeuticArea;
    }

    public void setTherapeuticArea(TherapeuticArea therapeuticArea) {
        this.therapeuticArea = therapeuticArea;
    }
}
