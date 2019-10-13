package gui.panels;

/**
 *
 * @author Apurv Jain
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.FlowLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.AttributeSet;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AbstractDocument;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import gui.listener.TypeListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import enigma.Enigma;
import gui.lib.JStatic;
import gui.lib.JReflector;
import gui.lib.JRotor;
import java.awt.Color;
import javax.swing.JPanel;

//For the wires Display
public class WiresPanel extends JPanel
{
    private JRotor rotorLeft;
    private JRotor rotorRight;
    private JRotor rotorMiddle;
    private JReflector reflector;
    private JStatic rotorStatic;
    private Enigma enigma;
    private JTextField input;
    private JTextField previous;
    private JLabel output;
    private TypeListener typeListener;
    private JPanel wiresPanel;
    private JPanel textPanel;
    private GridBagConstraints gc;
    
    public WiresPanel() {
        this.setLayout(new GridBagLayout());
        this.wiresPanel = new JPanel();
        this.textPanel = new JPanel();
        this.rotorStatic = new JStatic();
        this.rotorLeft = new JRotor();
        this.rotorMiddle = new JRotor();
        this.rotorRight = new JRotor();
        this.reflector = new JReflector();
        this.input = new JTextField(1);
        this.previous = new JTextField(10);
        this.output = new JLabel("-");
        
        ((AbstractDocument)this.input.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(final FilterBypass fb, final int offset, final int length, String text, final AttributeSet attrs) throws BadLocationException {
                if (text.length() != 1) {
                    return;
                }
                if ((text.charAt(0) < 'a' || text.charAt(0) > 'z') && (text.charAt(0) < 'A' || text.charAt(0) > 'Z')) {
                    return;
                }
                text = text.toUpperCase();
                WiresPanel.this.previous.setText(String.valueOf(WiresPanel.this.previous.getText()) + text);
            }
        });
        this.previous.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(final DocumentEvent e) {
                this.insertUpdate(e);
            }
            
            @Override
            public void insertUpdate(final DocumentEvent e) {
                WiresPanel.this.enigma.resetRotation();
                WiresPanel.this.enigma.type(WiresPanel.this.previous.getText());
                WiresPanel.this.typeListener.typeAction();
                if (WiresPanel.this.previous.getText().length() == 0) {
                    WiresPanel.this.rotorStatic.removeThickLines();
                    WiresPanel.this.rotorRight.removeThickLines();
                    WiresPanel.this.rotorMiddle.removeThickLines();
                    WiresPanel.this.rotorLeft.removeThickLines();
                    WiresPanel.this.reflector.removeThickLines();
                    WiresPanel.this.output.setText("-");
                }
                else {
                    int result = WiresPanel.this.previous.getText().charAt(WiresPanel.this.previous.getText().length() - 1) - 'A';
                    WiresPanel.this.rotorStatic.setThickOutLine(result);
                    if (WiresPanel.this.enigma.getPlugboardOf(result) != -1) {
                        result = WiresPanel.this.enigma.getPlugboardOf(result);
                    }
                    WiresPanel.this.rotorRight.setThickOutLine(result);
                    result = WiresPanel.this.enigma.getRightRotor().getAnOutWire(result);
                    WiresPanel.this.rotorMiddle.setThickOutLine(result);
                    result = WiresPanel.this.enigma.getMiddleRotor().getAnOutWire(result);
                    WiresPanel.this.rotorLeft.setThickOutLine(result);
                    result = WiresPanel.this.enigma.getLeftRotor().getAnOutWire(result);
                    WiresPanel.this.reflector.setThickOutLine(result);
                    result = WiresPanel.this.enigma.getLeftRotor().getAnInWire(WiresPanel.this.enigma.getReflector().getAnOutWire(result));
                    WiresPanel.this.rotorLeft.setThickInLine(result);
                    result = WiresPanel.this.enigma.getMiddleRotor().getAnInWire(result);
                    WiresPanel.this.rotorMiddle.setThickInLine(result);
                    result = WiresPanel.this.enigma.getRightRotor().getAnInWire(result);
                    WiresPanel.this.rotorRight.setThickInLine(result);
                    if (WiresPanel.this.enigma.getPlugboardOf(result) != -1) {
                        result = WiresPanel.this.enigma.getPlugboardOf(result);
                    }
                    WiresPanel.this.rotorStatic.setThickInLine(result);
                    WiresPanel.this.output.setText(new StringBuilder(String.valueOf((char)(result + 65))).toString());
                }
                WiresPanel.this.redraw();
            }
            
            @Override
            public void changedUpdate(final DocumentEvent e) {
            }
        });
        this.wiresPanel.setLayout(new FlowLayout(1, 0, 0));
        this.textPanel.setLayout(new GridBagLayout());
        this.textPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Input text"), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        this.output.setFont(new Font(this.output.getFont().getName(), 1, 20));
        this.previous.setEditable(false);
        this.gc = new GridBagConstraints();
        this.gc.fill = 0;
        this.gc.weightx = 1.0;
        this.gc.insets = new Insets(5, 2, 5, 2);
        this.gc.gridx = 0;
        this.gc.gridy = 0;
        this.gc.gridwidth = 2;
        this.textPanel.add(this.output, this.gc);
        this.gc.gridx = 0;
        this.gc.gridy = 1;
        this.gc.gridwidth = 1;
        this.textPanel.add(this.previous, this.gc);
        this.gc.gridx = 1;
        this.gc.gridy = 1;
        this.textPanel.add(this.input, this.gc);
        this.gc.gridx = 0;
        this.gc.gridy = 2;
        this.gc.gridwidth = 2;
        final JButton reset = new JButton("Clear");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                WiresPanel.this.previous.setText("");
            }
        });
        this.textPanel.add(reset, this.gc);
        this.wiresPanel.add(this.reflector);
        this.wiresPanel.add(this.rotorLeft);
        this.wiresPanel.add(this.rotorMiddle);
        this.wiresPanel.add(this.rotorRight);
        this.wiresPanel.add(this.rotorStatic);
        this.add(this.wiresPanel);
        this.add(this.textPanel);
    }
    
    public void redraw() {
        final int[][] connectionStatic = new int[26][2];
        for (int i = 0; i < 26; ++i) {
            if (this.enigma.getPlugboardOf(i) == -1) {
                connectionStatic[i][0] = (connectionStatic[i][1] = i);
            }
            else {
                connectionStatic[i][1] = i;
                connectionStatic[i][0] = this.enigma.getPlugboardOf(i);
            }
        }
        this.rotorStatic.redraw(connectionStatic);
        this.reflector.redraw(this.enigma.getReflector());
        this.rotorLeft.redraw(this.enigma.getLeftRotor());
        this.rotorMiddle.redraw(this.enigma.getMiddleRotor());
        this.rotorRight.redraw(this.enigma.getRightRotor());
        this.repaint();
        this.revalidate();
    }
    
    public void clear() {
        this.enigma.resetRotation();
        this.typeListener.typeAction();
        this.previous.setText("");
        this.redraw();
    }
    
    public void setTypeListener(final TypeListener typeListener) {
        this.typeListener = typeListener;
    }
    
    public void setEnigma(final Enigma enigma) {
        this.enigma = enigma;
    }
    
    public void updateWires() {
        this.previous.setText("");
        this.redraw();
    }
}

