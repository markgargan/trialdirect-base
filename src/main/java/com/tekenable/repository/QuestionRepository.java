package com.tekenable.repository;

import com.tekenable.model.Question;
import com.tekenable.model.projections.WithAnswers;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = WithAnswers.class, collectionResourceRel = "questions", path = "questions")
public interface QuestionRepository extends PagingAndSortingRepository<Question, Integer> {

}

