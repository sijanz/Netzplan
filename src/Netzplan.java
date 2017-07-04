import java.util.ArrayList;

public class Netzplan {

    static ArrayList<Arbeitspaket> liste = new ArrayList<>();

    private static void forwardPass() {
        for (Arbeitspaket pointer : liste) {
            pointer.calculateFaz();
            pointer.calculateFez();
        }
    }

    private static void setSezToFez() {
        for (Arbeitspaket pointer : liste) {
            if (pointer.getNachfolger() == null) {
                pointer.setSez(pointer.getFez());
                pointer.calculateSaz();
            }
        }
    }

    private static void backwardPass() {
        for (int i = liste.size() - 1; i >= 0; i--) {
            liste.get(i).calculateSez();
            liste.get(i).calculateSaz();
        }
    }

    private static void bufferCalculation() {
        for (Arbeitspaket pointer : liste) {
            pointer.calculateGp();
            pointer.calculateFp();
            pointer.calculateUp();
        }
    }

    private static void output() {
        System.out.println("Legende:");
        System.out.println("i FAZ FEZ");
        System.out.println("D SAZ SEZ");
        System.out.println("GP FP UP");
        System.out.println();
        for (Arbeitspaket pointer : liste) {
            pointer.printArbeitspaket();
            System.out.println();
        }
    }

    static void calculate() {
        // Vorwaertsrechnung
        forwardPass();

        // End-SEZ auf End-FEZ setzen
        setSezToFez();

        // Rueckwaertsrechnung
        backwardPass();

        // Pufferberechnung
        bufferCalculation();

        // Output
        output();
    }

    public static void main(String[] args) {


        // *** TEST-INITIALISIERUNG ***

        // Arbeitspakete als Objekte initialisieren
        Arbeitspaket a = new Arbeitspaket('A', 5);
        Arbeitspaket b = new Arbeitspaket('B', 4);
        Arbeitspaket c = new Arbeitspaket('C', 7);
        Arbeitspaket d = new Arbeitspaket('D', 3);

        // Arbeitspake zur Liste hinzufuegen
        liste.add(a);
        liste.add(b);
        liste.add(c);
        liste.add(d);

        // Vorgaenger und Nachfolger der Arbeitspakete hinzufuegen (in ArrayLists "vorgaenger" und "nachfolger" schreiben)
        a.setNachfolger(new ArrayList<>());
        a.getNachfolger().add(b);
        a.getNachfolger().add(c);

        b.setVorgaenger(new ArrayList<>());
        b.getVorgaenger().add(a);
        b.setNachfolger(new ArrayList<>());
        b.getNachfolger().add(d);

        c.setVorgaenger(new ArrayList<>());
        c.getVorgaenger().add(a);
        c.setNachfolger(new ArrayList<>());
        c.getNachfolger().add(d);

        d.setVorgaenger(new ArrayList<>());
        d.getVorgaenger().add(b);
        d.getVorgaenger().add(c);

        // *** TEST-INITIALISIERUNG ***


        UserInterface ui = new UserInterface();
        ui.start();
    }
}
