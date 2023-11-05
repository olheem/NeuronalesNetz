/**
 * Die Sigmoidfunktion.
 * 
 * @author Dr. Oliver Heidbüchel
 * @version 2023-11-05
 */
public class Sigmoidfunktion extends Aktivierungsfunktion
{
    /**
     * Die Sigmoidfunktion.
     * 
     * @param x eine Zahl
     * @return der Funktionswert der Sigmoidfunktion an der Stelle x
     */
    public double phi(double x){
        return 1 / (1 + Math.exp(-x));
    }

    /**
     * Die Ableitung der Sigmoidfunktion.
     *
     * @param x eine Zahl
     * @return die Ableitung der Sigmoidfunktion an der Stelle x
     */
    public double phiStrich(double x){
        return phi(x) * (1 - phi(x));
    }
}
