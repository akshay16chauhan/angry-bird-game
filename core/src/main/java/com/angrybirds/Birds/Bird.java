package com.angrybirds.Birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Bird {
    protected Body body; // Box2D body
    protected TextureRegion textureRegion; // Use TextureRegion instead of Texture
    private boolean launched;
    private int width;
    private int height;
    private int power;


    // Bird parameters for controlling launch speed and behavior
    protected float launchSpeed;

    public Bird(World world, float x, float y, int power) {
        this.launched = false;
        this.launchSpeed = 0;
        this.power = power;
        this.width = setWidth();
        this.height = setHeight();

        // Create Box2D body for the bird
        createBirdBody(world, x, y, height);

        // Load bird texture
        this.textureRegion = new TextureRegion(loadBirdTexture());
    }

    // Abstract method to load bird texture, can be overridden in subclasses
    protected abstract Texture loadBirdTexture();
    public abstract int setWidth();
    public abstract int setHeight();

    private void createBirdBody(World world, float x, float y, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody; // Initially, set the body type to Static


        // Birds are dynamic
        bodyDef.position.set(x / 100f, y / 100f); // Convert to meters for Box2D

        // Create the body
        body = world.createBody(bodyDef);

        // Define the shape of the bird (circle for simplicity)
        CircleShape shape = new CircleShape();
        shape.setRadius((height/2)/ 100f); // Radius in meters (assuming 20px radius)

        // Define fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f; // Set density for the bird
        fixtureDef.friction = 0.3f; // Slight friction
        fixtureDef.restitution = 0.0f; // Some bounce effect

        // Attach the fixture to the bird body
        body.createFixture(fixtureDef);


        shape.dispose();
        body.setUserData(this);// Dispose shape when done to free memory
    }

    public Body getBody() {
        return body;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void launch(float speed, float dx, float dy) {

        // Launch bird with speed and direction (dx, dy)
        body.setType(BodyDef.BodyType.DynamicBody);
        float angle = (float) Math.atan2(dy, dx);
        float vx = speed * (float) Math.cos(angle); // Horizontal velocity
        float vy = speed * (float) Math.sin(angle); // Vertical velocity
        body.setLinearVelocity(vx, vy); // Apply velocity to Box2D body
        launchSpeed = speed;
        launched = true;
    }

    public void updatePosition(float deltaTime) {
        if (launched) {
            // Box2D handles position and velocity updates automatically
        }
    }

    public boolean isLaunched() {
        return launched;
    }

    public void resetBird(World world, float x, float y) {
        body.setTransform(x / 100f, y / 100f, 0); // Reset position
        body.setLinearVelocity(0, 0); // Stop any movement
        body.setType(BodyDef.BodyType.StaticBody); // Make it static again
        launched = false;
    }


    public void setLaunched(boolean launched) {
        this.launched = launched;
    }

    public float getRadius() {
        return 20; // Example radius (in pixels)
    }

    public int getPower() {
        return power;
    }

    public void dispose() {
        // Dispose of the texture when no longer needed
        textureRegion.getTexture().dispose();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Vector2 getPosition() {
        return body.getPosition(); // Returns the position of the bird in meters
    }

    public boolean hasStopped(int Y) {
        // Check if the bird's y position is close to 200
        if(launched && body.getPosition().y <= Y / 100f){
            return true;
        }
        else if(launched && body.getLinearVelocity().len() < 0.1f ){
            return true;
        }
        return false; // Convert 200 pixels to meters
    }


    public abstract void applySpecialEffect();
}
