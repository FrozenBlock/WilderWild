package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.block.Blocks;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.particle.ParticleTypes;
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
public class VibrationListenerMixin {

    @Inject(at = @At("TAIL"), method = "tick")
    public void tick(World world, CallbackInfo info) {
        VibrationListener listener = VibrationListener.class.cast(this);
        if (world instanceof ServerWorld server) {
            Optional<Vec3d> pos = listener.getPositionSource().getPos(server);
            if (pos.isPresent()) {
                BlockPos blockPos = new BlockPos(pos.get());
                if (world.getBlockState(blockPos).isOf(Blocks.SCULK_SENSOR)) {
                    if (!world.getBlockState(blockPos).get(RegisterProperties.NOT_HICCUPPING)) {
                        boolean bl2 = world.random.nextBoolean();
                        if (bl2) {
                            double x = (blockPos.getX() - 0.1) + (world.random.nextFloat() * 1.2);
                            double y = blockPos.getY() + world.random.nextFloat();
                            double z = (blockPos.getZ() - 0.1) + (world.random.nextFloat() * 1.2);
                            EasyPacket.EasySensorHiccupPacket.createParticle(server, new Vec3d(x, y, z));
                        }
                        if (world.random.nextInt(400) <= 1 && SculkSensorBlock.isInactive(world.getBlockState(blockPos))) {
                            WilderWild.log("Sensor Hiccups " + pos, WilderWild.DEV_LOGGING);
                            SculkSensorBlock.setActive(null, world, blockPos, world.getBlockState(blockPos), WilderWild.random().nextInt(15));
                            world.emitGameEvent(null, GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, blockPos);
                            world.emitGameEvent(null, RegisterGameEvents.SCULK_SENSOR_ACTIVATE, blockPos);
                            world.playSound(null, blockPos, RegisterSounds.BLOCK_SCULK_SENSOR_HICCUP, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.7F);
                        }
                    }
                }
            }
        }
    }

}
