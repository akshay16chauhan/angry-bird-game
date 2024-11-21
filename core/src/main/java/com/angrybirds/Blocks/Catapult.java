package com.angrybirds.Blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Catapult {
    private Vector2 sling1Pos;
    private Texture sling1;

    private Vector2 sling2Pos;
    private Texture sling2;

    private Vector2 basePos;
    private Texture base;

    private final int width = 32;
    private final int height = 160;

    public Catapult(int x, int y) {
        sling1Pos = new Vector2(x, y);
        sling1 = new Texture("Blocks/sling1.png");
    }

    public Catapult(int x, int y, String k) {
        sling2Pos = new Vector2(x, y);
        sling2 = new Texture("Blocks/sling2.png");
    }

    public Catapult(int x, int y, int z) {
        basePos = new Vector2(x, y);
        base = new Texture("Blocks/base.png");
    }


    public Vector2 getBasePos() {
        return basePos;
    }

    public Texture getSling1() {
        return sling1;
    }
    public Texture getSling2() {
        return sling2;
    }

    public Vector2 getSling1Pos() {
        return sling1Pos;
    }
    public Vector2 getSling2Pos() {
        return sling2Pos;
    }

    public Texture getBaseTexture() {
        return base;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
