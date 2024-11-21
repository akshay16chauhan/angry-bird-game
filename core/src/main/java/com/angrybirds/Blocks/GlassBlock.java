package com.angrybirds.Blocks;

import com.badlogic.gdx.physics.box2d.World;

public class GlassBlock extends Block {

    public GlassBlock(World world, float x, float y, float width, float height, String type) {
        super(world, x, y, width, height, 15, getTexturePath(type));
    }

    private static String getTexturePath(String type) {
        String texture = "";

        switch (type) {
            case "v":
                texture = "Blocks/glass/v.png";
                break;
            case "h":
                texture = "Blocks/glass/h.png";
                break;
            case "rect":
                texture = "Blocks/glass/rect.png";
                break;
            case "square":
                texture = "Blocks/glass/square.png";
                break;
            case "tri":
                texture = "Blocks/glass/tri.png";
                break;
            case "triangle":
                texture = "Blocks/glass/triangle.png";
                break;
        }
        return texture;
    }
}
