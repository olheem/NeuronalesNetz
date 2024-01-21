import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.*; 
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

public class PanelFigur extends JPanel {

    private BufferedImage bild = null;
    public String[] figuren = new String[] {"Quadrat","Kreis","Dreieck"};
      

    /**
     * Konstruktor eines Figur-Panels
     */
    public PanelFigur() {
        super();
        addMouseMotionListener(
            new MouseMotionAdapter() { 
                public void mouseDragged(MouseEvent evt) {
                  Graphics2D g = bild.createGraphics();
                  g.setColor(Color.BLACK);
                  g.fillOval(evt.getX() - 20, evt.getY() - 20, 40, 40); 
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
      bild = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_BYTE_GRAY);
      Graphics2D g = bild.createGraphics();
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, getWidth(), getHeight());
    }
  
    private BufferedImage skaliere(BufferedImage bild, int groesse) {
//      BufferedImage skaliert = new BufferedImage(groesse, groesse, BufferedImage.TYPE_INT_RGB);
//      Graphics2D g = skaliert.createGraphics();
//    
//      //g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//      g.drawImage(bild, 0, 0, groesse, groesse, this);

      BufferedImage skaliert =  new BufferedImage(groesse, groesse, BufferedImage.TYPE_BYTE_GRAY);
      Graphics2D g = skaliert.createGraphics();
      g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g.drawImage(bild, 0, 0, groesse, groesse, null);
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

    public String zeichneDatensatz(Datensatz pDatensatz) {
            
      if (pDatensatz != null) {
        BufferedImage hilf = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < 10 * 10; i++) {
          int grauwert = (int)pDatensatz.gibEingabe()[i];
          hilf.setRGB(i % 10, i / 10, grauwert*(1 + 256 + 256 * 256));
        }
        bild = skaliere(hilf, 400);
        repaint();        
        double max = 0;
        int f = 0;
        for (int i = 0; i < 3; i++) {
          if (pDatensatz.gibZielwert()[i] > max) {
            max = pDatensatz.gibZielwert()[i];
            f = i;
          }
        }
        return figuren[f];
      } else {
        return "";
      }
    }

    /**
     * Die Methode das aktuelle Bild in einen Datensatz um
     * @param pFigur die Figur, der das Bild entspricht.
     * @return der zugehoerige Datensatz
     */
    public Datensatz erstelleDatensatz(int pFigur) {
//      BufferedImage hilf = skaliere(bild, 10);
//      int[] sp = schwerpunkt(hilf);
//      hilf = verschiebe(hilf, hilf.getWidth() / 2 - sp[0], hilf.getHeight() / 2-sp[1]);

      int[] sp = schwerpunkt(bild);
      bild = verschiebe(bild, bild.getWidth() / 2 - sp[0], bild.getHeight() / 2 - sp[1]);
      BufferedImage hilf = skaliere(bild, 10);
      
      double[] eingabe = new double[10*10];
      for (int x = 0; x < 10; x++) {
        for (int y = 0; y < 10; y++) {
          eingabe[x + y*10] = 1.0*(hilf.getRGB(x, y)  & 0x000000FF);
        }
      }
      double[] zielwert = new double[3];
      zielwert[pFigur] = 1;
      Datensatz d = new Datensatz(eingabe, zielwert);
      zeichneDatensatz(d);
      return d;
    }


}

