package com.tekenable.model;

import javax.persistence.*;

@Entity
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"user_id", "specialist_area_id","question_id", "answer_id"})
)
public class UserSelectorQuestionnaireEntry extends QuestionEntry {

    private User user;

    private SpecialistArea specialistarea;

    public UserSelectorQuestionnaireEntry(){}

    public UserSelectorQuestionnaireEntry(User user, Question question, Answer answer, SpecialistArea specialistarea) {
        super(question, answer);
        this.user = user;
        this.specialistarea = specialistarea;
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
    public SpecialistArea getSpecialistarea() {
        return specialistarea;
    }

    public void setSpecialistarea(SpecialistArea specialistarea) {
        this.specialistarea = specialistarea;
    }
}
