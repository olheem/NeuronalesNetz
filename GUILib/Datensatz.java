package GUILib;

/**
 * Die Klasse Datensatz enthaelt die Daten fuer einen einzelnen Trainingsdatensatz
 * fuer ein neuronales Netz. Mit Objekten dieser Klasse kann ein neuronales Netz
 * trainiert werden.
 * 
 * @author Daniel Garmann
 * @version 2023-04-11
 */
public class Datensatz {
    // Eingabe und Zielwerte eines Trainingsdatensatz sind eindimensionale Array ueber den Datentyp double
    private double[] eingabe;
    private double[] zielwert;

    /**
     * Konstruktor der Klasse fuer einen neuen Datensatz
     * @param pEingabe die Eingabewerte fuer das neuronale Netz
     * @param pZielwert die Zielwerte dieses Datensatzes, die vom neuronale Netz gelernt werden sollen
     */
    public Datensatz(double[] pEingabe, double[] pZielwert) {
        eingabe = pEingabe;
        zielwert = pZielwert;
    }

    /**
     * Die Methode liefert die Eingaben dieses Trainingsdatensatzes
     * @return die Eingabewerte fuer das neuronale Netz
     */
    public double[] gibEingabe() {
        return eingabe;
    }

    /**
     * Die Methode liefert die Zielwerte dieses Trainingsdatensatzes
     * @return die Zielwerte dieses Datensatzes, die vom neuronalen Netz gelernt werden sollen.
     */
    public double[] gibZielwert() {
        return zielwert;
    }  

    /**
     * Die Methode liefert eine String-Representation des Datensatzes
     * @return eine String-Repraesentation des Datensatzes
     */
    @Override
    public String toString() {
        String s = String.format("%f", eingabe[0]);
        for (int i = 1; i < eingabe.length; i++) {
            s = s + String.format(";%f", eingabe[i]);
        }
        s = s + String.format(">%f", zielwert[0]);
        for (int i = 1; i < zielwert.length; i++) {
            s = s + String.format(";%f", zielwert[i]);
        }
        return s; 
    }   

    /**
     * Die Methode liefert einen neuen Datensatz, welcher aus einer String-Repraesentation ausgelesen wird.
     * @param s die Zeichenkette mit der String-Repraesentation eines Datensatze
     * @return ein neues Objekt der Klasse Datensatz mit dem durch s repraesentierten Datensatz
     */
    public static Datensatz parseDatensatz(String s) {
        s = s.replaceAll(",",".");
        String[] teile = s.split(">");
        String[] eingabeString = teile[0].split(";");
        String[] zielwertString = teile[1].split(";");
        double[] eingabe = new double[eingabeString.length];
        for (int i = 0; i < eingabe.length; i++){
            eingabe[i] = Double.parseDouble(eingabeString[i]);
        }
        double[] zielwert = new double[zielwertString.length];
        for (int i = 0; i < zielwert.length; i++){
            zielwert[i] = Double.parseDouble(zielwertString[i]);
        }
        return new Datensatz(eingabe, zielwert);
    }    
}
