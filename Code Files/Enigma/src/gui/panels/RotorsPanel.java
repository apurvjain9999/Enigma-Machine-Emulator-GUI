
package gui.panels;

/**
 *
 * @author Apurv Jain
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Insets;
import javax.swing.border.Border;
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import enigma.Enigma;
import gui.listener.RotorListener;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;

//Rotors Panel
public class RotorsPanel extends JPanel
{
    private GridBagConstraints gc;
    private JComboBox reflectorBox;
    private JComboBox leftRotorBox;
    private JComboBox middleRotorBox;
    private JComboBox rightRotorBox;
    private JComboBox leftRotorStartBox;
    private JComboBox middleRotorStartBox;
    private JComboBox rightRotorStartBox;
    private JComboBox leftRotorRingBox;
    private JComboBox middleRotorRingBox;
    private JComboBox rightRotorRingBox;
    private JButton applyBtn;
    private JTextField leftState;
    private JTextField middleState;
    private JTextField rightState;
    private JPanel reflectorPanel;
    private JPanel leftRotorPanel;
    private JPanel middleRotorPanel;
    private JPanel rightRotorPanel;
    private JPanel stateRotorPanel;
    private String[][] ENIGMA_ROTORS;
    private String[] ENIGMA_REFLECTORS;
    private RotorListener rotorListener;
    private Enigma enigma;
    private JLabel[] rLabels;
    private TitledBorder[] titleBorders;
    
    public RotorsPanel() {
        this.ENIGMA_ROTORS = new String[][] { Enigma.I, Enigma.II, Enigma.III, Enigma.IV, Enigma.V };
        this.ENIGMA_REFLECTORS = new String[] { "EJMZALYXVBWFCRQUONTSPIKHGD", "YRUHQSLDPXNGOKMIEBFZCWVJAT", "FVPJIAOYEDRZXWGCTKUQSBNMHL" };
        this.rLabels = new JLabel[] { new JLabel("Type: "), new JLabel("Type: "), new JLabel("Start: "), new JLabel("Ring: "), new JLabel("Type: "), new JLabel("Start: "), new JLabel("Ring: "), new JLabel("Type: "), new JLabel("Start: "), new JLabel("Ring: "), new JLabel("L"), new JLabel("M"), new JLabel("R") };
        this.titleBorders = new TitledBorder[] { BorderFactory.createTitledBorder("Reflector"), BorderFactory.createTitledBorder("Left Rotor"), BorderFactory.createTitledBorder("Middle Rotor"), BorderFactory.createTitledBorder("Right Rotor"), BorderFactory.createTitledBorder("State") };
        this.setLayout(new GridBagLayout());
        (this.reflectorPanel = new JPanel()).setLayout(new GridBagLayout());
        this.reflectorPanel.setPreferredSize(new Dimension(100, 100));
        (this.leftRotorPanel = new JPanel()).setLayout(new GridBagLayout());
        this.leftRotorPanel.setPreferredSize(new Dimension(220, 100));
        (this.middleRotorPanel = new JPanel()).setLayout(new GridBagLayout());
        this.middleRotorPanel.setPreferredSize(new Dimension(220, 100));
        (this.rightRotorPanel = new JPanel()).setLayout(new GridBagLayout());
        this.rightRotorPanel.setPreferredSize(new Dimension(220, 100));
        (this.stateRotorPanel = new JPanel()).setLayout(new GridBagLayout());
        this.stateRotorPanel.setPreferredSize(new Dimension(130, 100));
        final String[] rotorBoxOption = { "I", "II", "III", "IV", "V" };
        final String[] rotorBoxLetterOption = new String[26];
        for (int i = 0; i < 26; ++i) {
            rotorBoxLetterOption[i] = new StringBuilder(String.valueOf((char)(65 + i))).toString();
        }
        (this.leftState = new JTextField("A", 1)).setEditable(false);
        (this.middleState = new JTextField("A", 1)).setEditable(false);
        (this.rightState = new JTextField("A", 1)).setEditable(false);
        final String[] reflectorBoxOption = { "A", "B", "C" };
        (this.reflectorBox = new JComboBox(reflectorBoxOption)).setSelectedIndex(0);
        (this.leftRotorBox = new JComboBox(rotorBoxOption)).setSelectedIndex(0);
        (this.middleRotorBox = new JComboBox(rotorBoxOption)).setSelectedIndex(1);
        (this.rightRotorBox = new JComboBox(rotorBoxOption)).setSelectedIndex(2);
        (this.leftRotorStartBox = new JComboBox(rotorBoxLetterOption)).setSelectedIndex(0);
        (this.middleRotorStartBox = new JComboBox(rotorBoxLetterOption)).setSelectedIndex(0);
        (this.rightRotorStartBox = new JComboBox(rotorBoxLetterOption)).setSelectedIndex(0);
        (this.leftRotorRingBox = new JComboBox(rotorBoxLetterOption)).setSelectedIndex(0);
        (this.middleRotorRingBox = new JComboBox(rotorBoxLetterOption)).setSelectedIndex(0);
        (this.rightRotorRingBox = new JComboBox(rotorBoxLetterOption)).setSelectedIndex(0);
        (this.applyBtn = new JButton("SAVE", new ImageIcon(this.getClass().getResource("/gui/images/s.png")))).setBorderPainted(false);
        this.applyBtn.setContentAreaFilled(false);
        this.applyBtn.setFocusPainted(false);
        this.applyBtn.setOpaque(false);
        this.applyBtn.setHorizontalTextPosition(0);
        this.applyBtn.setForeground(Color.black);
        this.applyBtn.setPreferredSize(new Dimension(86, 50));
        this.applyBtn.setPressedIcon(new ImageIcon(this.getClass().getResource("/gui/images/s2.png")));
        final Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        this.reflectorPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, this.titleBorders[0]));
        this.leftRotorPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, this.titleBorders[1]));
        this.middleRotorPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, this.titleBorders[2]));
        this.rightRotorPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, this.titleBorders[3]));
        this.stateRotorPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, this.titleBorders[4]));
        this.gc = new GridBagConstraints();
        this.gc.fill = 0;
        this.gc.insets = new Insets(0, 10, 0, 10);
        this.gc.anchor = 18;
        final int y = 0;
        this.gc.weightx = 1.0;
        this.gc.weighty = 0.15;
        this.gridPosition(0, y);
        this.reflectorPanel.add(this.rLabels[0], this.gc);
        this.gridPosition(0, y + 1);
        this.reflectorPanel.add(this.reflectorBox, this.gc);
        this.gridPosition(0, y);
        this.leftRotorPanel.add(this.rLabels[1], this.gc);
        this.gridPosition(0, y + 1);
        this.leftRotorPanel.add(this.leftRotorBox, this.gc);
        this.gridPosition(1, y);
        this.leftRotorPanel.add(this.rLabels[2], this.gc);
        this.gridPosition(1, y + 1);
        this.leftRotorPanel.add(this.leftRotorStartBox, this.gc);
        this.gridPosition(2, y);
        this.leftRotorPanel.add(this.rLabels[3], this.gc);
        this.gridPosition(2, y + 1);
        this.leftRotorPanel.add(this.leftRotorRingBox, this.gc);
        this.gridPosition(0, y);
        this.middleRotorPanel.add(this.rLabels[4], this.gc);
        this.gridPosition(0, y + 1);
        this.middleRotorPanel.add(this.middleRotorBox, this.gc);
        this.gridPosition(1, y);
        this.middleRotorPanel.add(this.rLabels[5], this.gc);
        this.gridPosition(1, y + 1);
        this.middleRotorPanel.add(this.middleRotorStartBox, this.gc);
        this.gridPosition(2, y);
        this.middleRotorPanel.add(this.rLabels[6], this.gc);
        this.gridPosition(2, y + 1);
        this.middleRotorPanel.add(this.middleRotorRingBox, this.gc);
        this.gridPosition(0, y);
        this.rightRotorPanel.add(this.rLabels[7], this.gc);
        this.gridPosition(0, y + 1);
        this.rightRotorPanel.add(this.rightRotorBox, this.gc);
        this.gridPosition(1, y);
        this.rightRotorPanel.add(this.rLabels[8], this.gc);
        this.gridPosition(1, y + 1);
        this.rightRotorPanel.add(this.rightRotorStartBox, this.gc);
        this.gridPosition(2, y);
        this.rightRotorPanel.add(this.rLabels[9], this.gc);
        this.gridPosition(2, y + 1);
        this.rightRotorPanel.add(this.rightRotorRingBox, this.gc);
        this.gridPosition(0, y);
        this.stateRotorPanel.add(this.rLabels[10], this.gc);
        this.gridPosition(0, y + 1);
        this.stateRotorPanel.add(this.leftState, this.gc);
        this.gridPosition(1, y);
        this.stateRotorPanel.add(this.rLabels[11], this.gc);
        this.gridPosition(1, y + 1);
        this.stateRotorPanel.add(this.middleState, this.gc);
        this.gridPosition(2, y);
        this.stateRotorPanel.add(this.rLabels[12], this.gc);
        this.gridPosition(2, y + 1);
        this.stateRotorPanel.add(this.rightState, this.gc);
        this.add(this.reflectorPanel);
        this.add(this.leftRotorPanel);
        this.add(this.middleRotorPanel);
        this.add(this.rightRotorPanel);
        this.add(this.stateRotorPanel);
        this.add(this.applyBtn);
        this.applyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                RotorsPanel.this.rotorListener.configure(RotorsPanel.this.ENIGMA_ROTORS[RotorsPanel.this.leftRotorBox.getSelectedIndex()], RotorsPanel.this.ENIGMA_ROTORS[RotorsPanel.this.middleRotorBox.getSelectedIndex()], RotorsPanel.this.ENIGMA_ROTORS[RotorsPanel.this.rightRotorBox.getSelectedIndex()], (char)(65 + RotorsPanel.this.leftRotorStartBox.getSelectedIndex()), (char)(65 + RotorsPanel.this.middleRotorStartBox.getSelectedIndex()), (char)(65 + RotorsPanel.this.rightRotorStartBox.getSelectedIndex()), (char)(65 + RotorsPanel.this.leftRotorRingBox.getSelectedIndex()), (char)(65 + RotorsPanel.this.middleRotorRingBox.getSelectedIndex()), (char)(65 + RotorsPanel.this.rightRotorRingBox.getSelectedIndex()), RotorsPanel.this.ENIGMA_REFLECTORS[RotorsPanel.this.reflectorBox.getSelectedIndex()]);
                RotorsPanel.this.leftState.setText(new StringBuilder(String.valueOf(RotorsPanel.this.enigma.getLeftRotor().getRotorHead())).toString());
                RotorsPanel.this.middleState.setText(new StringBuilder(String.valueOf(RotorsPanel.this.enigma.getMiddleRotor().getRotorHead())).toString());
                RotorsPanel.this.rightState.setText(new StringBuilder(String.valueOf(RotorsPanel.this.enigma.getRightRotor().getRotorHead())).toString());
            }
        });
        this.reflectorPanel.setOpaque(false);
        this.leftRotorPanel.setOpaque(false);
        this.middleRotorPanel.setOpaque(false);
        this.rightRotorPanel.setOpaque(false);
        this.stateRotorPanel.setOpaque(false);
        this.setPreferredSize(new Dimension(100, 100));
    }
    
    public void setRotorListener(final RotorListener rotorListener) {
        this.rotorListener = rotorListener;
    }
    
    public void setStates(final char l, final char c, final char r) {
        this.leftState.setText(new StringBuilder(String.valueOf(l)).toString());
        this.middleState.setText(new StringBuilder(String.valueOf(c)).toString());
        this.rightState.setText(new StringBuilder(String.valueOf(r)).toString());
    }
    
    public void setEnigma(final Enigma enigma) {
        this.enigma = enigma;
    }
    
    private void gridPosition(final int x, final int y) {
        this.gc.gridx = x;
        this.gc.gridy = y;
    }
    
    public void darkTheme() {
        this.setBackground(Color.DARK_GRAY);
        for (int i = 0; i < this.rLabels.length; ++i) {
            this.rLabels[i].setForeground(Color.WHITE);
        }
        for (int i = 0; i < this.titleBorders.length; ++i) {
            this.titleBorders[i].setTitleColor(Color.WHITE);
        }
    }
    
    public void lightTheme() {
        this.setBackground(null);
        for (int i = 0; i < this.rLabels.length; ++i) {
            this.rLabels[i].setForeground(null);
        }
        for (int i = 0; i < this.titleBorders.length; ++i) {
            this.titleBorders[i].setTitleColor(null);
        }
    }
}

