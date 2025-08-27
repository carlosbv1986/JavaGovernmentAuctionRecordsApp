package com.governmentauctionrecords.daos;

import com.governmentauctionrecords.models.Bid;
import com.governmentauctionrecords.utils.SqlServerDatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BidDAO {

    private static Bid mapRow(ResultSet rs) throws SQLException {
        Bid bid = new Bid();
        bid.setId(rs.getInt("Id"));
        bid.setAuctionId(rs.getInt("AuctionId"));
        bid.setBidderName(rs.getString("BidderName"));
        bid.setBidAmount(rs.getBigDecimal("BidAmount"));
        bid.setBidTime(rs.getTimestamp("BidTime"));
        return bid;
    }

    public static List<Bid> getBidsForAuction(int auctionId) throws SQLException {
        String sql = "SELECT * FROM Bid WHERE AuctionId = ? ORDER BY BidTime ASC";
        List<Bid> bids = new ArrayList<>();

        try (Connection conn = SqlServerDatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, auctionId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bids.add(mapRow(rs));
                }
            }
        }
        return bids;
    }
}
