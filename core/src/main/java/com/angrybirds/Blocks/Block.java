package com.angrybirds.Blocks;

import com.angrybirds.GameContactListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Block {
    private Body body; // Box2D body
    private TextureRegion textureRegion; // Use TextureRegion instead of Texture
    private Vector2 position;
    private int durability;
    private float width, height;
    private float radius;
    private World world; // Store reference to the Box2D World

    public Block(World world, float x, float y, float width, float height, int durability, String path) {
        this.world = world; // Store the world reference
        this.position = new Vector2(x, y);
        this.durability = durability;
        this.width = width;
        this.height = height;
        this.radius = 20;

        Texture texture = new Texture(path); // Load texture
        this.textureRegion = new TextureRegion(texture); // Wrap in a TextureRegion

        // Define Box2D body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Blocks are dynamic by default
        bodyDef.position.set(x / 100f, y / 100f); // Convert pixels to meters
        body = world.createBody(bodyDef);

        // Define the shape of the block (as a rectangle)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width / 2) / 100f, (height / 2) / 100f); // Define size in meters

        // Define the fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f; // Mass of the block
        fixtureDef.friction = 0.5f; // Friction
        fixtureDef.restitution = 0.0f; // No bounce
        body.createFixture(fixtureDef);

        shape.dispose(); // Dispose of shape to free memory

        // Set user data for collision handling
        body.setUserData(this);
    }

    public Body getBody() {
        return body;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public int getDurability() {
        return durability;
    }

    public void takeDamage(int damage) {
        durability -= damage;
        if (durability <= 0) {
            onDestroyed();
        }
    }

    public void onDestroyed() {
        System.out.println("Block destroyed at: " + position);

        GameContactListener.queueBodyForDestruction(body);
//        dispose();
    }





    public void dispose() {
        if (textureRegion != null && textureRegion.getTexture() != null) {
            textureRegion.getTexture().dispose(); // Dispose the underlying texture
        }
    }


    public Vector2 getPosition() {
        return position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean isDestroyed() {
        if(durability>0){
            return false;
        }
        return true;
    }
}
