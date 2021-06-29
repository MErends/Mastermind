package nl.erends.mastermind;

import javafx.scene.image.ImageView;

class Verf extends ImageView {

    private Kleur kleur;

    Verf(String url, Kleur kleur, int nummer) {
        super(url);
        this.kleur = kleur;
        setScaleX(0.3);
        setScaleY(0.3);
        setX(325);
        setY(25 + nummer * 100);
        if (nummer == 0) {
            setActive(true);
        }
    }

    Kleur getKleur() {
        return kleur;
    }

    void setActive(boolean active) {
        setX(active ? 275 : 325);
    }
}
