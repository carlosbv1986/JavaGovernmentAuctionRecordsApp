/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.governmentauctionrecords.utils;

import com.governmentauctionrecords.models.Auction;
import java.awt.*;
import java.awt.print.*;

/**
 *
 * @author carlo
 */
public class AuctionRecordPrinter implements Printable {

    private final Auction auction;

    public AuctionRecordPrinter(Auction auction) {
        this.auction = auction;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {  // We only print one page
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        // Translate to printable area (margins)
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        int y = 20; // start position for text

        g2d.setFont(new Font("Serif", Font.BOLD, 14));
        g2d.drawString("Auction Details", 250, y);
        y += 30;

        g2d.setFont(new Font("Serif", Font.PLAIN, 12));

        g2d.drawString("Title: " + auction.getTitle(), 10, y);
        y += 20;

        g2d.drawString("Description: " + auction.getDescription(), 10, y);
        y += 20;

        g2d.drawString("Auction Date: " + auction.getAuctionDate(), 10, y);
        y += 20;

        g2d.drawString("Created At: " + auction.getCreatedAt(), 10, y);
        y += 20;

        g2d.drawString("Winning Bid Amount: " + auction.getWinningBidAmount(), 10, y);
        y += 20;

        g2d.drawString("Winning Bidder Name: " + auction.getWinningBidderName(), 10, y);
        y += 20;

        return PAGE_EXISTS;
    }
}
