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
    protected TherapeuticArea therapeuticarea;

    public QuestionnaireEntry() {
    }

    public QuestionnaireEntry(String question, String answer) {
        this.question = new Question(question);
        this.answers = new LinkedHashSet();
        this.answers.add(new Answer(answer));
    }

    public QuestionnaireEntry(Question question, Answer answer, TherapeuticArea therapeuticarea) {
        this.question = question;
        this.answers = new LinkedHashSet();
        this.answers.add(answer);
        this.therapeuticarea = therapeuticarea;
    }

    public QuestionnaireEntry(Question question, Set<Answer> answers, TherapeuticArea therapeuticarea) {
        this.question = question;
        this.answers = answers;
        this.therapeuticarea = therapeuticarea;
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
    @JoinColumn(name = "therapeutic_area_id")
    public TherapeuticArea getTherapeuticarea() {
        return therapeuticarea;
    }

    public void setTherapeuticarea(TherapeuticArea therapeuticarea) {
        this.therapeuticarea = therapeuticarea;
    }

}
