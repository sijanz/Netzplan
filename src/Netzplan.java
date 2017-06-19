import java.util.ArrayList;

public class Netzplan {

    private static ArrayList<Arbeitspaket> liste = new ArrayList<>();

    private static void initializeFaz() {
        for (Arbeitspaket pointer : liste) {
            if (pointer.getVorgaenger() == null) {
                pointer.setFaz(0);
            }
        }
    }

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

    public static void main(String[] args) {

        // TODO: implement another way to initialise and add objects

        Arbeitspaket a = new Arbeitspaket('A', 2);
        Arbeitspaket b = new Arbeitspaket('B', 4);
        Arbeitspaket c = new Arbeitspaket('C', 10);
        Arbeitspaket d = new Arbeitspaket('D', 3);

        liste.add(a);
        liste.add(b);
        liste.add(c);
        liste.add(d);

        a.setNachfolger(new Arbeitspaket[]{b, c});
        b.setVorgaenger(new Arbeitspaket[]{a});
        b.setNachfolger(new Arbeitspaket[]{d});
        c.setVorgaenger(new Arbeitspaket[]{a});
        c.setNachfolger(new Arbeitspaket[]{d});
        d.setVorgaenger(new Arbeitspaket[]{b, c});

        // Startzeitpunkt auf 0 setzen
        initializeFaz();

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
}
