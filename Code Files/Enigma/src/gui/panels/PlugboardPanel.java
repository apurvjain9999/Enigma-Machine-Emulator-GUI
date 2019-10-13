package gui.panels;

/**
 *
 * @author Apurv Jain
 */
import javax.swing.text.BadLocationException;
import javax.swing.text.AttributeSet;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AbstractDocument;
import java.awt.Component;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JPanel;

//Plugboard
public class PlugboardPanel extends JPanel
{
    private JTextField pbField;
    private TitledBorder innerBorder;
    
    public PlugboardPanel() {
        (this.pbField = new JTextField(95)).setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        final Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        this.innerBorder = BorderFactory.createTitledBorder("Plugboard (e.g. AB CD) - Press \"SAVE\" to save the settings");
        this.setBorder(BorderFactory.createCompoundBorder(outerBorder, this.innerBorder));
        this.add(this.pbField);
        ((AbstractDocument)this.pbField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(final FilterBypass fb, final int offset, final int length, final String text, final AttributeSet attrs) throws BadLocationException {
                for (int i = 0; i < text.length(); ++i) {
                    if ((text.charAt(i) < 'a' || text.charAt(i) > 'z') && (text.charAt(i) < 'A' || text.charAt(i) > 'Z') && text.charAt(0) != ' ') {
                        return;
                    }
                }
                super.replace(fb, offset, length, text.toUpperCase(), attrs);
            }
        });
    }
    
    public String getPlugboard() {
        return this.pbField.getText();
    }
    
    public void darkTheme() {
        this.setBackground(new Color(153, 51, 0));
        this.innerBorder.setTitleColor(Color.WHITE);
    }
    
    public void lightTheme() {
        this.setBackground(null);
        this.innerBorder.setTitleColor(null);
    }
}

