package com.getitdone.services.core;

import com.getitdone.services.domain.Bid;

import java.math.BigDecimal;
import java.util.List;

public interface IBidService {

    String createBid(Bid bid, String projectId);
    Bid getBid(String projectId, String id);
    List<Bid> getAllBids(String projectId);

    void cloneBid(String projectId, Bid bid, BigDecimal price);
}
