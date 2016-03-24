package com.tekenable.repository;

import com.tekenable.model.UserSelectorQuestionnaireEntry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "userselectorquestionnaireentries", path = "userselectorquestionnaireentries")
public interface UserSelectorQuestionnaireEntryRepository extends PagingAndSortingRepository<UserSelectorQuestionnaireEntry, Integer> {

}

