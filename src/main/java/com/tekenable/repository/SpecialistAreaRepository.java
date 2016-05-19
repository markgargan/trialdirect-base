package com.tekenable.repository;

import com.tekenable.model.SpecialistArea;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "specialistareas", path = "specialistareas")
public interface SpecialistAreaRepository extends PagingAndSortingRepository<SpecialistArea, Integer> {

}

