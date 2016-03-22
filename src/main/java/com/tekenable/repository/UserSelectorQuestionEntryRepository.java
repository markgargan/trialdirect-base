package com.tekenable.repository;

import com.tekenable.model.UserSelectorQuestionEntry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "userselectorentrys", path = "userselectorentrys")
public interface UserSelectorQuestionEntryRepository extends PagingAndSortingRepository<UserSelectorQuestionEntry, Integer> {

}

