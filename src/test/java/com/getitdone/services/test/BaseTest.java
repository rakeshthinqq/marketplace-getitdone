package com.getitdone.services.test;

import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeTest;


public class BaseTest {

    @BeforeTest
    public void setUpBaseUri(){
        RestAssured.baseURI = "http://localhost:8080/getitdone/";
    }

    public void setProjectsPath(){
        RestAssured.basePath = "projects";
    }

    public void setBasePath(String projectPath){
        RestAssured.basePath = projectPath;
    }


}
