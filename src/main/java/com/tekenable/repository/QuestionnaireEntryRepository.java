package com.tekenable.repository;

import com.tekenable.model.QuestionnaireEntry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by smoczyna on 17/03/16.
 */
@RepositoryRestResource(collectionResourceRel = "questionentry", path = "questionentry")
public interface QuestionnaireEntryRepository extends PagingAndSortingRepository<QuestionnaireEntry, Integer> {

}