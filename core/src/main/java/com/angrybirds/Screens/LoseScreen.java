package com.angrybirds.Screens;

import com.angrybirds.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoseScreen implements Screen {
    private Main game;
    private Stage stage;
    private Texture background;

    public LoseScreen(Main game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        background = new Texture("Levels/levellose.jpg");
        Menuoption();
        ReplayLevel();
    }

    private void Menuoption() {
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (x >= 630 && x <= 630+100 && y >= 70 && y <= 70+100) {
                    game.setScreen(new SelectLevel(game));
                }
            }
        });
    }

    private void ReplayLevel() {
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (x >= 850 && x <= 850+100 && y >= 70 && y <= 70+100) {
                    Replaylogic();
                }
            }
        });
    }

    private void Replaylogic() {
        if (Main.currentLevel == 1) {
            game.setScreen(new Level1(game,"Levels/level1.jpeg"));
        }
        else if (Main.currentLevel == 2) {
            game.setScreen(new Level2(game,"Levels/level2.png"));
        }
        else if (Main.currentLevel == 3) {
            game.setScreen(new Level3(game,"Levels/level3.png"));
        }
        else if (Main.currentLevel == 4) {
            game.setScreen(new Level4(game,"Levels/level4.png"));
        }
        else if (Main.currentLevel == 5) {
            game.setScreen(new Level5(game,"Levels/level5.png"));
        }
    }


    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0, 0, Main.width, Main.height);
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
        background.dispose();
    }
}
