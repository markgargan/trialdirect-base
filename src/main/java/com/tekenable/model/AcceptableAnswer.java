package com.tekenable.model;


import javax.persistence.*;
import java.util.Set;

//@Entity
public class AcceptableAnswer{

    protected Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrialSelector trialSelector;

    public AcceptableAnswer() {

    }

    @ManyToOne(cascade=CascadeType.ALL)
    public TrialSelector getTrialSelector() {
        return trialSelector;
    }

    public void setTrialSelector(TrialSelector trialSelector) {
        this.trialSelector = trialSelector;
    }
}