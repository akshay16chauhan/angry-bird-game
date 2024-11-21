package com.angrybirds.Screens;

import com.angrybirds.Main;
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

public class MenuScreen implements Screen {
    private Main game;
    private Stage stage;
    private Texture background;
    private Skin skin;
    private BitmapFont font;

    public MenuScreen(Main game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        background = new Texture("menu.png");
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        font = new BitmapFont(Gdx.files.internal("nicefont.fnt"));
        font.getData().setScale(2f);
        StartExitButtons();
    }

    private void StartExitButtons() {
        TextButtonStyle button = new TextButtonStyle();
        button.font = font;
        button.fontColor = Color.BLACK;
        button.up = skin.newDrawable("white", Color.GREEN);
        button.down = skin.newDrawable("white", Color.WHITE);

        Table table = new Table();
        table.setFillParent(true);

        // Start Game button
        TextButton startButton = new TextButton("Start Game", button);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SelectLevel(game));
            }
        });

        // Exit button
        TextButton exitButton = new TextButton("Exit", button);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(startButton).width(200).height(80).padBottom(25).row();
        table.add(exitButton).width(200).height(80).padTop(15).row();
        stage.addActor(table);
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
