package com.angrybirds.Screens;

import com.angrybirds.Main;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PauseScreen implements Screen {
    private Main game;
    private Stage stage;
    private Texture background;
    private Skin skin;
    private BitmapFont font;

    public PauseScreen(Main game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        background = new Texture("pausemenu.jpg");
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        font = new BitmapFont(Gdx.files.internal("nicefont.fnt"));
        font.getData().setScale(2f);
        PauseScreenButtons();
    }

    private void PauseScreenButtons() {
        TextButtonStyle pausebutton = new TextButtonStyle();
        pausebutton.font = font;
        pausebutton.fontColor = Color.BLACK;
        pausebutton.up = skin.newDrawable("white", Color.VIOLET);
        pausebutton.down = skin.newDrawable("white", Color.WHITE);

        Table table = new Table();
        table.setFillParent(true);

        // Resume Game Button
        TextButton resumeButton = new TextButton("Resume Game", pausebutton);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(Main.currentScreen);
            }
        });


        // Back to Menu button
        TextButton backToMenu = new TextButton("Back to Menu", pausebutton);
        backToMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SelectLevel(game));
                backlogic();
            }
        });

        // Exit button
        TextButton exit = new TextButton("Exit", pausebutton);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(resumeButton).width(280).height(80).padBottom(50).row();
        table.add(backToMenu).width(280).height(80).padBottom(45).row();
        table.add(exit).width(280).height(80).padBottom(30).row();
        stage.addActor(table);
    }


    private void backlogic() {
        if (Main.currentLevel == 1) {
            Level1.clearComponents();
        }
        else if (Main.currentLevel == 2) {
            Level2.clearComponents();
        }
        else if (Main.currentLevel == 3) {
            Level3.clearComponents();
        }
        else if (Main.currentLevel == 4) {
            Level4.clearComponents();
        }
        else if (Main.currentLevel == 5) {
            Level5.clearComponents();
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
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
    }
}
