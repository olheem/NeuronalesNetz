/**
 * Der Tangenshyperbolicus als Aktivierungsfunktion.
 * 
 * @author Dr. Oliver Heidbüchel
 * @version 2023-11-24
 */
public class TangensHyperbolicus extends Aktivierungsfunktion
{
    /**
     * Die Tangens hyperbolicus Funktion.
     * 
     * @param x eine Zahl
     * @return der Funktionswert des Tangens hyperbolicus an der Stelle x
     */
    public double phi(double x){
        return Math.tanh(x);
    }

    /**
     * Die Ableitung der Tangens hyperbolicus Funktion.
     *
     * @param x eine Zahl
     * @return die Ableitung des Tangens hyperbolicus an der Stelle x
     */
    public double phiStrich(double x){
        double phiVonX = phi(x);
        return 1 - phiVonX * phiVonX;
    }
}
