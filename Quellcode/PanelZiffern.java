import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.*; 
import java.awt.RenderingHints;

public class PanelZiffern extends JPanel {

    private BufferedImage bild = null;

    /**
     * Konstruktor eines Ziffern-Panels
     */
    public PanelZiffern() {
        super();
        addMouseMotionListener(
            new MouseMotionAdapter() { 
                public void mouseDragged(MouseEvent evt) {
                    Graphics2D g = bild.createGraphics();
                    g.setColor(Color.BLACK);
                    g.fillOval(evt.getX() - 15, evt.getY() - 15, 30, 30); 
                    repaint();
                }
            }
        );
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x,y,width,height);
        neuesBild();
    }

    /**
     * Methode mit der das Panel bei Bedarf neu gezeichnet wird
     * @param g die Grafik-Ausgabe des Panels
     */
    @Override 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bild, 0, 0, getWidth(), getHeight(), this);
        // noch einen schwarzen Rahmen um das Bild
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    public void neuesBild() {
        bild = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bild.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private BufferedImage skaliere(BufferedImage bild, int groesse) {
        BufferedImage skaliert = new BufferedImage(groesse, groesse, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = skaliert.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(bild, 0, 0, groesse, groesse, this);
        return skaliert;
    }

    private BufferedImage verschiebe(BufferedImage bild, int dx, int dy) {
        BufferedImage verschoben = new BufferedImage(bild.getWidth(), bild.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = verschoben.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, bild.getWidth(), bild.getHeight());
        g.drawImage(bild, dx, dy, this);
        return verschoben;  
    }

    private int[] dimensionen(BufferedImage bild) {
        int maxx = 0;
        int maxy = 0;
        int minx = bild.getWidth();
        int miny = bild.getHeight();
        for (int x = 0; x < bild.getWidth(); x++) {
            for (int y = 0; y < bild.getHeight(); y++) {
                if ((bild.getRGB(x, y) & 0x000000FF) < 255) {
                    minx = Math.min(x, minx);
                    maxx = Math.max(x, maxx);
                    miny = Math.min(y, miny);
                    maxy = Math.max(y, maxy);
                }
            }
        }
        return new int[] {minx, miny, maxx, maxy};
    }

    private int[] schwerpunkt(BufferedImage bild) {
        int[] dim = dimensionen(bild);
        return new int[] {(dim[2]+dim[0]) / 2, (dim[3]+dim[1]) / 2};
    }

    public int zeichneDatensatz(Datensatz pDatensatz) {
        if (pDatensatz != null) {
            BufferedImage hilf = new BufferedImage(28, 28, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < 28 * 28; i++) {
                int grauwert = (int)pDatensatz.gibEingabe()[i];
                hilf.setRGB(i % 28, i / 28, grauwert*(1 + 256 + 256 * 256));
            }
            bild = skaliere(hilf, 400);
            repaint();        
            double max = 0;
            int ziffer = 0;
            for (int i = 0; i < 10; i++) {
                if (pDatensatz.gibZielwert()[i] > max) {
                    max = pDatensatz.gibZielwert()[i];
                    ziffer = i;
                }
            }
            return ziffer;
        } else {
            return -1;
        }
    }

    /**
     * Die Methode das aktuelle Bild in einen Datensatz um
     * @param pZiffer die Ziffer, der das Bild entspricht.
     * @return der zugehoerige Datensatz
     */
    public Datensatz erstelleDatensatz(int pZiffer) {
        int[] sp = schwerpunkt(bild);
        bild = verschiebe(bild, bild.getWidth() / 2 - sp[0], bild.getHeight() / 2 - sp[1]);
        BufferedImage hilf = skaliere(bild, 28);
        double[] eingabe = new double[28*28];
        for (int x = 0; x < 28; x++) {
            for (int y = 0; y < 28; y++) {
                eingabe[x + y*28] = 1.0*(hilf.getRGB(x, y)  & 0x000000FF);
            }
        }
        double[] zielwert = new double[10];
        zielwert[pZiffer] = 1;
        Datensatz d = new Datensatz(eingabe, zielwert);
        zeichneDatensatz(d);
        return d;
    }

}
