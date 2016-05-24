package com.tekenable.model;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by smoczyna on 17/03/16.
 *
 * Create a new QuestionnaireEntry via Data Rest
 * curl -v -X PUT -H "Content-Type: text/uri-list" \
 -d "http://localhost:7070/study-spring-data/teacher/1" \
 http://localhost:7070/study-spring-data/course/123/teacher
 *
 *
 */
@Entity
public class QuestionnaireEntry extends BaseEntity {

    private static final long serialVersionUID = 1L;


    protected Question question;
    protected Set<Answer> answers;
    protected SpecialistArea specialistarea;

    public QuestionnaireEntry() {
    }

    public QuestionnaireEntry(String question, String answer) {
        this.question = new Question(question);
        this.answers = new LinkedHashSet();
        this.answers.add(new Answer(answer));
    }

    public QuestionnaireEntry(Question question, Answer answer, SpecialistArea specialistarea) {
        this.question = question;
        this.answers = new LinkedHashSet();
        this.answers.add(answer);
        this.specialistarea = specialistarea;
    }

    public QuestionnaireEntry(Question question, Set<Answer> answers, SpecialistArea specialistarea) {
        this.question = question;
        this.answers = answers;
        this.specialistarea = specialistarea;
    }

    @ManyToOne
    @JoinColumn(name = "question_id")
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "questionnaireEntry_answer",
            joinColumns=@JoinColumn(name="questionnaire_entry_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="answer_id", referencedColumnName="id"))
    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
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
