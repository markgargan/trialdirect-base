package com.tekenable.repository;

import com.tekenable.model.TherapeuticArea;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by nbarrett on 19/05/2016.
 */
@RepositoryRestResource(collectionResourceRel = "parentareas", path = "parentareas")
public interface SyndromeRepository extends PagingAndSortingRepository<TherapeuticArea, Integer> {

}
