package com.tekenable.model.trial;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tekenable.model.BaseEntity;

import javax.persistence.*;

/**
 * Created by mark on 04/04/2016.
 */
@Embeddable
public class TrialDirectImage {

    private String filename;

    private String type;

    private byte[] content;

    public TrialDirectImage() {
    }

    public TrialDirectImage(String filename, String type, byte[] content) {
        this.filename = filename;
        this.type = type;
        this.content = content;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonIgnore
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="longblob")
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

}
