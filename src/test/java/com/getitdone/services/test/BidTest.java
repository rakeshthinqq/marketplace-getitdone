package com.getitdone.services.test;

import com.getitdone.services.core.IBidService;
import com.getitdone.services.core.impl.ProjectServiceImpl;
import com.getitdone.services.domain.Bid;
import com.getitdone.services.domain.Project;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigDecimal;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BidTest extends AbstractTestNGSpringContextTests {

    @Autowired
    ProjectServiceImpl projectService;

    @Autowired
    IBidService bidService;

    private JSONObject bidsPayload = null;


    @BeforeTest
    public void setup() throws IOException {
        bidsPayload = new JSONObject(IOUtils.toString(
                this.getClass().getResourceAsStream("/bid.json"), "UTF-8"
        ));

        bidsPayload.put("bidPrice", 100);
        bidsPayload.put("cutOffBid", 50);
        bidsPayload.put("comment", "bid1");



    }

    @Test
    public void testLowestBidTrigger(){

        Project project = new Project();
        project.setName("newProject");

        String pid = projectService.createProject(project);
        Bid bid = new Bid();
        bid.setBidPrice(new BigDecimal(100));
        bid.setCutOffBid(new BigDecimal(50));
        bid.setComment("first Bid");
        String newBId = bidService.createBid(bid, pid);

        Project savedProject = projectService.getProject(pid);
        Assert.assertEquals("100", savedProject.getLowestBidPrice().toString());
        Assert.assertEquals(1, savedProject.getBids().size());

        Bid newBid = new Bid();
        newBid.setBidPrice(new BigDecimal(90));
        newBid.setComment("2nd bid");
        String newBidId = bidService.createBid(newBid, pid);

        savedProject = projectService.getProject(pid);
        Assert.assertEquals(3, savedProject.getBids().size());
        Assert.assertEquals(89, savedProject.getLowestBidPrice());
    }
}
