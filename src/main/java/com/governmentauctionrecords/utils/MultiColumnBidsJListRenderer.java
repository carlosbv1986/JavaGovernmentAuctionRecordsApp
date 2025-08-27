/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.governmentauctionrecords.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class MultiColumnBidsJListRenderer extends JPanel implements ListCellRenderer<Object> {

    private JLabel[] labels = new JLabel[4];
    private Border focusBorder = UIManager.getBorder("List.focusCellHighlightBorder");
    private Border noFocusBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);

    public MultiColumnBidsJListRenderer() {
        setLayout(new GridLayout(1, 4));
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel();
            labels[i].setOpaque(true); // background color will be visible
            labels[i].setBorder(BorderFactory.createEmptyBorder(1, 3, 1, 3)); // padding
            add(labels[i]);
        }
    }

    @Override
    public Component getListCellRendererComponent(
            JList<?> list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {

        String[] row = (String[]) value;

        // Get default colors
        Color bg = isSelected ? list.getSelectionBackground() : list.getBackground();
        Color fg = isSelected ? list.getSelectionForeground() : list.getForeground();

        setBackground(bg);
        setForeground(fg);
        setBorder(cellHasFocus ? focusBorder : noFocusBorder);

        for (int i = 0; i < labels.length; i++) {
            labels[i].setText(row[i]);
            labels[i].setBackground(bg);
            labels[i].setForeground(fg);
        }

        return this;
    }
}
