package com.angrybirds.Birds;

import com.angrybirds.Blocks.Block;
import com.angrybirds.Pigs.Pig;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;


import java.util.List;

public class BlackBird extends Bird {
    private boolean isExploded;
    private float explosionRadius; // Radius of effect for the explosion
    private List<Pig> pigs;        // List of pigs in the game
    private List<Block> blocks;    // List of blocks in the game

    public BlackBird(World world, float x, float y, List<Pig> pigs, List<Block> blocks) {
        super(world, x, y,40);  // Pass World, x, and y to the Bird constructor
        this.isExploded = false;
        this.explosionRadius = 5.0f;  // Explosion radius in meters
        this.pigs = pigs;
        this.blocks = blocks;
    }

    @Override
    protected Texture loadBirdTexture() {
        return new Texture("Birds/black.png");
    }

    @Override
    public int setWidth() {
        return 65; // Width in pixels
    }

    @Override
    public int setHeight() {
        return 80; // Height in pixels
    }

    @Override
    public void launch(float speed, float dx, float dy) {
        super.launch(speed, dx, dy); // Use the parent method for consistent launch behavior
    }

    // Activate explosion behavior
    public void activateExplosion() {
        if (!isExploded) {
            isExploded = true;
            System.out.println("Black Bird exploded!");
            applyExplosionEffects();
        }
    }

    @Override
    public void updatePosition(float deltaTime) {
        super.updatePosition(deltaTime);
        if (isExploded) {
            body.setLinearVelocity(0, 0); // Stop movement after explosion
        }
    }

    private void applyExplosionEffects() {
        // Damage pigs within the explosion radius
        for (Pig pig : pigs) {
            float distance = body.getPosition().dst(pig.getBody().getPosition());
            if (distance <= explosionRadius) {
                pig.reduceHealth(getPower()); // Reduce pig health (this should work if Pig has reduceHealth method)
                if (pig.getHealth() <= 0) {
                    System.out.println("Pig destroyed by explosion!");
                }
            }
        }

        // Damage blocks within the explosion radius
        for (Block block : blocks) {
            float distance = body.getPosition().dst(block.getBody().getPosition());
            if (distance <= explosionRadius) {
                block.takeDamage(getPower()); // Use takeDamage instead of reduceHealth
                if (block.getDurability() <= 0) {
                    System.out.println("Block destroyed by explosion!");
                }
            }
        }
    }


    @Override
    public void dispose() {
        super.dispose();
    }

    public boolean isExploded() {
        return isExploded;
    }

    @Override
    public void applySpecialEffect(){

        activateExplosion();
    }

}
