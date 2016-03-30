package com.tekenable.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@NamedQueries({


        @NamedQuery(name = "Trial.getAllTrialsForUserId",
                query = "select t from Trial t where id=:userId")
})

@NamedNativeQueries({
        @NamedNativeQuery(name="Trial.getAvailableTrialIds",
                query="select matching_trial.id from Trial matching_trial  where matching_trial.id not in ( " +
                        "select distinct (trial_id) from Trial t " +
                        "  inner join User u on t.therapeutic_area_id = u.therapeutic_area_id " +
                        "  inner join UserSelectorQuestionnaireEntry usqe " +
                        "  inner join TrialSelectorQuestionnaireEntry tsqe " +
                        "    on usqe.question_id=tsqe.question_id " +
                        "       and usqe.answer_id=tsqe.answer_id " +
                        "where user_id=:userId) " +
                        "and matching_trial.therapeutic_area_id = :usersTherapeuticAreaId")
})


@Entity
@EntityListeners(TrialListener.class)
public class Trial extends BaseEntity {

    private String title;

    private Set<TrialSelectorQuestionnaireEntry> trialselectorquestionnaireentries;

    private TherapeuticArea therapeuticArea;

    private Set<TrialInfo> trialInfos;

    public Trial() {
    }

    public Trial(String title, TherapeuticArea therapeuticArea) {
        this.title = title;
        this.therapeuticArea = therapeuticArea;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "trial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<TrialSelectorQuestionnaireEntry> getTrialselectorquestionnaireentries() {
        return trialselectorquestionnaireentries;
    }

    public void setTrialselectorquestionnaireentries(Set<TrialSelectorQuestionnaireEntry> trialselectorquestionnaireentries) {
        this.trialselectorquestionnaireentries = trialselectorquestionnaireentries;
    }

    @ManyToOne
    @JoinColumn(nullable = true, name = "therapeutic_area_id")
    public TherapeuticArea getTherapeuticArea() {
        return therapeuticArea;
    }

    public void setTherapeuticArea(TherapeuticArea therapeuticArea) {
        this.therapeuticArea = therapeuticArea;
    }

    @OneToMany(mappedBy = "trial", cascade = CascadeType.ALL)
    public Set<TrialInfo> getTrialInfos() {
        return trialInfos;
    }

    public void setTrialInfos(Set<TrialInfo> trialInfos) {
        this.trialInfos = trialInfos;
    }
}
