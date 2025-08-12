/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.governmentauctionrecords.utils;

import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 *
 * @author carlo
 */
public class MultiColumnRenderer extends JPanel implements ListCellRenderer<String> {
    private JLabel col1 = new JLabel("Label 1");
    private JLabel col2 = new JLabel("Label 2");
    private JLabel col3 = new JLabel("Label 3");

    public MultiColumnRenderer() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 50, 0));
        add(col1);
        add(col2);
        add(col3);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        String[] parts = value.split("\t");
        col1.setText(parts.length > 0 ? parts[0] : "");
        col2.setText(parts.length > 1 ? parts[1] : "");
        col3.setText(parts.length > 2 ? parts[2] : "");

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
            col1.setForeground(list.getSelectionForeground());
            col2.setForeground(list.getSelectionForeground());
            col3.setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            col1.setForeground(list.getForeground());
            col2.setForeground(list.getForeground());
            col3.setForeground(list.getForeground());
        }
        
        setOpaque(true);
        return this;
    }
}
