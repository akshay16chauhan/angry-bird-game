package com.angrybirds.Blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Rigidbody {
    private Body body;
    private Texture texture;
    private float width, height;

    public Rigidbody(World world, float x, float y, float width, float height) {
        this.width = width;
        this.height = height;
        this.texture = new Texture("Blocks/blur.png");

        float box2dWidth = width / 100f;
        float box2dHeight = height / 100f;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((x + width / 2) / 100f, (y + height / 2) / 100f);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(box2dWidth / 2, box2dHeight / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.0f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void render(SpriteBatch batch) {
        float renderX = (body.getPosition().x * 100f) - (width / 2);
        float renderY = (body.getPosition().y * 100f) - (height / 2);
        batch.draw(texture, renderX, renderY, width, height);
    }

    public void dispose() {
        texture.dispose();
    }
}
