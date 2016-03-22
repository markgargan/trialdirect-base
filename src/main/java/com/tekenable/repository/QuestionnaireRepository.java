package com.tekenable.repository;

import com.tekenable.model.Questionnaire;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by smoczyna on 17/03/16.
 */
@RepositoryRestResource(collectionResourceRel = "questionnaires", path = "questionnaires")
public interface QuestionnaireRepository extends PagingAndSortingRepository<Questionnaire, Integer> {

}

