/**
 * Die Softsign-Funktion als Aktivierungsfunktion.
 * 
 * @author Dr. Oliver Heidbüchel
 * @version 2023-11-24
 */
public class Softsign extends Aktivierungsfunktion
{
    /**
     * Die Softsign-Funktion.
     * 
     * @param x eine Zahl
     * @return der Funktionswert der Softsign-Funktion an der Stelle x
     */
    public double phi(double x){
        return x / (Math.abs(x) + 1);
    }

    /**
     * Die Ableitung der Softsign-Funktion.
     *
     * @param x eine Zahl
     * @return die Ableitung der Softsign-Funktion an der Stelle x
     */
    public double phiStrich(double x){
        double d = Math.abs(x) + 1;
        return 1 / (d * d);
    }
}
