package com.tekenable.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"question_id", "answer_id"})
)
public class QuestionEntry implements Serializable {

    protected int id;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Question question;

    private Answer answer;

    public QuestionEntry(){}

    public QuestionEntry(Question question, Answer answer) {
        this.question = question;
        this.answer = answer;
    }

    @ManyToOne
    @JoinColumn(name = "answer_id")
    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
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