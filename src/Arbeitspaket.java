import java.util.ArrayList;

class Arbeitspaket {

    private char i;
    private int d;
    private int faz;
    private int fez;
    private int saz;
    private int sez;
    private int gp;
    private int fp;
    private int up;
    private ArrayList<Arbeitspaket> vorgaenger;
    private ArrayList<Arbeitspaket> nachfolger;
    private boolean isMarked;

    Arbeitspaket(char i, int d) {
        this.i = i;
        this.d = d;
    }

    private int getFaz() {
        return this.faz;
    }

    int getFez() {
        return this.fez;
    }

    private int getSaz() {
        return this.saz;
    }

    private int getSez() {
        return this.sez;
    }

    ArrayList<Arbeitspaket> getVorgaenger() {
        return this.vorgaenger;
    }

    ArrayList<Arbeitspaket> getNachfolger() {
        return this.nachfolger;
    }

    char getI() {
        return this.i;
    }

    int getD() {
        return this.d;
    }

    boolean isMarked() {
        return this.isMarked;
    }

    void setI(char i) {
        this.i = i;
    }

    void setD(int d) {
        this.d = d;
    }

    void setSez(int sez) {
        this.sez = sez;
    }

    void setVorgaenger(ArrayList<Arbeitspaket> vorgaenger) {
        this.vorgaenger = vorgaenger;
    }

    void setNachfolger(ArrayList<Arbeitspaket> nachfolger) {
        this.nachfolger = nachfolger;
    }

    void setMark() {
        this.isMarked = true;
    }

    void removeMark() {
        this.isMarked = false;
    }

    void calculateFaz() {
        if (vorgaenger != null) {
            faz = vorgaenger.get(0).getFez();
            for (Arbeitspaket pointer : vorgaenger) {
                if (pointer.getFez() > faz) {
                    faz = pointer.getFez();
                }
            }
        }
    }

    void calculateFez() {
        fez = faz + d;
    }

    void calculateSaz() {
        saz = sez - d;
    }

    void calculateSez() {
        if (nachfolger != null) {
            int min = nachfolger.get(0).getSaz();
            for (Arbeitspaket pointer : nachfolger) {
                if (pointer.getSaz() < min) {
                    min = pointer.getSaz();
                }
            }
            this.sez = min;
        }
    }

    void calculateGp() {
        gp = saz - faz;
    }

    void calculateUp() {
        if (nachfolger != null) {
            int min = nachfolger.get(0).getFaz();
            for (Arbeitspaket pointer : nachfolger) {
                if (pointer.getFaz() < min) {
                    min = pointer.getFaz();
                }
            }
            int max = 0;
            if (vorgaenger != null) {
                for (Arbeitspaket pointer : vorgaenger) {
                    if (pointer.getSez() > max) {
                        max = pointer.getSez();
                    }
                }
            }
            up = min - max - d;
        }
    }

    void calculateFp() {
        if (nachfolger != null) {
            int min = nachfolger.get(0).getFaz();
            for (Arbeitspaket pointer : nachfolger) {
                if (pointer.getFaz() < min) {
                    min = pointer.getFaz();
                }
            }
            fp = min - fez;
        }
    }

    void printArbeitspaket() {
        System.out.printf("%s %d %d%n", i, faz, fez);
        System.out.printf("%d %d %d%n", d, saz, sez);
        System.out.printf("%d %d %d%n", gp, fp, up);
    }
}
