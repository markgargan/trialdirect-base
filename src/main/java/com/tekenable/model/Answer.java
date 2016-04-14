package com.tekenable.model;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Answer extends BaseEntity {

    private String answerText;
    // lowercased in order to keep the rest api all lower case
    private Set<QuestionnaireEntry> questionnaireentries;

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

    @ManyToMany(mappedBy = "answers", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    public Set<QuestionnaireEntry> getQuestionnaireentries() {
        return questionnaireentries;
    }

    public void setQuestionnaireentries(Set<QuestionnaireEntry> questionnaireentries) {
        this.questionnaireentries = questionnaireentries;
    }
}