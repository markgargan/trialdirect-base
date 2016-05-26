package com.tekenable.model;

import javax.persistence.*;

@Entity
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"user_id", "therapeutic_area_id","question_id", "answer_id"})
)
public class UserSelectorQuestionnaireEntry extends QuestionEntry {

    private User user;

    private TherapeuticArea therapeuticarea;

    public UserSelectorQuestionnaireEntry(){}

    public UserSelectorQuestionnaireEntry(User user, Question question, Answer answer, TherapeuticArea therapeuticarea) {
        super(question, answer);
        this.user = user;
        this.therapeuticarea = therapeuticarea;
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
    public TherapeuticArea getTherapeuticarea() {
        return therapeuticarea;
    }

    public void setTherapeuticarea(TherapeuticArea therapeuticarea) {
        this.therapeuticarea = therapeuticarea;
    }
}
