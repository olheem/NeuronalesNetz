import java.io.*;

public class MNIST1 extends CSV
{
    public MNIST1(){
    }
    
    public void init(){
        int[] eingabeSpalten = new int[784];
        for (int i = 0; i < 784; i++){
            eingabeSpalten[i] = i + 1;
        }
        super.init("mnist_train.csv", ',', 60000, eingabeSpalten, new int[]{0}, new double[]{10});
        erzeugeNetz(new int[]{20, 20, 10}, new NeuronalesNetz.Sigmoid());
    }

    /**
     * Trainiere das Netz
     * 
     * @return der Fehler
     */
    public double trainiere(){
        return trainiere(0.001, 1000, 100);
    }

    /**
     * Teste das Netz mit den MNIST-Trainingsdaten<br>
     * Die Ausgabe erfolgt über die Konsole.
     */
    public void teste(){
        int korrekt = 0;
        int[] abweichungen = new int[10];

        try {
            FileReader filereader = new FileReader("mnist_test.csv");
            BufferedReader reader = new BufferedReader(filereader);
            String line = reader.readLine();
            while (line != null){
                String[] eintraege = line.split(",");
                int zahl = Integer.parseInt(eintraege[0]);
                double daten[] = new double[784];
                for (int i = 1; i < 785; i++){
                    daten[i - 1] = Double.parseDouble(eintraege[i]);
                }
                double ausgabe[] = berechne(daten);

                int berechnet = (int)ausgabe[0];
                System.out.println(zahl + " - " + berechnet);
                if (zahl == berechnet) korrekt++; else abweichungen[zahl]++;
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception ex){
            System.err.println("Fehler beim Einlesen der Trainingsdaten!");
        }

        System.out.println("Korrekt: " + korrekt);
        System.out.println("Anzahl der Fehler bei ...");
        for (int i = 0; i < 10; i++){
            System.out.println("   ... " + i + ": " + abweichungen[i]);
        }
        System.out.println("insgesamt: " + (10000 - korrekt));
    }
}



