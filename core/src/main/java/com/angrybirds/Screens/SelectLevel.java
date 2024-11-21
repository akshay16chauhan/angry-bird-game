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

public class SelectLevel implements Screen {
    private Main game;
    private Stage stage;
    private Texture background;
    private Texture text;
    private BitmapFont levelFont;
    private Skin levelskin;

    public SelectLevel(Main game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        background = new Texture("level.jpg");
        text = new Texture("Texts/select.png");
        levelFont = new BitmapFont(Gdx.files.internal("nicefont.fnt"));
        levelskin = new Skin(Gdx.files.internal("uiskin.json"));
        levelFont.getData().setScale(2.5f);
        LevelButtons();
    }

    private void LevelButtons() {
        TextButtonStyle levelbutton = new TextButtonStyle();
        levelbutton.font = levelFont;
        levelbutton.fontColor = Color.BLACK;
        levelbutton.up = levelskin.newDrawable("white", Color.YELLOW);
        levelbutton.down = levelskin.newDrawable("white", Color.ORANGE);

        Table table = new Table();
        table.setFillParent(true);

        // Go to Level1
        TextButton level1 = new TextButton("1", levelbutton);
        level1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level1(game,"Levels/level1.jpeg"));
                Main.currentLevel = 1;
            }
        });

        // Go to Level2
        TextButton level2 = new TextButton("2", levelbutton);
        level2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level2(game,"Levels/level2.png"));
                Main.currentLevel = 2;
            }
        });

        // Go to Level3
        TextButton level3 = new TextButton("3", levelbutton);
        level3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level3(game,"Levels/level3.png"));
                Main.currentLevel = 3;
            }
        });

        // Go to Level4
        TextButton level4 = new TextButton("4", levelbutton);
        level4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level4(game,"Levels/level4.png"));
                Main.currentLevel = 4;
            }
        });

        // Go to Level5
        TextButton level5 = new TextButton("5", levelbutton);
        level5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level5(game,"Levels/level5.png"));
                Main.currentLevel = 5;
            }
        });

        table.add(level1).width(80).height(80).padTop(20).pad(60);
        table.add(level2).width(80).height(80).padTop(20).pad(60);
        table.add(level3).width(80).height(80).padTop(20).pad(60);
        table.add(level4).width(80).height(80).padTop(20).pad(60);
        table.add(level5).width(80).height(80).padTop(20).pad(60);
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
        game.batch.draw(text, 650, 485, 300, 80);

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
        text.dispose();
    }
}
