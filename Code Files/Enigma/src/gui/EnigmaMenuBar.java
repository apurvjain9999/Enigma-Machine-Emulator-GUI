package gui;

/**
 *
 * @author Apurv Jain
 */
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import java.awt.Component;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFrame;
import gui.listener.EnigmaMenuListener;
import javax.swing.JFileChooser;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import javax.swing.JMenuBar;

//Menu Bar
public class EnigmaMenuBar
{
    private JMenuBar menuBar;
    private File lastFile;
    private Scanner fileInputReader;
    private PrintWriter fileOutputWriter;
    private JFileChooser fileChooser;
    private String FILE_EXTENSION;
    private EnigmaMenuListener emlistener;
    
    public EnigmaMenuBar(final JFrame frame) {
        this.FILE_EXTENSION = "txt";
        this.menuBar = new JMenuBar();
        (this.fileChooser = new JFileChooser()).addChoosableFileFilter(new FileFilter() {
            @Override
            public String getDescription() {
                return EnigmaMenuBar.this.FILE_EXTENSION;
            }
            
            @Override
            public boolean accept(final File file) {
                final String[] extentions = { EnigmaMenuBar.this.FILE_EXTENSION };
                if (file.isDirectory()) {
                    return true;
                }
                final String path = file.getAbsolutePath().toLowerCase();
                for (int i = 0, n = extentions.length; i < n; ++i) {
                    final String extension = extentions[i];
                    if (path.endsWith(extension) && path.charAt(path.length() - extension.length() - 1) == '.') {
                        return true;
                    }
                }
                return false;
            }
        });
        //File Menu
        final JMenu file = new JMenu("File");
        final JMenuItem importTxt = new JMenuItem("Import text");
        final JMenuItem exportTxt = new JMenuItem("Export text");
        final JMenuItem restart = new JMenuItem("Restart");
        final JMenuItem exit = new JMenuItem("Exit");
        file.add(importTxt);
        file.add(exportTxt);
        file.add(restart);
        file.add(exit);
        
        //Display Menu
        final JMenu display = new JMenu("Display");
        final ButtonGroup displayGroup = new ButtonGroup();
        final JRadioButtonMenuItem textBox = new JRadioButtonMenuItem("Text box");
        final JRadioButtonMenuItem keyboard = new JRadioButtonMenuItem("Keyboard");
        final JRadioButtonMenuItem wiresConnection = new JRadioButtonMenuItem("Wires connection");
        textBox.setSelected(true);
        displayGroup.add(textBox);
        displayGroup.add(keyboard);
        displayGroup.add(wiresConnection);
        display.add(textBox);
        display.add(keyboard);
        display.add(wiresConnection);
        this.menuBar.add(file);
        this.menuBar.add(display);
        keyboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                EnigmaMenuBar.this.emlistener.keyboardDisplay();
            }
        });
        textBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                EnigmaMenuBar.this.emlistener.textBoxDisplay();
            }
        });
        wiresConnection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                EnigmaMenuBar.this.emlistener.wiresConnectionDisplay();
            }
        });
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                EnigmaMenuBar.this.emlistener.restart();
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.exit(1);
            }
        });
        importTxt.setAccelerator(KeyStroke.getKeyStroke(73, 2));
        importTxt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                if (EnigmaMenuBar.this.lastFile != null) {
                    EnigmaMenuBar.this.fileChooser.setCurrentDirectory(EnigmaMenuBar.this.lastFile);
                }
                if (EnigmaMenuBar.this.fileChooser.showOpenDialog(frame) == 1) {
                    return;
                }
                EnigmaMenuBar.access$4(EnigmaMenuBar.this, EnigmaMenuBar.this.fileChooser.getSelectedFile());
                if (EnigmaMenuBar.this.lastFile != null) {
                    if (EnigmaMenuBar.this.getExtension(EnigmaMenuBar.this.lastFile).equalsIgnoreCase(EnigmaMenuBar.this.FILE_EXTENSION)) {
                        try {
                            EnigmaMenuBar.access$7(EnigmaMenuBar.this, new Scanner(EnigmaMenuBar.this.lastFile));
                            String text = "";
                            while (EnigmaMenuBar.this.fileInputReader.hasNextLine()) {
                                text = String.valueOf(text) + EnigmaMenuBar.this.fileInputReader.nextLine() + "\n";
                            }
                            EnigmaMenuBar.this.emlistener.importFile(text);
                        }
                        catch (FileNotFoundException e) {
                            JOptionPane.showMessageDialog(frame, "An error occurred while loading the file.");
                            return;
                        }
                        finally {
                            if (EnigmaMenuBar.this.fileInputReader != null) {
                                EnigmaMenuBar.this.fileInputReader.close();
                            }
                        }
                        if (EnigmaMenuBar.this.fileInputReader != null) {
                            EnigmaMenuBar.this.fileInputReader.close();
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "Unsupported file input.");
                    }
                }
            }
        });
        exportTxt.setAccelerator(KeyStroke.getKeyStroke(69, 2));
        exportTxt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                final String text = EnigmaMenuBar.this.emlistener.exportFile();
                if (text.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "You can't export an empty output");
                    return;
                }
                if (text.equals("-1")) {
                    JOptionPane.showMessageDialog(frame, "You can't export a text in this display");
                    return;
                }
                if (EnigmaMenuBar.this.lastFile != null) {
                    EnigmaMenuBar.this.fileChooser.setCurrentDirectory(EnigmaMenuBar.this.lastFile);
                }
                if (EnigmaMenuBar.this.fileChooser.showSaveDialog(frame) == 1) {
                    return;
                }
                EnigmaMenuBar.access$4(EnigmaMenuBar.this, EnigmaMenuBar.this.fileChooser.getSelectedFile());
                if (EnigmaMenuBar.this.lastFile != null) {
                    if (EnigmaMenuBar.this.lastFile.exists() && JOptionPane.showConfirmDialog(frame, "Would you like to overwrite the existing file ?", "Overwite existing file", 0) == 2) {
                        return;
                    }
                    try {
                        if (EnigmaMenuBar.this.getExtension(EnigmaMenuBar.this.lastFile).equalsIgnoreCase(EnigmaMenuBar.this.FILE_EXTENSION)) {
                            EnigmaMenuBar.access$9(EnigmaMenuBar.this, new PrintWriter(EnigmaMenuBar.this.lastFile.getAbsolutePath()));
                        }
                        else {
                            EnigmaMenuBar.access$9(EnigmaMenuBar.this, new PrintWriter(String.valueOf(EnigmaMenuBar.this.lastFile.getAbsolutePath()) + "." + EnigmaMenuBar.this.FILE_EXTENSION));
                        }
                        EnigmaMenuBar.this.fileOutputWriter.print(EnigmaMenuBar.this.emlistener.exportFile());
                        JOptionPane.showMessageDialog(frame, "File output exported successfully");
                    }
                    catch (FileNotFoundException e) {
                        JOptionPane.showMessageDialog(frame, "An error occured while exporting the file");
                        return;
                    }
                    finally {
                        if (EnigmaMenuBar.this.fileOutputWriter != null) {
                            EnigmaMenuBar.this.fileOutputWriter.close();
                        }
                    }
                    if (EnigmaMenuBar.this.fileOutputWriter != null) {
                        EnigmaMenuBar.this.fileOutputWriter.close();
                    }
                }
            }
        });
    }
    
    public JMenuBar getMenuBar() {
        return this.menuBar;
    }
    
    private String getExtension(final File f) {
        String ext = "";
        final String s = f.getName();
        final int i = s.lastIndexOf(46);
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
    
    public void setEmlistener(final EnigmaMenuListener emlistener) {
        this.emlistener = emlistener;
    }
    
    static /* synthetic */ void access$4(final EnigmaMenuBar enigmaMenuBar, final File lastFile) {
        enigmaMenuBar.lastFile = lastFile;
    }
    
    static /* synthetic */ void access$7(final EnigmaMenuBar enigmaMenuBar, final Scanner fileInputReader) {
        enigmaMenuBar.fileInputReader = fileInputReader;
    }
    
    static /* synthetic */ void access$9(final EnigmaMenuBar enigmaMenuBar, final PrintWriter fileOutputWriter) {
        enigmaMenuBar.fileOutputWriter = fileOutputWriter;
    }
}
