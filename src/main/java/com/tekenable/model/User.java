package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User extends BaseEntity{

    private String pseudonym;

    private Set<UserSelectorQuestionnaireEntry> userselectorquestionnaireentries;

    private TherapeuticArea therapeuticArea;

    public User() {}

    public User(String pseudonym, TherapeuticArea therapeuticArea) {
        this.pseudonym = pseudonym;
        this.therapeuticArea = therapeuticArea;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<UserSelectorQuestionnaireEntry> getUserSelectorquestionnaireentries() {
        return userselectorquestionnaireentries;
    }

    public void setUserSelectorquestionnaireentries(Set<UserSelectorQuestionnaireEntry> userselectorquestionnaireentries) {
        this.userselectorquestionnaireentries = userselectorquestionnaireentries;
    }

    @ManyToOne
    @JoinColumn(name = "therapeutic_area_id")
    public TherapeuticArea getTherapeuticArea() {
        return therapeuticArea;
    }

    public void setTherapeuticArea(TherapeuticArea therapeuticArea) {
        this.therapeuticArea = therapeuticArea;
    }
}
