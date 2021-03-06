package com.getitdone.services.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Project {
    @Id
    private String id;

    private String name;
    private String description;
    private BigDecimal listingPrice;

    @ApiModelProperty(hidden = true)
    private String createdBy;

    private Set<Map<String, String>> links;
    private BigDecimal lowestBidPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date listingExpiryDate;

    private String status = STATUS.OPEN.name();

    @JsonIgnore
    private List<Bid> bids;

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
        OPEN, CLOSED, INPROGRESS, COMPLETED;
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

    public Date getListingExpiryDate() {
        return listingExpiryDate;
    }

    public void setListingExpiryDate(Date listingExpiryDate) {
        this.listingExpiryDate = listingExpiryDate;
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

    public Set<Map<String,String>> getLinks() {
        return links;
    }

    public void addLink(Map<String, String> link) {
        if(links == null) {
            links = new HashSet<>();
        }
        links.add(link);
    }

    public void setLowestBidPrice(BigDecimal lowestBidPrice) {
        this.lowestBidPrice = lowestBidPrice;
    }

    public BigDecimal getLowestBidPrice() {
        return lowestBidPrice;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", listingPrice=" + listingPrice +
                ", createdBy='" + createdBy + '\'' +
                ", links=" + links +
                ", bids=" + bids +
                ", lowestBidPrice=" + lowestBidPrice +
                ", listingExpiryDate=" + listingExpiryDate +
                ", status='" + status + '\'' +
                '}';
    }
}
