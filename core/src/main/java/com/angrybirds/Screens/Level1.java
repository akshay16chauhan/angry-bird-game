package com.angrybirds.Screens;

import com.angrybirds.Birds.*;
import com.angrybirds.GameContactListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.angrybirds.Blocks.*;
import com.angrybirds.Main;
import com.angrybirds.Pigs.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.ArrayList;


public class Level1 implements Screen {
    public Main game;
    public Stage stage;
    public Texture background;
    public Texture leveltitle;
    public Texture trajectory_texture;
    public Texture pauseIcon;
    public ImageButton pauseButton;

    public static final float SPEED_SCALE_FACTOR = 4.0f;
    public final World world = new World(new Vector2(0, -9.8f), true);

    public Blockbase blockbase;
    public Catapult sling1;
    public Catapult sling2;
    public Catapult base;
    public Rigidbody ground;
    public Rigidbody airspace;

    public static ArrayList<Bird> birds = new ArrayList<>();
    public static ArrayList<Block> blocks = new ArrayList<>();
    public static ArrayList<Pig> pigs = new ArrayList<>();
    private float accumulator = 0;

    public Bird currentBird;
    public int currentBirdIndex = 0;

    public boolean isDragging;
    public float dragStartX, dragStartY;
    public float releaseX, releaseY;
    public int stopY;


    public Level1(Main game, String path) {
        this.game = game;
        Main.currentScreen = this;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        world.setContactListener(new GameContactListener());
        background = new Texture(path);
        leveltitle = new Texture("Texts/txt1.png");
        pauseIcon = new Texture("pause.png");
        trajectory_texture = new Texture("smoke.png");
        stopY = 178;
        ConstructBirds();
        ConstructCatapult();
        ConstructPigs();
        ConstructBlocks();
        PauseButton();
    }

    public static void removeBlock(Block block) {
        blocks.remove(block);
    }
    public static void removePig(Pig pig) {
        pigs.remove(pig);
    }

    public static void clearComponents() {
        birds.clear();
        blocks.clear();
        pigs.clear();
    }

    public void ConstructBirds() {
        birds.add(new BlueBird(world,137, 300));
        birds.add(new RedBird(world,137, 300));
        birds.add(new RedBird(world,137, 300));
        birds.add(new YellowBird(world,137, 300));
        birds.add(new YellowBird(world,137, 300));
        birds.add(new BlackBird(world,137,300,pigs,blocks));
        currentBird = birds.get(currentBirdIndex);
    }

    public void ConstructCatapult() {
        sling1 = new Catapult(138,206);
        sling2 = new Catapult(170,206,"");
        base = new Catapult(120,171,0);
        blockbase = new Blockbase(world,1080,171,520,55);
        ground = new Rigidbody(world,220,160,850,7);
        airspace = new Rigidbody(world,1593,220,7,440);
    }

    public void ConstructPigs() {
        pigs.add(new MinionPig(world,1190,400));
        pigs.add(new MinionPig(world,1318,400));
        pigs.add(new MinionPig(world,1446,400));

        pigs.add(new MediumPig(world,1315,600));

        pigs.add(new KingPig(world,1318,300));
    }

    public void ConstructBlocks() {
        blocks.add(new WoodenBlock(world,1170,220,35,120,"v"));
        blocks.add(new WoodenBlock(world,1470,220,35,120,"v"));
        blocks.add(new WoodenBlock(world,1330,360,335,30,"h"));

        blocks.add(new GlassBlock(world,1270,440,35,120,"v"));
        blocks.add(new GlassBlock(world,1390,440,35,120,"v"));
        blocks.add(new GlassBlock(world,1326,580,155,30,"h"));
    }

    public void PauseButton() {
        pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseIcon)));
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseScreen(game));
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.top().right();
        table.add(pauseButton).width(80).height(80).pad(10);
        stage.addActor(table);
    }


    @Override
    public void show() {}

    public void update(float deltaTime) {
        accumulator += deltaTime;

        while (accumulator >= 1 / 60f) {
            world.step(1 / 60f, 6, 2);
            accumulator -= 1 / 60f;
        }
        GameContactListener.destroyBodiesAfterStep();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        if (currentBird != null && currentBird.isLaunched() && currentBird.hasStopped(stopY)) {
            switchToNextBird();
        }
        if (currentBird != null && !isDragging) {
            currentBird.updatePosition(delta);
        }

        game.batch.begin();
        game.batch.draw(background, 0, 0, Main.width, Main.height);
        game.batch.draw(leveltitle, 25, 775, 180, 65);
        game.batch.draw(sling2.getSling2(), sling2.getSling2Pos().x, sling2.getSling2Pos().y, sling2.getWidth(), sling2.getHeight());
        game.batch.draw(base.getBaseTexture(), base.getBasePos().x, base.getBasePos().y, 100, 40);
        blockbase.render(game.batch);
        ground.render(game.batch);
        airspace.render(game.batch);

        renderBirds();
        game.batch.draw(sling1.getSling1(), sling1.getSling1Pos().x, sling1.getSling1Pos().y, sling1.getWidth(), sling1.getHeight());

        renderPigs();
        renderBlocks();

        game.batch.end();

        renderStrip();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        dragRegion();
    }


    public void renderBirds() {
        if (isDragging) {
            Vector3 worldCoordinates = stage.getCamera().unproject(new Vector3(releaseX, releaseY, 0));

            float worldX = worldCoordinates.x;
            float worldY = worldCoordinates.y;

            if (currentBird instanceof BlueBird) {
                BlueBird blueBird = (BlueBird) currentBird;

                if (Gdx.input.justTouched()) {
                    blueBird.applySpecialEffect();
                    blueBird.renderSplitBirds(game.batch);
                }
                else {
                    game.batch.draw(currentBird.getTextureRegion(), worldX, worldY, currentBird.getWidth(), currentBird.getHeight());
                }
            }
            else {
                game.batch.draw(currentBird.getTextureRegion(), worldX, worldY, currentBird.getWidth(), currentBird.getHeight());
            }
        }
        else {
            if (currentBird instanceof BlueBird) {
                BlueBird blueBird = (BlueBird) currentBird;

                if (blueBird.isSplitActive()) {
                    blueBird.renderSplitBirds(game.batch);
                }
                else {
                    Vector2 position = blueBird.getBody().getPosition();
                    game.batch.draw(currentBird.getTextureRegion(),position.x * 100,position.y * 100, currentBird.getWidth(), currentBird.getHeight());
                }
            }
            else {
                Vector2 position = currentBird.getBody().getPosition();
                game.batch.draw(currentBird.getTextureRegion(),position.x * 100,position.y * 100, currentBird.getWidth(), currentBird.getHeight());
            }
        }
    }


    public void renderStrip() {
        if (isDragging) {
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            Color Dark_brown = new Color(77/255f, 38/255f, 17/255f, 1);

            shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Dark_brown);

            Vector3 worldCoordinates = stage.getCamera().unproject(new Vector3(releaseX, releaseY, 0));
            Vector2 vector = new Vector2(worldCoordinates.x, worldCoordinates.y);

            Vector2 baseLeft = new Vector2(147,334);
            Vector2 baseRight = new Vector2(187,333);
            shapeRenderer.rectLine(baseLeft, vector, 10);
            shapeRenderer.rectLine(baseRight, vector, 10);

            shapeRenderer.end();
            shapeRenderer.dispose();
        }
    }



    public void renderPigs() {
        for (Pig pig : pigs) {
            if (!pig.isDestroyed()) {
                game.batch.draw(pig.getTexture(),pig.getPosition().x - pig.getRadius(),pig.getPosition().y - pig.getRadius(),pig.getRadius()*2, pig.getRadius()*2);
            }
        }
    }

    public void renderBlocks() {
        for (Block block : blocks) {
            if (!block.isDestroyed()) {
                Vector2 position = block.getBody().getPosition();
                float angle = (float) Math.toDegrees(block.getBody().getAngle());

                game.batch.draw(block.getTextureRegion(),(position.x * 100f) - (block.getWidth() / 2),(position.y * 100f) - (block.getHeight() / 2),
                    block.getWidth() / 2,block.getHeight() / 2, block.getWidth(), block.getHeight(),1, 1, angle);
            }
        }
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
        leveltitle.dispose();
        pauseIcon.dispose();
        world.dispose();
        blockbase.dispose();
        ground.dispose();
        airspace.dispose();
        sling1.getSling1().dispose();
        sling2.getSling2().dispose();
        base.getBaseTexture().dispose();

        if (trajectory_texture != null) {
            trajectory_texture.dispose();
        }

        for (Block block : blocks) {
            block.dispose();
        }

        for (Pig pig : pigs) {
            pig.dispose();
        }
    }


    public void switchToNextBird() {
        currentBirdIndex++;

        if (pigs.isEmpty()) {
            game.setScreen(new WinScreen(game));
            clearComponents();
        }
        if (currentBirdIndex >= birds.size() && !pigs.isEmpty()) {
            game.setScreen(new LoseScreen(game));
            clearComponents();
        }
        if (currentBirdIndex < birds.size()) {
            currentBird = birds.get(currentBirdIndex);
        }
    }


    public void dragRegion() {
        handleMouseInput(80,280,80,80);
    }


    public void handleMouseInput(float x, float y, float w, float h) {
        if (currentBird == null) return;

        if (!currentBird.isLaunched()) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                if (!isDragging) {
                    dragStartX = Gdx.input.getX();
                    dragStartY = Gdx.input.getY();

                    Vector3 worldStart = stage.getCamera().unproject(new Vector3(dragStartX, dragStartY, 0));
                    if (worldStart.x >= x && worldStart.x <= x+w && worldStart.y >= y && worldStart.y <= y+h) {
                        isDragging = true;
                    }
                }

                if (isDragging) {
                    releaseX = Gdx.input.getX();
                    releaseY = Gdx.input.getY();

                    Vector3 worldStart = stage.getCamera().unproject(new Vector3(dragStartX, dragStartY, 0));
                    Vector3 worldRelease = stage.getCamera().unproject(new Vector3(releaseX, releaseY, 0));
                    drawTrajectory((float) Math.atan2(worldRelease.y - worldStart.y, worldRelease.x - worldStart.x),
                        worldStart.dst(worldRelease) * SPEED_SCALE_FACTOR*0.4f, worldStart.x, worldStart.y);
                }
            }
            else if (isDragging) {
                isDragging = false;

                Vector3 worldStart = stage.getCamera().unproject(new Vector3(dragStartX, dragStartY, 0));
                Vector3 worldRelease = stage.getCamera().unproject(new Vector3(releaseX, releaseY, 0));
                launchBird(worldStart, worldRelease);
            }
        }
        else if (Gdx.input.justTouched()) {
            currentBird.applySpecialEffect();
        }
    }

    public void drawTrajectory(float angle, float speed, float startX, float startY) {
        SpriteBatch spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(stage.getCamera().combined);

        int steps = 50;
        float timeStep = 0.1f;
        float gravity = 9.8f;

        float velocityX = (float) (speed * Math.cos(angle)) * -1;
        float velocityY = (float) (speed * Math.sin(angle)) * -1;

        spriteBatch.begin();

        for (int i = 0; i < steps; i++) {
            float t = i * timeStep;
            float x = startX + velocityX * t;
            float y = startY + velocityY * t - 0.5f * gravity * t * t;

            if (y < 0) break;

            spriteBatch.draw(trajectory_texture,x - trajectory_texture.getWidth() / 2f,y - trajectory_texture.getHeight() / 2f,
                trajectory_texture.getWidth(), trajectory_texture.getHeight());
        }

        spriteBatch.end();
        spriteBatch.dispose();
    }


    public void launchBird(Vector3 dragStartPos, Vector3 releasePos) {
        float dx = dragStartPos.x - releasePos.x;
        float dy = dragStartPos.y - releasePos.y;
        float speed = (float) Math.sqrt(dx * dx + dy * dy);

        float maxSpeed = 800;
        if (speed > maxSpeed) {
            float scaleFactor = maxSpeed / speed;
            dx *= scaleFactor;
            dy *= scaleFactor;
            speed = maxSpeed;
        }

        currentBird.launch(speed * 0.17f, dx, dy);
        currentBird.setLaunched(true);
    }

}
