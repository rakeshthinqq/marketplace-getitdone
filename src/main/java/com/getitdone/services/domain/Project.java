package com.getitdone.services.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Project {
    @Id
    private String id;

    private String name;
    private String description;
    private BigDecimal listingPrice;
    private String createdBy;
    private List<Bid> bids;

    private BigDecimal lowestBidPrice;






    private Date lastDate;
    private String status = STATUS.OPEN.name();

    public Project() {
        this.createdBy = "system";
    }

    public Project(String name, String description, String createdBy) {
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
    }

    public void addBid(Bid bid) {
        if(bids == null) {
            bids = new ArrayList<>();
        }
        bids.add(bid);
    }

    public enum STATUS {
        OPEN, INPROGRESS, CLOSED;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public BigDecimal getListingPrice() {
        return listingPrice;
    }

    public void setListingPrice(BigDecimal listingPrice) {
        this.listingPrice = listingPrice;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Bid> getBids() {
        return bids;
    }
}
