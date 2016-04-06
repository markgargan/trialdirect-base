package com.tekenable.repository;

import com.tekenable.model.trial.TrialInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "trialinfos", path = "trialinfos")
public interface TrialInfoRepository extends PagingAndSortingRepository<TrialInfo, Integer> {

}

