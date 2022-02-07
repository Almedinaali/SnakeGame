package com.almedina;

import com.almedina.gui.MainWindow;
import com.almedina.konzola.Konzola;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Snake {

	public static void main(String[] args) {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Konzola ili GUI: ");
		try {
			String line = in.readLine();
			if (line.equalsIgnoreCase("konzola")) {
				Konzola konzola = new Konzola();
				konzola.start();
			} else {
				MainWindow mainWindow = new MainWindow();
				mainWindow.start();
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
