package com.tekenable.repository;

import com.tekenable.model.Trial;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "trialinfos", path = "trialinfos")
public interface TrialInformationRepository extends PagingAndSortingRepository<Trial, Integer> {

}

