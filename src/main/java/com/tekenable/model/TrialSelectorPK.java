package com.tekenable.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TrialSelectorPK implements Serializable {
    
    @Basic(optional = false)
    @Column(name = "trial_id")
    private int commandeId;
    
    @Basic(optional = false)
    @Column(name = "question_id")
    private int produitId;

    @Basic(optional = false)
    @Column(name = "answer_id")
    private int answerId;
}