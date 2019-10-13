package gui;

/**
 *
 * @author Apurv Jain
 */
import java.awt.Dimension;
import javax.swing.JOptionPane;
import gui.listener.EnigmaMenuListener;
import gui.listener.TypeListener;
import java.util.Scanner;
import gui.listener.RotorListener;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import gui.panels.WiresPanel;
import gui.panels.TypewriterPanel;
import enigma.Enigma;
import gui.panels.PlugboardPanel;
import gui.panels.RotorsPanel;
import gui.panels.EncodePanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Image;

//Setting up the Display
public class Machine extends JFrame
{
    private EncodePanel epanel;
    private RotorsPanel rpanel;
    private PlugboardPanel pbpanel;
    private Enigma enigma;
    private TypewriterPanel twpanel;
    private WiresPanel dpanel;
    private EnigmaMenuBar menuBar;
    private final int TEXT_MODE = 1;
    private final int KB_MODE = 2;
    private final int WIRES_MODE = 3;
    private int mode;
    
    public Machine() {
        this.setTitle("Enigma Machine Emulator GUI - by Apurv Jain");
        //Image icon = Toolkit.getDefaultToolkit().getImage("/gui/images/e.png");
        //this.setIconImage(icon);
        this.mode = 1;
        this.enigma = new Enigma(Enigma.I, Enigma.II, Enigma.III, "EJMZALYXVBWFCRQUONTSPIKHGD");
        this.setLayout(new BorderLayout());
        this.menuBar = new EnigmaMenuBar(this);
        this.setJMenuBar(this.menuBar.getMenuBar());
        this.epanel = new EncodePanel();
        this.rpanel = new RotorsPanel();
        this.pbpanel = new PlugboardPanel();
        this.twpanel = new TypewriterPanel();
        this.dpanel = new WiresPanel();
        this.epanel.setEnigma(this.enigma);
        this.rpanel.setEnigma(this.enigma);
        this.twpanel.setEnigma(this.enigma);
        this.dpanel.setEnigma(this.enigma);
        this.add(this.epanel, "Center");
        this.add(this.rpanel, "North");
        this.add(this.pbpanel, "South");
        this.rpanel.setRotorListener(new RotorListener() {
            @Override
            public void configure(final String[] leftRotor, final String[] middleRotor, final String[] rightRotor, final char leftStart, final char middleStart, final char rightStart, final char leftRing, final char middleRing, final char rightRing, final String reflector) {
                Machine.this.enigma.getLeftRotor().setRotor(leftRotor);
                Machine.this.enigma.getMiddleRotor().setRotor(middleRotor);
                Machine.this.enigma.getRightRotor().setRotor(rightRotor);
                Machine.this.enigma.getLeftRotor().setRotorHead(leftStart);
                Machine.this.enigma.getMiddleRotor().setRotorHead(middleStart);
                Machine.this.enigma.getRightRotor().setRotorHead(rightStart);
                Machine.this.enigma.getLeftRotor().setRingHead(leftRing);
                Machine.this.enigma.getMiddleRotor().setRingHead(middleRing);
                Machine.this.enigma.getRightRotor().setRingHead(rightRing);
                Machine.this.enigma.getReflector().setReflector(reflector);
                Machine.this.enigma.resetPlugboard();
                final Scanner scan = new Scanner(Machine.this.pbpanel.getPlugboard());
                while (scan.hasNext()) {
                    final String wire = scan.next();
                    if (wire.length() == 2) {
                        final char from = wire.charAt(0);
                        final char to = wire.charAt(1);
                        if (Machine.this.enigma.isPlugged(from) || Machine.this.enigma.isPlugged(to) || from == to) {
                            continue;
                        }
                        Machine.this.enigma.insertPlugboardWire(from, to);
                    }
                }
                scan.close();
                if (Machine.this.mode == 3) {
                    Machine.this.dpanel.updateWires();
                }
                if (Machine.this.mode == 1) {
                    Machine.this.epanel.refresh();
                }
            }
        });
        this.epanel.setTypeListener(new TypeListener() {
            @Override
            public void typeAction() {
                Machine.this.rpanel.setStates(Machine.this.enigma.getLeftRotor().getRotorHead(), Machine.this.enigma.getMiddleRotor().getRotorHead(), Machine.this.enigma.getRightRotor().getRotorHead());
            }
        });
        this.twpanel.setTypeListener(new TypeListener() {
            @Override
            public void typeAction() {
                Machine.this.rpanel.setStates(Machine.this.enigma.getLeftRotor().getRotorHead(), Machine.this.enigma.getMiddleRotor().getRotorHead(), Machine.this.enigma.getRightRotor().getRotorHead());
            }
        });
        this.dpanel.setTypeListener(new TypeListener() {
            @Override
            public void typeAction() {
                Machine.this.rpanel.setStates(Machine.this.enigma.getLeftRotor().getRotorHead(), Machine.this.enigma.getMiddleRotor().getRotorHead(), Machine.this.enigma.getRightRotor().getRotorHead());
            }
        });
        this.epanel.setDefaultText("ENTER YOUR TEXT HERE");
        this.menuBar.setEmlistener(new EnigmaMenuListener() {
            @Override
            public void textBoxDisplay() {
                Machine.access$6(Machine.this, 1);
                Machine.this.epanel.setVisible(true);
                Machine.this.twpanel.setVisible(false);
                Machine.this.dpanel.setVisible(false);
                Machine.this.add(Machine.this.epanel, "Center");
                Machine.this.revalidate();
                Machine.this.epanel.setDefaultText("A");
                Machine.this.epanel.setDefaultText("");
                Machine.this.pbpanel.lightTheme();
                Machine.this.rpanel.lightTheme();
            }
            
            @Override
            public void keyboardDisplay() {
                Machine.access$6(Machine.this, 2);
                Machine.this.twpanel.setVisible(true);
                Machine.this.epanel.setVisible(false);
                Machine.this.dpanel.setVisible(false);
                Machine.this.add(Machine.this.twpanel, "Center");
                Machine.this.revalidate();
                Machine.this.twpanel.clear();
                Machine.this.pbpanel.darkTheme();
                Machine.this.rpanel.darkTheme();
            }
            
            @Override
            public void wiresConnectionDisplay() {
                Machine.access$6(Machine.this, 3);
                Machine.this.dpanel.setVisible(true);
                Machine.this.epanel.setVisible(false);
                Machine.this.twpanel.setVisible(false);
                Machine.this.add(Machine.this.dpanel, "Center");
                Machine.this.revalidate();
                Machine.this.dpanel.clear();
                Machine.this.pbpanel.lightTheme();
                Machine.this.rpanel.lightTheme();
            }
            
            @Override
            public void importFile(final String text) {
                if (Machine.this.mode != 1) {
                    JOptionPane.showMessageDialog(Machine.this, "You cannot import a file in this display");
                    return;
                }
                Machine.this.epanel.setDefaultText(text);
            }
            
            @Override
            public String exportFile() {
                if (Machine.this.mode == 1) {
                    return Machine.this.epanel.getOutput();
                }
                if (Machine.this.mode == 2) {
                    return Machine.this.twpanel.getOutput();
                }
                return "-1";
            }
            
            @Override
            public void restart() {
                Machine.this.dispose();
                Machine.access$8(Machine.this, null);
                Machine.access$9(Machine.this, null);
                Machine.access$10(Machine.this, null);
                Machine.access$11(Machine.this, null);
                Machine.access$12(Machine.this, null);
                Machine.access$13(Machine.this, null);
                Machine.access$14(Machine.this, null);
                System.gc();
                new Machine();
            }
        });
        this.setVisible(true);
        final Dimension dim = new Dimension(1150, 700);
        this.setPreferredSize(dim);
        this.setMinimumSize(dim);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.pack();
    }
    
    static /* synthetic */ void access$6(final Machine machine, final int mode) {
        machine.mode = mode;
    }
    
    static /* synthetic */ void access$8(final Machine machine, final EncodePanel epanel) {
        machine.epanel = epanel;
    }
    
    static /* synthetic */ void access$9(final Machine machine, final RotorsPanel rpanel) {
        machine.rpanel = rpanel;
    }
    
    static /* synthetic */ void access$10(final Machine machine, final PlugboardPanel pbpanel) {
        machine.pbpanel = pbpanel;
    }
    
    static /* synthetic */ void access$11(final Machine machine, final Enigma enigma) {
        machine.enigma = enigma;
    }
    
    static /* synthetic */ void access$12(final Machine machine, final TypewriterPanel twpanel) {
        machine.twpanel = twpanel;
    }
    
    static /* synthetic */ void access$13(final Machine machine, final WiresPanel dpanel) {
        machine.dpanel = dpanel;
    }
    
    static /* synthetic */ void access$14(final Machine machine, final EnigmaMenuBar menuBar) {
        machine.menuBar = menuBar;
    }
}

