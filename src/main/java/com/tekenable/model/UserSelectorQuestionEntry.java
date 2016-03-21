package com.tekenable.model;

import javax.persistence.*;

@Entity
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"user_session_id", "question_id", "answer_id"})
)
public class UserSelectorQuestionEntry extends QuestionEntry {

    private Long userSessionId;

    public UserSelectorQuestionEntry(){}

    public UserSelectorQuestionEntry(Long userSessionId, Question question, Answer answer) {
        super(question, answer);
        this.userSessionId = userSessionId;
    }

    @Column(name = "user_session_id")
    public Long getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(Long userSessionId) {
        this.userSessionId = userSessionId;
    }
}