package com.tekenable.model;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Answer extends BaseEntity {

    private String answerText;
    private Set<QuestionEntry> questionEntries;
    private Set<QuestionnaireEntry> questionnaireEntries;
    private Set<Result> results;
    public Answer() {

    }

    public Answer(String answerText) {
        this.answerText = answerText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<QuestionEntry> getQuestionEntries() {
        return questionEntries;
    }

    public void setQuestionEntries(Set<QuestionEntry> questionEntries) {
        this.questionEntries = questionEntries;
    }

    @ManyToMany(mappedBy = "answers")
    public Set<QuestionnaireEntry> getQuestionnaireEntries() {
        return questionnaireEntries;
    }

    public void setQuestionnaireEntries(Set<QuestionnaireEntry> questionnaireEntries) {
        this.questionnaireEntries = questionnaireEntries;
    }

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Result> getResults() {
        return results;
    }

    public void setResults(Set<Result> results) {
        this.results = results;
    }
}