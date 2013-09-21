package com.pogs.pogs;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Pogs";
		cfg.useGL20 = false;
		cfg.width = 320;
		cfg.height = 420;
		
		new LwjglApplication(new PogsMain(), cfg);
	}
}
