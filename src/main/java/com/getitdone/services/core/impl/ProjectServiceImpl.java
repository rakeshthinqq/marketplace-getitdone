package com.getitdone.services.core.impl;

import com.getitdone.services.core.Constants;
import com.getitdone.services.core.IProjectService;
import com.getitdone.services.domain.Bid;
import com.getitdone.services.domain.Project;
import com.getitdone.services.repo.ProjectRepository;
import com.getitdone.services.util.GetitDonAppException;
import com.getitdone.services.util.ServiceHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProjectServiceImpl implements IProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);


    @Autowired
    ProjectRepository repository;

    @Autowired
    ServiceHelper serviceHelper;

    @Override
    public String createProject(Project project) {
        if(project.getListingExpiryDate() == null) {
            //TODO: add default expiry date
            // or it can never be expired ?
        }
        Project savedProject = repository.save(project);
        return savedProject.getId();
    }

    @Override
    public Project getProject(String id) {
        Project p = null;
        Optional<Project> project = repository.findById(id);
        if(project.isPresent()){
            p =  project.get();
            addLink(Arrays.asList(p));
            serviceHelper.doStatusChange(p);
        }
        return p;
    }

    @Override
    @Async
    public void setLowestBid(Project p) {
        try {
            if (p != null) {
                BigDecimal lowestAmount = new BigDecimal(-1);
                List<Bid> bids = p.getBids();
                if (!CollectionUtils.isEmpty(bids)) {
                    for (Bid bid : bids) {
                        if (lowestAmount.equals(new BigDecimal(-1)) || (bid.getBidPrice() != null && bid.getBidPrice().compareTo(lowestAmount) <= 0)) {
                            lowestAmount = bid.getBidPrice();
                        }
                    }
                }
                p.setLowestBidPrice(lowestAmount);
            }
        } catch (Exception e){
            throw new GetitDonAppException("code-lowestBid", e.getMessage());
        }
    }

    private void addLink(List<Project> projectList) {
        if(!CollectionUtils.isEmpty(projectList)){
            for(Project p : projectList) {
                Map<String, String> link = new HashMap<>();
                link.put("href", "/projects/"+p.getId()+"/"+"bids");
                link.put("rel", "bids");
                link.put("type", "GET");
                p.addLink(link);

                Map<String, String> selfLink = new HashMap<>();
                selfLink.put("href", "/projects/"+p.getId());
                selfLink.put("rel", "self");
                selfLink.put("type", "GET");
                p.addLink(selfLink);
            }
        }
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
            projects = getProjectsByFilter(filterMap, size);
        } else {
        /*        Page<Project> all = repository.findAll(PageRequest.of(page, size));
        if(all != null) {
            return all.getContent();
        }*/
          projects = repository.findAll();

        }
        addLink(projects);
        return projects;
    }

    @Override
    public void updateProject(Project project) {
        Optional<Project> one = repository.findById(project.getId());

        Project existing = null;
        if(one.isPresent()){
            existing = one.get();
            logger.info("Existing project here >>>>>>>>>>>>>:{}", existing);
            BeanUtils.copyProperties(project, existing, "lowestBidPrice");
            logger.info("Source to  Copy here >>>>>>>>>>>>>:{}", project);

            logger.info("Existing After Copy here >>>>>>>>>>>>>:{}", existing);

        }

        repository.save(existing);
    }

    @Override
    public boolean validateProject(Project project) {
        if(project.getId() == null){
            //TODO, add other params
            return false;
        }

        return true;
    }

    private List<Project> getProjectsByFilter(Map<String, String> filterMap, int size) {
        List<Project> projects;
        if (filterMap.containsKey(Constants.QUERY_PARAM_STATUS) && filterMap.containsKey(Constants.QUERY_PARAM_CREATEBY)) {
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
        return projects;
    }

    public static void main(String[] arg){
        Project project = new Project();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");

        try {
            Date date =  format.parse("13-03-2018 02:10");
            project.setListingExpiryDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        project.setName("test");
        project.setName("test descri[tion");
        Bid bid = new Bid();
        bid.setBidPrice(new BigDecimal(10));

        project.addBid(bid);

        Bid bid1 = new Bid();
        bid1.setBidPrice(new BigDecimal(20));
        project.addBid(bid1);

        Bid bid2 = new Bid();
        bid2.setBidPrice(new BigDecimal(30));
        project.addBid(bid2);
        new ServiceHelper().doStatusChange(project);
        System.out.println(project);
    }

}
