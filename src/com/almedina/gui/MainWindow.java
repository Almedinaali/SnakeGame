package com.almedina.gui;

import com.almedina.logika.SnakeLogic;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow {

    public void start() {
        JFrame frame = new JFrame();

        frame.add(new CustomPanel());
        frame.pack();
        frame.setTitle("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private class CustomPanel extends JPanel implements ActionListener {

        private final int SIRINA_PLOCE = 700;
        private final int VISINA_PLOCE = 700;

        private Image diozmije;
        private Image hrana;
        private Image glavazmije;

        private SnakeLogic snakeLogic;
        private Timer timer;

        private final int KASNJENJE = 100;  // odredjuje brzinu igre

        public CustomPanel() {
            initPanel();
        }

        /**
         * Inicijalizacija ploce za igranje
         * */
        public void initPanel () {
            snakeLogic = new SnakeLogic("gui");
            ucitajIkone();

            addKeyListener(new GameKeyAdapter());
            setBackground(Color.WHITE);
            setFocusable(true);
            setPreferredSize(new Dimension(700, 700));

            timer = new Timer(KASNJENJE, this);
            timer.start();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            nacrtaj(g);
        }

        /** Crtanje zmije i hrane */
        private void nacrtaj(Graphics g) {

            if (snakeLogic.isIgraTraje()) {
                for (int i = 0; i < snakeLogic.getDijelovi(); i++) {
                    if (i == 0) {
                        g.drawImage(glavazmije, snakeLogic.getcX()[i], snakeLogic.getcY()[i], this);
                    }
                    else {
                        g.drawImage(diozmije, snakeLogic.getcX()[i], snakeLogic.getcY()[i], this);
                    }
                }

                g.drawImage(hrana, snakeLogic.getHrana_x(), snakeLogic.getHrana_y(), this);

                Toolkit.getDefaultToolkit().sync();
            }
            else {
                krajIgre(g);
            }

        }

        /** Pomocu ove metode se ispisuje tekst "Kraj igre" na ekran ukoliko je doslo do kraja igre. */
        private void krajIgre(Graphics g) {
            String poruka = "Kraj igre";
            Font f = new Font("Helvetica", Font.ITALIC, 17);
            FontMetrics fontMetrics = getFontMetrics(f);

            g.setColor(Color.BLACK);
            g.setFont(f);
            g.drawString(poruka, (SIRINA_PLOCE - fontMetrics.stringWidth(poruka)) / 2, VISINA_PLOCE / 2);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            snakeLogic.provjeriHranu();
            snakeLogic.provjeriSudar(VISINA_PLOCE, SIRINA_PLOCE);
            snakeLogic.move(10);

            repaint();
        }

        /** U ovoj metodi ucitavamo slike potrebne za igricu */
        private void ucitajIkone() {
            diozmije = (new ImageIcon("src/slike/body.png")).getImage();
            hrana = (new ImageIcon("src/slike/hrana.png")).getImage();
            glavazmije = (new ImageIcon("src/slike/head.png")).getImage();
        }


        private class GameKeyAdapter extends KeyAdapter {

            @Override
            public void keyPressed(KeyEvent e) {

                int key = e.getKeyCode();

                if ((key == KeyEvent.VK_LEFT) && (!snakeLogic.isDesno())) {
                    snakeLogic.changeDirection(false, true, false, false);
                }

                if ((key == KeyEvent.VK_RIGHT) && (!snakeLogic.isLijevo())) {
                    snakeLogic.changeDirection(false, false, false, true);
                }

                if ((key == KeyEvent.VK_UP) && (!snakeLogic.isDolje())) {
                    snakeLogic.changeDirection(true, false, false, false);
                }

                if ((key == KeyEvent.VK_DOWN) && (!snakeLogic.isGore())) {
                    snakeLogic.changeDirection(false, false, true, false);
                }
            }
        }

    }


}
