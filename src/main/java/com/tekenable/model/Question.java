package com.tekenable.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
public class Question extends BaseEntity {

    private String questionText;
    private Set<TherapeuticArea> therapeuticAreas;
    private Set<QuestionEntry> questionEntries;
    private Set<Answer> answers;

    public Question() {}

    public Question(String questionText) {
        this.questionText = questionText;
    }

    public Question(String questionText, Set<TherapeuticArea> therapeuticAreas) {
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
    public Set<TherapeuticArea> getTherapeuticAreas() {
        return therapeuticAreas;
    }

    public void setTherapeuticAreas(Set<TherapeuticArea> therapeuticAreas) {
        this.therapeuticAreas = therapeuticAreas;
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<QuestionEntry> getQuestionEntries() {
        return questionEntries;
    }

    public void setQuestionEntries(Set<QuestionEntry> questionEntries) {
        this.questionEntries = questionEntries;
    }
}