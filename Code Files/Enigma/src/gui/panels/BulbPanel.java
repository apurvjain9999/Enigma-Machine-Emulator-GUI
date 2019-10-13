package gui.panels;

/**
 *
 * @author Apurv Jain
 */

import java.awt.Component;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JPanel;

//Bulb Panel
public class BulbPanel extends JPanel
{
    private JLabel[] alphaBulbs;
    private GridBagConstraints gc;
    private JPanel boardPanel;
    private JPanel textPanel;
    private JTextField outputField;
    
    public BulbPanel() {
        this.alphaBulbs = new JLabel[26];
        this.boardPanel = new JPanel();
        this.textPanel = new JPanel();
        this.setLayout(new BorderLayout());
        this.boardPanel.setLayout(new GridBagLayout());
        (this.outputField = new JTextField(95)).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.outputField.setEditable(false);
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.textPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        for (int i = 0; i < 26; ++i) {
            (this.alphaBulbs[i] = new JLabel("<html><b>" + (char)(65 + i) + "</b></html>", new ImageIcon(this.getClass().getResource("/gui/images/button_bulb.png")), 0)).setOpaque(false);
            this.alphaBulbs[i].setHorizontalTextPosition(0);
            this.alphaBulbs[i].setForeground(Color.GRAY);
            this.alphaBulbs[i].setPreferredSize(new Dimension(50, 50));
            this.alphaBulbs[i].setFont(new Font(this.getFont().getFontName(), 0, this.getFont().getSize() + 2));
        }
        this.gc = new GridBagConstraints();
        this.gc.fill = 0;
        this.gc.weightx = 1.0;
        this.gc.weighty = 0.15;
        final String row1 = "QWERTZUIO";
        for (int j = 0; j < 9; ++j) {
            this.gc.gridx = j * 2;
            this.gc.gridy = 0;
            this.boardPanel.add(this.alphaBulbs[row1.charAt(j) - 'A'], this.gc);
        }
        final String row2 = "ASDFGHJK";
        for (int k = 0; k < 8; ++k) {
            this.gc.gridx = k * 2 + 1;
            this.gc.gridy = 1;
            this.boardPanel.add(this.alphaBulbs[row2.charAt(k) - 'A'], this.gc);
        }
        final String row3 = "PYXCVBNML";
        for (int l = 0; l < 9; ++l) {
            this.gc.gridx = l * 2;
            this.gc.gridy = 2;
            this.boardPanel.add(this.alphaBulbs[row3.charAt(l) - 'A'], this.gc);
        }
        this.textPanel.add(this.outputField);
        this.setBackground(new Color(153, 51, 0));
        this.boardPanel.setOpaque(false);
        this.textPanel.setOpaque(false);
        this.outputField.setBackground(null);
        this.outputField.setForeground(Color.WHITE);
        this.outputField.setOpaque(false);
        this.add(this.boardPanel, "Center");
        this.add(this.textPanel, "North");
    }
    
    //Bulb will On for the encoded alphabet
    public void bulbOn(final int c) {
        this.alphaBulbs[c].setForeground(Color.YELLOW);
    }
    
    public void bulbOff(final int c) {
        this.alphaBulbs[c].setForeground(Color.GRAY);
    }
    
    public JTextField getOutputField() {
        return this.outputField;
    }
}
