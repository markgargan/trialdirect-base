package com.tekenable.model;

import javax.persistence.DiscriminatorValue;

@DiscriminatorValue("ts")
public class TrialSelectorEntry extends QuestionEntry {

    public TrialSelectorEntry(Question question, Answer answer) {
        super(question, answer);
    }
}