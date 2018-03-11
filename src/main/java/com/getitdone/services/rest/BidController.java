package com.getitdone.services.rest;

import com.getitdone.services.domain.Bid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class BidController {

    private static final Logger logger = LoggerFactory.getLogger(BidController.class);

    /**
     * POST: url: /projects/{projectId}/bids
     * body: project json
     * @param bid
     * @param response
     * @return
     */
    @RequestMapping(value= "/projects/{projectId}/bids", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createProject(@RequestBody Bid bid,@PathVariable String projectId,@RequestHeader String userId, HttpServletResponse response){
        logger.info("create bid for a project projectId: {}, bid:{}", projectId, bid);
        //TODO: create bid for projects
        return "Location";
    }

}
