import java.util.Scanner;

class UserInterface {

    void showMainMenu() {
        System.out.println("***Netzplan-Rechner***");
        System.out.println();
        System.out.println("1       Arbeitspaket hinzufuegen");
        System.out.println("2       Arbeitspaket bearbeiten");
        System.out.println("3       Arbeitspaket entfernen");
        System.out.println("4       Netzplan berechnen und anzeigen");
        System.out.println("5       Netzplan entfernen");
        System.out.println("0       Programm beenden");

        Scanner scanner = new Scanner(System.in);
        int selection = scanner.nextInt();
        switch (selection) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                entferneArbeitspaket();
                break;
            case 4:
                zeigeNetzplan();
                break;
            case 5:
                entferneNetzplan();
                break;
            case 0:
                closeProgram();
                break;
            default:
                showMainMenu();
                break;
        }
    }

    private void bearbeiteArbeitspaket() {
        System.out.println("Verfuegbare Arbeitspakete:");
        for (Arbeitspaket pointer : Netzplan.liste) {
            System.out.println(pointer.getI());
        }
        System.out.print("Zu bearbeitendes Arbeitspaket: ");
        Scanner scanner = new Scanner(System.in);
        boolean found = false;
        char selection = scanner.nextLine().charAt(0);
        for (Arbeitspaket pointer : Netzplan.liste) {
            if (pointer.getI() == selection) {
                showEditMenu(pointer);
                found = true;
            }
        }
        if (!found) {
            System.err.println("Arbeitspaket nicht gefunden!");
        }
    }

    private void showEditMenu(Arbeitspaket paket) {
        System.out.printf("Arbeitspaket %s:%n", paket.getI());
        System.out.println();
        System.out.println("1       Bezeichner aendern");
        System.out.println("2       Dauer aendern");
        System.out.println("3       Vorgaenger hinzufuegen");
        System.out.println("4       Vorgaenger entfernen");
        System.out.println("5       Nachfolger hinzufuegen");
        System.out.println("6       Nachfolger entfernen");
        System.out.println("0       Hauptmenue");

        Scanner scanner = new Scanner(System.in);
        int selection = scanner.nextInt();
        switch (selection) {
            case 1:
                changeI(paket);
                break;
            case 2:
                changeD(paket);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 0:
                break;
            default:
                break;
        }
    }

    private void changeI(Arbeitspaket paket) {
        System.out.println("Bisheriger Bezeichner: " + paket.getI());
        System.out.print("Neuer Bezeichner: ");

        //TODO: exceptions
        Scanner scanner = new Scanner(System.in);
        char newI = scanner.nextLine().charAt(0);
        paket.setI(newI);
    }

    private void changeD(Arbeitspaket paket) {
        System.out.println("Bisherige Dauer: " + paket.getD());
        System.out.print("Neue Dauer: ");

        //TODO: exceptions
        Scanner scanner = new Scanner(System.in);
        int newD = scanner.nextInt();
        paket.setD(newD);
    }

    private void entferneArbeitspaket() {
        System.out.println("Verfuegbare Arbeitspakete:");
        for (Arbeitspaket pointer : Netzplan.liste) {
            System.out.println(pointer.getI());
        }
        System.out.print("Zu loeschendes Arbeitspaket: ");
        Scanner scanner = new Scanner(System.in);
        boolean found = false;
        Arbeitspaket tmp = null;
        char selection = scanner.nextLine().charAt(0);
        for (Arbeitspaket pointer : Netzplan.liste) {
            if (pointer.getI() == selection) {
                tmp = pointer;
                removeDependecies(pointer);
                found = true;
            }
        }
        if (tmp != null) {
            Netzplan.liste.remove(tmp);
        }
        if (!found) {
            System.err.println("Arbeitspaket nicht gefunden!");
        }
    }

    private void removeDependecies(Arbeitspaket paket) {
        for (Arbeitspaket pointer : Netzplan.liste) {
            if (pointer.getVorgaenger() != null) {
                for (Arbeitspaket vor : pointer.getVorgaenger()) {
                    if (vor == paket) {
                        pointer.getVorgaenger().remove(vor);
                        break;
                    }
                }
            }
            if (pointer.getNachfolger() != null) {
                for (Arbeitspaket nach : pointer.getNachfolger()) {
                    if (nach == paket) {
                        pointer.getNachfolger().remove(nach);
                        break;
                    }
                }
            }
        }
    }

    private void zeigeNetzplan() {
        Netzplan.calculate();
        showMainMenu();
    }

    private void entferneNetzplan() {
        System.out.println("Wollen Sie den aktuellen Netzplan wirklich entfernen?");
        System.out.println("1       Ja");
        System.out.println("2       Nein");

        Scanner scanner = new Scanner(System.in);
        int selection = scanner.nextInt();
        switch (selection) {
            case 1:
                Netzplan.liste.clear();
                break;
            case 2:
                showMainMenu();
                break;
            default:
                entferneNetzplan();
                break;
        }
    }

    private void closeProgram() {
        System.out.println("Wollen Sie das Programm wirklich beenden?");
        System.out.println("1       Ja");
        System.out.println("2       Nein");

        Scanner scanner = new Scanner(System.in);
        int selection = scanner.nextInt();
        switch (selection) {
            case 1:
                Netzplan.stop = true;
                break;
            case 2:
                showMainMenu();
                break;
            default:
                closeProgram();
        }
    }
}
