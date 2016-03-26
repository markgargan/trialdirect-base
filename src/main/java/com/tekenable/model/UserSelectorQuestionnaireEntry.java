package com.tekenable.model;

import javax.persistence.*;

@Entity
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"user_session_id", "therapeutic_area_id","question_id", "answer_id"})
)
public class UserSelectorQuestionnaireEntry extends QuestionEntry {

    private Long userSessionId;

    private TherapeuticArea therapeuticArea;

    public UserSelectorQuestionnaireEntry(){}

    public UserSelectorQuestionnaireEntry(Long userSessionId, Question question, Answer answer, TherapeuticArea therapeuticArea) {
        super(question, answer);
        this.userSessionId = userSessionId;
        this.therapeuticArea = therapeuticArea;
    }

    @Column(name = "user_session_id")
    public Long getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(Long userSessionId) {
        this.userSessionId = userSessionId;
    }

    @ManyToOne
    @JoinColumn(name = "therapeutic_area_id")
    public TherapeuticArea getTherapeuticArea() {
        return therapeuticArea;
    }

    public void setTherapeuticArea(TherapeuticArea therapeuticArea) {
        this.therapeuticArea = therapeuticArea;
    }
}
