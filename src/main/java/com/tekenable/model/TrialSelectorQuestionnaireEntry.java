package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"trial_id", "question_id", "answer_id"})
)
public class TrialSelectorQuestionnaireEntry extends QuestionEntry {

    private Trial trial;

    public TrialSelectorQuestionnaireEntry() {
    }

    public TrialSelectorQuestionnaireEntry(Question question, Answer answer, Trial trial) {
        super(question, answer);
        this.trial = trial;
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