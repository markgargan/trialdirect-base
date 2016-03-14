package com.tekenable.model;

import javax.persistence.*;

/**
 * Created by mark on 14/03/2016.
 */
@Entity
public class Trial{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private TrialSelector trialSelector;

    private String trialSummary;

    public Trial() {

    }

    public Trial(String trialSummary) {
        this.trialSummary = trialSummary;
    }

    public String getTrialSummary() {
        return trialSummary;
    }

    public void setTrialSummary(String trialSummary) {
        this.trialSummary = trialSummary;
    }

}
