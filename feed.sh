curl -v -H "Content-Type: application/json" -X POST -d '{"firstName":"Dev","lastName":"Dev","email":"Dev@gmai.com"}' http://localhost:8080/getitdone/users;
curl -v -H "Content-Type: application/json" -X POST -d '{"firstName":"ben","lastName":"ben","email":"ben@gmai.com"}' http://localhost:8080/getitdone/users;
curl -v -H "Content-Type: application/json" -X POST -d '{"firstName":"ken","lastName":"ben","email":"ben@gmai.com"}' http://localhost:8080/getitdone/users;
curl -v -H "Content-Type: application/json" -X POST -d '{"firstName":"Den","lastName":"Den","email":"Den@gmai.com"}' http://localhost:8080/getitdone/users;
curl -v -H "Content-Type: application/json" -H "userId: 5aa5b2fb1e34f3136e13f067"  -d '{"name":"Logo creation","description":"I need a logo for my blog","createdBy":"userId", "listingPrice": "1000","listingExpiryDate": "15-04-2018 04:30"}' -X POST http://localhost:8080/getitdone/projects;
curl -v -H "Content-Type: application/json" -H "userId: 5aa5b2fb1e34f3136e13f067"  -d '{"name":"new selenium automation","description":"Need a automation test suite for my app","createdBy":"userId", "listingPrice": "1000","listingExpiryDate": "15-03-2018 04:30"}' -X POST http://localhost:8080/getitdone/projects;
curl -v -H "Content-Type: application/json" -H "userId: 5aa5b2fb1e34f3136e13f067"  -d '{"name":"I need a ecommerce app","description":"need help to create catalog in shoppify","createdBy":"userId", "listingPrice": "1000","listingExpiryDate": "15-03-2018 04:30"}' -X POST http://localhost:8080/getitdone/projects;
curl -v -H "Content-Type: application/json" -H "userId: 5aa5b2fb1e34f3136e13f067"  -d '{"name":"create a catalog","description":"I need a catalog for my ecommerce app", "listingPrice": "1000", "listingExpiryDate": "15-03-2018 04:30"}' -X POST http://localhost:8080/getitdone/projects;
curl -v -H "Content-Type: application/json" -H "userId: 5aa5c9db6e700f177a3483f9"  -d '{"bidPrice":100, "comment": "I am interested to take this up"}' -X POST http://localhost:8080/getitdone/projects/5aa79493d88451698c90f7f6/bids;
curl -v -H "Content-Type: application/json" -H "userId: 5aa5c9db6e700f177a3483f9"  -d '{"bidPrice":10}' -X POST http://localhost:8080/getitdone/projects/5aa79493d88451698c90f7f6/bids;
curl -v -H "Content-Type: application/json" -H "userId: 5aa5c9db6e700f177a3483f9"  -d '{"bidPrice":1000}' -X POST http://localhost:8080/getitdone/projects/5aa79493d88451698c90f7f6/bids;
curl -v -H "Content-Type: application/json" -H "userId: 5aa5c9db6e700f177a3483f9"  -d '{"bidPrice":4.9}' -X POST http://localhost:8080/getitdone/projects/5aa79493d88451698c90f7f6/bids;


