package com.getitdone.services.repo;

import com.getitdone.services.domain.Project;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String>{

    Page<Project> findAll(Pageable pageable);
    List<Project> findProjectByCreatedByAndStatus(String createdBy, String status);
    List<Project> findProjectByCreatedBy(String createdBy);
    List<Project> findProjectByStatus(String status);


}
