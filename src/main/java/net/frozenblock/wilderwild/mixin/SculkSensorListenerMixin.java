package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.Blocks;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.VibrationListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(VibrationListener.class)
public class SculkSensorListenerMixin {

    @Inject(at = @At("TAIL"), method = "tick")
    public void tick(World world, CallbackInfo info) {
        VibrationListener listener = VibrationListener.class.cast(this);
        if (world instanceof ServerWorld server) {
            Optional<Vec3d> pos = listener.getPositionSource().getPos(server);
            if (pos.isPresent()) {
                BlockPos blockPos = new BlockPos(pos.get());
                if (world.getBlockState(blockPos).isOf(Blocks.SCULK_SENSOR)) {
                    if (SculkSensorBlock.isInactive(world.getBlockState(blockPos)) && !world.getBlockState(blockPos).get(RegisterProperties.NOT_HICCUPPING) && world.random.nextInt(320)<=1) {
                        SculkSensorBlock.setActive(null, world, blockPos, world.getBlockState(blockPos), (int)(Math.random()*15));
                        world.emitGameEvent(null, GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, blockPos);
                        world.emitGameEvent(null, WilderWild.SCULK_SENSOR_ACTIVATE, blockPos);
                        world.playSound(null, blockPos, RegisterSounds.BLOCK_SCULK_SENSOR_HICCUP, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.7F);
                    }
                    /*Box box = (new Box(blockPos.add(0, 0, 0), blockPos.add(1, 1, 1)));
                    List<SculkSensorTendrilEntity> list = world.getNonSpectatingEntities(SculkSensorTendrilEntity.class, box);
                    if (list.isEmpty()) {
                        SculkSensorTendrilEntity tendrils = RegisterEntities.TENDRIL_ENTITY.create(server);
                        assert tendrils != null;
                        tendrils.refreshPositionAndAngles(blockPos.getX() + 0.5D, blockPos.getY()+0.5D, blockPos.getZ() + 0.5D, 0.0F, 0.0F);
                        server.spawnEntity(tendrils);
                    }*/
                }
            }
        }
    }

}
