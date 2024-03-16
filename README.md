# Neuronales Netz

Diese Implementation soll die Erstellung eigener neuronaler Netze mit Java im Unterricht der gymnasialen Oberstufe ermöglichen.

## Installation und Verwendung
In den Ordner GUILIb muss für die graphischen Beispiele die Datei List.java der [nordrhein-westfälischen Abiturklassen](https://www.schulentwicklung.nrw.de/lehrplaene/lehrplannavigator-s-ii/gymnasiale-oberstufe/informatik/hinweise-und-beispiele/hinweise-und-beispiele.html) kopiert werden. Danach kann man das Projekt z.B. mit [BlueJ](https://www.bluej.org/) oder dem [Java-Editor](https://javaeditor.org/doku.php?id=start) öffnen. Mit letzterem Programm kann man auch die GUIs bearbeiten.

## Klassen
Die Klassen Neuron, Schicht und Netz im Unterordner NeuronalesNetz modellieren ein Neuronales Netz in einem BlueJ-Projekt.
Dabei kann man die folgenden Aktivierungsfunktionen wählen:
- Sigmoid
- Softsign
- Tangens hyperbolicus
- Relu

Die Klassen im Unterordner GUILib sind Hilfsklassen für die graphischen Benutzeroberflächen.

## Beispiele

### MNIST1 und MNIST10
Mit dieser Klasse kann man ein Standardbeispiel für Neuronale Netze, die Erkennung von handschriftlichen Ziffern durchspielen. Bei ersterer Klasse gibt es ein Ausgabeneuron, bei letzterer zehn.

Dazu benötigt man die MNIST-Daten im Format csv. Die Dateien mnist_train.csv und mnist_test.csv müssen in den Ordner Quellcode kopiert werden. Außerdem muss jeweils die erste Zeile mit der "Überschrift" entfernt werden.

### CSV
Mit dieser Klasse kann man Daten aus CSV-Dateien einlesen und diese zum Training nutzen.

### Auftriebskraft
Eine Implementation zum Training mit Messwerten aus einem Versuch zur Ermittlung der Auftriebskraft.

### GUIRGB
Eine Programm mit graphischer Oberfläche zur Clusterung von verschieden gefärbten Punkten.

### GUIKurve
Eine Programm mit graphischer Oberfäche zur Näherung von PUnkten durch eine Kurve.

### GUIFigur
Eine Programm mit graphischer Oberfläche zur Erkennung von per Hand gezeichneten Figuren.

### GUIZiffern
Ein Programm mit graphischer Oberfläche zur Handschriftenerkennung von Ziffern.




