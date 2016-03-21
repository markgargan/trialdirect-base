package com.tekenable.repository;

import com.tekenable.model.Questionnaire;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by smoczyna on 17/03/16.
 */
@RepositoryRestResource(collectionResourceRel = "questionentry", path = "questionentry")
public interface QuestionnaireRepository extends PagingAndSortingRepository<Questionnaire, Integer> {

}

