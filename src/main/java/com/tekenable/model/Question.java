package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Question extends SortEntity {

    private String questionText;
    // lowercased in order to keep the rest api all lower case
    private Set<QuestionnaireEntry> questionnaireentries;

    public Question() {}

    public Question(String questionText) {
        this.questionText = questionText;
    }

    public Question(String questionText, Set<SpecialistArea> specialistAreas) {
        this.questionText = questionText;
    }

    public Question(String questionText, Integer sortOrder) {
        this.questionText = questionText;
        this.sortOrder = sortOrder;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL,
            orphanRemoval = true,fetch = FetchType.LAZY)
    public Set<QuestionnaireEntry> getQuestionnaireentries() {
        return questionnaireentries;
    }

    public void setQuestionnaireentries(Set<QuestionnaireEntry> questionnaireentries) {
        this.questionnaireentries = questionnaireentries;
    }

}