package com.angrybirds.Blocks;
import com.badlogic.gdx.physics.box2d.World;

public class WoodenBlock extends Block {

    public WoodenBlock(World world, float x, float y, float width, float height, String type) {
        super(world, x, y, width, height, 10, getTexturePath(type));
    }

    private static String getTexturePath(String type) {
        String texture = "";

        switch (type) {
            case "v":
                texture = "Blocks/wooden/v.png";
                break;
            case "h":
                texture = "Blocks/wooden/h.png";
                break;
            case "rect":
                texture = "Blocks/wooden/rect.png";
                break;
            case "square":
                texture = "Blocks/wooden/square.png";
                break;
            case "tri":
                texture = "Blocks/wooden/tri.png";
                break;
            case "triangle":
                texture = "Blocks/wooden/triangle.png";
                break;
        }
        return texture;
    }
}
