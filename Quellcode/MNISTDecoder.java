import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MNISTDecoder {
  
  public List<Datensatz> trainDigits;
  public List<Datensatz> testDigits; 
    
  public void loadData() {
    try {
      trainDigits = loadData("train-images.idx3", "train-labels.idx1");
      testDigits  = loadData("test-images.idx3", "test-labels.idx1");
    } catch(Exception e) {
      System.out.println(e);
    }    
  }

  public List<Datensatz> loadData(String datas, String labels) throws IOException {
    Path dataPath = Paths.get(datas);
    Path labelPath = Paths.get(labels);
    
    byte[] dataByte = Files.readAllBytes(dataPath);
    byte[] labelByte = Files.readAllBytes(labelPath);
    
    List<Datensatz> digits = new List<>();
    
    int readHeadData = 16;
    int readHeadLabel = 8;
    while(readHeadData < dataByte.length)  {
      double[] eingabe = new double[28*28];
        
      for (int i = 0; i < 28*28; i++) {
          eingabe[i] = 255.0 - dataByte[readHeadData++];
        }
      int label = labelByte[readHeadLabel++];
      double[] zielwert = new double[10];
      zielwert[label] = 1;
      digits.append(new Datensatz(eingabe, zielwert));
    }
    return digits;
  }

}
