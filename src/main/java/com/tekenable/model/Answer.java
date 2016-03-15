package com.tekenable.model;


import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
public class Answer extends BaseEntity {

    private String answerText;
    private Question question;
    private Collection<QuestionEntry> questionEntries;

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

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Collection<QuestionEntry> getQuestionEntry() {
        return questionEntries;
    }

    public void setQuestionEntry(Collection<QuestionEntry> questionEntries) {
        this.questionEntries = questionEntries;
    }
}