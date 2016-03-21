package com.tekenable.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by smoczyna on 18/03/16.
 */
@Entity
public class Result extends BaseEntity {

    private Question question;
    private Answer answer;

    public Result() {
    }

    public Result(Question question, Answer answer) {
        this.question = question;
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

    @ManyToOne
    @JoinColumn(name = "answer_id")
    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

}
