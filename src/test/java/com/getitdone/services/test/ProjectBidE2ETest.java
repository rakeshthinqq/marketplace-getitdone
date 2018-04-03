package com.getitdone.services.test;

import com.getitdone.services.core.impl.ProjectServiceImpl;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class ProjectBidE2ETest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    ResourceLoader resourceLoader;

    private String projctUrl;
    private String bidUrl;

    private JSONObject projectsPayload =  null;

    private JSONObject bidsPayload = null;

    private static String sellerId = "12345";
    private static String buyerId = "6789";

    private static String[] bidPrice = new String[]{"980.5", "750", "999"};



    public ProjectBidE2ETest() throws IOException {
    }

    @BeforeTest
    public void createProjectAndBid() throws IOException {
        projectsPayload = new JSONObject(IOUtils.toString(
                this.getClass().getResourceAsStream("/project.json"), "UTF-8"
        ));

        bidsPayload = new JSONObject(IOUtils.toString(
                this.getClass().getResourceAsStream("/bid.json"), "UTF-8"
        ));

        setProjectsPath();
        String projctPath = creatProject();
        setBasePath(projctPath);
        createBids(bidPrice[0]);
        createBids(bidPrice[1]);
        createBids(bidPrice[2]);


    }

    private String creatProject() {
        String projectUrl = RestAssured.given().log().all()
                .content(projectsPayload.toString())
                .contentType("application/json")
                .header("userId", sellerId)
                .post().then().statusCode(201).extract().body().asString();
        logger.info("ProjectUrl: {}",projectUrl);
        Assert.assertTrue(StringUtils.startsWith(projectUrl, "Location:"));

        projctUrl = projectUrl.replaceAll("Location:", "").trim();

        setBasePath(projctUrl);

        JsonPath jsonPath = RestAssured.given().log().all()
                .get().then().statusCode(200).extract().body().jsonPath();

        Assert.assertEquals(jsonPath.getString("description"), projectsPayload.getString("description"), "description doesn't match with input");
        Assert.assertEquals(jsonPath.getString("createdBy"), sellerId, "createdBy doesn't match with sellerId");
        return projctUrl;
    }

    private void createBids(String bidPice) {

        bidsPayload.put("bidPrice", bidPice);

        String bidsUrl = RestAssured.given().log().all()
                .content(bidsPayload.toString())
                .contentType("application/json")
                .header("userId", sellerId)
                .post("/bids").then().extract().body().asString();
        logger.info("Base Url: {}", bidsUrl);

        bidUrl = bidsUrl.replaceAll("Location:", "").trim();
        Assert.assertTrue(StringUtils.startsWith(bidsUrl,"Location:"), "new resource location does not exist in POST response");
    }

    @Test
    public void validateBids(){
        setBasePath(projctUrl+"/bids");

        JsonPath jsonPath = RestAssured.given().log().all()
                .get().then().statusCode(200).extract().body().jsonPath();

        logger.info("project response: {}", jsonPath.prettify());

        Assert.assertTrue(jsonPath.prettify().contains(bidPrice[0]), "bidPrice does not exist in bid response");
        Assert.assertTrue(jsonPath.prettify().contains(bidPrice[1]), "bidPrice does not exist in bid response");
        Assert.assertTrue(jsonPath.prettify().contains(bidPrice[2]), "bidPrice does not exist in bid response");
    }

    @Test
    public void validateLowestBid(){
        setBasePath(projctUrl);

        JsonPath jsonPath = RestAssured.given().log().all()
                .get().then().statusCode(200).extract().body().jsonPath();

        Assert.assertEquals(jsonPath.get("lowestBidPrice").toString(), bidPrice[1], "lowestBidPrice does not match with expected");
    }

    @Test
    public void validateListingExpiry(){

        setBasePath(projctUrl);

        JsonPath jsonPath = RestAssured.given().log().all()
                .get().then().statusCode(200).extract().body().jsonPath();

        Assert.assertEquals(jsonPath.get("status"), "OPEN", "project status should be OPEN");


        projectsPayload.put("listingExpiryDate", "11-03-2018");
        RestAssured.given().log().all()
                .content(projectsPayload.toString())
                .contentType("application/json")
                .header("userId", sellerId)
                .patch().then().statusCode(200);

        setBasePath(projctUrl);

        jsonPath = RestAssured.given().log().all()
                .get().then().statusCode(200).extract().body().jsonPath();
        Assert.assertEquals(jsonPath.get("status"), "CLOSED", "project status should be CLOSED");
    }

    @Test
    public void setLowestBids() {
        setBasePath(projctUrl);
        bidsPayload.put("bidPrice", 100);
        bidsPayload.put("cutOffBid", 50);
        bidsPayload.put("comment", "bid1");

        String bidsUrl = RestAssured.given().log().all()
                .content(bidsPayload.toString())
                .contentType("application/json")
                .header("userId", sellerId)
                .post("/bids").then().statusCode(201).extract().body().asString();
        logger.info("Base Url: {}", bidsUrl);

        bidUrl = bidsUrl.replaceAll("Location:", "").trim();
        Assert.assertTrue(StringUtils.startsWith(bidsUrl,"Location:"), "new resource location does not exist in POST response");

        //create new Bid with

        bidsPayload.put("bidPrice", 90);
        bidsPayload.put("comment", "bid2");

        bidsUrl = RestAssured.given().log().all()
                .content(bidsPayload.toString())
                .contentType("application/json")
                .header("userId", sellerId)
                .post("/bids").then().statusCode(201).extract().body().asString();
        logger.info("Base Url: {}", bidsUrl);

        bidUrl = bidsUrl.replaceAll("Location:", "").trim();

        setBasePath(projctUrl);

        JsonPath jsonPath = RestAssured.given().log().all()
                .get().then().statusCode(200).extract().body().jsonPath();

        Assert.assertEquals(jsonPath.get("lowestBidPrice").toString(), 89, "lowestBidPrice does not match with expected");
    }

}
