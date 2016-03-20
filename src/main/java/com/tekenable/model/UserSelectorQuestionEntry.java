package com.tekenable.model;

import javax.persistence.*;

@Entity

@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"question_id", "answer_id", "user_session_id"})
)
public class UserSelectorQuestionEntry extends BaseEntity{

    private Question question;

    private Answer answer;

    private Long userSessionId;

    public UserSelectorQuestionEntry(){}

    public UserSelectorQuestionEntry(Long userSessionId, Question question, Answer answer) {
        this.question = question;
        this.answer = answer;
        this.userSessionId = userSessionId;
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

    @Column(name = "user_session_id")
    public Long getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(Long userSessionId) {
        this.userSessionId = userSessionId;
    }
}