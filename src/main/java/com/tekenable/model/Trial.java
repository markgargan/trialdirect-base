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
                        "  inner join User u on t.specialist_area_id = u.specialist_area_id " +
                        "  inner join UserSelectorQuestionnaireEntry usqe " +
                        "  inner join TrialSelectorQuestionnaireEntry tsqe " +
                        "    on usqe.question_id=tsqe.question_id " +
                        "       and usqe.answer_id=tsqe.answer_id " +
                        "where user_id=:userId) " +
                        "and matching_trial.specialist_area_id = :usersSpecialistAreaId")
})


@Entity
//@EntityListeners(TrialListener.class)
public class Trial extends SortEntity {

    private String title;

    private Set<TrialSelectorQuestionnaireEntry> trialselectorquestionnaireentries;

    private SpecialistArea specialistArea;

    private Set<TrialInfo> trialInfos;

    public Trial() {
    }

    public Trial(String title, SpecialistArea specialistArea) {
        this.title = title;
        this.specialistArea = specialistArea;
    }

    public Trial(String title, SpecialistArea specialistArea, Integer sortOrder) {
        this.title = title;
        this.specialistArea = specialistArea;
        this.sortOrder = sortOrder;
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
    @JoinColumn(nullable = true, name = "specialist_area_id")
    public SpecialistArea getSpecialistArea() {
        return specialistArea;
    }

    public void setSpecialistArea(SpecialistArea specialistArea) {
        this.specialistArea = specialistArea;
    }

    @OneToMany(mappedBy = "trial", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<TrialInfo> getTrialInfos() {
        return trialInfos;
    }

    public void setTrialInfos(Set<TrialInfo> trialInfos) {
        this.trialInfos = trialInfos;
    }

}
