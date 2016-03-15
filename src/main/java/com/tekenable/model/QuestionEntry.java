package com.tekenable.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name="question_entity_type",
        discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue("qe")
public class QuestionEntry implements Serializable {

    @EmbeddedId
    private QuestionEntryId questionEntryId;

    @ManyToOne
    @JoinColumn(name = "question_id", insertable=false, updatable=false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "answer_id", insertable=false, updatable=false)
    private Answer answer;

    public QuestionEntry(Question question, Answer answer) {
        this.question = question;
        this.answer = answer;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}