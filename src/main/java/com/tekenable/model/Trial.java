package com.tekenable.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tekenable.model.trial.TrialInfo;

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
//@EntityListeners(TrialListener.class)
public class Trial extends SortEntity {

    private String title;

    private String trialCode; //This code will be used to search for a trial. The code is used internally by Icon
    //to uniquely identify a trial. The codes are tracked by Icon in their own Gira
    //and are used when an admin user logs on to lookup a trial.

    private Set<TrialSelectorQuestionnaireEntry> trialselectorquestionnaireentries;

    private TherapeuticArea therapeuticarea;

    private Set<TrialInfo> trialInfos;

    public Trial() {
    }

    public Trial(String title, TherapeuticArea therapeuticarea) {
        this.title = title;
        this.therapeuticarea = therapeuticarea;
    }

    public Trial(String title, TherapeuticArea therapeuticarea, String trialCode) {
        this.title = title;
        this.therapeuticarea = therapeuticarea;
        this.trialCode = trialCode;
    }

    public Trial(String title, TherapeuticArea therapeuticarea, Integer sortOrder) {
        this.title = title;
        this.therapeuticarea = therapeuticarea;
        this.sortOrder = sortOrder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrialCode() {
        return trialCode;
    }

    public void setTrialCode(String trialCode) {
        this.trialCode = trialCode;
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
    public TherapeuticArea getTherapeuticarea() {
        return therapeuticarea;
    }

    public void setTherapeuticarea(TherapeuticArea therapeuticarea) {
        this.therapeuticarea = therapeuticarea;
    }

    @OneToMany(mappedBy = "trial", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<TrialInfo> getTrialInfos() {
        return trialInfos;
    }

    public void setTrialInfos(Set<TrialInfo> trialInfos) {
        this.trialInfos = trialInfos;
    }

}
