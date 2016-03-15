package com.tekenable.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
public class Question extends BaseEntity {

    private String questionText;
    private Collection<TherapeuticArea> therapeuticAreas;
    private Collection<QuestionEntry> questionEntries;
    private Collection<Answer> answers;

    public Question() {}

    public Question(String questionText) {
        this.questionText = questionText;
    }

    public Question(String questionText, Collection<TherapeuticArea> therapeuticAreas) {
        this.questionText = questionText;
        this.therapeuticAreas = therapeuticAreas;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "question_therapeuticArea",
            joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "therapeuticArea_id", referencedColumnName = "id"))
    public Collection<TherapeuticArea> getTherapeuticAreas() {
        return therapeuticAreas;
    }
    

    public void setTherapeuticAreas(Collection<TherapeuticArea> therapeuticAreas) {
        this.therapeuticAreas = therapeuticAreas;
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Collection<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<Answer> answers) {
        this.answers = answers;
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Collection<QuestionEntry> getQuestionEntries() {
        return questionEntries;
    }

    public void setQuestionEntries(Collection<QuestionEntry> questionEntries) {
        this.questionEntries = questionEntries;
    }
}