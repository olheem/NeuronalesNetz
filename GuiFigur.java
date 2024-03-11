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
 * Grafische Oberflaeche fuer Neuronale Netze auf Figuren-Erkennung
 *
 * @version 2024-02-25
 * @author Daniel Garmann
 */

public class GuiFigur extends JFrame {
    // Anfang Attribute
    private Netz neuronalesNetz = null;
    private Datensammlung trainingsdaten = new Datensammlung();
    private int datensatzNummer = 0;

    private PanelFigur jpFigur = new PanelFigur();
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
    private JTextField jtfFigur = new JTextField();
    private JButton jbErkenne = new JButton();
    private JLabel jlNummer = new JLabel();
    private JButton jbStatistik = new JButton();
    // Ende Attribute

    public GuiFigur (String title) {
        super (title);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 760; 
        int frameHeight = 521;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        Container cp = getContentPane();
        cp.setLayout(null);
        // Anfang Komponenten
        jpFigur.setBounds(24, 8, 400, 400);
        jpFigur.setOpaque(false);
        jpFigur.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 0));
        cp.add(jpFigur);
        jbNetzErstellen.setBounds(440, 208, 283, 25);
        jbNetzErstellen.setText("Netz erstellen");
        jbNetzErstellen.setMargin(new Insets(2, 2, 2, 2));
        jbNetzErstellen.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbNetzErstellen_ActionPerformed(evt);
                }
            });
        cp.add(jbNetzErstellen);
        jbTrainiere.setBounds(440, 392, 115, 25);
        jbTrainiere.setText("Training");
        jbTrainiere.setMargin(new Insets(2, 2, 2, 2));
        jbTrainiere.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbTrainiere_ActionPerformed(evt);
                }
            });
        cp.add(jbTrainiere);
        jbLoeschen.setBounds(440, 72, 283, 25);
        jbLoeschen.setText("Trainingsdaten loeschen");
        jbLoeschen.setMargin(new Insets(2, 2, 2, 2));
        jbLoeschen.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbLoeschen_ActionPerformed(evt);
                }
            });
        cp.add(jbLoeschen);
        jpNetzarchitektur.setBounds(440, 128, 289, 73);
        jpNetzarchitektur.setOpaque(false);
        jpNetzarchitektur.setBorder(BorderFactory.createTitledBorder("Netzarchitektur"));
        cp.add(jpNetzarchitektur);
        jtfNetzarchitektur.setBounds(179, 24, 54, 20);
        jcbFunktion.setBounds(139, 45, 110, 20);
        jlSchichten.setBounds(14, 24, 67, 20);
        jlAktivierungsfunktion.setBounds(13, 45, 124, 20);
        jtfNetzarchitektur.setText("30");
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
        jpTraining.setBounds(440, 272, 289, 113);
        jpTraining.setOpaque(false);
        jpTraining.setBorder(BorderFactory.createTitledBorder("Training"));
        cp.add(jpTraining);
        jtfLernrate.setBounds(131, 69, 70, 20);
        jlLernrate.setBounds(14, 69, 67, 20);
        lWiederholungen.setBounds(13, 45, 100, 20);
        jtfWiederholungen.setBounds(131, 45, 70, 20);
        lPaketgroesse.setBounds(13, 21, 100, 20);
        jtfPaketgroesse.setBounds(131, 21, 70, 20);
        jtfLernrate.setText("0.05");
        jtfLernrate.setHorizontalAlignment(SwingConstants.CENTER);
        jpTraining.add(jtfLernrate);
        jlLernrate.setText("Lernrate:");
        jpTraining.add(jlLernrate);
        lWiederholungen.setText("Wiederholungen:");
        jpTraining.add(lWiederholungen);
        jtfWiederholungen.setText("1000");
        jtfWiederholungen.setHorizontalAlignment(SwingConstants.CENTER);
        jpTraining.add(jtfWiederholungen);
        lPaketgroesse.setText("Paketgroesse:");
        jpTraining.add(lPaketgroesse);
        jtfPaketgroesse.setText("10");
        jtfPaketgroesse.setHorizontalAlignment(SwingConstants.CENTER);
        jpTraining.add(jtfPaketgroesse);
        jtfFehler.setBounds(651, 397, 70, 20);
        jlFehler.setBounds(574, 397, 67, 20);
        jtfFehler.setText("----");
        jtfFehler.setEditable(false);
        cp.add(jtfFehler);
        jlFehler.setText("Fehler:");
        jlFehler.setHorizontalAlignment(SwingConstants.RIGHT);
        cp.add(jlFehler);
        jtfEingabeSchicht.setBounds(136, 24, 46, 20);
        jtfEingabeSchicht.setText("10*10>");
        jtfEingabeSchicht.setEditable(false);
        jpNetzarchitektur.add(jtfEingabeSchicht);
        htfAusgabeschicht.setBounds(232, 24, 38, 20);
        htfAusgabeschicht.setText(">3");
        htfAusgabeschicht.setEditable(false);
        jpNetzarchitektur.add(htfAusgabeschicht);

        jbNetzSpeichern.setBounds(440, 240, 139, 25);
        jbNetzSpeichern.setText("Netz speichern...");
        jbNetzSpeichern.setMargin(new Insets(2, 2, 2, 2));
        jbNetzSpeichern.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbNetzSpeichern_ActionPerformed(evt);
                }
            });
        cp.add(jbNetzSpeichern);
        jbNetzLaden.setBounds(584, 240, 139, 25);
        jbNetzLaden.setText("Netz laden...");
        jbNetzLaden.setMargin(new Insets(2, 2, 2, 2));
        jbNetzLaden.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbNetzLaden_ActionPerformed(evt);
                }
            });
        cp.add(jbNetzLaden);
        jbDatenSpeichern.setBounds(440, 104, 139, 25);
        jbDatenSpeichern.setText("Daten speichern...");
        jbDatenSpeichern.setMargin(new Insets(2, 2, 2, 2));
        jbDatenSpeichern.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbDatenSpeichern_ActionPerformed(evt);
                }
            });
        cp.add(jbDatenSpeichern);
        jbDatenLaden.setBounds(584, 104, 139, 25);
        jbDatenLaden.setText("Daten laden...");
        jbDatenLaden.setMargin(new Insets(2, 2, 2, 2));
        jbDatenLaden.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbDatenLaden_ActionPerformed(evt);
                }
            });
        cp.add(jbDatenLaden);
        jbNeueZiffer.setBounds(234, 408, 190, 25);
        jbNeueZiffer.setText("Figur -> Trainingsdaten");
        jbNeueZiffer.setMargin(new Insets(2, 2, 2, 2));
        jbNeueZiffer.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbNeueZiffer_ActionPerformed(evt);
                }
            });
        cp.add(jbNeueZiffer);
        jbZeichenflaecheLoeschen.setBounds(24, 408, 190, 25);
        jbZeichenflaecheLoeschen.setText("Zeichenflaeche loeschen");
        jbZeichenflaecheLoeschen.setMargin(new Insets(2, 2, 2, 2));
        jbZeichenflaecheLoeschen.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbZeichenflaecheLoeschen_ActionPerformed(evt);
                }
            });
        cp.add(jbZeichenflaecheLoeschen);
        jbZumAnfang.setBounds(544, 8, 50, 50);
        jbZumAnfang.setText("|<");
        jbZumAnfang.setMargin(new Insets(2, 2, 2, 2));
        jbZumAnfang.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbZumAnfang_ActionPerformed(evt);
                }
            });
        jbZumAnfang.setFont(new Font("Dialog", Font.BOLD, 24));
        cp.add(jbZumAnfang);
        jbVor.setBounds(592, 8, 50, 50);
        jbVor.setText(">");
        jbVor.setMargin(new Insets(2, 2, 2, 2));
        jbVor.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbVor_ActionPerformed(evt);
                }
            });
        jbVor.setFont(new Font("Dialog", Font.BOLD, 24));
        cp.add(jbVor);
        jbZumEnde.setBounds(640, 8, 50, 50);
        jbZumEnde.setText(">|");
        jbZumEnde.setMargin(new Insets(2, 2, 2, 2));
        jbZumEnde.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbZumEnde_ActionPerformed(evt);
                }
            });
        jbZumEnde.setFont(new Font("Dialog", Font.BOLD, 24));
        cp.add(jbZumEnde);
        jtfFigur.setBounds(424, 8, 113, 49);
        jtfFigur.setHorizontalAlignment(SwingConstants.CENTER);
        jtfFigur.setText("");
        jtfFigur.setEditable(false);
        jtfFigur.setFont(new Font("Dialog", Font.PLAIN, 24));
        cp.add(jtfFigur);
        jbErkenne.setBounds(24, 440, 190, 25);
        jbErkenne.setText("Figur erkennen");
        jbErkenne.setMargin(new Insets(2, 2, 2, 2));
        jbErkenne.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbErkenne_ActionPerformed(evt);
                }
            });
        cp.add(jbErkenne);
        jlNummer.setBounds(696, 32, 41, 25);
        jlNummer.setText("0");
        jlNummer.setHorizontalAlignment(SwingConstants.CENTER);
        cp.add(jlNummer);
        jbStatistik.setBounds(234, 440, 190, 25);
        jbStatistik.setText("Statistik");
        jbStatistik.setMargin(new Insets(2, 2, 2, 2));
        jbStatistik.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbStatistik_ActionPerformed(evt);
                }
            });
        cp.add(jbStatistik);
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
            schichten[schichten.length - 1] = 3;
            Aktivierungsfunktion funktion = Aktivierungsfunktion.parseFunktion((String)jcbFunktion.getSelectedItem());
            neuronalesNetz = new Netz(10 * 10, schichten, funktion);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Fehler in Netzeingabe\nAnzahl Neuronen pro Schicht\nTrennung der Schichten mit >");  
        } 
    }

    public void jbTrainiere_ActionPerformed(ActionEvent evt) {
        if (neuronalesNetz != null) {
            try {
                trainingsdaten.mischen();
                double lernrate = Double.parseDouble(jtfLernrate.getText());
                int wiederholungen = Integer.parseInt(jtfWiederholungen.getText());
                int paketgroesse = Integer.parseInt(jtfPaketgroesse.getText());
                double fehler = neuronalesNetz.trainiere(trainingsdaten.gibEingabenArray(), trainingsdaten.gibZielwerteArray(), lernrate, paketgroesse, wiederholungen);
                jtfFehler.setText(String.format("%f",fehler));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Bitte im gueltigen Zahlenformat eingeben!\nAnzahlen: Ganzzahl\nLenrrate: Dezimalzahl mit .-Notation");
            }    
        } else {
            JOptionPane.showMessageDialog(this, "Erst Netz erstellen!");
        }    
        jpFigur.repaint();
    }

    public void jbLoeschen_ActionPerformed(ActionEvent evt) {
        trainingsdaten.loescheAlle();
        datensatzNummer = 0;
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
        jpFigur.neuesBild();
        jpFigur.repaint();
        GUILib.List<Datensatz> l = trainingsdaten.gibDaten(); 
        String figur = jpFigur.zeichneDatensatz(l.getContent());
        jtfFigur.setText(figur);
        jlNummer.setText("" + datensatzNummer);
    }

    public void jbNeueZiffer_ActionPerformed(ActionEvent evt) {

        int f = JOptionPane.showOptionDialog(this, "Welcher Figur entspricht das Bild", "Auswahl", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, jpFigur.figuren, null);
        if (f >= 0 && f <= 2) {
            trainingsdaten.fuegeEin(jpFigur.erstelleDatensatz(f));
        }
        jpFigur.repaint();
        jbZumEnde_ActionPerformed(evt);
    }

    public void jbZeichenflaecheLoeschen_ActionPerformed(ActionEvent evt) {
        jpFigur.neuesBild();
        jtfFigur.setText("");
        jpFigur.repaint();

    }

    public void jbZumAnfang_ActionPerformed(ActionEvent evt) {
        trainingsdaten.gibDaten().toFirst(); 
        datensatzNummer = 1;
        zeigeAktuellenDatensatz();
    }

    public void jbVor_ActionPerformed(ActionEvent evt) {
        trainingsdaten.gibDaten().next(); 
        datensatzNummer++;    
        zeigeAktuellenDatensatz();
    }

    public void jbZumEnde_ActionPerformed(ActionEvent evt) {
        datensatzNummer = trainingsdaten.gibGroesse();
        trainingsdaten.gibDaten().toLast(); 
        zeigeAktuellenDatensatz();
    }

    public void jbErkenne_ActionPerformed(ActionEvent evt) {
        if (neuronalesNetz == null) {
            JOptionPane.showMessageDialog(this, "Erst ein neuronales Netz erstellen");
        } else {
            Datensatz d = jpFigur.erstelleDatensatz(0);
            double[] eingabe = d.gibEingabe();
            double[] ausgabe = neuronalesNetz.berechne(eingabe);   
            Datensatz z = new Datensatz(eingabe, ausgabe);
            String figur = jpFigur.zeichneDatensatz(z);
            jtfFigur.setText("" + figur);
        }
    }

    public void jbStatistik_ActionPerformed(ActionEvent evt) {
        if (neuronalesNetz == null) {
            JOptionPane.showMessageDialog(this, "Erst ein neuronales Netz erstellen");
        } else {
            GUILib.List<Datensatz> daten = trainingsdaten.gibDaten();
            daten.toFirst();
            int[][] erkannt = new int[3][2];
            while (daten.hasAccess()) {
                Datensatz d = daten.getContent();
                double[] eingabe = d.gibEingabe();
                double[] zielwert = d.gibZielwert();
                double[] ausgabe = neuronalesNetz.berechne(eingabe);
                int soll = 0;
                int ist = 0;
                for (int i = 1; i < 3; i++) {
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
            for (int i = 0; i < 3; i++) {
                s += jpFigur.figuren[i] + ": " + erkannt[i][0] + " richtig / " + erkannt[i][1] + " falsch\n";
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
        new GuiFigur("Gui-Figur");
    }
}

