package com.getitdone.services.repo;

import com.getitdone.services.domain.Bid;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends MongoRepository<Bid, ObjectId> {

}
