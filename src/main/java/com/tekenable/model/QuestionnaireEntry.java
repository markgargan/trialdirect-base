package com.tekenable.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by smoczyna on 17/03/16.
 */
@Entity
public class QuestionnaireEntry extends BaseEntity {

    private static final long serialVersionUID = 1L;


    private String title;
    private Question question;
    private Set<Answer> answers;
    private Questionnaire questionnaire;

    public QuestionnaireEntry() {
    }

    public QuestionnaireEntry(String title, String question, String answer) {
        this.title= title;
        this.question = new Question(question);
        this.answers = new LinkedHashSet();
        this.answers.add(new Answer(answer));
    }

    public QuestionnaireEntry(String title, Question question, Answer answer) {
        this.title = title;
        this.question = question;
        this.answers = new LinkedHashSet();
        this.answers.add(answer);
    }

    public QuestionnaireEntry(String title, Question question, Set<Answer> answers) {
        this.title = title;
        this.question = question;
        this.answers = answers;
    }

    @Column(name= "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne
    @JoinColumn(name = "question_id")
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
    @JoinColumn(name = "questionnaire_id")
    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

}
