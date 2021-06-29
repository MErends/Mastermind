package nl.erends.mastermind;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

class Vakje extends Circle {



    private boolean locked;
    private int x;
    private int y;

    private Kleur kleur;

    private Vakje(int x, int y, Kleur kleur) {
        this.x = x;
        this.y = y;
        setStroke(Paint.valueOf("black"));
        this.setRadius(30);
        this.setCenterX(100 + x * 65);
        this.setCenterY(100 + y * 70);
        setKleur(kleur);
        locked = true;
        if (y == 0) {
            unlock();
        }
    }

    Vakje(int x, int y) {
        this(x, y, Kleur.GRIJS);
    }

    void setKleur(Kleur kleur) {
        this.kleur = kleur;
        this.setFill(Paint.valueOf(kleur.naam));
    }

    boolean isLocked() {
        return locked;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void unlock() {
        setKleur(Kleur.WIT);
        locked = false;
    }

    void lock() {
        locked = true;
    }

    Kleur getKleur() {
        return kleur;
    }
}
