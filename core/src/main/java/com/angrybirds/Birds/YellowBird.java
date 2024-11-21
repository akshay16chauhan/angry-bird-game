package com.angrybirds.Birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class YellowBird extends Bird {
    private boolean isSpeedUpActive;
    private float speedUpFactor;

    public YellowBird(World world, float x, float y) {
        super(world, x, y,20);
        this.isSpeedUpActive = false;
        this.speedUpFactor = 2.0f; // Speed-up factor
    }

    @Override
    protected Texture loadBirdTexture() {
        return new Texture("Birds/yellow.png");
    }

    @Override
    public int setWidth() {
        return 65;
    }

    @Override
    public int setHeight() {
        return 65;
    }



    // Method to activate speed-up ability
    public void activateSpeedUp() {
        isSpeedUpActive = true;
    }

    @Override
    public void updatePosition(float deltaTime) {
        if (isSpeedUpActive) {
            Vector2 velocity = body.getLinearVelocity();
            body.setLinearVelocity(velocity.x * speedUpFactor, velocity.y * speedUpFactor);
            isSpeedUpActive = false; // Speed-up effect is temporary
        }
    }

    @Override
    public void applySpecialEffect() {
        activateSpeedUp();
    }
}
