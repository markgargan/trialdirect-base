package com.tekenable.model;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Answer extends BaseEntity {

    private String answerText;
    private Question question;

    private Set<QuestionEntry> questionEntries;
    private Set<TrialSelectorQuestionEntry> trialSelectorQuestionEntries;
    private Set<UserSelectorQuestionEntry> userSelectionQuestionEntries;

    public Answer() {

    }

    public Answer(String answerText) {
        this.answerText = answerText;
    }

    public Answer(String answerText, Question question) {
        this.answerText = answerText;
        this.question = question;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @ManyToOne
    @JoinColumn(name = "question_id")
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @OneToMany(mappedBy = "answer", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    public Set<QuestionEntry> getQuestionEntries() {
        return questionEntries;
    }

    public void setQuestionEntries(Set<QuestionEntry> questionEntries) {
        this.questionEntries = questionEntries;
    }

    @OneToMany(mappedBy = "answer", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    public Set<TrialSelectorQuestionEntry> getTrialSelectorQuestionEntries() {
        return trialSelectorQuestionEntries;
    }

    public void setTrialSelectorQuestionEntries(Set<TrialSelectorQuestionEntry> trialSelectorQuestionEntries) {
        this.trialSelectorQuestionEntries = trialSelectorQuestionEntries;
    }

    @OneToMany(mappedBy = "answer", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    public Set<UserSelectorQuestionEntry> getUserSelectionQuestionEntries() {
        return userSelectionQuestionEntries;
    }

    public void setUserSelectionQuestionEntries(Set<UserSelectorQuestionEntry> userSelectionQuestionEntries) {
        this.userSelectionQuestionEntries = userSelectionQuestionEntries;
    }
}