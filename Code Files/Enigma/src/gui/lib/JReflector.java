package gui.lib;

/**
 *
 * @author Apurv Jain
 */
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import enigma.Reflector;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import javax.swing.JPanel;

public class JReflector extends JPanel
{
    Dimension dim;
    private GridBagConstraints gc;
    private int thickOutLine;
    
    public JReflector() {
        this.thickOutLine = -1;
        this.dim = new Dimension(150, 445);
        this.setLayout(new GridBagLayout());
        this.gc = new GridBagConstraints();
        this.gc.fill = 0;
        this.gc.weightx = 1.0;
        this.gc.weighty = 1.0;
        this.setPreferredSize(this.dim);
    }
    
    public void redraw(final Reflector reflector) {
        this.removeAll();
        for (int i = 0; i < 26; ++i) {
            this.gc.gridx = 1;
            this.gc.gridy = i;
            this.gc.anchor = 10;
            final JLabel letter = new JLabel(new StringBuilder().append((char)(65 + i)).toString());
            letter.setFont(new Font(letter.getFont().getName(), 0, 11));
            this.add(letter, this.gc);
        }
        final int[][] connection = new int[26][2];
        for (int j = 0; j < 26; ++j) {
            connection[j][1] = j;
            connection[j][0] = reflector.getAnOutWire(j);
        }
        this.gc.gridheight = 26;
        this.gc.gridy = 0;
        this.gc.gridx = 0;
        this.gc.anchor = 22;
        this.add(new DrawingRectangle(connection), this.gc);
        this.gc.gridheight = 1;
    }
    
    public void setThickOutLine(final int line) {
        this.thickOutLine = line;
    }
    
    public void removeThickLines() {
        this.setThickOutLine(-1);
    }
    
    private class DrawingRectangle extends JPanel
    {
        private int[][] connection;
        private int width;
        Color[] colors;
        
        public DrawingRectangle(final int[][] connection) {
            this.colors = new Color[] { Color.MAGENTA, Color.DARK_GRAY, Color.ORANGE.darker(), Color.RED.darker(), Color.BLUE, Color.RED, Color.YELLOW.darker(), Color.PINK.darker() };
            this.connection = connection;
            this.width = JReflector.this.dim.width - 50;
        }
        
        public void paintComponent(final Graphics g) {
            for (int i = 0; i < this.connection.length; ++i) {
                final Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (JReflector.this.thickOutLine == i || JReflector.this.thickOutLine == this.connection[i][0]) {
                    g2.setStroke(new BasicStroke(3.0f));
                    g2.setColor(Color.BLACK);
                }
                else {
                    g2.setStroke(new BasicStroke(1.0f));
                    g2.setColor(this.colors[i % this.colors.length]);
                }
                final int from = this.connection[i][0] * 17 + 10;
                final int height = this.connection[i][1] * 17 + 10 - from;
                if (height > 0) {
                    g.drawRect(1 + i * 3, from, this.width, height);
                }
            }
        }
        
        @Override
        public Dimension getPreferredSize() {
            this.setOpaque(false);
            return new Dimension(this.width + 1, JReflector.this.dim.height);
        }
    }
}
