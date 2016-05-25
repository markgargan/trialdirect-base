package com.tekenable.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by nbarrett on 19/05/2016.
 */
@Entity
public class Syndrome extends BaseEntity {

    private String title; //Name of the Therapeutic area e.g. Cancer,

    private Set<SpecialistArea> specialistareas; //Link to one or more specialist areas e.g. Lung cancer.

    /**
     *
     */
    public Syndrome(){}

    /**
     *
     * @param title
     */
    public Syndrome(String title) {
        this.title =title;
    }


    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "therapeuticarea", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    public Set<SpecialistArea> getSpecialistareas() {
        return specialistareas;
    }

    public void setSpecialistareas(Set<SpecialistArea> specialistareas) {
        this.specialistareas = specialistareas;
    }
}
