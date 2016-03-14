package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Question extends BaseEntity {

    private String name;
    private Set<TherapeuticArea> therapeuticAreas;
    private Set<Answer> answers;

    public Question() {

    }


    public Question(String name) {
        this.name = name;
    }

    public Question(String name, Set<TherapeuticArea> therapeuticAreas, Set<Answer> answers) {
        this.name = name;
        this.therapeuticAreas = therapeuticAreas;
        this.answers = answers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "question_therapeuticArea",
            joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "therapeuticArea_id", referencedColumnName = "id"))
    public Set<TherapeuticArea> getTherapeuticAreas() {
        return therapeuticAreas;
    }

    public void setTherapeuticAreas(Set<TherapeuticArea> therapeuticAreas) {
        this.therapeuticAreas = therapeuticAreas;
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

}