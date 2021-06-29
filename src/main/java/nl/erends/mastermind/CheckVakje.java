package nl.erends.mastermind;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

class CheckVakje extends Circle {

    CheckVakje(int rij, int vaknummer) {
        setStroke(Paint.valueOf("black"));
        this.setRadius(10);
        int x = 35;
        if (vaknummer == 0 || vaknummer == 2) {
            x -= 12;
        } else {
            x += 12;
        }
        this.setCenterX(x);
        int y = 100;
        y += (rij * 70);
        if (vaknummer == 0 || vaknummer == 1) {
            y -= 12;
        } else {
            y += 12;
        }
        this.setCenterY(y);
        setKleur(Kleur.WIT);
    }

    void setKleur(Kleur kleur) {
        this.setFill(Paint.valueOf(kleur.naam));
    }
}
