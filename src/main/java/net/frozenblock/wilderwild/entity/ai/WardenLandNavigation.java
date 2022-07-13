package net.frozenblock.wilderwild.entity.ai;

import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class WardenLandNavigation extends MobNavigation {
    public WardenLandNavigation(MobEntity warden, World world) {
        super(warden, world);
    }

    @Override
    protected PathNodeNavigator createPathNodeNavigator(int range) {
        this.nodeMaker = new LandPathNodeMaker();
        this.nodeMaker.setCanEnterOpenDoors(true);
        return new PathNodeNavigator(this.nodeMaker, range) {
            @Override
            protected float getDistance(PathNode a, PathNode b) {
                return a.getHorizontalDistance(b);
            }
        };
    }
}
