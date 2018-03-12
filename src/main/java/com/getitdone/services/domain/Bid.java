package com.getitdone.services.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Bid {

    @Id
    private String id;

    private BigDecimal bidPrice;

    private String createdBy;


    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
