package gui.lib;

/**
 *
 * @author Apurv Jain
 */
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import enigma.Rotor;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import javax.swing.JPanel;

public class JRotor extends JPanel
{
    private Dimension dim;
    private GridBagConstraints gc;
    private int thickOutLine;
    private int thickInLine;
    
    public JRotor() {
        this.thickOutLine = -1;
        this.thickInLine = -1;
        this.dim = new Dimension(210, 445);
        this.setLayout(new GridBagLayout());
        this.gc = new GridBagConstraints();
        this.gc.fill = 0;
        this.gc.weightx = 1.0;
        this.gc.weighty = 1.0;
        this.setPreferredSize(this.dim);
    }
    
    public void redraw(final Rotor rotor) {
        this.removeAll();
        for (int i = 0; i < 26; ++i) {
            this.gc.gridx = 0;
            this.gc.gridy = i;
            JLabel letter = new JLabel(new StringBuilder().append((char)((rotor.getRingHead() - 'A' + i) % 26 + 65)).toString());
            letter.setFont(new Font(letter.getFont().getName(), 0, 11));
            this.add(letter, this.gc);
            this.gc.gridx = 2;
            final char current = (char)((rotor.getRotorHead() - 'A' + i) % 26 + 65);
            final String pre = (rotor.getNotch() == current) ? "[" : "";
            final String post = (rotor.getNotch() == current) ? "]" : "";
            letter = new JLabel(String.valueOf(pre) + current + post);
            letter.setFont(new Font(letter.getFont().getName(), 0, 11));
            this.add(letter, this.gc);
        }
        final int[][] connection = new int[26][2];
        for (int j = 0; j < 26; ++j) {
            connection[j][1] = j;
            connection[j][0] = rotor.getAnOutWire(j);
        }
        this.gc.gridheight = 26;
        this.gc.gridy = 0;
        this.gc.gridx = 1;
        this.add(new DrawingLine(connection), this.gc);
        this.gc.gridheight = 1;
    }
    
    public void paintComponent(final Graphics g) {
        final Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY);
        g2.drawRect(1, 0, this.dim.width - 6, 17);
    }
    
    public void setThickOutLine(final int line) {
        this.thickOutLine = line;
    }
    
    public void setThickInLine(final int line) {
        this.thickInLine = line;
    }
    
    public void removeThickLines() {
        this.setThickInLine(-1);
        this.setThickOutLine(-1);
    }
    
    private class DrawingLine extends JPanel
    {
        private int[][] connection;
        private int width;
        Color[] colors;
        
        public DrawingLine(final int[][] connection) {
            this.colors = new Color[] { Color.MAGENTA, Color.DARK_GRAY, Color.ORANGE.darker(), Color.RED.darker(), Color.BLUE, Color.RED, Color.YELLOW.darker(), Color.PINK.darker() };
            this.connection = connection;
            this.width = JRotor.this.dim.width - 50;
        }
        
        public void paintComponent(final Graphics g) {
            for (int i = 0; i < this.connection.length; ++i) {
                final Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (JRotor.this.thickInLine == i || JRotor.this.thickOutLine == i) {
                    g2.setStroke(new BasicStroke(3.0f));
                    g2.setColor(Color.BLACK);
                }
                else {
                    g2.setStroke(new BasicStroke(1.0f));
                    g2.setColor(this.colors[i % this.colors.length]);
                }
                g2.drawLine(0, this.connection[i][0] * 17 + 10, this.width, this.connection[i][1] * 17 + 10);
            }
        }
        
        @Override
        public Dimension getPreferredSize() {
            this.setOpaque(false);
            return new Dimension(this.width + 1, JRotor.this.dim.height);
        }
    }
}
