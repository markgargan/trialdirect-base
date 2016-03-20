package com.tekenable.model;
import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
public class TherapeuticArea extends BaseEntity {

  private String name;
  private Set<Question> questions;

  public TherapeuticArea(){

  }

  public TherapeuticArea(String name){
    this.name = name;
  }

  public TherapeuticArea(String name, Set<Question> questions){
    this.name = name;
    this.questions = questions;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * To understand the cascading options
   *
   * @link http://vladmihalcea.com/2015/03/05/a-beginners-guide-to-jpa-and-hibernate-cascade-types/
   * @return
   **/
  @ManyToMany(mappedBy = "therapeuticAreas",
          cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  public Set<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<Question> questions) {
    this.questions = questions;
  }
}