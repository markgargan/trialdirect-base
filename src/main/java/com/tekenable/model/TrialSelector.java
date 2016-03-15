package com.tekenable.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class TrialSelector implements Serializable {

    @EmbeddedId
    protected TrialSelectorPK trialSelectorPK;

    public TrialSelector(Trial trial, Question question, Answer answer) {
        this.trial = trial;
        this.question = question;
        this.answer = answer;
    }

    @JoinColumn(name = "trial_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Trial trial;

    @JoinColumn(name = "question_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Question question;

    @JoinColumn(name = "answer_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Answer answer;

}