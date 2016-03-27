package com.tekenable.repository;

import com.tekenable.model.QuestionnaireEntry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "questionnaireentries", path = "questionnaireentries")
public interface QuestionnaireEntryRepository extends PagingAndSortingRepository<QuestionnaireEntry, Integer> {

}

