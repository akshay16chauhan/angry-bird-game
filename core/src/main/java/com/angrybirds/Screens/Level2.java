package com.angrybirds.Screens;

import com.angrybirds.Birds.BlackBird;
import com.angrybirds.Birds.BlueBird;
import com.angrybirds.Birds.RedBird;
import com.angrybirds.Birds.YellowBird;
import com.angrybirds.Blocks.*;
import com.angrybirds.Main;
import com.angrybirds.Pigs.KingPig;
import com.angrybirds.Pigs.MediumPig;
import com.angrybirds.Pigs.MinionPig;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Level2 extends Level1 {

    public Level2(Main game, String path) {
        super(game, path);
        leveltitle = new Texture("Texts/txt2.png");
        stopY = 137;
    }

    @Override
    public void ConstructBirds() {
        birds.add(new BlueBird(world,137, 259));
        birds.add(new RedBird(world,137, 259));
        birds.add(new RedBird(world,137, 259));
        birds.add(new YellowBird(world,137, 259));
        birds.add(new YellowBird(world,137, 259));
        birds.add(new BlackBird(world,137,259,pigs,blocks));
        currentBird = birds.get(currentBirdIndex);
    }

    @Override
    public void ConstructCatapult() {
        sling1 = new Catapult(138,165);
        sling2 = new Catapult(170,165,"");
        base = new Catapult(120,130,0);
        blockbase = new Blockbase(world,1080,130,520,55);
        ground = new Rigidbody(world,220,119,850,7);
        airspace = new Rigidbody(world,1593,179,7,440);
    }

    @Override
    public void ConstructPigs() {
        pigs.add(new MinionPig(world,1190,400));
        pigs.add(new MinionPig(world,1318,400));
        pigs.add(new MinionPig(world,1446,400));

        pigs.add(new MediumPig(world,1315,600));

        pigs.add(new KingPig(world,1318,300));
    }

    @Override
    public void ConstructBlocks() {
        blocks.add(new WoodenBlock(world,1170,220,35,120,"v"));
        blocks.add(new WoodenBlock(world,1470,220,35,120,"v"));
        blocks.add(new WoodenBlock(world,1330,360,335,30,"h"));

        blocks.add(new GlassBlock(world,1270,440,35,120,"v"));
        blocks.add(new GlassBlock(world,1390,440,35,120,"v"));
        blocks.add(new GlassBlock(world,1326,580,155,30,"h"));
    }



    @Override
    public void dragRegion() {
        handleMouseInput(80,239,80,80);
    }

    @Override
    public void renderStrip() {
        if (isDragging) {
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            Color Dark_brown = new Color(77/255f, 38/255f, 17/255f, 1);

            shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Dark_brown);

            Vector3 worldCoordinates = stage.getCamera().unproject(new Vector3(releaseX, releaseY, 0));
            Vector2 vector = new Vector2(worldCoordinates.x, worldCoordinates.y);

            Vector2 baseLeft = new Vector2(147,293);
            Vector2 baseRight = new Vector2(187,292);
            shapeRenderer.rectLine(baseLeft, vector, 10);
            shapeRenderer.rectLine(baseRight, vector, 10);

            shapeRenderer.end();
            shapeRenderer.dispose();
        }
    }

}
