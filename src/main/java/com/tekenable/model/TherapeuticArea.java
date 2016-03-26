package com.tekenable.model;

import java.util.Set;
import javax.persistence.*;

/**
 * Created by smoczyna on 17/03/16.
 */
@Entity
public class TherapeuticArea extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String title;
    private Set<QuestionnaireEntry> questionnaireentries;
    private Set<UserSelectorQuestionnaireEntry> userselectorquestionnaireentries;

    private Set<Trial> trials;

    public TherapeuticArea() {
    }

    public TherapeuticArea(String title) {
        this.title =title;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "therapeuticArea", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    public Set<QuestionnaireEntry> getQuestionnaireentries() {
        return questionnaireentries;
    }

    public void setQuestionnaireentries(Set<QuestionnaireEntry> questionnaireentries) {
        this.questionnaireentries = questionnaireentries;
    }

    @OneToMany(mappedBy = "therapeuticArea", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    public Set<Trial> getTrials() {
        return trials;
    }

    public void setTrials(Set<Trial> trials) {
        this.trials = trials;
    }

    @OneToMany(mappedBy = "therapeuticArea", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<UserSelectorQuestionnaireEntry> getUserselectorquestionnaireentries() {
        return userselectorquestionnaireentries;
    }

    public void setUserselectorquestionnaireentries(Set<UserSelectorQuestionnaireEntry> userselectorquestionnaireentries) {
        this.userselectorquestionnaireentries = userselectorquestionnaireentries;
    }

}
