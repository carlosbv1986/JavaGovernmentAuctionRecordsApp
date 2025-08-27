package com.governmentauctionrecords.daos;

import com.governmentauctionrecords.models.Auction;

import com.governmentauctionrecords.utils.SqlServerDatabaseConnection;

import java.sql.*;

public class AuctionDAO {

    private static Auction mapRow(ResultSet rs) throws SQLException {
        Auction auction = new Auction();
        auction.setId(rs.getInt("Id"));
        auction.setTitle(rs.getString("Title"));
        auction.setDescription(rs.getString("Description"));
        auction.setAuctionDate(rs.getTimestamp("AuctionDate"));

        Timestamp createdAtTs = rs.getTimestamp("CreatedAt");
        System.out.println("Fetched auction CreatedAt: " + createdAtTs);
        auction.setCreatedAt(createdAtTs);
        return auction;
    }

    public static Auction getLatestAuction() throws SQLException {
        String sql = "SELECT TOP 1 * FROM Auction WHERE Title IS NOT NULL ORDER BY CreatedAt DESC";
        try (Connection conn = SqlServerDatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return mapRow(rs);
            }
        }
        return null;
    }

    public static Auction getOldestAuction() throws SQLException {
        String sql = "SELECT TOP 1 * FROM Auction WHERE Title IS NOT NULL ORDER BY CreatedAt ASC";
        try (Connection conn = SqlServerDatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return mapRow(rs);
            }
        }
        return null;
    }

    public static Auction getPreviousAuction(Timestamp currentCreatedAt) throws SQLException {
        System.out.println("getPreviousAuction called with timestamp: " + currentCreatedAt);
        String sql = "SELECT TOP 1 * FROM Auction WHERE CreatedAt < ? AND Title IS NOT NULL ORDER BY CreatedAt DESC";
        try (Connection conn = SqlServerDatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, currentCreatedAt);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Auction a = mapRow(rs);
                    System.out.println("Previous auction found: " + a.getTitle() + " @ " + a.getCreatedAt());
                    return a;
                }
            }
        }
        System.out.println("No previous auction found.");
        return null;
    }

    public static Auction getNextAuction(Timestamp currentCreatedAt) throws SQLException {
        System.out.println("getNextAuction called with timestamp: " + currentCreatedAt);
        String sql = "SELECT TOP 1 * FROM Auction WHERE CreatedAt > ? AND Title IS NOT NULL ORDER BY CreatedAt ASC";
        try (Connection conn = SqlServerDatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, currentCreatedAt);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Auction a = mapRow(rs);
                    System.out.println("Next auction found: " + a.getTitle() + " @ " + a.getCreatedAt());
                    return a;
                }
            }
        }
        System.out.println("No next auction found.");
        return null;
    }

    public static Auction searchAuction(String searchCriteria) throws SQLException {
        if (searchCriteria == null || searchCriteria.trim().isBlank()) {
            return null; // no search term provided
        }

        String sql = "SELECT TOP 1 * FROM Auction "
                + "WHERE Title IS NOT NULL AND LTRIM(RTRIM(Title)) <> '' "
                + "AND (CAST(Id AS VARCHAR) LIKE ? "
                + "OR Title LIKE ?) "
                + "ORDER BY CreatedAt DESC";

        try (Connection conn = SqlServerDatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            String pattern = "%" + searchCriteria.trim() + "%";
            stmt.setString(1, pattern); // search Id
            stmt.setString(2, pattern); // search Title

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null; // no match found
    }
}
