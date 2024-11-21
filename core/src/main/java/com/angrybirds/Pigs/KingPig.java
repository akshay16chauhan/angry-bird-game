package com.angrybirds.Pigs;
import com.badlogic.gdx.physics.box2d.World;

public class KingPig extends Pig{

    public KingPig(World world, int x, int y) {
        super(world, x, y,"Pigs/king.png", 36,40);
    }
}
