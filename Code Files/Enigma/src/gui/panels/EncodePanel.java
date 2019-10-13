package gui.panels;

/**
 *
 * @author Apurv Jain
 */
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.text.Document;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.AttributeSet;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.Action;
import javax.swing.undo.CannotUndoException;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import java.awt.Toolkit;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import java.awt.Color;
import javax.swing.border.Border;
import java.awt.Component;
import javax.swing.BorderFactory;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import gui.listener.TypeListener;
import enigma.Enigma;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;

//Encode Panel
public class EncodePanel extends JPanel
{
    private JTextArea inputText;
    private JTextArea outputText;
    private JSplitPane splitter;
    private JScrollPane spInput;
    private JScrollPane spOutput;
    private Enigma enigma;
    private TypeListener typeListener;
    
    public EncodePanel() {
        this.setLayout(new GridLayout());
        final Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        this.setBorder(outerBorder);
        this.inputText = new JTextArea();
        this.outputText = new JTextArea();
        this.spInput = new JScrollPane(this.inputText);
        this.spOutput = new JScrollPane(this.outputText);
        this.spInput.setBorder(BorderFactory.createCompoundBorder(outerBorder, BorderFactory.createTitledBorder("Input")));
        this.spInput.setBackground(Color.white);
        this.spOutput.setBorder(BorderFactory.createCompoundBorder(outerBorder, BorderFactory.createTitledBorder("Output")));
        this.spOutput.setBackground(Color.white);
        this.outputText.setEditable(false);
        (this.splitter = new JSplitPane(0, this.spOutput, this.spInput)).setResizeWeight(0.5);
        this.splitter.setDividerSize(10);
        this.splitter.setContinuousLayout(true);
        this.inputText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.outputText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        final UndoManager undoManager = new UndoManager();
        final Document doc = this.inputText.getDocument();
        doc.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(final UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });
        final InputMap im = this.inputText.getInputMap(0);
        final ActionMap am = this.inputText.getActionMap();
        im.put(KeyStroke.getKeyStroke(90, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
        im.put(KeyStroke.getKeyStroke(89, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");
        am.put("Undo", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    }
                }
                catch (CannotUndoException ex) {}
            }
        });
        am.put("Redo", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    if (undoManager.canRedo()) {
                        undoManager.redo();
                    }
                }
                catch (CannotUndoException ex) {}
            }
        });
        this.add(this.splitter);
        ((AbstractDocument)this.inputText.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(final FilterBypass fb, final int offset, final int length, String text, final AttributeSet attrs) throws BadLocationException {
                for (int i = 0; i < text.length(); ++i) {
                    if ((text.charAt(i) < 'a' || text.charAt(i) > 'z') && (text.charAt(i) < 'A' || text.charAt(i) > 'Z') && text.charAt(i) != ' ' && text.charAt(i) != '\n') {
                        return;
                    }
                }
                text = text.toUpperCase();
                super.replace(fb, offset, length, text, attrs);
            }
        });
        this.inputText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(final DocumentEvent e) {
                EncodePanel.this.enigma.resetRotation();
                EncodePanel.this.outputText.setText(EncodePanel.this.enigma.type(EncodePanel.this.inputText.getText()));
                EncodePanel.this.typeListener.typeAction();
            }
            
            @Override
            public void insertUpdate(final DocumentEvent e) {
                EncodePanel.this.enigma.resetRotation();
                EncodePanel.this.outputText.setText(EncodePanel.this.enigma.type(EncodePanel.this.inputText.getText()));
                EncodePanel.this.typeListener.typeAction();
            }
            
            @Override
            public void changedUpdate(final DocumentEvent e) {
            }
        });
    }
    
    public void setEnigma(final Enigma enigma) {
        this.enigma = enigma;
    }
    
    public void refresh() {
        this.inputText.setText(this.inputText.getText());
    }
    
    public void setTypeListener(final TypeListener typeListener) {
        this.typeListener = typeListener;
    }
    
    public void setDefaultText(final String text) {
        this.inputText.setText(text);
    }
    
    public String getOutput() {
        return this.outputText.getText();
    }
}
