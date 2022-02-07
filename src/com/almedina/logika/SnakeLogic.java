package com.almedina.logika;

public class SnakeLogic {
	
	/** Konstante koristene u igri */
    private final int TACKE = 40;      // max broj mogucih tacaka na igracoj ploci

    // ova dva niza sadrze x i y koordinate svih tacaka zmije
    private int x[];
    private int y[];
    
    private int dijelovi;
    private int hrana_x;
    private int hrana_y;
    
    private boolean lijevo;
    private boolean desno;
    private boolean gore;
	private boolean dolje;

	private boolean igraTraje;
	private String tipIgre;

    public SnakeLogic(String tipIgre) {
    	this.tipIgre = tipIgre;
    	initGame();
	}

	/** Kreiramo zmiju, na random nacin lociramo hranu na ploci i zapocinjemo tajmer */
	public void initGame() {

		x = new int[TACKE];
		y = new int[TACKE];

		gore = false;
		desno = true;
		dolje = false;
		lijevo = false;

		igraTraje = true;

		dijelovi = 3;

		int velicinaIkone;
		int konstanta;
		if (tipIgre.equals("console")) {
			velicinaIkone = 1;
			konstanta = 10;
		} else {
			velicinaIkone = 10;
			konstanta = 200;
		}

		for (int z = 0; z < dijelovi; z++) {
			x[z] = (konstanta / 2) - z * velicinaIkone;
			y[z] = (konstanta / 2);
		}

		smjestiHranu();

	}

	/** Provjeravamo da li je zmija pojela hranu. Ako jeste, povecavamo broj dijelova zmije.
	 *  Zatim pozivamo metodu koja ce na random mjesto na ploci smjestiti novu hranu */
	public void provjeriHranu() {

		if ((getcX()[0] == getHrana_x()) && (getcY()[0] == getHrana_y())) {
			dijelovi = getDijelovi() + 1;
			smjestiHranu();
		}
	}

	/** Kontrolisemo kretanje zmije. Glavu zmije pomjeramo lijevo, desno, gore ili dolje u zavisnosti od pritisnute cursor tipke.
	 * Unutar for petlje svaki dio zmije pomjeramo za jedno mjesto */
	public void move(int velicinaTacke) {

		for (int i = getDijelovi(); i > 0; i--) {
			getcX()[i] = getcX()[(i - 1)];
			getcY()[i] = getcY()[(i - 1)];
		}

		if (lijevo) {
			getcX()[0] -= velicinaTacke;
		}

		if (desno) {
			getcX()[0] += velicinaTacke;
		}

		if (gore) {
			getcY()[0] -= velicinaTacke;
		}

		if (dolje) {
			getcY()[0] += velicinaTacke;
		}
	}

	/** Provjeravamo da li je glava zmije udarila u zid ili u samu sebe.
	 *  Ako jeste, igra zavrsava pa zaustavljamo tajmer. */
	public void provjeriSudar(int visinaPloce, int sirinaPloce) {

		for (int i = getDijelovi(); i > 0; i--) {

			if ((i > 4) && (getcX()[0] == getcX()[i]) && (getcY()[0] == getcY()[i])) {
				igraTraje = false;
			}
		}

		if (getcY()[0] >= visinaPloce) {
			igraTraje = false;
		}

		if (getcY()[0] < 0) {
			igraTraje = false;
		}

		if (getcX()[0] >= sirinaPloce) {
			igraTraje = false;
		}

		if (getcX()[0] < 0) {
			igraTraje = false;
		}

	}

	/** Na random mjesto na ploci smjestamo hranu za zmiju. */
	public void smjestiHranu() {
		int randomPos;
		if (tipIgre.equals("console")) {
			randomPos = 2;
		} else {
			randomPos = 69;
		}
		int r = (int) (Math.random() * randomPos) + 1;
		hrana_x = (r * 10);

		r = (int) (Math.random() * randomPos) + 1;
		hrana_y = (r * 10);
	}

	public void changeDirection(boolean gore, boolean lijevo, boolean dolje, boolean desno) {
		this.gore = gore;
		this.lijevo = lijevo;
		this.dolje = dolje;
		this.desno = desno;
	}

	public boolean isLijevo() {
		return lijevo;
	}

	public boolean isDesno() {
		return desno;
	}

	public boolean isGore() {
		return gore;
	}

	public boolean isDolje() {
		return dolje;
	}

	public boolean isIgraTraje() {
		return igraTraje;
	}

	public int getHrana_x() {
		return hrana_x;
	}

	public int getHrana_y() {
		return hrana_y;
	}

	public int getDijelovi() {
		return dijelovi;
	}

	public int[] getcX() {
		return x;
	}

	public int[] getcY() {
		return y;
	}


}
