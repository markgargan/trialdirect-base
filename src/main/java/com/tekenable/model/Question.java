package com.tekenable.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Question extends BaseEntity {

    private String questionText;
    private Set<TherapeuticArea> therapeuticAreas;
//    private Set<QuestionEntry> questionEntries;
    private Set<QuestionnaireEntry> questionnaireEntries;

    public Question() {}

    public Question(String questionText) {
        this.questionText = questionText;
    }

    public Question(String questionText, TherapeuticArea area) {
        this.questionText = questionText;
        this.therapeuticAreas = new HashSet();
        this.therapeuticAreas.add(area);
    }

    public Question(String questionText, Set<TherapeuticArea> therapeuticAreas) {
        this.questionText = questionText;
        this.therapeuticAreas = therapeuticAreas;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "question_therapeuticArea",
            joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "therapeuticArea_id", referencedColumnName = "id"))
    public Set<TherapeuticArea> getTherapeuticAreas() {
        return therapeuticAreas;
    }

    public void setTherapeuticAreas(Set<TherapeuticArea> therapeuticAreas) {
        this.therapeuticAreas = therapeuticAreas;
    }

//    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    public Set<QuestionEntry> getQuestionEntries() {
//        return questionEntries;
//    }
//
//    public void setQuestionEntries(Set<QuestionEntry> questionEntries) {
//        this.questionEntries = questionEntries;
//    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<QuestionnaireEntry> getQuestionnaireEntries() {
        return questionnaireEntries;
    }

    public void setQuestionnaireEntries(Set<QuestionnaireEntry> questionnaireEntries) {
        this.questionnaireEntries = questionnaireEntries;
    }

}