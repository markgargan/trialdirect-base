package com.tekenable.repository;

import com.tekenable.model.Answer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "answers", path = "answers")
public interface AnswerRepository extends PagingAndSortingRepository<Answer, Integer> {

}

