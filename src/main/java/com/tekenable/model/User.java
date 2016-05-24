package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User extends BaseEntity{

    private String pseudonym;

    private Set<UserSelectorQuestionnaireEntry> userselectorquestionnaireentries;

    private SpecialistArea specialistArea;

    public User() {}

    public User(String pseudonym, SpecialistArea specialistArea) {
        this.pseudonym = pseudonym;
        this.specialistArea = specialistArea;
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
    @JoinColumn(name = "specialist_area_id")
    public SpecialistArea getSpecialistArea() {
        return specialistArea;
    }

    public void setSpecialistArea(SpecialistArea specialistArea) {
        this.specialistArea = specialistArea;
    }
}
