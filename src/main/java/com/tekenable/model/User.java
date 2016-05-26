package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User extends BaseEntity{

    private String pseudonym;

    private Set<UserSelectorQuestionnaireEntry> userselectorquestionnaireentries;

    private TherapeuticArea therapeuticarea;

    public User() {}

    public User(String pseudonym, TherapeuticArea therapeuticarea) {
        this.pseudonym = pseudonym;
        this.therapeuticarea = therapeuticarea;
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
    public TherapeuticArea getTherapeuticarea() {
        return therapeuticarea;
    }

    public void setTherapeuticarea(TherapeuticArea therapeuticarea) {
        this.therapeuticarea = therapeuticarea;
    }
}
