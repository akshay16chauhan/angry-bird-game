package com.angrybirds.Birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class RedBird extends Bird {
    private boolean isSpeedUpActive;
    private float speedUpFactor;

    public RedBird(World world, float x, float y) {
        super(world, x, y,20);
        this.isSpeedUpActive = false;
        this.speedUpFactor = 1f; // Example speed-up factor
    }

    @Override
    protected Texture loadBirdTexture() {
        return new Texture("Birds/red.png"); // Adjust to your actual texture path
    }

    @Override
    public int setWidth() {
        return 65;
    }

    @Override
    public int setHeight() {
        return 65;
    }

    public void activateSpeedUp() {
        isSpeedUpActive = true;
    }

    @Override
    public void updatePosition(float deltaTime) {
        if (isSpeedUpActive) {
            // Apply the speed-up factor
            body.setLinearVelocity(body.getLinearVelocity().x * speedUpFactor,
                body.getLinearVelocity().y * speedUpFactor);
            isSpeedUpActive = false; // Speed-up effect is temporary
        }
        super.updatePosition(deltaTime);
    }

    @Override
    public void applySpecialEffect() {
        activateSpeedUp();
    }
}
