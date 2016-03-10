package com.tekenable.model;

import javax.persistence.*;

@Entity
public class Item extends BaseEntity{

  @Column
  private boolean checked;
  
  @Column
  private String description;

  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
