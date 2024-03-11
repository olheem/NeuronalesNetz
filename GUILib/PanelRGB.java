package GUILib;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.*;

import NeuronalesNetz.*;

public class PanelRGB extends JPanel {

    private Color aktuelleFarbe = Color.RED;
    private Datensammlung trainingsdaten;
    private Netz neuronalesNetz = null;  
    private int verzoegerung = 0;
    private final int verzoegerungszahl = 5;

    /**
     * Konstruktor eines RGBPanels
     */
    public PanelRGB() {
        super();
        trainingsdaten = new Datensammlung();
        addMouseMotionListener(
            new MouseMotionAdapter() { 
                public void mouseDragged(MouseEvent evt) { 
                    verzoegerung++;
                    if (verzoegerung > verzoegerungszahl) {
                        Datensatz d = erstelleDatensatz(evt.getX(), evt.getY(), aktuelleFarbe);
                        trainingsdaten.fuegeEin(d);
                        zeichneDatensatz(d);
                        verzoegerung = 0;
                    }
                }
            }
        );
        addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent evt) {
                    Datensatz d = erstelleDatensatz(evt.getX(), evt.getY(), aktuelleFarbe);
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
        int y = (int)(pDatensatz.gibEingabe()[1] * getHeight());
        Color farbe = rgbZuFarbe(pDatensatz.gibZielwert());

        g.setColor(farbe);
        g.fillOval(x - 5, y - 5, 10, 10);
        g.setColor(Color.WHITE);
        g.drawOval(x - 5, y - 5, 10, 10);    
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
            int y = (int)(datensatz.gibEingabe()[1] * getHeight());
            Color farbe = rgbZuFarbe(datensatz.gibZielwert());
            g.setColor(farbe);
            g.fillOval(x - 5, y - 5, 10, 10);
            g.setColor(Color.WHITE);
            g.drawOval(x - 5, y - 5, 10, 10);

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
     * Die aktuelle Farbe fuer neue Trainingsdaten wird geaendert.
     * @param pFarbe die aktuelle zu verwendende Farbe
     */
    public void setzeFarbe(Color pFarbe) {
        aktuelleFarbe = pFarbe;
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
            for (int pixelx = 0; pixelx < getWidth(); pixelx++) {
                for (int pixely = 0; pixely < getHeight(); pixely++) {
                    double[] eingabe = new double[] {1.0 * pixelx / getWidth(), 1.0 * pixely / getHeight()};
                    double[] ausgabe = pNetz.berechne(eingabe);
                    bild.setRGB(pixelx, pixely, rgbZuFarbe(ausgabe).getRGB());
                }
            }
        }
        return bild;
    }  

    /**
     * Die Methode wandelt einen Output eines neuronalen Netzes in eine Farbe um
     * @param pFarbanteile ein Array mit den Farbanteilen zu RGB
     * @return die zugehoerige Farbe
     */
    private Color rgbZuFarbe(double[] pFarbanteile) {
        float r = (float)pFarbanteile[0];
        float g = (float)pFarbanteile[1];
        float b = (float)pFarbanteile[2];
        // auf erlaubten Zielbereich einschraenken
        if (r > 1) r = 1;
        if (r < 0) r = 0;
        if (g > 1) g = 1;
        if (g < 0) g = 0;
        if (b > 1) b = 1;
        if (b < 0) b = 0;
        return new Color(r, g, b);

    }

    /**
     * Die Methode wandelt einen Punkt in einen Datensatz um
     * @param pPunkt der Punkt, der umgewandelt werden soll
     * @return der zugehoerige Datensatz
     */
    private Datensatz erstelleDatensatz(int pX, int pY, Color pFarbe) {
        double[] eingabe = new double[2];
        eingabe[0] = 1.0 * pX / getWidth();
        eingabe[1] = 1.0 * pY / getHeight();
        double[] zielwert = new double[3];
        if (pFarbe != null) {
            zielwert[0] = 1.0 * pFarbe.getRed() / 255;
            zielwert[1] = 1.0 * pFarbe.getGreen() / 255;
            zielwert[2] = 1.0 * pFarbe.getBlue() / 255;
        }  
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

