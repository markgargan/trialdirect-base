package com.tekenable.repository;

import com.tekenable.model.TrialSelectorQuestionnaireEntry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "trialselectorquestionnaireentries", path = "trialselectorquestionnaireentries")
public interface TrialSelectorQuestionnaireEntryRepository extends PagingAndSortingRepository<TrialSelectorQuestionnaireEntry, Integer> {

}

