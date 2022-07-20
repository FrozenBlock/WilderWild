package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(VibrationListener.class)
public class VibrationListenerMixin {

    @Inject(at = @At("TAIL"), method = "tick")
    public void tick(Level world, CallbackInfo info) {
        VibrationListener listener = VibrationListener.class.cast(this);
        if (world instanceof ServerLevel server) {
            Optional<Vec3> pos = listener.getListenerSource().getPosition(server);
            if (pos.isPresent()) {
                BlockPos blockPos = new BlockPos(pos.get());
                if (world.getBlockState(blockPos).is(Blocks.SCULK_SENSOR)) {
                    if (!world.getBlockState(blockPos).getValue(RegisterProperties.NOT_HICCUPPING)) {
                        boolean bl2 = world.random.nextBoolean();
                        if (bl2) {
                            double x = (blockPos.getX() - 0.1) + (world.random.nextFloat() * 1.2);
                            double y = blockPos.getY() + world.random.nextFloat();
                            double z = (blockPos.getZ() - 0.1) + (world.random.nextFloat() * 1.2);
                            EasyPacket.EasySensorHiccupPacket.createParticle(server, new Vec3(x, y, z));
                        }
                        if (world.random.nextInt(400) <= 1 && SculkSensorBlock.canActivate(world.getBlockState(blockPos))) {
                            WilderWild.log("Sensor Hiccups " + pos, WilderWild.DEV_LOGGING);
                            SculkSensorBlock.activate(null, world, blockPos, world.getBlockState(blockPos), WilderWild.random().nextInt(15));
                            world.gameEvent(null, GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, blockPos);
                            world.gameEvent(null, RegisterGameEvents.SCULK_SENSOR_ACTIVATE, blockPos);
                            world.playSound(null, blockPos, RegisterSounds.BLOCK_SCULK_SENSOR_HICCUP, SoundSource.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.7F);
                        }
                    }
                }
            }
        }
    }

}
