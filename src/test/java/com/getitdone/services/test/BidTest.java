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
        bidService.createBid(bid, pid);

        Project savedProject = projectService.getProject(pid);
        Assert.assertEquals(savedProject.getLowestBidPrice().toString(), "100");
        Assert.assertEquals(savedProject.getBids().size(),1);

        Bid bid_2 = new Bid();
        bid_2.setBidPrice(new BigDecimal(90));
        bid_2.setComment("2nd bid");
        bidService.createBid(bid_2, pid);

        savedProject = projectService.getProject(pid);
        Assert.assertEquals(savedProject.getBids().size(), 3);
        Assert.assertEquals(savedProject.getLowestBidPrice().toString(),"89");


        Bid bid_4 = new Bid();
        bid_4.setBidPrice(new BigDecimal(95));
        bidService.createBid(bid_4, pid);

        savedProject = projectService.getProject(pid);
        Assert.assertEquals(savedProject.getBids().size(), 4);

        Bid fifthBid = new Bid();
        fifthBid.setBidPrice(new BigDecimal(87));
        bidService.createBid(fifthBid, pid);

        savedProject = projectService.getProject(pid);
        Assert.assertEquals( savedProject.getBids().size(), 6);
        Assert.assertEquals(savedProject.getLowestBidPrice().toString(),"86");







    }
}
