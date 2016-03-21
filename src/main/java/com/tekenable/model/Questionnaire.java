package com.tekenable.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * Created by smoczyna on 17/03/16.
 */
@Entity
public class Questionnaire extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String title;
    private Set<QuestionnaireEntry> questionaireEntries;

    public Questionnaire() {
    }

    public Questionnaire(String title) {
        this.title = title;
    }

    public Questionnaire(String title, Set<QuestionnaireEntry> entries) {
        this.title =title;
        this.questionaireEntries = entries;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<QuestionnaireEntry> getQuestionaireEntries() {
        return questionaireEntries;
    }

    public void setQuestionaireEntries(Set<QuestionnaireEntry> questionaireEntries) {
        this.questionaireEntries = questionaireEntries;
    }
}
