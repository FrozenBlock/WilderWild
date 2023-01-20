package net.frozenblock.wilderwild.mixin.worldgen.general;

import net.frozenblock.wilderwild.misc.interfaces.ChestBlockEntityInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.structures.ShipwreckPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ShipwreckPieces.ShipwreckPiece.class)
public class ShipwreckPieceMixin {

    @Inject(method = "handleDataMarker", at = @At("HEAD"))
    public void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box, CallbackInfo info) {
		if (level.getBlockEntity(pos.below()) instanceof ChestBlockEntity chestBlockEntity && random.nextBoolean() && random.nextBoolean()) {
			((ChestBlockEntityInterface)chestBlockEntity).setHasJellyfish(true);
		}
    }

}
