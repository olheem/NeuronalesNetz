import java.io.*;

/**
 * Das neuronale Netzwerk wird trainiert,
 * um handgeschriebene Ziffern zu erkennen.
 * 
 * @author Dr. Oliver Heidbüchel
 * @version 2023-04-26
 */
public class MNIST
{
    private Netz n;
    private double[][] eingabe;
    private double[][] ausgabe;
    double lernrate = 0.003;
    int paketGroesse = 1000;
    int wiederholungen = 100;

    public MNIST()
    {
        n = new Netz(784, new int[]{20,20,10});

        // lese Trainingsdaten
        int anzahl = 60000;
        eingabe = new double[anzahl][784];
        ausgabe = new double[anzahl][10];

        int counter = 0;
        try {
            FileReader filereader = new FileReader("mnist_train.csv");
            BufferedReader reader = new BufferedReader(filereader);
            String line = reader.readLine();
            while (line != null && counter < anzahl){
                String[] eintraege = line.split(",");
                int z = Integer.parseInt(eintraege[0]);
                ausgabe[counter][z] = 1;
                for (int i = 1; i < 785; i++){
                    eingabe[counter][i - 1] = Double.parseDouble(eintraege[i]);
                }
                counter++;
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception ex){
            System.err.println("Fehler beim Einlesen der Trainingsdaten!");
        }
    }

    public double trainiere(double lernrate, int paketGroesse, int wiederholungen){
        this.lernrate = lernrate;
        this.paketGroesse = paketGroesse;
        this.wiederholungen = wiederholungen;
        return trainiere();
    }

    public double trainiere(){
        long beginn = System.nanoTime();
        double fehler = n.trainiere(eingabe, ausgabe, lernrate, paketGroesse, wiederholungen);
        long dauer = System.nanoTime() - beginn;
        dauer /= 1000000000;
        System.out.println("Training: " + dauer + " Sekunden, d.h. " + (dauer / 60 / 60) % 60 + " Stunden " + (dauer / 60) % 60 + " Minuten " + dauer % 60 + " Sekunden");
        return fehler;
    }

    public void teste(){
        int korrekt = 0;
        int abweichungen = 0;

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
                double ausgabe[] = n.berechne(daten);

                double maxWert = ausgabe[0];
                int maxStelle = 0;
                for (int i = 1; i < 10; i++){
                    if (ausgabe[i] > maxWert){
                        maxWert = ausgabe[i];
                        maxStelle = i;
                    }
                }
                int berechnet = maxStelle;
                System.out.println(zahl + " - " + berechnet);
                if (zahl == berechnet) korrekt++; else abweichungen++;
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception ex){
            System.err.println("Fehler beim Einlesen der Trainingsdaten!");
        }

        System.out.println("Korrekt: " + korrekt);
        System.out.println("Abweichungen: " + abweichungen);
    }

}
