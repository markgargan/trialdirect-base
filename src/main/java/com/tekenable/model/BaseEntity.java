package com.tekenable.model;

import java.io.Serializable;
import javax.persistence.*;

@MappedSuperclass
public class BaseEntity implements Serializable {
  protected int id;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
