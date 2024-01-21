/**
 * Die Aktivierungsfunktion für ein neuronales Netz.
 * 
 * @author Dr. Oliver Heidbüchel
 * @version 2023-11-05
 * @author Daniel Garmann
 * @version 2024-01-20
 */
public abstract class Aktivierungsfunktion
{
    /**
     * Die Aktivierungsfunktion
     *
     * @param x eine Zahl
     * @return der Funktionswert der Aktivierungsfunktion an der Stelle x
     */
    public abstract double phi(double x);

    /**
     * Die Ableitung der Aktivierungsfunktion
     *
     * @param x eine Zahl
     * @return die Ableitung der Aktivierungsfunktion an der Stelle x
     */
    public abstract double phiStrich(double x);
    
        /**
     * Die Methode liefert eine neue Funktion, welche aus einer String-Repraesentation ausgelesen wird.
     * @param s die Zeichenkette mit der String-Repraesentation der Funktion
     * @return ein neues Objekt der Klasse Funktion mit der durch s repraesentierten Funktion
     */
    public static Aktivierungsfunktion parseFunktion(String s) {
        switch (s) {
            case "Sigmoid": return new Sigmoid();
            case "SoftSign": return new Softsign();
            case "TanHyp": return new TangensHyperbolicus();
        }
        return null;
    }
  
}
