import java.util.ArrayList;
import java.util.Scanner;

class UserInterface {

    //TODO: add 'clearConsole()' and 'System.console().readLine()' where needed
    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    void showMainMenu() {
        clearConsole();
        System.out.println("***Netzplan-Rechner***");
        System.out.println();
        System.out.println("1:  Uebersicht Arbeitspakete");
        System.out.println("2:  Arbeitspaket hinzufuegen");
        System.out.println("3:  Arbeitspaket bearbeiten");
        System.out.println("4:  Arbeitspaket entfernen");
        System.out.println("5:  Netzplan berechnen und anzeigen");
        System.out.println("6:  Netzplan entfernen");
        System.out.println("0:  Programm beenden");

        Scanner scanner = new Scanner(System.in);
        int selection = scanner.nextInt();
        clearConsole();
        switch (selection) {
            case 1:
                showArbeitspakete();
                break;
            case 2:
                break;
            case 3:
                bearbeiteArbeitspaket();
                break;
            case 4:
                entferneArbeitspaket();
                break;
            case 5:
                zeigeNetzplan();
                break;
            case 6:
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

    private void showArbeitspakete() {
        if (Netzplan.liste == null || Netzplan.liste.isEmpty()) {
            System.err.println("Keine Eintraege gefunden!");
            System.console().readLine();
        } else {
            for (Arbeitspaket pointer : Netzplan.liste) {
                System.out.printf("Arbeitspaket %s, Dauer: %d%n", pointer.getI(), pointer.getD());
                if (pointer.getVorgaenger() != null) {
                    System.out.println(" Vorgaenger:");
                    for (Arbeitspaket vor : pointer.getVorgaenger()) {
                        System.out.println("  " + vor.getI());
                    }
                    System.out.println();
                }
                if (pointer.getNachfolger() != null) {
                    System.out.println(" Nachfolger:");
                    for (Arbeitspaket nach : pointer.getNachfolger()) {
                        System.out.println("  " + nach.getI());
                    }
                }
                System.out.println();
            }
            System.console().readLine();
        }
    }

    private void bearbeiteArbeitspaket() {
        //TODO: exceptions
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
            System.console().readLine();
            clearConsole();
        }
    }

    private void showEditMenu(Arbeitspaket paket) {
        System.out.printf("Arbeitspaket %s:%n", paket.getI());
        System.out.println();
        System.out.println("1:  Bezeichner aendern");
        System.out.println("2:  Dauer aendern");
        System.out.println("3:  Vorgaenger hinzufuegen");
        System.out.println("4:  Vorgaenger entfernen");
        System.out.println("5:  Nachfolger hinzufuegen");
        System.out.println("6:  Nachfolger entfernen");
        System.out.println("0:  Hauptmenue");

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
                addVorgaenger(paket);
                break;
            case 4:
                //TODO
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

        //TODO: exceptions, dependencies
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

    private void addVorgaenger(Arbeitspaket paket) {
        System.out.print("Bezeichner des Vorgaengers: ");

        Scanner scanner = new Scanner(System.in);
        char bez = scanner.nextLine().charAt(0);
        boolean found = false;
        for (Arbeitspaket pointer : Netzplan.liste) {
            if (pointer.getI() == bez) {
                found = true;
                if (paket.getVorgaenger() == null) {
                    paket.setVorgaenger(new ArrayList<>());
                }
                paket.getVorgaenger().add(pointer);
            }
        }
        if (!found) {
            System.err.println("Kein Arbeitspaket mit diesem Bezeichner gefunden!");
            System.console().readLine();
            clearConsole();
        }
    }

    private void entferneArbeitspaket() {
        if (Netzplan.liste == null || Netzplan.liste.isEmpty()) {
            System.err.println("Keine Eintraege gefunden!");
            System.console().readLine();
        } else {
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
                    removeDependencies(pointer);
                    found = true;
                }
            }
            if (tmp != null) {
                Netzplan.liste.remove(tmp);
                clearConsole();
                System.out.println("Arbeitspaket entfernt.");
                System.console().readLine();
            }
            if (!found) {
                System.err.println("Arbeitspaket nicht gefunden!");
                System.console().readLine();
                clearConsole();
            }
        }
    }

    private void removeDependencies(Arbeitspaket paket) {
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
        if (Netzplan.liste == null || Netzplan.liste.isEmpty()) {
            System.err.println("Keine Eintraege gefunden!");
            System.console().readLine();
        } else {
            Netzplan.calculate();
            System.console().readLine();
            showMainMenu();
        }
    }

    private void entferneNetzplan() {
        System.out.println("Wollen Sie den aktuellen Netzplan wirklich entfernen?");
        System.out.println("1:  Ja");
        System.out.println("2:  Nein");

        Scanner scanner = new Scanner(System.in);
        int selection = scanner.nextInt();
        switch (selection) {
            case 1:
                Netzplan.liste.clear();
                clearConsole();
                System.out.println("Netzplan entfernt!");
                System.console().readLine();
                clearConsole();
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
        System.out.println("1:  Ja");
        System.out.println("2:  Nein");

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
