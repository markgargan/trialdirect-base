package com.tekenable.model;

import java.util.Set;
import javax.persistence.*;

/**
 * Created by smoczyna on 17/03/16.
 */
@Entity
public class TherapeuticArea extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String title;
    private Set<QuestionnaireEntry> questionnaireentries;

    public TherapeuticArea() {
    }

    public TherapeuticArea(String title) {
        this.title =title;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "therapeuticArea", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    public Set<QuestionnaireEntry> getQuestionnaireentries() {
        return questionnaireentries;
    }

    public void setQuestionnaireentries(Set<QuestionnaireEntry> questionnaireentries) {
        this.questionnaireentries = questionnaireentries;
    }
}
