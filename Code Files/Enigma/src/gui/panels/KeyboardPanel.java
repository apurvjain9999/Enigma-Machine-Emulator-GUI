package gui.panels;

/**
 *
 * @author Apurv Jain
 */

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import gui.listener.KeyboardListener;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JPanel;

//Keyboard Panel
public class KeyboardPanel extends JPanel
{
    private JButton[] alphaButtons;
    private GridBagConstraints gc;
    private JPanel boardPanel;
    private JPanel textPanel;
    private JTextField inputField;
    private KeyboardListener keyboardListener;
    
    public KeyboardPanel() {
        this.alphaButtons = new JButton[26];
        this.boardPanel = new JPanel();
        this.textPanel = new JPanel();
        this.setLayout(new BorderLayout());
        this.boardPanel.setLayout(new GridBagLayout());
        (this.inputField = new JTextField(95)).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.inputField.setEditable(false);
        for (int i = 0; i < 26; ++i) {
            (this.alphaButtons[i] = new JButton(new StringBuilder().append((char)(65 + i)).toString(), new ImageIcon(this.getClass().getResource("/gui/images/button.png")))).setBorderPainted(false);
            this.alphaButtons[i].setContentAreaFilled(false);
            this.alphaButtons[i].setFocusPainted(false);
            this.alphaButtons[i].setOpaque(false);
            this.alphaButtons[i].setHorizontalTextPosition(0);
            //this.alphaButtons[i].setBackground(Color.white);
            this.alphaButtons[i].setForeground(Color.black);
            this.alphaButtons[i].setPreferredSize(new Dimension(50, 50));
            this.alphaButtons[i].setPressedIcon(new ImageIcon(this.getClass().getResource("/gui/images/button_clicked.png")));
            final int currentID = i;
            this.alphaButtons[i].addMouseListener(new MouseListener() {
                int lastLetter;
                
                @Override
                public void mouseReleased(final MouseEvent e) {
                    KeyboardPanel.this.keyboardListener.releaseAction(this.lastLetter);
                }
                
                @Override
                public void mousePressed(final MouseEvent e) {
                    KeyboardPanel.this.inputField.setText(String.valueOf(KeyboardPanel.this.inputField.getText()) + (char)(65 + currentID));
                    this.lastLetter = KeyboardPanel.this.keyboardListener.pressAction((char)(65 + currentID));
                }
                
                @Override
                public void mouseExited(final MouseEvent e) {
                }
                
                @Override
                public void mouseEntered(final MouseEvent e) {
                }
                
                @Override
                public void mouseClicked(final MouseEvent e) {
                }
            });
        }
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.textPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        this.gc = new GridBagConstraints();
        this.gc.fill = 0;
        this.gc.weightx = 1.0;
        this.gc.weighty = 0.15;
        final String row1 = "QWERTZUIO";
        for (int j = 0; j < 9; ++j) {
            this.gc.gridx = j * 2;
            this.gc.gridy = 0;
            this.boardPanel.add(this.alphaButtons[row1.charAt(j) - 'A'], this.gc);
        }
        final String row2 = "ASDFGHJK";
        for (int k = 0; k < 8; ++k) {
            this.gc.gridx = k * 2 + 1;
            this.gc.gridy = 1;
            this.boardPanel.add(this.alphaButtons[row2.charAt(k) - 'A'], this.gc);
        }
        final String row3 = "PYXCVBNML";
        for (int l = 0; l < 9; ++l) {
            this.gc.gridx = l * 2;
            this.gc.gridy = 2;
            this.boardPanel.add(this.alphaButtons[row3.charAt(l) - 'A'], this.gc);
        }
        this.textPanel.add(this.inputField);
        this.setBackground(new Color(153, 51, 0));
        this.boardPanel.setOpaque(false);
        this.textPanel.setOpaque(false);
        this.inputField.setBackground(null);
        this.inputField.setOpaque(false);
        this.inputField.setForeground(Color.WHITE);
        this.add(this.boardPanel, "Center");
        this.add(this.textPanel, "North");
    }
    
    public void setKeyboardListener(final KeyboardListener keyboardListener) {
        this.keyboardListener = keyboardListener;
    }
    
    public JTextField getInputField() {
        return this.inputField;
    }
}
