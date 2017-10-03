import java.util.ArrayList;
import java.util.Scanner;

class UserInterface {

    private boolean stop = false;

    void start() {
        while (!stop) {
            showMainMenu();
        }
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void showMainMenu() {
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
                addArbeitspaket();
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

    private void notFound() {
        clearConsole();
        System.err.println("Arbeitspaket nicht gefunden!");
        System.console().readLine();
        clearConsole();
    }

    private void showArbeitspakete() {
        if (Netzplan.getListe() == null || Netzplan.getListe().isEmpty()) {
            System.err.println("Keine Eintraege gefunden!");
            System.console().readLine();
        } else {
            for (Arbeitspaket pointer : Netzplan.getListe()) {
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

    private void addArbeitspaket() {
        Arbeitspaket tmp = new Arbeitspaket('0', 0);
        initializeI(tmp);
        initializeD(tmp);
        addVorgaenger(tmp);
        addNachfolger(tmp);
        Netzplan.getListe().add(tmp);
        clearConsole();
        System.out.println("Neues Arbeitspaket hinzugefuegt.");
        System.console().readLine();
    }

    private void initializeI(Arbeitspaket paket) {
        clearConsole();
        System.out.print("Bezeichner: ");
        Scanner scanner = new Scanner(System.in);
        char i = '0';
        try {
            i = scanner.nextLine().charAt(0);
        } catch (Exception e) {
            clearConsole();
            System.out.println("Bitte einen gueltigen Bezeichner eingeben!");
            System.console().readLine();
            initializeI(paket);
        }
        boolean vorhanden = false;
        for (Arbeitspaket pointer : Netzplan.getListe()) {
            if (pointer.getI() == i) {
                vorhanden = true;
                clearConsole();
                System.err.println("Bezeichner bereits vorhanden!");
                System.console().readLine();
                initializeI(paket);
            }
        }
        if (!vorhanden) {
            paket.setI(i);
            clearConsole();
            System.out.println("Bezeichner gesetzt.");
            System.console().readLine();
            clearConsole();
        }
    }

    private void initializeD(Arbeitspaket paket) {
        clearConsole();
        System.out.print("Dauer: ");
        Scanner scanner = new Scanner(System.in);
        int newD = 0;
        try {
            newD = scanner.nextInt();
        } catch (Exception e) {
            clearConsole();
            System.out.println("Bitte eine gueltige Dauer eingeben!");
            System.console().readLine();
            initializeD(paket);
        }
        paket.setD(newD);
        clearConsole();
        System.out.println("Dauer gesetzt.");
        System.console().readLine();
        clearConsole();
    }

    private void bearbeiteArbeitspaket() {
        if (Netzplan.getListe() == null || Netzplan.getListe().isEmpty()) {
            System.err.println("Keine Eintraege gefunden!");
            System.console().readLine();
        } else {
            System.out.println("Verfuegbare Arbeitspakete:");
            for (Arbeitspaket pointer : Netzplan.getListe()) {
                System.out.println(pointer.getI());
            }
            System.out.println();
            System.out.print("Zu bearbeitendes Arbeitspaket: ");
            Scanner scanner = new Scanner(System.in);
            boolean found = false;
            char selection = '0';
            boolean set = true;
            try {
                selection = scanner.nextLine().charAt(0);
            } catch (Exception e) {
                set = false;
                clearConsole();
                bearbeiteArbeitspaket();
            }
            if (set) {
                for (Arbeitspaket pointer : Netzplan.getListe()) {
                    if (pointer.getI() == selection) {
                        clearConsole();
                        showEditMenu(pointer);
                        found = true;
                    }
                }
                if (!found) {
                    notFound();
                }
            }
        }
    }

    private void showEditMenu(Arbeitspaket paket) {
        clearConsole();
        System.out.printf("Arbeitspaket %s%n", paket.getI());
        System.out.println(" Dauer: " + paket.getD());
        if (paket.getVorgaenger() != null) {
            System.out.print(" Vorgaenger: ");
            for (Arbeitspaket vor : paket.getVorgaenger()) {
                System.out.print(vor.getI() + " ");
            }
            System.out.println();
        }
        if (paket.getNachfolger() != null) {
            System.out.print(" Nachfolger: ");
            for (Arbeitspaket nach : paket.getNachfolger()) {
                System.out.print(nach.getI() + " ");
            }
            System.out.printf("%n");
        }
        System.out.println();
        System.out.println("1:  Bezeichner aendern");
        System.out.println("2:  Dauer aendern");
        System.out.println("3:  Vorgaenger hinzufuegen");
        System.out.println("4:  Vorgaenger entfernen");
        System.out.println("5:  Nachfolger hinzufuegen");
        System.out.println("6:  Nachfolger entfernen");
        System.out.println("0:  Hauptmenu");

        //FIXME
        Scanner scanner = new Scanner(System.in);
        int selection = -1;
        try {
            selection = scanner.nextInt();
        } catch (Exception e) {
            showEditMenu(paket);
        }
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
                removeVorgaenger(paket);
                break;
            case 5:
                addNachfolger(paket);
                break;
            case 6:
                removeNachfolger(paket);
                break;
            case 0:
                showMainMenu();
                break;
            default:
                showEditMenu(paket);
                break;
        }
    }

    private void changeI(Arbeitspaket paket) {
        clearConsole();
        System.out.println("Bisheriger Bezeichner: " + paket.getI());
        System.out.print("Neuer Bezeichner: ");

        Scanner scanner = new Scanner(System.in);
        char newI = '0';
        boolean set = true;
        try {
            newI = scanner.nextLine().charAt(0);
        } catch (Exception e) {
            set = false;
            clearConsole();
            System.out.println("Bitte einen gueltigen Bezeichner eingeben!");
            System.console().readLine();
            showEditMenu(paket);
        }
        if (set) {
            boolean vorhanden = false;
            for (Arbeitspaket pointer : Netzplan.getListe()) {
                if (pointer.getI() == newI) {
                    vorhanden = true;
                    clearConsole();
                    System.err.println("Bezeichner bereits vorhanden!");
                    System.console().readLine();
                    clearConsole();
                    break;
                }
            }
            if (!vorhanden) {
                paket.setI(newI);
                clearConsole();
                System.out.println("Neuer Bezeichner gesetzt.");
                System.console().readLine();
                clearConsole();
            }
        }
    }

    private void changeD(Arbeitspaket paket) {
        clearConsole();
        System.out.println("Bisherige Dauer: " + paket.getD());
        System.out.print("Neue Dauer: ");
        Scanner scanner = new Scanner(System.in);
        int newD = 0;
        boolean set = true;
        try {
            newD = scanner.nextInt();
        } catch (Exception e) {
            set = false;
            clearConsole();
            System.out.println("Bitte eine gueltige Dauer eingeben!");
            System.console().readLine();
            showEditMenu(paket);
        }
        if (set) {
            paket.setD(newD);
            clearConsole();
            System.out.println("Neue Dauer gesetzt.");
            System.console().readLine();
            clearConsole();
        }
    }

    //TODO: exceptions
    private void addVorgaenger(Arbeitspaket paket) {
        clearConsole();
        System.out.print("Bezeichner des Vorgaengers: ");

        Scanner scanner = new Scanner(System.in);
        char bez = scanner.nextLine().charAt(0);
        boolean found = false;
        for (Arbeitspaket pointer : Netzplan.getListe()) {
            if (pointer.getI() == bez) {
                found = true;

                //TODO: check for duplicates
                if (paket.getVorgaenger() == null) {
                    paket.setVorgaenger(new ArrayList<>());
                }
                paket.getVorgaenger().add(pointer);
                clearConsole();
                System.out.println("Vorgaenger hinzugefuegt.");
                System.console().readLine();
                break;
            }
        }
        if (!found) {
            System.err.println("Kein Arbeitspaket mit diesem Bezeichner gefunden!");
            System.console().readLine();
        }
    }

    //TODO: exceptions
    private void removeVorgaenger(Arbeitspaket paket) {
        clearConsole();
        if (paket.getVorgaenger() != null || !paket.getVorgaenger().isEmpty()) {
            System.out.println("Zu loeschende Vorgaenger:");
            for (Arbeitspaket vor : paket.getVorgaenger()) {
                System.out.println(vor.getI());
            }
            Scanner scanner = new Scanner(System.in);
            char selection = scanner.nextLine().charAt(0);
            boolean found = false;
            for (Arbeitspaket vor : paket.getVorgaenger()) {
                if (vor.getI() == selection) {
                    clearConsole();
                    found = true;
                    paket.getVorgaenger().remove(vor);
                    System.out.println("Vorgaenger entfernt.");
                    System.console().readLine();
                    break;
                }
            }
            if (!found) {
                notFound();
            }
        } else {
            System.err.println("Keine Vorgaenger gefunden!");
            System.console().readLine();
        }
    }

    //TODO: exceptions
    private void addNachfolger(Arbeitspaket paket) {
        clearConsole();
        System.out.print("Bezeichner des Nachfolger: ");

        Scanner scanner = new Scanner(System.in);
        char bez = scanner.nextLine().charAt(0);
        boolean found = false;
        for (Arbeitspaket pointer : Netzplan.getListe()) {
            if (pointer.getI() == bez) {
                found = true;

                //TODO: check for duplicates
                if (paket.getNachfolger() == null) {
                    paket.setNachfolger(new ArrayList<>());
                }
                paket.getNachfolger().add(pointer);
                clearConsole();
                System.out.println("Nachfolger hinzugefuegt.");
                System.console().readLine();
                break;
            }
        }
        if (!found) {
            System.err.println("Kein Arbeitspaket mit diesem Bezeichner gefunden!");
            System.console().readLine();
        }
    }

    //TODO: exceptions
    private void removeNachfolger(Arbeitspaket paket) {
        clearConsole();
        if (paket.getNachfolger() != null || !paket.getNachfolger().isEmpty()) {
            System.out.println("Zu loeschende Nachfolger:");
            for (Arbeitspaket nach : paket.getNachfolger()) {
                System.out.println(nach.getI());
            }
            Scanner scanner = new Scanner(System.in);
            char selection = scanner.nextLine().charAt(0);
            boolean found = false;
            for (Arbeitspaket nach : paket.getNachfolger()) {
                if (nach.getI() == selection) {
                    clearConsole();
                    found = true;
                    paket.getNachfolger().remove(nach);
                    System.out.println("Nachfolger entfernt.");
                    System.console().readLine();
                    break;
                }
            }
            if (!found) {
                notFound();
            }
        } else {
            System.err.println("Keine Nachfolger gefunden!");
            System.console().readLine();
        }
    }

    //TODO: exceptions
    private void entferneArbeitspaket() {
        if (Netzplan.getListe() == null || Netzplan.getListe().isEmpty()) {
            System.err.println("Keine Eintraege gefunden!");
            System.console().readLine();
        } else {
            System.out.println("Verfuegbare Arbeitspakete:");
            for (Arbeitspaket pointer : Netzplan.getListe()) {
                System.out.println(pointer.getI());
            }
            System.out.print("Zu loeschendes Arbeitspaket: ");
            Scanner scanner = new Scanner(System.in);
            boolean found = false;
            Arbeitspaket tmp = null;
            char selection = scanner.nextLine().charAt(0);
            for (Arbeitspaket pointer : Netzplan.getListe()) {
                if (pointer.getI() == selection) {
                    tmp = pointer;
                    removeDependencies(pointer);
                    found = true;
                }
            }
            if (tmp != null) {
                Netzplan.getListe().remove(tmp);
                clearConsole();
                System.out.println("Arbeitspaket entfernt.");
                System.console().readLine();
            }
            if (!found) {
                notFound();
            }
        }
    }

    private void removeDependencies(Arbeitspaket paket) {
        for (Arbeitspaket pointer : Netzplan.getListe()) {
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
        if (Netzplan.getListe() == null || Netzplan.getListe().isEmpty()) {
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
                Netzplan.getListe().clear();
                clearConsole();
                System.out.println("Netzplan entfernt!");
                System.console().readLine();
                clearConsole();
                break;
            case 2:
                showMainMenu();
                break;
            default:
                clearConsole();
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
                clearConsole();
                this.stop = true;
                break;
            case 2:
                showMainMenu();
                break;
            default:
                clearConsole();
                closeProgram();
                break;
        }
    }
}
