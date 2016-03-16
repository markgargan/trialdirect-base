package com.tekenable.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
public class Trial{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String trialSummary;

    public Trial(String trialSummary) {
        this.trialSummary = trialSummary;
    }
}
