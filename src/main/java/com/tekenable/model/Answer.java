package com.tekenable.model;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Answer extends BaseEntity {

    private String answerText;
    private Question question;
    private Set<QuestionEntry> questionEntries;
    private Set<QuestionnaireEntry> questionnaireEntries;

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

    @OneToOne
    @JoinColumn(name = "question_id")
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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
}