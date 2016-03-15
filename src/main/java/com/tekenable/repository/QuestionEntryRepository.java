package com.tekenable.repository;

import com.tekenable.model.Question;
import com.tekenable.model.QuestionEntry;
import com.tekenable.model.QuestionEntryId;
import com.tekenable.model.projections.WithAnswers;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "questionentry", path = "questionentry")
public interface QuestionEntryRepository extends PagingAndSortingRepository<QuestionEntry, QuestionEntryId> {

}

