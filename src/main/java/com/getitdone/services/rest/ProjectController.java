package com.getitdone.services.rest;

import com.getitdone.services.core.Constants;
import com.getitdone.services.core.IProjectService;
import com.getitdone.services.domain.Project;
import com.getitdone.services.util.GetitDonAppException;
import com.wordnik.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"projects"})
@RequestMapping(value = "/projects")
@RestController
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    IProjectService service;

    /**
     * POST: url: /projects
     * body: project json
     * @param project
     * @param response
     * @return
     */
    @RequestMapping(method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createProject(@RequestBody Project project, @RequestHeader String userId, HttpServletResponse response){
        logger.info("create Project request with data: {}", project);
        try {
            project.setCreatedBy(userId);
            String id = service.createProject(project);
            response.setStatus(201);
            return "Location: /projects/"+id;

        } catch (JSONException e){
            response.setStatus(404);
            return "Not valid JSON";
        }
    }

    /**
     * GET: url: /projects/{id}
     * return Project json
     * @param id
     * @return
     */
    @RequestMapping(method= RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Project getProject(@PathVariable String id) {
        logger.info("API- getProject - projec: {}", id);
        Project project =  service.getProject(id);
        if(project == null) {
            throw new GetitDonAppException(HttpStatus.NOT_FOUND,"project_notfound", "No project found for this id");
        }
        return project;
    }


    /**
     * GET: /projects
     *      /projects?status=open
     *      /projects?status=open&createdBy={useId}
     *      /projects?status=closed&start=0&records=10
     * optional query params
     *          start = page number,
     *          records = number records to retrieve
     *          createdBy = filter the by userId who created this project
     *          status = filter based on project status
     * default for start and records are 1, 10 respectively
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getProjects(HttpServletRequest request, HttpServletResponse response){
        logger.info("getProjects API: {}");


        String createdBy = request.getParameter(Constants.QUERY_PARAM_CREATEBY);
        String status = request.getParameter(Constants.QUERY_PARAM_STATUS);
        String start = request.getParameter(Constants.QUERY_PARAM_START);
        String records =  request.getParameter(Constants.QUERY_PARAM_RECORDS);

        Map<String, String> filterMap = new HashMap<>();
        if(!StringUtils.isEmpty(createdBy)) {
            filterMap.put(Constants.QUERY_PARAM_CREATEBY, createdBy);
        }

        if(!StringUtils.isEmpty(status)) {
            filterMap.put(Constants.QUERY_PARAM_STATUS, status);
        }

        if(!StringUtils.isEmpty(start)) {
            filterMap.put(Constants.QUERY_PARAM_START, start);
        }

        if(!StringUtils.isEmpty(records)) {
            filterMap.put(Constants.QUERY_PARAM_RECORDS, records);
        }

        if(filterMap.size() ==0) {
            //if no filter get first 100
            start = (StringUtils.isEmpty(start) || !StringUtils.isNumeric(start)) ? "1" : start ;
            records= (StringUtils.isEmpty(start) || !StringUtils.isNumeric(records)) ? "100" : start ;

            filterMap.put(Constants.QUERY_PARAM_START, start);
            filterMap.put(Constants.QUERY_PARAM_RECORDS, records);
        }



        List<Project> projects = service.getAllProjects(filterMap);
        return projects;
    }

    /**
     * PUT: /projects/{id}
     * body: Project json
     * @param project
     * @param response
     * @return
     */
    @RequestMapping(method= RequestMethod.PUT, value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateProject(@RequestBody Project project, HttpServletResponse response){
        logger.info("update request with data: {}", project);
        response.setStatus(501);
        //TODO: implement update project
    }

    /**
     * DELETE: /projects/{id}
     * @param project
     * @param response
     * @return
     */
    @RequestMapping(method= RequestMethod.DELETE, value = "/{id}")
    public void delete(@RequestBody Project project, HttpServletResponse response){
        logger.info("delete request with data: {}", project);
        //TODO: implement delete project
        response.setStatus(501);
    }

}
