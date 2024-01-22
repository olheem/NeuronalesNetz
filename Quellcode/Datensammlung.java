/**
 * Die Klasse Datensammlung enthaelt eine Sammlung von Trainingsdaten in Form 
 * von Objekten der Klasse Datensatz.
 * Sie dient dazu, ein neuronales Netz mit Trainigsdaten zu fuettern.
 * 
 * @author Daniel Garmann
 * @version 2024-01-20
 */
public class Datensammlung {
    private List<Datensatz> daten;

    /**
     * Konstruktor einer neuen Datenammlung fuer Trainingsdatensaetze
     */
    public Datensammlung() {
        daten = new List<>();
    }

    /**
     * Konstruktor einer neuen gefuellten Datenammlung fuer Trainingsdatensaetze
     * @param pDaten die schon gefuellten Daten
     */
    public Datensammlung(List<Datensatz> pDaten) {
        daten = pDaten;
    }

    /**
     * Ein neuer Datensatz wird der Datensammlung hinzugefuegt
     * @param pDatensatz der einzufuegende Datensatz
     */
    public void fuegeEin(Datensatz pDatensatz) {
        daten.append(pDatensatz);
    }

    /**
     * Alle Datensaetze werden geloescht
     */
    public void loescheAlle() {
        daten = new List<>();
    }

    /**
     * Die Liste der gespeicherten Datensaetze wird als Liste zurueckgegeben
     * @return die Liste aller Datensaetze
     */
    public List<Datensatz> gibDaten() {
        return daten;
    }

    public double[][] gibEingabenArray() {
        int anzahl = gibGroesse();
        daten.toFirst();
        Datensatz d = daten.getContent();
        double[][] eingaben = new double[anzahl][d.gibEingabe().length];
        int stelle = 0;
        while (daten.hasAccess()) {
            d = daten.getContent();
            eingaben[stelle] = d.gibEingabe();
            stelle++;
            daten.next();
        }
        return eingaben;
    }

    public double[][] gibZielwerteArray() {
        int anzahl = gibGroesse();
        daten.toFirst();
        Datensatz d = daten.getContent();
        double[][] zielwerte = new double[anzahl][d.gibZielwert().length];
        int stelle = 0;
        while (daten.hasAccess()) {
            d = daten.getContent();
            zielwerte[stelle] = d.gibZielwert();
            stelle++;
            daten.next();
        }
        return zielwerte;
    }

    /**
     * Die Anzahl der Trainingsdatensaetze der Datensammlung wird geliefert.
     * @return die Anzahl der gespeicherten Datensaetze
     */
    public int gibGroesse() {
        int ergebnis = 0;
        daten.toFirst();
        while (daten.hasAccess()) {
            ergebnis++;
            daten.next();
        }
        return ergebnis;
    }

    /** 
     * Die gespeicherten Datensaetze werden gemischt
     */
    public void mischen() {
        int anzahl = gibGroesse();
        for (int i = 0; i < anzahl * 5; i++) {
            int position = (int)(Math.random() * anzahl);
            daten.toFirst();
            for (int j = 0; j < position; j++) {
                daten.next();
            }
            Datensatz d = daten.getContent();
            daten.remove();
            daten.append(d);
        }  
    }

    /**
     * Die Methode liefert eine String-Representation der Datensammlung
     * @return eine String-Repraesentation der Datensammlung
     */
    @Override
    public String toString() {
        String s = "";    
        daten.toFirst();
        while (daten.hasAccess()) {
            s = s + daten.getContent() + "\n";
            daten.next();
        }
        return s;
    }

    /**
     * Die Methode liefert eine neue Datensammlung, welche aus einer String-Repraesentation ausgelesen wird.
     * @param s die Zeichenkette mit der String-Repraesentation einer Datensammlung
     * @return ein neues Objekt der Klasse Datensammlung mit den durch s repraesentierten Datensaetzen
     */
    public static Datensammlung parseDatensammlung(String s) {
        String[] zeilen = s.split("\n");
        List<Datensatz> daten = new List<>();
        for (int i = 0; i < zeilen.length; i++) {
            daten.append(Datensatz.parseDatensatz(zeilen[i]));
        }
        Datensammlung ergebnis = new Datensammlung();
        ergebnis.daten = daten;
        return ergebnis;
    }

}