package com.angrybirds.Pigs;
import com.badlogic.gdx.physics.box2d.World;

public class MinionPig extends Pig{
    public MinionPig(World world, int x, int y) {
        super(world, x, y,"Pigs/minion.png", 25,13);
    }
}
