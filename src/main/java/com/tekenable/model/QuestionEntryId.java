package com.tekenable.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class QuestionEntryId implements Serializable {

    @Column(name = "question_id")
    private int questionId;

    @Column(name = "answer_id")
    private int answerId;

    @Column(name = "question_entity_type")
    private String questionEntityType;

}