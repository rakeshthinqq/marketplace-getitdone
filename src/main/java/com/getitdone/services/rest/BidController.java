package com.getitdone.services.rest;

import com.getitdone.services.core.IBidService;
import com.getitdone.services.domain.Bid;
import com.getitdone.services.domain.Project;
import com.getitdone.services.util.GetitDonAppException;
import com.wordnik.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Api(tags = {"/projects/{projectId}/bids"})
@RestController
public class BidController {

    private static final Logger logger = LoggerFactory.getLogger(BidController.class);

    @Autowired
    IBidService bidService;

    /**
     * POST: url: /projects/{projectId}/bids
     * body: project json
     * @param bid
     * @param response
     * @return
     */
    @RequestMapping(value= "/projects/{projectId}/bids", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createBid(@RequestBody Bid bid,@PathVariable String projectId,@RequestHeader String userId, HttpServletResponse response){
        logger.info("create bid for a project projectId: {}, bid:{}", projectId, bid);
        try {
            if (!StringUtils.isEmpty(userId)) {
                bid.setCreatedBy(userId);
            }

            String bidId = bidService.createBid(bid, projectId);
            response.setStatus(201);

            return String.format("Location: /projects/%s/bids/%s", projectId, bidId);
        }catch (Exception e) {
            e.printStackTrace();
            throw new GetitDonAppException("error-code", e.getMessage());
        }
    }


    /**
     * GET: url: /projects/{projectId}/bids/{id}
     * return Array of bids json
     * @return
     */
    @RequestMapping(method= RequestMethod.GET, value = "/projects/{projectId}/bids", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Bid> getAllBids(@PathVariable String projectId ) {
        logger.info("API- get all bids of a projectId: {}", projectId);
        List<Bid> allBids = bidService.getAllBids(projectId);
        if(allBids == null) {
            return new ArrayList<>();
        }

        return allBids;
    }

    /**
     * GET: url: /projects/{projectId}/bids/{id}
     * return Bid json
     * @param id
     * @return
     */
    @RequestMapping(method= RequestMethod.GET, value = "/projects/{projectId}/bids/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Bid getBid(@PathVariable String projectId, @PathVariable String id) {
        logger.info("get bid by projectId {} and bidId:{}", projectId, id);
        Bid bid = bidService.getBid(projectId, id);
        return bid;
    }


}
