package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by smoczyna on 17/03/16.
 * Updated by nbarrett on 19/05/2016:
 * The TherapeuticArea is the 'Disease' or 'Patient Condition' within a selected therapeutic area. E.g TherapeuticParent is
 * Cancer, the TherapeuticArea can be any one of Lung Cancer, Skin cancer etc.
 * Please Note: This class was originally called TherapeuticParent before the therapeuticarea was introduced.
 */
@Entity
public class TherapeuticArea extends SortEntity {

    private static final long serialVersionUID = 1L;

    //Please Note: Attribute names below in lower case to make for consistent rest url's on the browser.
    //e.g. http://localhost:8080/api/trials/1/therapeuticarea

    private String title;

    private TherapeuticParent therapeuticparent;

    private Set<QuestionnaireEntry> questionnaireentries;
    private Set<UserSelectorQuestionnaireEntry> userselectorquestionnaireentries;

    private Set<Trial> trials;

    public TherapeuticArea() {
    }

    public TherapeuticArea(String title, TherapeuticParent therapeuticparent) {

        this.title =title;
        this.therapeuticparent = therapeuticparent;
    }

    public TherapeuticArea(String title, TherapeuticParent therapeuticparent, Integer sortOrder) {
        this.title = title;
        this.therapeuticparent = therapeuticparent;
        this.sortOrder = sortOrder;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne
    @JoinColumn(nullable = false, name = "therapeutic_parent_id")
    public TherapeuticParent getTherapeuticparent() {
        return therapeuticparent;
    }

    public void setTherapeuticparent(TherapeuticParent therapeuticparent) {
        this.therapeuticparent = therapeuticparent;
    }

    @OneToMany(mappedBy = "therapeuticarea", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    public Set<QuestionnaireEntry> getQuestionnaireentries() {
        return questionnaireentries;
    }

    public void setQuestionnaireentries(Set<QuestionnaireEntry> questionnaireentries) {
        this.questionnaireentries = questionnaireentries;
    }

    @OneToMany(mappedBy = "therapeuticarea", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    public Set<Trial> getTrials() {
        return trials;
    }

    public void setTrials(Set<Trial> trials) {
        this.trials = trials;
    }

    @OneToMany(mappedBy = "therapeuticarea", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<UserSelectorQuestionnaireEntry> getUserselectorquestionnaireentries() {
        return userselectorquestionnaireentries;
    }

    public void setUserselectorquestionnaireentries(Set<UserSelectorQuestionnaireEntry> userselectorquestionnaireentries) {
        this.userselectorquestionnaireentries = userselectorquestionnaireentries;
    }

}
