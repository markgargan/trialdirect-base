package com.tekenable.repository;

import com.tekenable.model.TherapeuticArea;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "childareas", path = "childareas")
public interface TherapeuticAreaRepository extends PagingAndSortingRepository<TherapeuticArea, Integer> {

}

