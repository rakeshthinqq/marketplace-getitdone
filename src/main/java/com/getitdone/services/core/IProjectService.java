package com.getitdone.services.core;

import com.getitdone.services.domain.Bid;
import com.getitdone.services.domain.Project;

import java.util.List;
import java.util.Map;

public interface IProjectService {
    String createProject(Project project);
    Project getProject(String id);
    List<Project> getAllProjects(Map<String, String> filter);
    void updateProject(Project project);
    boolean validateProject(Project project);
    void setLowestBid(Project project, Bid bid);


    void triggerLowestBids(Project project, Bid bid);
}
