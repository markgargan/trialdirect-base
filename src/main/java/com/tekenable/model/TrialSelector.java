package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class TrialSelector{

    @Id
    Long id;

    @MapsId
    @OneToOne(mappedBy = "trialSelector")
    private Trial trial;

    @Column
    private String createdBy;

    public TrialSelector(String createdBy) {
        this.createdBy = createdBy;
    }

    public TrialSelector(String createdBy, Trial trial) {
        this.createdBy = createdBy;
        this.trial = trial;
    }

    public Trial getTrial() {
        return trial;
    }

    public void setTrial(Trial trial) {
        this.trial = trial;
    }

//    private Set<AcceptableAnswer> acceptableAnswers;
//
//    @OneToMany(mappedBy = "trialSelector", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    public Set<AcceptableAnswer> getAcceptableAnswers() {
//        return acceptableAnswers;
//    }
//
//    public void setAcceptableAnswers(Set<AcceptableAnswer> acceptableAnswers) {
//        this.acceptableAnswers = acceptableAnswers;
//    }

}