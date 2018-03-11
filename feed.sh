curl -v -H "Content-Type: application/json" -X POST -d '{"firstName":"Dev","lastName":"Dev","email":"Dev@gmai.com"}' http://localhost:8080/getitdone/users;
curl -v -H "Content-Type: application/json" -X POST -d '{"firstName":"ben","lastName":"ben","email":"ben@gmai.com"}' http://localhost:8080/getitdone/users;
curl -v -H "Content-Type: application/json" -X POST -d '{"firstName":"ken","lastName":"ben","email":"ben@gmai.com"}' http://localhost:8080/getitdone/users;
curl -v -H "Content-Type: application/json" -X POST -d '{"firstName":"Den","lastName":"Den","email":"Den@gmai.com"}' http://localhost:8080/getitdone/users;
curl -v -H "Content-Type: application/json" -H "userId: 5aa5b2fb1e34f3136e13f067"  -d '{"name":"Log creation","description":"I need a log for my blog","createdBy":"12345"}' -X POST http://localhost:8080/getitdone/projects;
curl -v -H "Content-Type: application/json" -H "userId: 5aa5b2fb1e34f3136e13f067"  -d '{"status":"closed","name":"new slenium automation","description":"test my blog","createdBy":"12345"}' -X POST http://localhost:8080/getitdone/projects;
curl -v -H "Content-Type: application/json" -H "userId: 5aa5b2fb1e34f3136e13f067"  -d '{"status":"open","name":"I need a ecommerce app","description":"need to help to create catalog in shopify","createdBy":"12345"}' -X POST http://localhost:8080/getitdone/projects;
curl -v -H "Content-Type: application/json" -H "userId: 5aa5b2fb1e34f3136e13f067"  -d '{"bidPrice":100}' -X POST http://localhost:8080/getitdone/projects/5aa5b3361e34f3136e13f06a/bids;


