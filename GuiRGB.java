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
 * Grafische Oberflaeche fuer Neuronale Netze mit Farben
 *
 * @version 2024-02-25
 * @author Daniel Garmann
 */

public class GuiRGB extends JFrame {
    // Anfang Attribute
    private Netz neuronalesNetz = null;

    private PanelRGB jpRGB = new PanelRGB();
    private JButton jbRot = new JButton();
    private JButton jbGruen = new JButton();
    private JButton jbBlau = new JButton();
    private JButton jbWeiss = new JButton();
    private JButton jbSchwarz = new JButton();
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
    private JButton jbFreieFarbe = new JButton();
    private JTextField jtfFehler = new JTextField();
    private JLabel jlFehler = new JLabel();
    private JButton jbNetzSpeichern = new JButton();
    private JButton jbNetzLaden = new JButton();
    private JButton jbDatenSpeichern = new JButton();
    private JButton jbDatenLaden = new JButton();
    // Ende Attribute

    public GuiRGB (String title) {
        super (title);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 760; 
        int frameHeight = 487;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        Container cp = getContentPane();
        cp.setLayout(null);
        // Anfang Komponenten
        jpRGB.setBounds(24, 8, 400, 400);
        jpRGB.setOpaque(false);
        jpRGB.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 0));
        cp.add(jpRGB);
        jbRot.setBounds(440, 8, 90, 25);
        jbRot.setText("");
        jbRot.setMargin(new Insets(2, 2, 2, 2));
        jbRot.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jpRGB.setzeFarbe(((JButton)evt.getSource()).getBackground());
                }
            });
        jbRot.setBackground(Color.RED);
        cp.add(jbRot);
        jbGruen.setBounds(536, 8, 90, 25);
        jbGruen.setText("");
        jbGruen.setMargin(new Insets(2, 2, 2, 2));
        jbGruen.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jpRGB.setzeFarbe(((JButton)evt.getSource()).getBackground());
                }
            });
        jbGruen.setBackground(Color.GREEN);
        cp.add(jbGruen);
        jbBlau.setBounds(632, 8, 90, 25);
        jbBlau.setText("");
        jbBlau.setMargin(new Insets(2, 2, 2, 2));
        jbBlau.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jpRGB.setzeFarbe(((JButton)evt.getSource()).getBackground());
                }
            });
        jbBlau.setBackground(Color.BLUE);
        cp.add(jbBlau);
        jbWeiss.setBounds(440, 40, 90, 25);
        jbWeiss.setText("");
        jbWeiss.setMargin(new Insets(2, 2, 2, 2));
        jbWeiss.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jpRGB.setzeFarbe(((JButton)evt.getSource()).getBackground());
                }
            });
        jbWeiss.setBackground(Color.WHITE);
        cp.add(jbWeiss);
        jbSchwarz.setBounds(536, 40, 90, 25);
        jbSchwarz.setText("");
        jbSchwarz.setMargin(new Insets(2, 2, 2, 2));
        jbSchwarz.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jpRGB.setzeFarbe(((JButton)evt.getSource()).getBackground());
                }
            });
        jbSchwarz.setBackground(Color.BLACK);
        cp.add(jbSchwarz);
        jbFreieFarbe.setBounds(632, 40, 90, 25);
        jbFreieFarbe.setText("freie Farbe");
        jbFreieFarbe.setMargin(new Insets(2, 2, 2, 2));
        jbFreieFarbe.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent evt) { 
                    jbFreieFarbe_ActionPerformed(evt);
                }
            });
        jbFreieFarbe.setBackground(new Color(0xC0C0C0));
        cp.add(jbFreieFarbe);
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
        jtfNetzarchitektur.setBounds(155, 24, 78, 20);
        jcbFunktion.setBounds(139, 45, 110, 20);
        jlSchichten.setBounds(14, 24, 67, 20);
        jlAktivierungsfunktion.setBounds(13, 45, 124, 20);
        jtfNetzarchitektur.setText("4>5");
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
        jtfLernrate.setText("0.01");
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
        jtfEingabeSchicht.setBounds(136, 24, 22, 20);
        jtfEingabeSchicht.setText("2>");
        jtfEingabeSchicht.setEditable(false);
        jpNetzarchitektur.add(jtfEingabeSchicht);
        htfAusgabeschicht.setBounds(232, 24, 22, 20);
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
        // Ende Komponenten
        setResizable(false);
        setVisible(true);
    }

    // Anfang Methoden
    public void jbFreieFarbe_ActionPerformed(ActionEvent evt) {
        JColorChooser farbauswahl = new JColorChooser(jbFreieFarbe.getBackground());
        Color farbe = farbauswahl.showDialog(this, "Farbauswahl", jbFreieFarbe.getBackground());
        jbFreieFarbe.setBackground(farbe);
        jpRGB.setzeFarbe(jbFreieFarbe.getBackground());
    }

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
            neuronalesNetz = new Netz(2, schichten, funktion);
            jpRGB.setzeNeuronalesNetz(neuronalesNetz);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Fehler in Netzeingabe\nAnzahl Neuronen pro Schicht\nTrennung der Schichten mit >");  
        } 
    }

    public void jbTrainiere_ActionPerformed(ActionEvent evt) {
        if (neuronalesNetz != null) {
            try {
                double lernrate = Double.parseDouble(jtfLernrate.getText());
                int wiederholungen = Integer.parseInt(jtfWiederholungen.getText());
                int paketgroesse = Integer.parseInt(jtfPaketgroesse.getText());
                double fehler = neuronalesNetz.trainiere(jpRGB.gibEingabenArray(), jpRGB.gibZielwerteArray(), lernrate, paketgroesse, wiederholungen);
                jtfFehler.setText(String.format("%f",fehler));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Bitte im gueltigen Zahlenformat eingeben!\nAnzahlen: Ganzzahl\nLenrrate: Dezimalzahl mit .-Notation");
            }    
        } else {
            JOptionPane.showMessageDialog(this, "Erst Netz erstellen!");
        }    
        jpRGB.repaint();
    }

    public void jbLoeschen_ActionPerformed(ActionEvent evt) {
        jpRGB.loescheDaten();

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
                String s = "";
                BufferedReader leser = new BufferedReader(new FileReader(datei));
                String zeile = leser.readLine();
                while (zeile != null) {
                    s = s + zeile + "\n";
                    zeile = leser.readLine();
                }
                neuronalesNetz = Netz.parseNetz(s);
                jpRGB.setzeNeuronalesNetz(neuronalesNetz);
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
                schreiber.write(jpRGB.gibTrainingsdaten().toString());
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
                jpRGB.setzeTrainingsdaten(Datensammlung.parseDatensammlung(s));
                leser.close();
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }  
        repaint();  
    }

    // Ende Methoden

    public static void main(String[] args) {
        new GuiRGB("Gui-RGB");
    }
}

