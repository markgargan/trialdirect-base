package com.tekenable.model;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {
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
