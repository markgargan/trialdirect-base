package com.tekenable.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("ts")
@Entity
public class TrialSelectorEntry extends QuestionEntry {

    public TrialSelectorEntry() {
    }

    public TrialSelectorEntry(Question question, Answer answer) {
        super(question, answer);
    }

}