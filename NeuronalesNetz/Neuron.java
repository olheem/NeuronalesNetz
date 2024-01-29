package NeuronalesNetz;

import java.util.Random;

/**
 * Ein Neuron eines neuronalen Netzwerks.
 * 
 * @author Dr. Oliver Heidbüchel
 * @version 2023-03-29
 * @author Daniel Garmann
 * @version 2024-01-20
 */
public class Neuron
{
    private int n;
    private double[] w;
    private double net;
    private double phi = 0;
    private double phiStrich = 0;
    private double[] deltaW;
    private double[] eingaben;
    private double delta;
    private Aktivierungsfunktion af;

    /**
     * Konstruktor für Objekte der Klasse Neuron
     * 
     * @param eingaenge die Anzahl der Eingänge
     * @param af die Aktivierungsfunktion
     */
    public Neuron(int eingaenge, Aktivierungsfunktion af)
    {
        this.af = af;
        n = eingaenge;
        net = 0;
        w = new double[n + 1];  // das letzte Gewicht ist für das Bias
        deltaW = new double[n + 1];
        initialisieren();
    }

    /**
     * Trage zufällige Werte für die Gewichte ein zwischen -0.5 und 0.5.
     * Für das Bias wird 0.1 eingetragen.
     */
    private void initialisieren(){
        Random rnd = new Random();
        for (int i = 0; i < n; i++){
            w[i] = rnd.nextDouble() - 0.5;
        }
        w[n] = 0.1; // Bias
    }    

    /**
     * Berechnet die Netzeingabe des Neurons für die Eingaben.
     * Die Länge des Arrays eingaben muss so groß sein wie die Anzahl der Eingänge.
     * 
     * @param input die Eingaben
     */
    public void berechneNetzeingabe(double[] input){     
        // input merken
        eingaben = input;

        // berechnen
        net = 0;
        for (int i = 0; i < n; i++){
            net += input[i] * w[i];
        }
        net += 1 * w[n]; // Bias

        // Aktivierungsfunktion und deren Ableitung anwenden
        phi = af.phi(net);
        phiStrich = af.phiStrich(net);
    }

    /**
     * Gibt die Aktivierungsfunktion angewendet auf die Netzeingabe zurück.
     * 
     * @return s.o.
     */
    public double phiVonNet(){
        return phi;
    }

    /**
     * Gibt die Ableitung der Aktivierungsfunktion angewendet auf die Netzeingabe zurück.
     * 
     * @return s.o.
     */
    public double phiStrichVonNet(){
        return phiStrich;
    }

    /**
     * Die Änderungen nach dem Training werden hinzugefügt.
     * 
     * @param lernrate die Lernrate
     * @param delta Fehlersignal
     */
    public void setzeAenderung(double lernrate, double delta){
        this.delta = delta;
        for (int i = 0; i < n; i++){
            deltaW[i] += - lernrate * delta * eingaben[i];
        }
        deltaW[n] += - lernrate * delta * 1; // Bias
    }

    /**
     * Die Änderungen nach dem Training werden angewandt. Die Deltas werden auf 0 gesetzt.
     */
    public void aendereGewichtungen(){
        for (int i = 0; i <= n; i++){
            w[i] += deltaW[i];
            deltaW[i] = 0;
        }
    }

    /**
     * Gibt ein Gewicht zurück für eine Eingabe oder das Bias.
     * 
     * @param nr die Nummer des Eingangs bzw. die Anzahl der Eingänge
     * @return das Gewicht für den Eingang (falls nr kleiner als die Anzahl der Eingänge) oder das das Gewicht für das Bias (falls nr gleich der Anzahl der Eingänge)
     */
    public double gibGewicht(int nr){
        return w[nr];
    }

    /**
     * Gibt delta zurück
     * 
     * @return delta
     */
    public double gibDelta(){
        return delta;
    }

    /**
     * Die Methode liefert die Anzahl der Eingaenge des Neurons
     * @return die Anzahl der Eingaenge
     */
    public int gibAnzahlEingaenge() {
        return n;
    }

    /**
     * Die Methode setzt die Gewichte auf vorgegebene Werte
     * @param pGewichte die Gewichte
     */
    public void setzeGewichte(double[] pGewichte) {
        w = pGewichte;
    }

    /**
     * Die Methode liefert eine String-Representation des Neurons des Netzes
     * @return eine String-Repraesentation des Neurons des Netzes
     */ 
    @Override
    public String toString() {
        String s = String.format("%f", w[0]);
        for (int i = 1; i < w.length; i++) {
            s = s + String.format(":%f", w[i]);
        }
        s = s + "/" + af.toString();
        return s;
    }

    /**
     * Die Methode liefert ein neues Neuron, welches aus einer String-Repraesentation ausgelesen wird.
     * @param s die Zeichenkette mit der String-Repraesentation des Neurons
     * @return ein neues Objekt der Klasse Neuron mit dem durch s repraesentierten Neuron
     */
    public static Neuron parseNeuron(String s) {    
        String f = "Sigmoid";
        if (s.indexOf("/") >= 0) {           
            f = s.substring(s.indexOf("/") + 1);
            s = s. substring(0, s.indexOf("/"));
        }
        s = s.replaceAll(",",".");
        String[] eingaengezeilen = s.split(":");
        double[] gewichte = new double[eingaengezeilen.length];
        for (int i = 0; i < gewichte.length; i++) {
            gewichte[i] = Double.parseDouble(eingaengezeilen[i]);
        }
        Neuron n = new Neuron(gewichte.length - 1, Aktivierungsfunktion.parseFunktion(f));
        n.setzeGewichte(gewichte);
        return n;
    }

}
