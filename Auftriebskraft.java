public class Auftriebskraft extends CSV
{
    /**
     * Konstruktor der Klasse Auftriebskraft
     */
    public Auftriebskraft(){
    }
    
    /**
     * Initialisiere das Netz
     */
    public void init(){
        super.init("Auftriebskraft.csv", ';', 32, new int[]{1}, new int[]{2}, new double[]{2});
        erzeugeNetz(new int[]{3,5,3}, new NeuronalesNetz.Sigmoid());
    }

    /**
     * Trainiere das Netz
     * 
     * @return der Fehler
     */
    public double trainiere(){
        return trainiere(0.001, 16, 500000);
    }

    /**
     * Teste das Netz
     */
    public void teste(){
        for (int i = 0; i < 150; i += 10){
            System.out.println(i + ";" + berechne(new double[]{i})[0]);
        }
    }
}



