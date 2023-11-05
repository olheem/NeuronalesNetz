/**
 * Die Aktivierungsfunktion für ein neuronales Netz.
 * 
 * @author Dr. Oliver Heidbüchel
 * @version 2023-11-05
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
}
