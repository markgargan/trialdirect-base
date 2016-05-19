package com.tekenable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Set;

/**
 * Created by nbarrett on 19/05/2016.
 */
@Entity
public class TherapeuticArea extends BaseEntity {

    private String title; //Name of the Therapeutic area e.g. Cancer,

    private Set<SpecialistArea> specialistAreaSet; //Link to one or more specialist areas e.g. Lung cancer.

    /**
     *
     * @param title
     */
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

    @ManyToOne
    @JoinColumn(nullable = true, name = "specialist_area_id")
    public Set<SpecialistArea> getSpecialistAreaSet() {
        return specialistAreaSet;
    }

    public void setSpecialistAreaSet(Set<SpecialistArea> specialistAreaSet) {
        this.specialistAreaSet = specialistAreaSet;
    }
}
