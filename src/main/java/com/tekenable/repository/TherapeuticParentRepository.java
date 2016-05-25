package com.tekenable.repository;

import com.tekenable.model.TherapeuticParent;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by nbarrett on 19/05/2016.
 */
@RepositoryRestResource(collectionResourceRel = "therapeuticparent", path = "therapeuticparent")
public interface TherapeuticParentRepository extends PagingAndSortingRepository<TherapeuticParent, Integer> {

}
