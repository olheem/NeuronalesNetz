package NeuronalesNetz;

/**
 * Die Sigmoidfunktion als Aktivierungsfunktion.
 * 
 * @author Dr. Oliver Heidbüchel
 * @version 2023-11-24
 * @author Daniel Garmann
 * @version 2024-01-20
 */
public class Sigmoid extends Aktivierungsfunktion
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
        double phiVonX = phi(x);
        return phiVonX * (1 - phiVonX);
    }

    /**
     * Die Methode liefert eine String-Representation der Funktion
     * @return eine String-Repraesentation der Funktion
     */ 
    @Override
    public String toString() {
        return "Sigmoid";
    }
}

