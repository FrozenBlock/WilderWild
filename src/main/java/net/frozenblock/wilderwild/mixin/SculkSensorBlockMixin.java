package net.frozenblock.wilderwild.mixin;

import net.minecraft.block.SculkSensorBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SculkSensorBlock.class)
public class SculkSensorBlockMixin {

    /*@Inject(at = @At("HEAD"), method = "onBlockAdded")
    public void addBlock(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo info) {
        if (!world.isClient() && !state.isOf(oldState.getBlock())) {
            SculkSensorTendrilEntity tendrils = RegisterEntities.TENDRIL_ENTITY.create(world);
            assert tendrils != null;
            tendrils.refreshPositionAndAngles(pos.getX() + 0.5D, pos.getY()+0.5D, pos.getZ() + 0.5D, 0.0F, 0.0F);
            world.spawnEntity(tendrils);
        }
    }*/

    //I'm keeping this just in case we need it later.

}
