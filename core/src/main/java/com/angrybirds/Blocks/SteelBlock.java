package com.angrybirds.Blocks;
import com.badlogic.gdx.physics.box2d.World;

public class SteelBlock extends Block{

    public SteelBlock(World world, float x, float y, float width, float height, String type) {
        super(world, x, y, width, height, 25, getTexturePath(type));
    }

    private static String getTexturePath(String type) {
        String texture = "";

        switch (type) {
            case "v":
                texture = "Blocks/steel/v.png";
                break;
            case "h":
                texture = "Blocks/steel/h.png";
                break;
            case "rect":
                texture = "Blocks/steel/rect.png";
                break;
            case "square":
                texture = "Blocks/steel/square.png";
                break;
            case "tri":
                texture = "Blocks/steel/tri.png";
                break;
            case "trir":
                texture = "Blocks/steel/trir.png";
                break;
            case "triangle":
                texture = "Blocks/steel/triangle.png";
                break;
        }
        return texture;
    }

}
