package com.tekenable.repository;

import com.tekenable.model.trial.TrialSite;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "trialsites", path = "trialsites")
public interface TrialSiteRepository extends PagingAndSortingRepository<TrialSite, Integer> {

}

