package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by smoczyna on 17/03/16.
 * Updated by nbarrett on 19/05/2016:
 * The TherapeuticArea is the 'Disease' or 'Patient Condition' within a selected therapeutic area. E.g Syndrome is
 * Cancer, the TherapeuticArea can be any one of Lung Cancer, Skin cancer etc.
 * Please Note: This class was originally called Syndrome before the specialistarea was introduced.
 */
@Entity
public class TherapeuticArea extends SortEntity {

    private static final long serialVersionUID = 1L;

    //Please Note: Attribute names below in lower case to make for consistent rest url's on the browser.
    //e.g. http://localhost:8080/api/trials/1/specialistarea

    private String title;

    //private Syndrome therapeuticarea;

    private Set<QuestionnaireEntry> questionnaireentries;
    private Set<UserSelectorQuestionnaireEntry> userselectorquestionnaireentries;

    private Set<Trial> trials;

    public TherapeuticArea() {
    }

    public TherapeuticArea(String title) {

        this.title =title;
        //this.therapeuticarea = therapeuticarea;
    }

//    public SpecialistArea(String title, Syndrome therapeuticarea, Integer sortOrder) {
//        this.title = title;
//        //this.therapeuticarea = therapeuticarea;
//        this.sortOrder = sortOrder;
//    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    @ManyToOne
//    @JoinColumn(nullable = false, name = "therapeutic_area_id")
//    public Syndrome getTherapeuticarea() {
//        return therapeuticarea;
//    }
//
//    public void setTherapeuticarea(Syndrome therapeuticarea) {
//        this.therapeuticarea = therapeuticarea;
//    }

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
