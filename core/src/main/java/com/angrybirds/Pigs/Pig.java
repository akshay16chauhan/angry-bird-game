package com.angrybirds.Pigs;

import com.angrybirds.GameContactListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*; // Import Box2D classes

public abstract class Pig {
    private Body body; // Box2D body for physics
    private Texture texture;
    private float radius;
    protected int health;
    private World world;// Store the original radius in pixels

    // PPM: Pixels-per-meter conversion factor
    private static final float PPM = 100.0f;

    public Pig(World world, int x, int y, String path, float radius, int health) {
        this.radius = radius;
        this.health = health;
        this.world = world;

        // Initialize texture
        texture = new Texture(path);

        // Convert position and radius to Box2D world units
        float radiusInMeters = radius / PPM;
        float xInMeters = x / PPM;
        float yInMeters = y / PPM;

        // Create Box2D body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xInMeters, yInMeters);
        bodyDef.linearVelocity.set(0, 0); // No initial velocity
        bodyDef.angularVelocity = 0;

        // Create the body in the world
        body = world.createBody(bodyDef);

        // Define the shape (circle)
        CircleShape shape = new CircleShape();
        shape.setRadius(radiusInMeters);

        // Create the fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.0f;

        // Collides with buildings
        body.setUserData(this);

        // Attach the fixture to the body
        body.createFixture(fixtureDef);

        // Apply damping to reduce unintended movement
        body.setLinearDamping(1.0f);
        body.setAngularDamping(1.0f);

        // Dispose of the shape to free memory
        shape.dispose();
    }

    // Getters for rendering and position
    public Vector2 getPosition() {
        // Convert the body's position from meters to pixels
        return new Vector2(
            body.getPosition().x * PPM, // Convert x to pixels
            body.getPosition().y * PPM  // Convert y to pixels
        );
    }

    public Texture getTexture() {
        return texture;
    }

    public float getRadius() {
        return radius;
    }

    public void dispose() {
        // Dispose of the texture when no longer needed
        texture.dispose();
    }
    public Body getBody() {
        return body;
    }

    public int getHealth() {
        return health;
    }
    public void reduceHealth(int damage) {
        health -= damage;
        if(health <= 0) {

            GameContactListener.queueBodyForDestruction(body);
        }
    }

    public boolean isDestroyed() {
        if(health>0){
            return false;
        }
        return true;
    }
}
