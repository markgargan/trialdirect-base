package com.tekenable.repository;

import com.tekenable.model.Trial;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "trials", path = "trials")
public interface TrialRepository extends PagingAndSortingRepository<Trial, Integer> {

   @Query(nativeQuery = true)
    List<Integer> getAvailableTrialIds(@Param("userId")Integer userId,
                                       @Param("usersTherapeuticAreaId")Integer usersTherapeuticAreaId);
}

