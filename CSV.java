import java.io.*;
import NeuronalesNetz.*;

/**
 * Training eines neuronalen Netzes mit Trainingsdaten aus einer CSV-Datei.
 * 
 * @author Dr. Oliver Heidbüchel
 * @version 2024-03-02
 */
public class CSV
{
    private Netz netz;
    private double[][] eingabe;
    private double[][] ausgabe;
    private double[] faktoren;

    /**
     * Konstruktor für die Klasse CSV
     */
    public CSV(){
    }
    
    /**
     * Die Daten werden aus aus einer CSV-Datei eingelesen
     * 
     * @param pfad Pfad der CSV-Datei
     * @param trennzeichen das Trennzeichen
     * @param anzahlDaten die Anzahl der Zeilen
     * @param eingabeSpalten Nummern der Spalten der Eingaben
     * @param ausgabeSpalten Nummern der Spalten der Ausgaben
     * @param faktoren die Werte in den einzelnen Ausgabespalten werden durch die entsprechenden Faktoren geteilt vor dem Training
     */
    public void init(String pfad, char trennzeichen, int anzahlDaten, int[] eingabeSpalten, int[] ausgabeSpalten, double[] faktoren)
    {
        eingabe = new double[anzahlDaten][eingabeSpalten.length];
        ausgabe = new double[anzahlDaten][ausgabeSpalten.length];
        this.faktoren = faktoren;

        int counter = 0;
        try {
            FileReader filereader = new FileReader(pfad);
            BufferedReader reader = new BufferedReader(filereader);
            String line = reader.readLine();
            while (line != null && counter < anzahlDaten){
                String[] eintraege = line.split("" + trennzeichen);
                for (int i = 0; i < eingabeSpalten.length; i++){
                    eingabe[counter][i] = Double.parseDouble(eintraege[eingabeSpalten[i]]);
                }
                for (int i = 0; i < ausgabeSpalten.length; i++){
                    ausgabe[counter][i] = Double.parseDouble(eintraege[ausgabeSpalten[i]]) / faktoren[i];
                }
                counter++;
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception ex){
            System.err.println("Fehler beim Einlesen der Trainingsdaten!");
        }
    }
    
    /**
     * Erzeuge ein neuronales Netz
     * 
     * @param neuronenProSchicht Anzahl der Neuronen pro Schicht (exklusive der Ausgabeschicht)
     * @param af Aktivierungsfunktion
     */
    public void erzeugeNetz(int[] anzahlNeuronenProSchicht, Aktivierungsfunktion af){
        int[] neuronenAnzahlen = new int[anzahlNeuronenProSchicht.length + 1];
        for (int i = 0; i < anzahlNeuronenProSchicht.length; i++){
            neuronenAnzahlen[i] = anzahlNeuronenProSchicht[i];
        }
        neuronenAnzahlen[anzahlNeuronenProSchicht.length] = ausgabe[0].length;
        netz = new Netz(eingabe[0].length, neuronenAnzahlen, af);
    }
        
    /**
     * Trainiere das neuronale Netz
     * 
     * @param lernrate die Lernrate
     * @param paketGroesse die Paketgröße
     * @param wiederholungen die Anzahl der Wiederholungen
     * @return der Fehler
     */
    public double trainiere(double lernrate, int paketGroesse, int wiederholungen){
        double fehler = netz.trainiere(eingabe, ausgabe, lernrate, paketGroesse, wiederholungen);
        return fehler;
    }

    /**
     * Berechne die Ausgabe des Netzes für eine Eingabe.
     * Die Ausgabe des Netzes wird wieder mit den Faktoren multipliziert,
     * so dass die Rückgabe auch wieder zu den eingelesenen Daten passt.
     * 
     * @param eingabe Eingabewerte
     * @return berechnete Ausgabe unter Berücksichtigung der Faktoren
     */
    public double[] berechne(double[] eingabe){
        double[] ergebnis = netz.berechne(eingabe);
        for (int i = 0; i < ergebnis.length; i++){
            ergebnis[i] *= faktoren[i];
        }
        return ergebnis;
    }
}






