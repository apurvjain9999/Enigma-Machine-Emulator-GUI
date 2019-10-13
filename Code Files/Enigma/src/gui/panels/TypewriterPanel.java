package gui.panels;

/**
 *
 * @author Apurv Jain
 */

import gui.listener.KeyboardListener;
import javax.swing.border.Border;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import gui.listener.TypeListener;
import enigma.Enigma;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.util.concurrent.TimeUnit;

//For TYpewriter Panel
public class TypewriterPanel extends JPanel
{
    private KeyboardPanel keyboardPanel;
    private BulbPanel bulbPanel;
    private JSplitPane splitter;
    private Enigma enigma;
    private TypeListener typeListener;
    
    public TypewriterPanel() {
        this.setLayout(new BorderLayout());
        this.keyboardPanel = new KeyboardPanel();
        this.bulbPanel = new BulbPanel();
        (this.splitter = new JSplitPane(0, this.bulbPanel, this.keyboardPanel)).setResizeWeight(0.5);
        this.splitter.setDividerSize(0);
        this.splitter.setContinuousLayout(true);
        this.splitter.setEnabled(false);
        this.splitter.setBorder(null);
        this.keyboardPanel.setKeyboardListener(new KeyboardListener() {
            @Override
            public void releaseAction(final int c) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500); //Keeping bulb on for atleast 2 seconds
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                TypewriterPanel.this.bulbPanel.bulbOff(c);
                
            }
            
            @Override
            public int pressAction(final char original) {
                final int encoded = TypewriterPanel.this.enigma.type(new StringBuilder().append(original).toString()).charAt(0) - 'A';
                TypewriterPanel.this.bulbPanel.bulbOn(encoded);
                TypewriterPanel.this.bulbPanel.getOutputField().setText(String.valueOf(TypewriterPanel.this.bulbPanel.getOutputField().getText()) + (char)(encoded + 65));
                TypewriterPanel.this.typeListener.typeAction();
                return encoded;
            }
        });
        this.add(this.splitter, "Center");
    }
    
    public void setEnigma(final Enigma enigma) {
        this.enigma = enigma;
    }
    
    public void setTypeListener(final TypeListener typeListener) {
        this.typeListener = typeListener;
    }
    
    public void clear() {
        this.enigma.resetRotation();
        this.typeListener.typeAction();
        this.keyboardPanel.getInputField().setText("");
        this.bulbPanel.getOutputField().setText("");
    }
    
    public String getOutput() {
        return this.bulbPanel.getOutputField().getText();
    }
}

