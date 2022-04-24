package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.entity.SculkSensorTendrilEntity;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkSensorBlock.class)
public class SculkSensorBlockMixin {

    @Inject(at = @At("HEAD"), method = "onBlockAdded")
    public void addBlock(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo info) {
        if (!world.isClient() && !state.isOf(oldState.getBlock())) {
            SculkSensorTendrilEntity tendrils = RegisterEntities.TENDRIL_ENTITY.create(world);
            assert tendrils != null;
            tendrils.refreshPositionAndAngles(pos.getX() + 0.5D, pos.getY()+0.5D, pos.getZ() + 0.5D, 0.0F, 0.0F);
            world.spawnEntity(tendrils);
        }
    }

}
