package com.getitdone.services.core.impl;

import com.getitdone.services.core.Constants;
import com.getitdone.services.core.IProjectService;
import com.getitdone.services.domain.Project;
import com.getitdone.services.repo.ProjectRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    ProjectRepository repository;

    @Override
    public String createProject(Project project) {
        Project savedProject = repository.save(project);
        return savedProject.getId();
    }

    @Override
    public Project getProject(String id) {
        Optional<Project> project = repository.findById(id);
        if(project.isPresent()){
            return project.get();
        }
        return null;
    }

    @Override
    public List<Project> getAllProjects(Map<String, String> filterMap) {
        int page = 1;
        int size = 100;

        String pgSTring = filterMap.get(Constants.QUERY_PARAM_START);
        if (!StringUtils.isEmpty(pgSTring)) {
            page = Integer.valueOf(pgSTring);
            if (page <= 0) {
                page = 1;
            }
        }

        String sizeString = filterMap.get(Constants.QUERY_PARAM_RECORDS);
        if (!StringUtils.isEmpty(sizeString)) {
            size = Integer.valueOf(pgSTring);
            if (page <= 100) {
                size = 100;
            }
        }


        List<Project> projects = null;
        if (filterMap.containsKey(Constants.QUERY_PARAM_STATUS) || filterMap.containsKey(Constants.QUERY_PARAM_CREATEBY)) {
            if (filterMap.containsKey(Constants.QUERY_PARAM_STATUS) & filterMap.containsKey(Constants.QUERY_PARAM_CREATEBY)) {
                projects = repository.findProjectByCreatedByAndStatus(
                        filterMap.get(Constants.QUERY_PARAM_CREATEBY),
                        filterMap.get(Constants.QUERY_PARAM_STATUS));

            } else if (filterMap.containsKey(Constants.QUERY_PARAM_STATUS)) {
                projects = repository.findProjectByStatus(filterMap.get(Constants.QUERY_PARAM_STATUS));
            } else {
                projects = repository.findProjectByStatus(filterMap.get(Constants.QUERY_PARAM_CREATEBY));
            }

            if (projects.size() > size) {
                //return first based upon page number
                projects = projects.subList(0, size);
                //TODO: do pagination
            }
        } else {
//        Page<Project> all = repository.findAll(PageRequest.of(page, size));
//        if(all != null) {
//            return all.getContent();
//        }
          projects = repository.findAll();

        }

        return projects;
    }

}
