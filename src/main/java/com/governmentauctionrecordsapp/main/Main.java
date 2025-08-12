package com.governmentauctionrecordsapp.main;

import com.governmentauctionrecords.forms.MainApplicationForm;
import java.awt.Font;
import java.util.Enumeration;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author carlos
 */
public class Main {

    public static void setUIFont(FontUIResource baseFont) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                if ("Label.font".equals(key)) {
                    // Make labels bold
                    Font boldFont = baseFont.deriveFont(Font.BOLD);
                    UIManager.put(key, new FontUIResource(boldFont));
                } else {
                    // Everything else uses the base font
                    UIManager.put(key, baseFont);
                }
            }
        }
    }

    public static void main(String args[]) {
        /* Set the Windows Classic look and feel
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows Classic".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            // Set font for all text
            Font customFont = new Font("Dialog", Font.PLAIN, 12);
            setUIFont(new FontUIResource(customFont));

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainApplicationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainApplicationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainApplicationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainApplicationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainApplicationForm().setVisible(true);
            }
        });
    }
}
