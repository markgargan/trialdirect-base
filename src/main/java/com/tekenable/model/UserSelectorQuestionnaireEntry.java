package com.tekenable.model;

import javax.persistence.*;

@Entity
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"user_id", "therapeutic_area_id","question_id", "answer_id"})
)
public class UserSelectorQuestionnaireEntry extends QuestionEntry {

    private User user;

    private TherapeuticArea therapeuticArea;

    public UserSelectorQuestionnaireEntry(){}

    public UserSelectorQuestionnaireEntry(User user, Question question, Answer answer, TherapeuticArea therapeuticArea) {
        super(question, answer);
        this.user = user;
        this.therapeuticArea = therapeuticArea;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
