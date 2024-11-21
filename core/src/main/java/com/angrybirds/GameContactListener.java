package com.angrybirds;

import com.angrybirds.Screens.Level1;
import com.badlogic.gdx.physics.box2d.*;
import com.angrybirds.Blocks.Block;
import com.angrybirds.Birds.Bird;
import com.angrybirds.Pigs.Pig;

import java.util.ArrayList;
import java.util.List;

public class GameContactListener implements ContactListener {
    private static List<Body> bodiesToDestroy = new ArrayList<>(); // List to store bodies for deferred destruction

    @Override
    public void beginContact(Contact contact) {
        // Get the fixtures involved in the collision
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // Get the objects attached to the bodies
        Object userDataA = fixtureA.getBody().getUserData();
        Object userDataB = fixtureB.getBody().getUserData();

        // Handle collisions between Bird and Block
        if (userDataA instanceof Bird && userDataB instanceof Block) {
            handleBirdBlockCollision((Bird) userDataA, (Block) userDataB);
        } else if (userDataA instanceof Block && userDataB instanceof Bird) {
            handleBirdBlockCollision((Bird) userDataB, (Block) userDataA);
        }

        // Handle collisions between Bird and Pig
        if (userDataA instanceof Bird && userDataB instanceof Pig) {
            handleBirdPigCollision((Bird) userDataA, (Pig) userDataB);
        } else if (userDataA instanceof Pig && userDataB instanceof Bird) {
            handleBirdPigCollision((Bird) userDataB, (Pig) userDataA);
        }
    }

    @Override
    public void endContact(Contact contact) {
        // Handle end of contact if needed
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Called before the contact is resolved
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Called after the contact is resolved
    }

    private void handleBirdBlockCollision(Bird bird, Block block) {
        int impactDamage = bird.getPower();
        block.takeDamage(impactDamage);
        if (block.getDurability() <= 0) {
            Level1.removeBlock(block);
        }
        System.out.println("Block took damage! Remaining durability: " + block.getDurability());
    }

    private void handleBirdPigCollision(Bird bird, Pig pig) {
        int impactDamage = bird.getPower();
        pig.reduceHealth(impactDamage);
        if (pig.getHealth() <= 0) {
            Level1.removePig(pig);
        }
        System.out.println("Pig took damage! Remaining health: " + pig.getHealth());
    }

    public static void queueBodyForDestruction(Body body) {
        bodiesToDestroy.add(body); // Add body to the destruction list
    }

    public static void destroyBodiesAfterStep() {
        for (Body body : bodiesToDestroy) {
            if (body != null) {

                body.getWorld().destroyBody(body);

                // Destroy the body in the world
                System.out.println("Body destroyed at: " + body.getPosition());
            }
        }
        bodiesToDestroy.clear(); // Clear the destruction queue after processing
    }

}
