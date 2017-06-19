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
    private Arbeitspaket[] vorgaenger;
    private Arbeitspaket[] nachfolger;

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

    Arbeitspaket[] getVorgaenger() {
        return this.vorgaenger;
    }

    Arbeitspaket[] getNachfolger() {
        return this.nachfolger;
    }

    void setFaz(int faz) {
        this.faz = faz;
    }

    void setSez(int sez) {
        this.sez = sez;
    }

    void setVorgaenger(Arbeitspaket[] vorgaenger) {
        this.vorgaenger = vorgaenger;
    }

    void setNachfolger(Arbeitspaket[] nachfolger) {
        this.nachfolger = nachfolger;
    }

    void calculateFaz() {
        if (vorgaenger != null) {
            faz = vorgaenger[0].getFez();
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
            int min = nachfolger[0].getSaz();
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
            int min = nachfolger[0].getFaz();
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
            int min = nachfolger[0].getFaz();
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
