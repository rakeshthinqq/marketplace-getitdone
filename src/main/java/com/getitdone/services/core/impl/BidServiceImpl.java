package com.getitdone.services.core.impl;

import com.getitdone.services.core.IBidService;
import com.getitdone.services.domain.Bid;
import com.getitdone.services.domain.Project;
import com.getitdone.services.repo.BidRepository;
import com.getitdone.services.repo.ProjectRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidServiceImpl implements IBidService {

    @Autowired
    BidRepository bidRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public String createBid(Bid bid, String projectId) {
        Optional<Project> byId = projectRepository.findById(projectId);
        if(byId.isPresent()) {
            Project project = byId.get();

            String objectId = new ObjectId().toString();
            bid.setId(objectId);
            project.addBid(bid);
            projectRepository.save(project);
            return objectId;
        }

        return null;
    }

    @Override
    public Bid getBid(String projectId, String id) {
        Bid bid = null;
        Optional<Project> byId = projectRepository.findById(projectId);
        if (byId.isPresent()){
           Project project = byId.get();
            List<Bid> bids = project.getBids();
            for (Bid bd: bids) {
               if(bd != null && bd.getId() != null && bd.getId().toString().equals(id)){
                   bid = bd;
                   break;
               }
            }
        }
        return bid;
    }

    @Override
    public List<Bid> getAllBids(String projectId) {
        Optional<Project> byId = projectRepository.findById(projectId);
        if (byId.isPresent()) {
            Project project = byId.get();
            List<Bid> bids = project.getBids();
            return bids;
        }
        return null;
    }
}
