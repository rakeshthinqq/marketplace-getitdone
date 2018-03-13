Spring boot - embedded mongo, Rest app

**Swagger**

http://localhost:8080/getitdone/swagger-ui.html

**set up**

$ git clone git@github.com:rakeshthinqq/marketplace-getitdone.git
$ mvn clean install spring-boot:run

**Test**

./feed.sh
script contains all curl POST calls to create User, project and Bid

Run as Testng Test:
ProjectBidE2E.java

