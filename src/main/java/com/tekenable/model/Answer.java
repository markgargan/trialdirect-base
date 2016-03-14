package com.tekenable.model;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Answer extends BaseEntity {

    private String name;
    private Question question;

    public Answer() {

    }

    public Answer(String name, Question question) {
        this.name = name;
        this.question = question;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "question_id")
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}