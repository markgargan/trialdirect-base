package com.tekenable.repository;

import com.tekenable.model.Trial;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "trials", path = "trials")
public interface TrialRepository extends PagingAndSortingRepository<Trial, Integer> {

}

