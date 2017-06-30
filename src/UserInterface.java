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

    private void entferneArbeitspaket() {
        System.out.println("Verfuegbare Arbeitspakete:");
        for (Arbeitspaket pointer : Netzplan.liste) {
            System.out.println(pointer.getI());
        }
        System.out.print("Zu loeschendes Arbeitspaket: ");
        Scanner scanner = new Scanner(System.in);
        char selection = scanner.nextLine().charAt(0);
        // TODO
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
