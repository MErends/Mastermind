package nl.erends.mastermind;

public enum Kleur {
    ROOD("red"),
    GEEL("yellow"),
    BLAUW("blue"),
    GROEN("green"),
    ORANJE("orange"),
    PAARS("purple"),
    WIT("white"),
    ROZE("pink"),
    TURKOOIS("turquoise"),
    GRIJS("grey");

    public String naam;

    Kleur(String naam) {
        this.naam = naam;
    }

    @Override
    public String toString() {
        return naam;
    }
}
