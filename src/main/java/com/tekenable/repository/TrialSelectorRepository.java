package com.tekenable.repository;

import com.tekenable.model.TrialSelector;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "trialselector", path = "trialselector")
public interface TrialSelectorRepository extends PagingAndSortingRepository<TrialSelector, Integer> {

}

