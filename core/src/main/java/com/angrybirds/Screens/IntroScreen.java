package com.angrybirds.Screens;

import com.angrybirds.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;

public class IntroScreen implements Screen {
    private Main game;
    private Texture StartImage;

    public IntroScreen(Main game) {
        this.game = game;
        StartImage = new Texture("logo.png");
    }

    @Override
    public void show() {
        // Shows AngryBird screen for 3 seconds
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new MenuScreen(game));
            }
        }, 3);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(StartImage, 0, 0, Main.width, Main.height);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        StartImage.dispose();
    }
}
