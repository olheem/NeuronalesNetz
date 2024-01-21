import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.*; 

public class PanelKurve extends JPanel {

    private Datensammlung trainingsdaten;
    private Netz neuronalesNetz = null;  
    private int verzoegerung = 0;
    private final int verzoegerungszahl = 5;

    /**
     * Konstruktor eines Kurven-Panels
     */
    public PanelKurve() {
        super();
        trainingsdaten = new Datensammlung();
        addMouseMotionListener(
            new MouseMotionAdapter() { 
                public void mouseDragged(MouseEvent evt) { 
                    verzoegerung++;
                    if (verzoegerung > verzoegerungszahl) {
                        Datensatz d = erstelleDatensatz(evt.getX(), evt.getY());
                        trainingsdaten.fuegeEin(d);
                        zeichneDatensatz(d);
                        verzoegerung = 0;
                    }  
                }
            }
        );
        addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent evt) {
                    Datensatz d = erstelleDatensatz(evt.getX(), evt.getY());
                    trainingsdaten.fuegeEin(d);
                    zeichneDatensatz(d);
                }

                public void mouseEntered(MouseEvent e) {}

                public void mouseExited(MouseEvent e) {}

                public void mousePressed(MouseEvent e) {}

                public void mouseReleased(MouseEvent e) {}
            });
    }

    /**
     * Methode mit der das Panel bei Bedarf neu gezeichnet wird
     * @param g die Grafik-Ausgabe des Panels
     */
    @Override 
    public void paint(Graphics g) {
        super.paint(g);
        // Bild, welches vom neuronalen Netz erzeugt wird, erstellen und ausgeben
        BufferedImage bild = erstelleBild(neuronalesNetz);
        Rectangle r = g.getClipBounds();
        g.drawImage(bild, r.x, r.y, r.x+r.width, r.y+r.height, r.x, r.y, r.x+r.width, r.y+r.height, this);
        // Trainingsdaten darueber ausgeben
        aktualisiereDaten(g);
        // noch einen schwarzen Rahmen um das Bild
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    /**
     * Zeichnet einen einzelnen Punkt auf der Zeichenflaeche
     * @param pPunkt der zu zeichnende Punkt
     */
    private void zeichneDatensatz(Datensatz pDatensatz) {
        Graphics g = getGraphics();
        int x = (int)(pDatensatz.gibEingabe()[0] * getWidth());
        int y = (int)(pDatensatz.gibZielwert()[0] * getHeight());

        g.setColor(Color.BLACK);
        g.fillOval(x - 5, y - 5, 10, 10);
    }

    /**
     * Alle Punkte (Traingsdaten) werden neu gezeichnet.
     * @param g die Grafik-Ausgabe, auf der sie dargestellt werden müssen.
     */
    private void aktualisiereDaten(Graphics g) {
        List<Datensatz> daten = trainingsdaten.gibDaten();
        daten.toFirst();
        while (daten.hasAccess()) {
            Datensatz datensatz = daten.getContent();
            int x = (int)(datensatz.gibEingabe()[0] * getWidth());
            int y = (int)(datensatz.gibZielwert()[0] * getHeight());

            g.setColor(Color.BLACK);
            g.fillOval(x - 5, y - 5, 10, 10);

            daten.next();
        }
    }

    /**
     * Alle Trainingsdaten werden geloescht
     */
    public void loescheDaten() {
        trainingsdaten.loescheAlle();
        repaint();
    }

    /**
     * Das zu verwendende neuronale Netz wird gesetzt.
     * @param pNetz das neuronale Netz, welches fuer die Traingsdaten verwendet werden soll.
     */
    public void setzeNeuronalesNetz(Netz pNetz) {
        neuronalesNetz = pNetz;
        repaint();
    }

    /**
     * Die Methode uebernimmt eine Sammlung von Trainingsdaten
     * @param pDatensammlung die Sammlung der Trainingsdaten
     */
    public void setzeTrainingsdaten(Datensammlung pDatensammlung) {
        trainingsdaten = pDatensammlung;
    }

    /**
     * Die Methode erstellt auf Grundlage eines neuronalen Netzes ein Bild mit gefaerbten Flaechen
     * @param pNetz das zugrundeliegende Netz.
     */
    public BufferedImage erstelleBild(Netz pNetz) {
        BufferedImage bild = null;
        if (pNetz != null) {
            bild = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics g = bild.createGraphics();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            for (int pixelx = 0; pixelx < getWidth(); pixelx++) {
                double[] eingabe = new double[] {1.0 * pixelx / getWidth()};
                double[] ausgabe = pNetz.berechne(eingabe);
                int pixely = (int)(ausgabe[0] * getHeight());

                g.setColor(Color.WHITE);
                g.fillOval(pixelx - 5, pixely - 5, 10, 10);;
            }
        }
        return bild;
    }  

    /**
     * Die Methode wandelt einen Punkt in einen Datensatz um
     * @param pPunkt der Punkt, der umgewandelt werden soll
     * @return der zugehoerige Datensatz
     */
    private Datensatz erstelleDatensatz(int pX, int pY) {
        double[] eingabe = new double[1];
        eingabe[0] = 1.0 * pX / getWidth();
        double[] zielwert = new double[1];
        zielwert[0] = 1.0 * pY / getHeight();
        Datensatz d = new Datensatz(eingabe, zielwert);
        return d;
    }

    /**
     * Die Methode liefert die gesetzten Punkte in Form einer Datensammlung
     * @return die Datensammlung aller Trainingsdatensaetze
     */
    public Datensammlung gibTrainingsdaten() {
        return trainingsdaten;
    }

    /**
     * Die Methode liefert die Eingaben der Trainingsdaten als Array
     * @return die Eingaben der Trainingsdaten als Array
     */
    public double[][] gibEingabenArray() {
        return trainingsdaten.gibEingabenArray();
    }

    /**
     * Die Methode liefert die Zielwerte der Trainingsdaten als Array
     * @return die Zielwerte der Trainingsdaten als Array
     */
    public double[][] gibZielwerteArray() {
        return trainingsdaten.gibZielwerteArray();
    }
}

