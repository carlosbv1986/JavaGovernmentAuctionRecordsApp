/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.governmentauctionrecords.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Auction {
    private int id;
    private String title;
    private String description;
    private Timestamp auctionDate;
    private Timestamp createdAt;

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Timestamp getAuctionDate() { return auctionDate; }
    public void setAuctionDate(Timestamp auctionDate) { this.auctionDate = auctionDate; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
