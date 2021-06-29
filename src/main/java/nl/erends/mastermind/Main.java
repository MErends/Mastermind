package nl.erends.mastermind;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Random;

import static nl.erends.mastermind.Kleur.GRIJS;
import static nl.erends.mastermind.Kleur.WIT;

public class Main extends Application {

    private static Random random = new Random();
    private static Kleur gekozenKleur = Kleur.GROEN;
    private static final int RIJEN = 12;
    private Vakje[][] vakjeArray;
    private CheckVakje[][] checkVakjeArray;
    private Vakje[] antwoordArray;
    private Verf[] verven;
    private int beurt = 0;
    private Button volgendeButton = new Button("Controleer!");

    private Kleur[] oplossing;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        vakjeArray = new Vakje[RIJEN][4];
        for (int x = 0; x < 4; x++) {
            for (int rij = 0; rij <  RIJEN; rij++) {
                Vakje vakje = new Vakje(x, rij);
                vakje.setOnMouseClicked(event -> vakjeClicked(vakje));
                vakjeArray[rij][x] = vakje;
                root.getChildren().add(vakje);
            }
        }
        checkVakjeArray = new CheckVakje[RIJEN][4];
        for (int x = 0; x < 4; x++) {
            for (int rij = 0; rij < RIJEN; rij++) {
                CheckVakje checkVakje = new CheckVakje(rij, x);
                checkVakjeArray[rij][x] = checkVakje;
                root.getChildren().add(checkVakje);
            }
        }

        verven = new Verf[8];
        verven[0] = new Verf("green.jpg", Kleur.GROEN, 0);
        verven[1] = new Verf("red.jpg", Kleur.ROOD, 1);
        verven[2] = new Verf("yellow.jpg", Kleur.GEEL, 2);
        verven[3] = new Verf("dark_blue.jpg", Kleur.BLAUW, 3);
        verven[4] = new Verf("purple.jpg", Kleur.PAARS, 4);
        verven[5] = new Verf("orange.jpg", Kleur.ORANJE, 5);
        verven[6] = new Verf("pink.jpg", Kleur.ROZE, 6);
        verven[7] = new Verf("light_blue.jpg", Kleur.TURKOOIS, 7);
        for (Verf verf : verven) {
            verf.setOnMouseClicked(event -> verfClicked(verf));
            root.getChildren().add(verf);
        }

        Line line = new Line();
        line.setStartY(940);
        line.setEndY(940);
        line.setStartX(70);
        line.setEndX(325);
        root.getChildren().add(line);

        antwoordArray = new Vakje[4];
        for (int x = 0; x < 4; x++) {
            Vakje vakje = new Vakje(x, RIJEN + 1);
            antwoordArray[x] = vakje;
            root.getChildren().add(vakje);
        }
        oplossing = genereerOplossing();

        volgendeButton.setLayoutX(400);
        volgendeButton.setLayoutY(900);
        volgendeButton.setOnAction(event -> volgendeBeurt());
        volgendeButton.setDisable(true);
        root.getChildren().add(volgendeButton);

        MenuBar menuBar = new MenuBar();
        menuBar.setPrefWidth(500);
        Menu newGame = new Menu("Start");
        menuBar.getMenus().add(newGame);
        MenuItem newGameItem = new MenuItem("Nieuw spel");
        newGameItem.setOnAction(event -> newGame());
        newGame.getItems().add(newGameItem);
        root.getChildren().add(menuBar);



        primaryStage.setTitle("Mastermind");
        primaryStage.setScene(new Scene(root, 500, 1100));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void vakjeClicked(Vakje vakje) {
        if (!vakje.isLocked()) {
            vakje.setKleur(gekozenKleur);
        }
        int vakjesGekleurd = 0;
        for (int x = 0; x < 4; x++) {
            if (vakjeArray[beurt][x].getKleur() != WIT) {
                vakjesGekleurd++;
            }
        }
        if (vakjesGekleurd == 4) {
            volgendeButton.setDisable(false);
        }
    }

    private void verfClicked(Verf verf) {
        gekozenKleur = verf.getKleur();
        for (Verf verf2 : verven) {
            verf2.setActive(false);
        }
        verf.setActive(true);
    }

    private void newGame() {
        oplossing = genereerOplossing();
        volgendeButton.setDisable(true);
        beurt = 0;
        for (Vakje[] rij : vakjeArray) {
            for (Vakje vakje : rij) {
                vakje.lock();
                vakje.setKleur(GRIJS);
            }
        }
        for (Vakje vakje : vakjeArray[0]) {
            vakje.unlock();
        }
        for (Vakje vakje : antwoordArray) {
            vakje.setKleur(GRIJS);
        }
        for (CheckVakje[] rij : checkVakjeArray) {
            for (CheckVakje vakje : rij) {
                vakje.setKleur(WIT);
            }
        }
    }

    private void volgendeBeurt() {
        volgendeButton.setDisable(true);
        for (Vakje vakje : vakjeArray[beurt]) {
            vakje.lock();
        }
        boolean opgelost = checkOplossing();
        beurt++;
        if (opgelost || beurt == RIJEN) {
            toonOplossing();
            return;
        }
        for (Vakje vakje : vakjeArray[beurt]) {
            vakje.unlock();
        }
    }

    private Kleur[] genereerOplossing() {
        Kleur[] oplossing = new Kleur[4];
        Kleur[] kleuren = Kleur.values();
        for (int i = 0; i < 4; i++) {
            Kleur kleur;
            do {
                kleur = kleuren[random.nextInt(kleuren.length)];
            } while (kleur == GRIJS || kleur == WIT);
            oplossing[i] = kleur;
        }
        return oplossing;
    }

    private boolean checkOplossing() {
        Kleur[] gok = new Kleur[4];
        for (int x = 0; x < 4; x++) {
            gok[x] = vakjeArray[beurt][x].getKleur();
        }
        Kleur[] oplossing = Arrays.copyOf(this.oplossing, 4);
        int countGoed = 0;
        for (int x = 0; x < 4; x++) {
            if (gok[x] == oplossing[x] && gok[x] != GRIJS) {
                countGoed++;
                gok[x] = GRIJS;
                oplossing[x] = GRIJS;
            }
        }
        int countFout = 0;
        outer:
        for (int x = 0; x < 4; x++) {
            if (gok[x] != GRIJS) {
                for (int x2 = 0; x2 < 4; x2++) {
                    if (gok[x] == oplossing[x2]) {
                        countFout++;
                        gok[x] = GRIJS;
                        oplossing[x2] = GRIJS;
                        continue outer;
                    }
                }
            }
        }
        int checkVakjeNummer = 0;
        for (int x = 0; x < countGoed; x++) {
            checkVakjeArray[beurt][checkVakjeNummer].setKleur(Kleur.ROOD);
            checkVakjeNummer++;
        }
        for (int x = 0; x < countFout; x++) {
            checkVakjeArray[beurt][checkVakjeNummer].setKleur(Kleur.GEEL);
            checkVakjeNummer++;
        }
        return countGoed == 4;
    }

    private void toonOplossing() {
        for (int x = 0; x < 4; x++) {
            antwoordArray[x].setKleur(oplossing[x]);
        }
    }
}
