package com.tekenable.model;


import javax.persistence.*;

@Entity
public class QuestionAndAcceptableAnswers {

    protected Long id;

    public Question question;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionAndAcceptableAnswers() {

    }
}