package com.angrybirds;

import com.angrybirds.Screens.IntroScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    public static final int width = 1600;
    public static final int height = 850;
    public static final String title = "Angry Birds";
    public static int currentLevel = 0;
    public static Screen currentScreen;
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new IntroScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
