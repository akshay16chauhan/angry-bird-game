package com.angrybirds.Birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Body;


public class BlueBird extends Bird {
    private boolean isSplitActive = false;
    private Vector2[] splitVelocities; // Velocities for split birds
    private Vector2[] splitPositions; // Positions for split birds
    private Texture splitTexture;
    private World world;// Texture for split birds

    public BlueBird(World world, float x, float y) {
        super(world, x, y,15); // Pass world and position to superclass
        this.splitTexture = new Texture("Birds/blue.png"); // Load split texture
        this.world=world;
    }

    @Override
    protected Texture loadBirdTexture() {
        return new Texture("Birds/blue.png");
    }

    @Override
    public int setWidth() {
        return 50;
    }

    @Override
    public int setHeight() {
        return 50;
    }

    @Override
    public void applySpecialEffect() {
        if (!isSplitActive && isLaunched()) {
            isSplitActive = true;

            // Initialize split birds
            splitVelocities = new Vector2[3];
            splitPositions = new Vector2[3];

            Vector2 currentVelocity = body.getLinearVelocity();
            Vector2 currentPosition = body.getPosition();

            splitVelocities[0] = new Vector2(currentVelocity.x + 5, currentVelocity.y + 2);
            splitVelocities[1] = new Vector2(currentVelocity.x, currentVelocity.y);
            splitVelocities[2] = new Vector2(currentVelocity.x - 5, currentVelocity.y - 2);

            // Destroy the main bird body
            world.destroyBody(body);

            for (int i = 0; i < 3; i++) {
                splitPositions[i] = new Vector2(currentPosition);

                // Create a new Box2D body for each split bird
                body = createSplitBody(splitPositions[i]);
                body.setLinearVelocity(splitVelocities[i]);
            }
        }
    }

    // Helper method to create Box2D bodies for split birds
    private Body createSplitBody(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);

        Body splitBody = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.25f); // Radius in meters

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.0f;

        splitBody.createFixture(fixtureDef);
        shape.dispose();

        return splitBody;
    }


    @Override
    public void updatePosition(float deltaTime) {
        if (!isSplitActive) {
            super.updatePosition(deltaTime);
        } else {
            // Split birds now have their own Box2D bodies, so their positions are updated by Box2D
            for (int i = 0; i < 3; i++) {
                splitPositions[i] = new Vector2(
                    body.getWorldCenter().x + i * 0.5f, // Example offset to spread them apart
                    body.getWorldCenter().y
                );
            }
        }
    }


    public void renderSplitBirds(SpriteBatch batch) {
        if (isSplitActive) {
            for (Vector2 position : splitPositions) {
                batch.draw(splitTexture, position.x * 100f, position.y * 100f, setWidth(), setHeight());
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        splitTexture.dispose(); // Dispose of split texture
    }

    public boolean isSplitActive() {
        return isSplitActive;
    }
}
