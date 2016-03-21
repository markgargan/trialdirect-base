package com.tekenable.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "question_entity_type",
        discriminatorType = DiscriminatorType.STRING
)

@DiscriminatorValue("qe")
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"trial_id", "question_id", "answer_id", "question_entity_type"})
)
public class QuestionEntry implements Serializable {

    protected int id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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