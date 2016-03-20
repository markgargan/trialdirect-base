package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Question extends BaseEntity {

    private String questionText;
    private Set<TherapeuticArea> therapeuticAreas;
    private Set<Answer> answers;

    private Set<QuestionEntry> questionEntries;

    private Set<TrialSelectorQuestionEntry> trialSelectorQuestionEntries;

    private Set<UserSelectorQuestionEntry> userSelectionQuestionEntries;

    public Question() {}

    public Question(String questionText) {
        this.questionText = questionText;
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

    /**
     * To understand the cascading options
     *
     * @link http://vladmihalcea.com/2015/03/05/a-beginners-guide-to-jpa-and-hibernate-cascade-types/
     * @return
     */
    @ManyToMany( cascade =
            {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "question_therapeuticArea",
            joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "therapeuticArea_id", referencedColumnName = "id"))
    public Set<TherapeuticArea> getTherapeuticAreas() {
        return therapeuticAreas;
    }

    public void setTherapeuticAreas(Set<TherapeuticArea> therapeuticAreas) {
        this.therapeuticAreas = therapeuticAreas;
    }

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "question", orphanRemoval = true)
    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<QuestionEntry> getQuestionEntries() {
        return questionEntries;
    }

    public void setQuestionEntries(Set<QuestionEntry> questionEntries) {
        this.questionEntries = questionEntries;
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<TrialSelectorQuestionEntry> getTrialSelectorQuestionEntries() {
        return trialSelectorQuestionEntries;
    }

    public void setTrialSelectorQuestionEntries(Set<TrialSelectorQuestionEntry> trialSelectorQuestionEntries) {
        this.trialSelectorQuestionEntries = trialSelectorQuestionEntries;
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<UserSelectorQuestionEntry> getUserSelectionQuestionEntries() {
        return userSelectionQuestionEntries;
    }

    public void setUserSelectionQuestionEntries(Set<UserSelectorQuestionEntry> userSelectionQuestionEntries) {
        this.userSelectionQuestionEntries = userSelectionQuestionEntries;
    }
}