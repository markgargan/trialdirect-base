package com.tekenable.repository;

import com.tekenable.model.TrialSelectorQuestionEntry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "trialselectorquestionentry", path = "trialselectorquestionentry")
public interface TrialSelectorQuestionEntryRepository extends PagingAndSortingRepository<TrialSelectorQuestionEntry, Integer> {

}

