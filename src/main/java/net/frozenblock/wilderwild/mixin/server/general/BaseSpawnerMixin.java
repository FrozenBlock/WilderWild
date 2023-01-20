package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.entity.Firefly;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseSpawner.class)
public class BaseSpawnerMixin {

    @Shadow @Nullable private Entity displayEntity;

    @Inject(at = @At("HEAD"), method = "clientTick")
    public void clientTick(Level level, BlockPos blockPos, CallbackInfo info) {
        if (this.displayEntity instanceof Firefly firefly) {
            firefly.setScale(2F);
			firefly.setPrevScale(2F);
        }
    }

}
