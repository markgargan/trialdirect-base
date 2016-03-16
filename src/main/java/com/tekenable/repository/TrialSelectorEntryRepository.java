package com.tekenable.repository;

import com.tekenable.model.TrialSelectorEntry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "trialselectorentry", path = "trialselectorentry")
public interface TrialSelectorEntryRepository extends PagingAndSortingRepository<TrialSelectorEntry, Integer> {

}

