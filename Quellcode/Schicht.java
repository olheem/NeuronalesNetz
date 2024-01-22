/**
 * Eine Schicht in einem neuronalen Netz.
 * 
 * @author Dr. Oliver Heidbüchel
 * @version 2023-03-29
 * @author Daniel Garmann
 * @version 2024-01-20
 */
public class Schicht
{
    private int eingaenge;
    private Neuron[] neuronen;
    private double[] eingaben;
    private double[] ausgaben;

    /**
     * Konstruktor für Objekte der Klasse Schicht
     * 
     * @param eingaenge die Anzahl der Eingaenge
     * @param neuronenanzahl die Anzahl der Neuronen
     * @param af die Aktivierungsfunktion
     */
    public Schicht(int eingaenge, int neuronenanzahl, Aktivierungsfunktion af)
    {
        this.eingaenge = eingaenge;
        neuronen = new Neuron[neuronenanzahl];
        for (int i = 0; i < neuronen.length; i++){
            neuronen[i] = new Neuron(eingaenge, af);
        }
    }

    /**
     * Berechnet die Ausgaben für die gegebenen Eingaben.
     * Die Länge des Arrays eingaben muss so groß sein wie die Anzahl der Eingänge der Schicht.
     * 
     * @param eingaben die Eingaben
     * @param Ausgaben der einzelnen Neuronen
     */
    public double[] berechne(double[] eingaben){
        this.eingaben = eingaben;
        ausgaben = new double[neuronen.length];
        for (int i = 0; i < neuronen.length; i++){
            neuronen[i].berechneNetzeingabe(eingaben);
            ausgaben[i] = neuronen[i].phiVonNet();
        }
        return ausgaben;
    }

    /**
     * Berechnet die Änderungen für die Ausgabeschicht.
     * 
     * @param lernrate die Lernrate
     * @param ausgabe die Ausgabe der letzten Schicht (Länge = Anzahl der Neuronen)
     */
    public void berechneAenderungen(double lernrate, double[] ausgabe){
        for (int j = 0; j < neuronen.length; j++){
            Neuron kn = neuronen[j];
            double delta = kn.phiStrichVonNet() * 
                           (kn.phiVonNet() - ausgabe[j]);
            kn.setzeAenderung(lernrate, delta);
        }
    }

    /**
     * Berechnet die Änderungen für eine verdeckte Schicht.
     * 
     * @param lernrate die Lernrate
     * @param nachfolgeSchicht die Schicht danach
     */
    public void berechneAenderungen(double lernrate, Schicht nachfolgeschicht){
        for (int j = 0; j < neuronen.length; j++){
            Neuron kn = neuronen[j];
            double s = 0;
            for (int k = 0; k < nachfolgeschicht.gibAnzahlNeuronen(); k++){
                s += nachfolgeschicht.gibNeuron(k).gibDelta() * 
                       nachfolgeschicht.gibNeuron(k).gibGewicht(j);
            }
            double delta = kn.phiStrichVonNet() * s;
            kn.setzeAenderung(lernrate, delta);
        }            
    }

    /**
     * Passe die Gewichtungen an und setzte Deltas auf 0.
     */
    public void aendereGewichtungen(){
        for (int j = 0; j < neuronen.length; j++){
            neuronen[j].aendereGewichtungen();
        }
    }

    /**
     * Gibt die Anzahl der Neuron in der Schicht zurück.
     * 
     * @return die Anzahl der Neuron
     */
    public int gibAnzahlNeuronen(){
        return neuronen.length;
    }

    /**
     * Gibt die Anzahl der Eingänge der Schicht zurück.
     * 
     * @return die Anzahl der Eingänge
     */
    public int gibAnzahlEingaenge(){
        return eingaenge;
    }

    /**
     * Gibt das i-te Neuron zurück.
     * 
     * @param i Nummer des Neurons
     */
    private Neuron gibNeuron(int i){
        return neuronen[i];
    }

    /**
     * Die Methode belegt die Neuronen der Schicht mit vorgefertigten Neuronen
     * @param pNeuronen das Array mit den Neuronen der Schicht
     */
    public void setzeNeuronen(Neuron[] pNeuronen) {
        neuronen = pNeuronen;
    }

    /**
     * Die Methode liefert eine String-Representation der Schicht des Netzes
     * @return eine String-Repraesentation der Schicht des Netzes
     */ 
    @Override
    public String toString() {
        String s = ""; 
        for (int i = 0; i < neuronen.length; i++) {
            s = s + "\nNeuron " + i + ":" + neuronen[i];
        }
        return s;
    }

    /**
     * Die Methode liefert eine neue Schicht, welche aus einer String-Repraesentation ausgelesen wird.
     * @param s die Zeichenkette mit der String-Repraesentation der Schicht
     * @return ein neues Objekt der Klasse Schicht mit der durch s repraesentierten Schicht
     */
    public static Schicht parseSchicht(String s) {
        String[] neuronenzeile = s.split("Neuron [0-9]*:");
        Neuron[] neuronen = new Neuron[neuronenzeile.length - 1];
        for (int i = 1; i < neuronenzeile.length; i++) {
            neuronen[i - 1] = Neuron.parseNeuron(neuronenzeile[i]);
        }
        int anzahlEingaenge = neuronen[0].gibAnzahlEingaenge();
        Schicht l = new Schicht(neuronen[0].gibAnzahlEingaenge(), neuronen.length, null);
        l.setzeNeuronen(neuronen);
        return l;
    }

}
