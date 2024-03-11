package GUILib;

import java.io.*;

public class MNISTDecoder {

    private Datensammlung load(String pDateiname) {
        List<Datensatz> digits = new List<Datensatz>();

        try {
            FileReader filereader = new FileReader(pDateiname);
            BufferedReader reader = new BufferedReader(filereader);
            String line = reader.readLine();
            while (line != null){
                double[] ausgabe = new double[10];
                double[] eingabe = new double[784];
                String[] eintraege = line.split(",");
                int z = Integer.parseInt(eintraege[0]);
                ausgabe[z] = 1;
                for (int i = 1; i < 785; i++){
                    eingabe[i - 1] = Double.parseDouble(eintraege[i]);
                }
                Datensatz d = new Datensatz(eingabe, ausgabe);
                digits.append(d);
                line = reader.readLine();
            }
            reader.close();
            return new Datensammlung(digits);
        } catch (Exception ex){
            System.err.println("Fehler beim Einlesen der Trainingsdaten!");
            return new Datensammlung();
        }
    }

    public Datensammlung loadData() {
        return load("mnist_train.csv");
    }

    public Datensammlung loadTest() {
        return load("mnist_test.csv");

    }

}
