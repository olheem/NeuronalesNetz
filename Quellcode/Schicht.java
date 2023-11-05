/**
 * Eine Schicht in einem neuronalen Netz.
 * 
 * @author Dr. Oliver Heidbüchel
 * @version 2023-03-29
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
            double delta = kn.phiStrichVonNet() * (kn.phiVonNet() - ausgabe[j]);
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
                s += nachfolgeschicht.gibNeuron(k).gibDelta() * nachfolgeschicht.gibNeuron(k).gibGewicht(j);
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
}
