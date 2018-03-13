Spring boot - embedded mongo, Rest app

**set up**

    $ git clone git@github.com:rakeshthinqq/marketplace-getitdone.git
    $ mvn clean install spring-boot:run


**Run feed to create more users, projects and bids**

    ./feed.sh
    script contains all curl POST calls to create User, project and Bid

**Test**

Run as TestNG

    ProjectBidE2ETest.java

**API's**

Swagger url:

    http://localhost:8080/getitdone/swagger-ui.html

Create Project:

    curl -v -H "Content-Type: application/json" -H "userId: 5aa5b2fb1e34f3136e13f067"  \
           -d '{"name":"Logo creation","description":"I need a logo for my blog","createdBy":"userId", "listingPrice": "1000","listingExpiryDate": "15-04-2018 04:30"}'  \
           -X POST http://localhost:8080/getitdone/projects;

    
Get Project by Id

    curl http://localhost:8080/getitdone/projects/5aa8449a9465b61de5aef402

Bid for a Project

    curl -v -H "Content-Type: application/json" -H "userId: 5aa5c9db6e700f177a3483f9"  \
        -d '{"bidPrice":15.5, "comment": "I am interested to take this up"}'
        -X POST http://localhost:8080/getitdone/projects/5aa8449a9465b61de5aef402/bids

Get All open projects

    curl http://localhost:8080/getitdone/projects?status=OPEN
    
Get All bids of project:

    curl http://localhost:8080/getitdone/projects/5aa8449a9465b61de5aef402/bids


**TODO**

    > Pagination  
    > API to accept a bid
    > API to reject a bid
    > API to relist 
    > do more validation on input
    > do mmore error code mapping
    > Authentication


