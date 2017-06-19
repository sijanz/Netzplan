import java.util.ArrayList;

public class Netzplan {

    public static void main(String[] args) {
        ArrayList<Arbeitspaket> liste = new ArrayList<>();
        Arbeitspaket a = new Arbeitspaket('A', 2);
        Arbeitspaket b = new Arbeitspaket('B', 4);
        Arbeitspaket c = new Arbeitspaket('C', 10);
        Arbeitspaket d = new Arbeitspaket('D', 3);

        liste.add(a);
        liste.add(b);
        liste.add(c);
        liste.add(d);

        a.setFaz(0);

        a.setNachfolger(new Arbeitspaket[]{b, c});
        b.setVorgaenger(new Arbeitspaket[]{a});
        b.setNachfolger(new Arbeitspaket[]{d});
        c.setVorgaenger(new Arbeitspaket[]{a});
        c.setNachfolger(new Arbeitspaket[]{d});
        d.setVorgaenger(new Arbeitspaket[]{b, c});

        // Vorwaertsrechnung
        for (Arbeitspaket pointer : liste) {
            pointer.calculateFaz();
            pointer.calculateFez();
        }

        // SEZ = FEZ
        for (Arbeitspaket pointer : liste) {
            if (pointer.getNachfolger() == null) {
                pointer.setSez(pointer.getFez());
                pointer.calculateSaz();
            }
        }

        // Rueckwaertsrechnung
        for (int i = liste.size() - 1; i >= 0; i--) {
            liste.get(i).calculateSez();
            liste.get(i).calculateSaz();
        }

        // Pufferberechnung
        for (Arbeitspaket pointer : liste) {
            pointer.calculateGp();
            pointer.calculateUp();
        }

        for (Arbeitspaket pointer : liste) {
            pointer.printArbeitspaket();
            System.out.println();
        }
    }
}
