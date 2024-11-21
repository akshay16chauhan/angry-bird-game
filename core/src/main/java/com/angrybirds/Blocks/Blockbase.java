package com.angrybirds.Blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Blockbase {
    private Body body; // Box2D body for the base
    private Texture texture; // Texture for rendering
    private float width, height; // Dimensions of the base (in pixels)

    public Blockbase(World world, float x, float y, float width, float height) {
        this.width = width;   // Width in pixels
        this.height = height; // Height in pixels
        this.texture = new Texture("Blocks/blockbase.png");

        // Convert dimensions to meters for Box2D
        float box2dWidth = width / 100f;  // Assuming 100 pixels = 1 meter
        float box2dHeight = height / 100f;

        // Define the body and fixture
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((x + width / 2) / 100f, (y + height / 2) / 100f); // Center position in meters
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(box2dWidth / 2, box2dHeight / 2); // Box dimensions in meters

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.5f; // Optional friction
        fixtureDef.restitution = 0.0f; // No bouncing

        body.createFixture(fixtureDef);
        shape.dispose(); // Dispose the shape to prevent memory leaks
    }

    public void render(SpriteBatch batch) {
        // Render the texture (Box2D uses meters, so convert to pixels)
        float renderX = (body.getPosition().x * 100f) - (width / 2);
        float renderY = (body.getPosition().y * 100f) - (height / 2);
        batch.draw(texture, renderX, renderY, width, height);
    }

    public void dispose() {
        texture.dispose();
    }
}
