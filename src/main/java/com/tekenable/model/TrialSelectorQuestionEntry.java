package com.tekenable.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity

@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"trial_id", "question_id", "answer_id"})
)
public class TrialSelectorQuestionEntry extends BaseEntity{

    private Question question;

    private Answer answer;

    private Trial trial;

    public TrialSelectorQuestionEntry(){}

    public TrialSelectorQuestionEntry(Question question, Answer answer, Trial trial) {
        this.question = question;
        this.answer = answer;
        this.trial = trial;
    }

    @ManyToOne
    @JoinColumn(name = "answer_id")
    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @ManyToOne
    @JoinColumn(name = "question_id")
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @ManyToOne
    @JoinColumn(name = "trial_id")
    public Trial getTrial() {
        return trial;
    }

    public void setTrial(Trial trial) {
        this.trial = trial;
    }
}