package com.tekenable.model;

import java.io.Serializable;
import javax.persistence.*;

@MappedSuperclass
public class BaseEntity implements Serializable {
  protected Integer id;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
