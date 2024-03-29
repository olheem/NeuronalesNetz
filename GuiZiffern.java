import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.io.FileWriter;
import java.io.FileReader;

import NeuronalesNetz.*;
import GUILib.*;

/**
 *
 * Grafische Oberflaeche fuer Neuronale Netze zur Ziffernerkennung
 *
 * @version 2024-02-23
 * @author Daniel Garmann
 */

public class GuiZiffern extends JFrame {
    // Anfang Attribute
    private Netz neuronalesNetz = null;
    private Datensammlung trainingsdaten = new Datensammlung();

    private PanelZiffern jpZiffer = new PanelZiffern();
    private JButton jbNetzErstellen = new JButton();
    private JButton jbTrainiere = new JButton();
    private JButton jbLoeschen = new JButton();
    private JPanel jpNetzarchitektur = new JPanel(null, true);
    private JTextField jtfNetzarchitektur = new JTextField();
    private JComboBox<String> jcbFunktion = new JComboBox<String>();
    private DefaultComboBoxModel<String> jcbFunktionModel = new DefaultComboBoxModel<String>();
    private JLabel jlSchichten = new JLabel();
    private JLabel jlAktivierungsfunktion = new JLabel();
    private JTextField jtfEingabeSchicht = new JTextField();
    private JTextField htfAusgabeschicht = new JTextField();
    private JPanel jpTraining = new JPanel(null, true);
    private JTextField jtfLernrate = new JTextField();
    private JLabel jlLernrate = new JLabel();
    private JLabel lWiederholungen = new JLabel();
    private JTextField jtfWiederholungen = new JTextField();
    private JLabel lPaketgroesse = new JLabel();
    private JTextField jtfPaketgroesse = new JTextField();
    private JTextField jtfFehler = new JTextField();
    private JLabel jlFehler = new JLabel();
    private JButton jbNetzSpeichern = new JButton();
    private JButton jbNetzLaden = new JButton();
    private JButton jbDatenSpeichern = new JButton();
    private JButton jbDatenLaden = new JButton();
    private JButton jbNeueZiffer = new JButton();
    private JButton jbZeichenflaecheLoeschen = new JButton();
    private JButton jbZumAnfang = new JButton();
    private JButton jbVor = new JButton();
    private JButton jbZumEnde = new JButton();
    private JTextField jtfZiffer = new JTextField();
    private JButton jbErkennen = new JButton();
    private JButton bMNISTTrainigsdaten = new JButton();
    private JButton bMNISTTestdaten = new JButton();
    private JButton jbStatistik = new JButton();
  private JPanel jpDiagramm = new JPanel(null, true);
    // Ende Attribute

    public GuiZiffern (String title) {
        super (title);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 760; 
        int frameHeight = 535;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        Container cp = getContentPane();
        cp.setLayout(null);
        // Anfang Komponenten
        jpZiffer.setBounds(24, 8, 400, 400);
        jpZiffer.setOpaque(false);
        jpZiffer.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 0));
        cp.add(jpZiffer);
        jbNetzErstellen.setBounds(440, 232, 283, 25);
        jbNetzErstellen.setText("Netz erstellen");
        jbNetzErstellen.setMargin(new Insets(2, 2, 2, 2));
        jbNetzErstellen.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbNetzErstellen_ActionPerformed(evt);
                }
            });
        cp.add(jbNetzErstellen);
        jbTrainiere.setBounds(440, 416, 115, 25);
        jbTrainiere.setText("Training");
        jbTrainiere.setMargin(new Insets(2, 2, 2, 2));
        jbTrainiere.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbTrainiere_ActionPerformed(evt);
                }
            });
        cp.add(jbTrainiere);
        jbLoeschen.setBounds(440, 64, 283, 25);
        jbLoeschen.setText("Trainingsdaten loeschen");
        jbLoeschen.setMargin(new Insets(2, 2, 2, 2));
        jbLoeschen.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbLoeschen_ActionPerformed(evt);
                }
            });
        cp.add(jbLoeschen);
        jpNetzarchitektur.setBounds(440, 152, 289, 73);
        jpNetzarchitektur.setOpaque(false);
        jpNetzarchitektur.setBorder(BorderFactory.createTitledBorder("Netzarchitektur"));
        cp.add(jpNetzarchitektur);
        jtfNetzarchitektur.setBounds(179, 24, 54, 20);
        jcbFunktion.setBounds(139, 45, 110, 20);
        jlSchichten.setBounds(14, 24, 67, 20);
        jlAktivierungsfunktion.setBounds(13, 45, 124, 20);
        jtfNetzarchitektur.setText("20>20");
        jtfNetzarchitektur.setHorizontalAlignment(SwingConstants.CENTER);
        jpNetzarchitektur.add(jtfNetzarchitektur);
        jcbFunktion.setModel(jcbFunktionModel);
        jcbFunktionModel.addElement("Sigmoid");
        jcbFunktionModel.addElement("SoftSign");
        jcbFunktionModel.addElement("TanHyp");
        jcbFunktionModel.addElement("ReLU");
        jpNetzarchitektur.add(jcbFunktion);
        jlSchichten.setText("Schichten:");
        jpNetzarchitektur.add(jlSchichten);
        jlAktivierungsfunktion.setText("Aktivierungsfunktion:");
        jpNetzarchitektur.add(jlAktivierungsfunktion);
        jpTraining.setBounds(440, 296, 289, 113);
        jpTraining.setOpaque(false);
        jpTraining.setBorder(BorderFactory.createTitledBorder("Training"));
        cp.add(jpTraining);
        jtfLernrate.setBounds(131, 69, 70, 20);
        jlLernrate.setBounds(14, 69, 67, 20);
        lWiederholungen.setBounds(13, 45, 100, 20);
        jtfWiederholungen.setBounds(131, 45, 70, 20);
        lPaketgroesse.setBounds(13, 21, 100, 20);
        jtfPaketgroesse.setBounds(131, 21, 70, 20);
        jtfLernrate.setText("0.001");
        jtfLernrate.setHorizontalAlignment(SwingConstants.CENTER);
        jpTraining.add(jtfLernrate);
        jlLernrate.setText("Lernrate:");
        jpTraining.add(jlLernrate);
        lWiederholungen.setText("Wiederholungen:");
        jpTraining.add(lWiederholungen);
        jtfWiederholungen.setText("100");
        jtfWiederholungen.setHorizontalAlignment(SwingConstants.CENTER);
        jpTraining.add(jtfWiederholungen);
        lPaketgroesse.setText("Paketgroesse:");
        jpTraining.add(lPaketgroesse);
        jtfPaketgroesse.setText("1000");
        jtfPaketgroesse.setHorizontalAlignment(SwingConstants.CENTER);
        jpTraining.add(jtfPaketgroesse);
        jtfFehler.setBounds(651, 421, 70, 20);
        jlFehler.setBounds(574, 421, 67, 20);
        jtfFehler.setText("----");
        jtfFehler.setEditable(false);
        cp.add(jtfFehler);
        jlFehler.setText("Fehler:");
        jlFehler.setHorizontalAlignment(SwingConstants.RIGHT);
        cp.add(jlFehler);
        jtfEingabeSchicht.setBounds(136, 24, 46, 20);
        jtfEingabeSchicht.setText("28*28>");
        jtfEingabeSchicht.setEditable(false);
        jpNetzarchitektur.add(jtfEingabeSchicht);
        htfAusgabeschicht.setBounds(232, 24, 38, 20);
        htfAusgabeschicht.setText(">10");
        htfAusgabeschicht.setEditable(false);
        jpNetzarchitektur.add(htfAusgabeschicht);

        jbNetzSpeichern.setBounds(440, 264, 139, 25);
        jbNetzSpeichern.setText("Netz speichern...");
        jbNetzSpeichern.setMargin(new Insets(2, 2, 2, 2));
        jbNetzSpeichern.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbNetzSpeichern_ActionPerformed(evt);
                }
            });
        cp.add(jbNetzSpeichern);
        jbNetzLaden.setBounds(584, 264, 139, 25);
        jbNetzLaden.setText("Netz laden...");
        jbNetzLaden.setMargin(new Insets(2, 2, 2, 2));
        jbNetzLaden.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbNetzLaden_ActionPerformed(evt);
                }
            });
        cp.add(jbNetzLaden);
        jbDatenSpeichern.setBounds(440, 128, 139, 25);
        jbDatenSpeichern.setText("Daten speichern...");
        jbDatenSpeichern.setMargin(new Insets(2, 2, 2, 2));
        jbDatenSpeichern.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbDatenSpeichern_ActionPerformed(evt);
                }
            });
        cp.add(jbDatenSpeichern);
        jbDatenLaden.setBounds(584, 128, 139, 25);
        jbDatenLaden.setText("Daten laden...");
        jbDatenLaden.setMargin(new Insets(2, 2, 2, 2));
        jbDatenLaden.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbDatenLaden_ActionPerformed(evt);
                }
            });
        cp.add(jbDatenLaden);
        jbNeueZiffer.setBounds(226, 416, 198, 25);
        jbNeueZiffer.setText("Ziffer -> Trainingsdaten");
        jbNeueZiffer.setMargin(new Insets(2, 2, 2, 2));
        jbNeueZiffer.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbNeueZiffer_ActionPerformed(evt);
                }
            });
        cp.add(jbNeueZiffer);
        jbZeichenflaecheLoeschen.setBounds(24, 416, 190, 25);
        jbZeichenflaecheLoeschen.setText("Zeichenflaeche loeschen");
        jbZeichenflaecheLoeschen.setMargin(new Insets(2, 2, 2, 2));
        jbZeichenflaecheLoeschen.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbZeichenflaecheLoeschen_ActionPerformed(evt);
                }
            });
        cp.add(jbZeichenflaecheLoeschen);
    jbZumAnfang.setBounds(576, 8, 50, 50);
        jbZumAnfang.setText("|<");
        jbZumAnfang.setMargin(new Insets(2, 2, 2, 2));
        jbZumAnfang.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbZumAnfang_ActionPerformed(evt);
                }
            });
        jbZumAnfang.setFont(new Font("Dialog", Font.BOLD, 24));
        cp.add(jbZumAnfang);
    jbVor.setBounds(624, 8, 50, 50);
        jbVor.setText(">");
        jbVor.setMargin(new Insets(2, 2, 2, 2));
        jbVor.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbVor_ActionPerformed(evt);
                }
            });
        jbVor.setFont(new Font("Dialog", Font.BOLD, 24));
        cp.add(jbVor);
    jbZumEnde.setBounds(672, 8, 50, 50);
        jbZumEnde.setText(">|");
        jbZumEnde.setMargin(new Insets(2, 2, 2, 2));
        jbZumEnde.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbZumEnde_ActionPerformed(evt);
                }
            });
        jbZumEnde.setFont(new Font("Dialog", Font.BOLD, 24));
        cp.add(jbZumEnde);
        jtfZiffer.setBounds(424, 8, 49, 49);
        jtfZiffer.setHorizontalAlignment(SwingConstants.CENTER);
        jtfZiffer.setText("");
        jtfZiffer.setFont(new Font("Dialog", Font.PLAIN, 36));
        jtfZiffer.setEditable(false);
        cp.add(jtfZiffer);
        jbErkennen.setBounds(24, 456, 190, 25);
        jbErkennen.setText("Ziffer erkennen");
        jbErkennen.setMargin(new Insets(2, 2, 2, 2));
        jbErkennen.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbErkennen_ActionPerformed(evt);
                }
            });
        cp.add(jbErkennen);
        bMNISTTrainigsdaten.setBounds(440, 96, 139, 25);
        bMNISTTrainigsdaten.setText("MNIST Trainigsdaten");
        bMNISTTrainigsdaten.setMargin(new Insets(2, 2, 2, 2));
        bMNISTTrainigsdaten.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    bMNISTTrainigsdaten_ActionPerformed(evt);
                }
            });
        cp.add(bMNISTTrainigsdaten);
        bMNISTTestdaten.setBounds(584, 96, 139, 25);
        bMNISTTestdaten.setText("MNIST Testdaten");
        bMNISTTestdaten.setMargin(new Insets(2, 2, 2, 2));
        bMNISTTestdaten.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    bMNISTTestdaten_ActionPerformed(evt);
                }
            });
        cp.add(bMNISTTestdaten);
        jbStatistik.setBounds(224, 456, 203, 25);
        jbStatistik.setText("Statistik");
        jbStatistik.setMargin(new Insets(2, 2, 2, 2));
        jbStatistik.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbStatistik_ActionPerformed(evt);
                }
            });
        cp.add(jbStatistik);
    jpDiagramm.setBounds(480, 8, 80, 50);
    jpDiagramm.setOpaque(false);
    cp.add(jpDiagramm);
        // Ende Komponenten
        setResizable(false);
        setVisible(true);
    }

    // Anfang Methoden
    public void jbNetzErstellen_ActionPerformed(ActionEvent evt) {
        try {
            String schichtenText = jtfNetzarchitektur.getText().replaceAll(" ","");
            String[] schichtenTexte = schichtenText.split(">");
            int[] schichten = new int[schichtenTexte.length + 1];
            for (int i = 0; i < schichtenTexte.length; i++) {
                schichten[i] = Integer.parseInt(schichtenTexte[i]);
            }
            schichten[schichten.length - 1] = 10;
            Aktivierungsfunktion funktion = Aktivierungsfunktion.parseFunktion((String)jcbFunktion.getSelectedItem());
            neuronalesNetz = new Netz(28 * 28, schichten, funktion);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Fehler in Netzeingabe\nAnzahl Neuronen pro Schicht\nTrennung der Schichten mit >");  
        } 
    }

    public void jbTrainiere_ActionPerformed(ActionEvent evt) {
        if (neuronalesNetz != null) {
            try {
                //trainingsdaten.mischen();
                double lernrate = Double.parseDouble(jtfLernrate.getText());
                int wiederholungen = Integer.parseInt(jtfWiederholungen.getText());
                int paketgroesse = Integer.parseInt(jtfPaketgroesse.getText());
                double fehler = neuronalesNetz.trainiere(trainingsdaten.gibEingabenArray(),trainingsdaten.gibZielwerteArray(), lernrate, paketgroesse, wiederholungen);
                jtfFehler.setText(String.format("%f",fehler));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Bitte im gueltigen Zahlenformat eingeben!\nAnzahlen: Ganzzahl\nLenrrate: Dezimalzahl mit .-Notation");
            }    
        } else {
            JOptionPane.showMessageDialog(this, "Erst Netz erstellen!");
        }    
        jpZiffer.repaint();
    }

    public void jbLoeschen_ActionPerformed(ActionEvent evt) {
        trainingsdaten.loescheAlle();
    }

    public void jbNetzSpeichern_ActionPerformed(ActionEvent evt) {
        JFileChooser auswahldialog = new JFileChooser();
        auswahldialog.setCurrentDirectory(new File(System.getProperty("user.dir"))); // Setze das aktuelle Verzeichnis
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Netz-Dateien (*.nn)", "nn"); // Setze Filter auf Netz-Dateien
        auswahldialog.setFileFilter(filter);
        auswahldialog.setSelectedFile(new File("*.nn")); // Setzt Auswahl auf Filter
        int buttonauswahl = auswahldialog.showSaveDialog(this); // Zeigt den Speichern-Dialog

        if (buttonauswahl == JFileChooser.APPROVE_OPTION) { // Datei wurde ausgewaehlt
            File datei = auswahldialog.getSelectedFile();
            String dateiPfad = datei.getAbsolutePath();
            if (!dateiPfad.toLowerCase().endsWith(".nn")) { // .nn Extension anfuegen, falls noetig.
                dateiPfad += ".nn";
                datei = new File(dateiPfad);
            }
            try {
                FileWriter schreiber = new FileWriter(datei);
                schreiber.write(neuronalesNetz.toString());
                schreiber.close();
            } catch (Exception e) {
                System.err.println(e.toString());
            }

        }    
        repaint();
    }

    public void jbNetzLaden_ActionPerformed(ActionEvent evt) {
        JFileChooser auswahldialog = new JFileChooser();
        auswahldialog.setCurrentDirectory(new File(System.getProperty("user.dir"))); // Setze das aktuelle Verzeichnis
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Netz-Dateien (*.nn)", "nn"); // Setze Filter auf Netz-Dateien
        auswahldialog.setFileFilter(filter);
        auswahldialog.setSelectedFile(new File("*.nn")); // Setzt Auswahl auf Filter
        int buttonauswahl = auswahldialog.showOpenDialog(this); // Zeigt den Oeffnen-Dialog

        if (buttonauswahl == JFileChooser.APPROVE_OPTION) { // Datei wurde ausgewaehlt
            File datei = auswahldialog.getSelectedFile();
            try {
                BufferedReader leser = new BufferedReader(new FileReader(datei));
                String s = "";
                String zeile = leser.readLine();
                while (zeile != null) {
                    s = s + zeile + "\n";
                    zeile = leser.readLine();
                }
                neuronalesNetz = Netz.parseNetz(s);
                leser.close();
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }  
        repaint();  
    }

    public void jbDatenSpeichern_ActionPerformed(ActionEvent evt) {
        JFileChooser auswahldialog = new JFileChooser();
        auswahldialog.setCurrentDirectory(new File(System.getProperty("user.dir"))); // Setze das aktuelle Verzeichnis
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Daten-Dateien (*.dat)", "dat"); // Setze Filter auf Daten-Dateien
        auswahldialog.setFileFilter(filter);
        auswahldialog.setSelectedFile(new File("*.dat")); // Setzt Auswahl auf Filter
        int buttonauswahl = auswahldialog.showSaveDialog(this); // Zeigt den Speichern-Dialog

        if (buttonauswahl == JFileChooser.APPROVE_OPTION) { // Datei wurde ausgewaehlt
            File datei = auswahldialog.getSelectedFile();
            String dateiPfad = datei.getAbsolutePath();
            if (!dateiPfad.toLowerCase().endsWith(".dat")) { // .dat Extension anfuegen, falls noetig.
                dateiPfad += ".dat";
                datei = new File(dateiPfad);
            }
            try {
                FileWriter schreiber = new FileWriter(datei);
                schreiber.write(trainingsdaten.toString());
                schreiber.close();
            } catch (Exception e) {
                System.err.println(e.toString());
            }

        }    
        repaint();
    }

    public void jbDatenLaden_ActionPerformed(ActionEvent evt) {
        JFileChooser auswahldialog = new JFileChooser();
        auswahldialog.setCurrentDirectory(new File(System.getProperty("user.dir"))); // Setze das aktuelle Verzeichnis
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Daten-Dateien (*.dat)", "dat"); // Setze Filter auf Daten-Dateien
        auswahldialog.setFileFilter(filter);
        auswahldialog.setSelectedFile(new File("*.dat")); // Setzt Auswahl auf Filter
        int buttonauswahl = auswahldialog.showOpenDialog(this); // Zeigt den Oeffnen-Dialog

        if (buttonauswahl == JFileChooser.APPROVE_OPTION) { // Datei wurde ausgewaehlt
            File datei = auswahldialog.getSelectedFile();
            try {
                BufferedReader leser = new BufferedReader(new FileReader(datei));
                String s = "";
                String zeile = leser.readLine();
                while (zeile != null) {
                    s = s + zeile + "\n";
                    zeile = leser.readLine();
                }
                trainingsdaten = Datensammlung.parseDatensammlung(s);
                leser.close();
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        } 
        trainingsdaten.gibDaten().toFirst(); 
        zeigeAktuellenDatensatz();
        repaint();  
    }

    private void zeigeAktuellenDatensatz() {
        jpZiffer.neuesBild();
        jpZiffer.repaint();
        GUILib.List<Datensatz> l = trainingsdaten.gibDaten(); 
        int ziffer = jpZiffer.zeichneDatensatz(l.getContent());
        jtfZiffer.setText("" + ziffer);
    }

    public void jbNeueZiffer_ActionPerformed(ActionEvent evt) {
        int ziffer = Integer.parseInt(JOptionPane.showInputDialog(this, "Welcher Ziffer entspricht das Bild?"));
        trainingsdaten.fuegeEin(jpZiffer.erstelleDatensatz(ziffer));
        jpZiffer.repaint();
    }

    public void jbZeichenflaecheLoeschen_ActionPerformed(ActionEvent evt) {
        jpZiffer.neuesBild();
        jtfZiffer.setText("");
        jpZiffer.repaint();

    }

    public void jbZumAnfang_ActionPerformed(ActionEvent evt) {
        trainingsdaten.gibDaten().toFirst(); 
        zeigeAktuellenDatensatz();
    }

    public void jbVor_ActionPerformed(ActionEvent evt) {
        trainingsdaten.gibDaten().next();     
        zeigeAktuellenDatensatz();
    }

    public void jbZumEnde_ActionPerformed(ActionEvent evt) {
        trainingsdaten.gibDaten().toLast(); 
        zeigeAktuellenDatensatz();
    }
  
  private void aktualisiereDiagramm(double[] ausgabe, int ziffer) {
    Graphics g = jpDiagramm.getGraphics();
    g.setColor(this.getBackground());
    g.fillRect(0, 0, 80, 50);
      
    for (int i = 0; i < 10; i++) {
      if (i == ziffer) {
        g.setColor(Color.RED);
      } else {
        g.setColor(Color.WHITE);
      } 
      g.fillRect(i * 8, 40 - (int)(ausgabe[i] * 40), 8, (int)(ausgabe[i] * 40));
      g.setColor(Color.BLACK);
      g.drawRect(i * 8, 40 - (int)(ausgabe[i] * 40), 8, (int)(ausgabe[i] * 40));
      g.drawString("" + i, i * 8, 50);
    } 
    
  }
  
    public void jbErkennen_ActionPerformed(ActionEvent evt) {
        if (neuronalesNetz == null) {
            JOptionPane.showMessageDialog(this, "Erst ein neuronales Netz erstellen");
        } else {
            Datensatz d = jpZiffer.erstelleDatensatz(0);
            double[] eingabe = d.gibEingabe();
            double[] ausgabe = neuronalesNetz.berechne(eingabe);
            Datensatz z = new Datensatz(eingabe, ausgabe);
            int ziffer = jpZiffer.zeichneDatensatz(z);
            jtfZiffer.setText("" + ziffer);
            aktualisiereDiagramm(ausgabe, ziffer);
        }
    }

    public void bMNISTTrainigsdaten_ActionPerformed(ActionEvent evt) {
        MNISTDecoder decoder = new MNISTDecoder();
        trainingsdaten = decoder.loadData();
        trainingsdaten.gibDaten().toFirst(); 
        zeigeAktuellenDatensatz();
    }

    public void bMNISTTestdaten_ActionPerformed(ActionEvent evt) {
        MNISTDecoder decoder = new MNISTDecoder();
        trainingsdaten = decoder.loadTest();
        trainingsdaten.gibDaten().toFirst(); 
        zeigeAktuellenDatensatz();
    } 

    public void jbStatistik_ActionPerformed(ActionEvent evt) {
        if (neuronalesNetz == null) {
            JOptionPane.showMessageDialog(this, "Erst ein neuronales Netz erstellen");
        } else {
            GUILib.List<Datensatz> daten = trainingsdaten.gibDaten();
            daten.toFirst();
            int[][] erkannt = new int[10][2];
            while (daten.hasAccess()) {
                Datensatz d = daten.getContent();
                double[] eingabe = d.gibEingabe();
                double[] zielwert = d.gibZielwert();
                double[] ausgabe = neuronalesNetz.berechne(eingabe);
                int soll = 0;
                int ist = 0;
                for (int i = 1; i < 10; i++) {
                    if (zielwert[i] > zielwert[soll]) soll = i;
                    if (ausgabe[i] > ausgabe[ist]) ist = i;
                }
                if (soll == ist) {
                    erkannt[soll][0]++;
                } else {
                    erkannt[soll][1]++;
                }
                daten.next();
            } // end of while
            String s = "";
            int summerichtig = 0;
            int summefalsch = 0;
            for (int i = 0; i < 10; i++) {
                s += "Ziffer " + i + ": " + erkannt[i][0] + " richtig / " + erkannt[i][1] + " falsch\n";
                summerichtig += erkannt[i][0];
                summefalsch += erkannt[i][1];
            }
            s += "-----------------------------------------\n";
            s += "Gesamt: " + summerichtig + " richtig / " + summefalsch + " falsch\n";
            s += "Quote: " + Math.round(((double)summerichtig) / (summerichtig + summefalsch) * 100) + "%";
            JOptionPane.showMessageDialog(this, s, "Auswertung", JOptionPane.INFORMATION_MESSAGE);
        }
    } 

    // Ende Methoden
    public static void main(String[] args) {
        new GuiZiffern("Gui-Ziffern");
    }
}

