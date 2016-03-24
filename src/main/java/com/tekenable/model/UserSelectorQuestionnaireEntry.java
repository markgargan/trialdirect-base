package com.tekenable.model;

import javax.persistence.*;

@Entity
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"user_session_id", "question_id", "answer_id"})
)
public class UserSelectorQuestionnaireEntry extends QuestionnaireEntry {

    private Long userSessionId;

    public UserSelectorQuestionnaireEntry(){}

    public UserSelectorQuestionnaireEntry(Long userSessionId, Question question, TherapeuticArea therapeuticArea, Answer answer) {
        super(question, answer, therapeuticArea);
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