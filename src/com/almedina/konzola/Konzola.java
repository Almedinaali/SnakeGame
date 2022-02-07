package com.almedina.konzola;

import com.almedina.logika.SnakeLogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** Napomena: Za pomjeranje zmijske glave lijevo, gore, desno, dolje u konzoli se koriste tipke a, w, d, s (respektivno) na tastaturi */
public class Konzola {

    private final int SIRINA_PLOCE = 70;
    private final int VISINA_PLOCE = 25;

    private SnakeLogic snakeLogic;
    private BufferedReader in;

    public void start() {
        in = new BufferedReader(new InputStreamReader(System.in));
        snakeLogic = new SnakeLogic("console");
        noviKorak();
    }

    /**Ova metoda crta sve sto vidimo u konzoli: zid, zmiju i hranu*/
    private void nacrtaj() {
        for (int i = 0; i < VISINA_PLOCE; i++) {
            for (int j = 0; j < SIRINA_PLOCE; j++) {
                if(i == 0 || i == (VISINA_PLOCE - 1) || j == 0 || j == (SIRINA_PLOCE - 1)) {
                    System.out.print("*");
                } else if (i == snakeLogic.getHrana_y() && j == snakeLogic.getHrana_x()) {
                    System.out.print("o");
                } else {
                    boolean isSnake = false;
                    for (int z = 0; z < snakeLogic.getDijelovi(); z++) {
                        if (i == snakeLogic.getcY()[z] && j == snakeLogic.getcX()[z]) {
                            System.out.print("#");
                            isSnake = true;
                        }
                    }
                    if (!isSnake) {
                        System.out.print(" ");
                    }
                }

            }
            System.out.println("");
        }
    }

    /** Ispisuje "Kraj Igre" kad se igra završi. */
    private void krajIgre() {
        System.out.println("Kraj Igre");
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    /** Svaki put kad korisnik unese novi input, ova metoda poziva sve provjere. */
    public void noviKorak() {
        snakeLogic.provjeriHranu();
        snakeLogic.move(1);
        snakeLogic.provjeriSudar(VISINA_PLOCE, SIRINA_PLOCE);
        if (!snakeLogic.isIgraTraje()) {
            krajIgre();
        }
        nacrtaj();
        keysReader();
    }

    private void keysReader() {
        try {
            String line = "";

            line = in.readLine();
            if ((line.equals("a")) && (!snakeLogic.isDesno())) {
                snakeLogic.changeDirection(false, true, false, false);
            }
            if ((line.equals("d")) && (!snakeLogic.isLijevo())) {
                snakeLogic.changeDirection(false, false, false, true);
            }
            if ((line.equals("w")) && (!snakeLogic.isDolje())) {
                snakeLogic.changeDirection(true, false, false, false);
            }
            if ((line.equals("s")) && (!snakeLogic.isGore())) {
                snakeLogic.changeDirection(false, false, true, false);
            }

            noviKorak();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
