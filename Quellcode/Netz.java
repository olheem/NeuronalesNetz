/**
 * Ein neuronales Netz
 * 
 * @author Dr. Oliver Heidbüchel
 * @version 2023-10-21
 */
public class Netz
{
    private Schicht[] schichten;

    /**
     * Ein neuronales Netz mit mehreren Schichten.
     * 
     * @param eingaenge Anzahl der Eingänge für die erste Schicht
     * @param neuronenProSchicht die Anzahl der Knoten für die einzelnen Schichten
     * @param af die Aktivierungsfunktion
     */
    public Netz(int eingaenge, int[] neuronenProSchicht, Aktivierungsfunktion af)
    {
        schichten = new Schicht[neuronenProSchicht.length];
        schichten[0] = new Schicht(eingaenge, neuronenProSchicht[0], af);
        for (int i = 1; i < schichten.length; i++){
            schichten[i] = new Schicht(neuronenProSchicht[i - 1], neuronenProSchicht[i], af);
        }
    }

    /**
     * Berechnet die Ausgaben für die gegebenen Eingaben.
     * Die Länge des Arrays eingaben muss so groß sein wie die Anzahl der Eingänge der ersten Schicht.
     * 
     * @param eingaben die Eingaben
     * @param Ausgaben der letzten Schicht
     */
    public double[] berechne(double[] eingaben){
        double[] ausgaben = eingaben;
        for (int i = 0; i < schichten.length; i++){
            ausgaben = schichten[i].berechne(ausgaben);
        }
        return ausgaben;
    }

    /** 
     * Trainiert das Netz mit gegebenen Trainigsdaten.
     * 
     * @param eingabe Eingabedaten
     * (eingabe[i].length muss gleich der Anzahl der Neuronen in der ersten Schicht sein, eingabe.length muss gleich ausgabe.length sein)
     * @param ausgabe Ausgabedaten
     * (ausgabe[i].length muss gleich der Anzahl der Neuronen in der Ausgabeschicht sein, eingabe.length muss gleich ausgabe.length sein)
     * @param lernrate die Lernrate
     * @param paketGroesse die Anzahl der Datensätze, die trainiert wird, bevor die Änderungen geschrieben werden
     * Wenn paketGroesse die Anzahl der Eingabedaten nicht teilt, so werden "überzählige" Datensätze weggelassen.
     * @param wiederholungen die Anzahl der Durchläufe der Trainingsdaten
     * @return der durchschnittliche Fehler für alle Datensätze nach dem letzten Training
     */
    public double trainiere(double[][] eingabe, double[][] ausgabe, double lernrate, int paketGroesse, int wiederholungen){
        int pakete = eingabe.length / paketGroesse;
        for (int h = 0; h < wiederholungen; h++){
            for (int i = 0; i < pakete; i++){
                for (int j = paketGroesse * i; j < paketGroesse * (i+1); j++){
                    double[] berechnet = berechne(eingabe[j]);
                    schichten[schichten.length - 1].berechneAenderungen(lernrate, ausgabe[j]);
                    for (int k = schichten.length - 2; k >= 0; k--){
                        schichten[k].berechneAenderungen(lernrate, schichten[k + 1]);
                    }
                }
                aendereGewichtungen();
            }
        }
        
        double f = 0;
        for (int i = 0; i < eingabe.length; i++){
            f += fehler(ausgabe[0], berechne(eingabe[0]));
        }
        return f /= eingabe.length;
    }

    /**
     * Die Gewichtungen werden geändert und die Deltas auf 0 gesetzt.
     */
    public void aendereGewichtungen(){
        for (int i = 0; i < schichten.length; i++){
            schichten[i].aendereGewichtungen();
        }
    }

    /**
     * Berechnet den Fehler.
     * 
     * @param t die tatsächlichen Werte
     * @param o die berechneten Werte
     * @return der Fehler
     */
    public double fehler(double[] t, double[] o){
        double summe = 0;
        for (int i = 0; i < t.length; i++){
            double faktor = t[i] - o[i];
            summe += faktor * faktor;
        }
        return summe / 2;
    }
}



