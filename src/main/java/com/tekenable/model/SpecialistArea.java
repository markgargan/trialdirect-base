package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by smoczyna on 17/03/16.
 * Updated by nbarrett on 19/05/2016:
 * The SpecialistArea is the 'Disease' or 'Patient Condition' within a selected therapeutic area. E.g TherapeuticArea is
 * Cancer, the SpecialistArea can be any one of Lung Cancer, Skin cancer etc.
 * Please Note: This class was originally called TherapeuticArea before the specialistArea was introduced.
 */
@Entity
public class SpecialistArea extends SortEntity {

    private static final long serialVersionUID = 1L;

    private String title;
    private Set<QuestionnaireEntry> questionnaireentries;
    private Set<UserSelectorQuestionnaireEntry> userselectorquestionnaireentries;

    private Set<Trial> trials;

    public SpecialistArea() {
    }

    public SpecialistArea(String title) {
        this.title =title;
    }

    public SpecialistArea(String title, Integer sortOrder) {
        this.title = title;
        this.sortOrder = sortOrder;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "specialistArea", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    public Set<QuestionnaireEntry> getQuestionnaireentries() {
        return questionnaireentries;
    }

    public void setQuestionnaireentries(Set<QuestionnaireEntry> questionnaireentries) {
        this.questionnaireentries = questionnaireentries;
    }

    @OneToMany(mappedBy = "specialistArea", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    public Set<Trial> getTrials() {
        return trials;
    }

    public void setTrials(Set<Trial> trials) {
        this.trials = trials;
    }

    @OneToMany(mappedBy = "specialistArea", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<UserSelectorQuestionnaireEntry> getUserselectorquestionnaireentries() {
        return userselectorquestionnaireentries;
    }

    public void setUserselectorquestionnaireentries(Set<UserSelectorQuestionnaireEntry> userselectorquestionnaireentries) {
        this.userselectorquestionnaireentries = userselectorquestionnaireentries;
    }

}
