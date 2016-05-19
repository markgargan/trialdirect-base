package com.tekenable.model;

import javax.persistence.*;

@Entity
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"user_id", "specialist_area_id","question_id", "answer_id"})
)
public class UserSelectorQuestionnaireEntry extends QuestionEntry {

    private User user;

    private SpecialistArea specialistArea;

    public UserSelectorQuestionnaireEntry(){}

    public UserSelectorQuestionnaireEntry(User user, Question question, Answer answer, SpecialistArea specialistArea) {
        super(question, answer);
        this.user = user;
        this.specialistArea = specialistArea;
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
    @JoinColumn(name = "specialist_area_id")
    public SpecialistArea getSpecialistArea() {
        return specialistArea;
    }

    public void setSpecialistArea(SpecialistArea specialistArea) {
        this.specialistArea = specialistArea;
    }
}
