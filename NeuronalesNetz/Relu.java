package NeuronalesNetz;

/**
 * Die ReLU-Funktion als Aktivierungsfunktion.
 * 
 * @author Daniel Garmann
 * @version 2024-02-25
 */
public class Relu extends Aktivierungsfunktion
{
    private final double alpha = 0.01;
    /**
     * Die ReLU-funktion.
     * 
     * @param x eine Zahl
     * @return der Funktionswert der ReLU-Funktion an der Stelle x
     */
    public double phi(double x){
        if (x > 0) {
            return x;
        } else {
            return alpha * x;
        }
    }

    /**
     * Die Ableitung der ReLU-Funktion.
     *
     * @param x eine Zahl
     * @return die Ableitung der ReLU-Funktion an der Stelle x
     */
    public double phiStrich(double x){
        if (x > 0) {
            return 1;
        } else {
            return alpha;
        }
    }

    /**
     * Die Methode liefert eine String-Representation der Funktion
     * @return eine String-Repraesentation der Funktion
     */ 
    @Override
    public String toString() {
        return "ReLU";
    }
}

