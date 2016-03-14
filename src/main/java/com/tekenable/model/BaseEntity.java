package com.tekenable.model;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {
  protected Long id;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
