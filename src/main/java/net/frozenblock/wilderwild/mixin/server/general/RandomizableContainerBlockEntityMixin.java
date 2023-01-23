package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.lib.math.api.EasyNoiseSampler;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.interfaces.ChestBlockEntityInterface;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RandomizableContainerBlockEntity.class)
public class RandomizableContainerBlockEntityMixin {

	@Shadow
	@Nullable
	public ResourceLocation lootTable;

    @Inject(method = "unpackLootTable", at = @At("HEAD"))
	public void unpackLootTable(@Nullable Player player, CallbackInfo info) {
		RandomizableContainerBlockEntity blockEntity = RandomizableContainerBlockEntity.class.cast(this);
		Level level = blockEntity.getLevel();
		if (this.lootTable != null && level != null && level.getServer() != null && blockEntity instanceof ChestBlockEntity chest) {
			BlockState state = level.getBlockState(blockEntity.getBlockPos());
			WilderSharedConstants.log("CHEST 1", WilderSharedConstants.UNSTABLE_LOGGING);
			if (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED)) {
				WilderSharedConstants.log("BEFORE BUBBLES", WilderSharedConstants.UNSTABLE_LOGGING);
				((ChestBlockEntityInterface) chest).setCanBubble(true);
				WilderSharedConstants.log("SET BUBBLES TRUE", WilderSharedConstants.UNSTABLE_LOGGING);
				if (this.lootTable.getPath().toLowerCase().contains("shipwreck") && EasyNoiseSampler.threadSafeRandom.nextBoolean()) {
					WilderSharedConstants.log("MAKING JELLY", WilderSharedConstants.UNSTABLE_LOGGING);
					Jellyfish jellyfish = new Jellyfish(RegisterEntities.JELLYFISH, level);
					BlockPos chestPos = chest.getBlockPos();
					jellyfish.setVariantFromPos(level, chestPos);
					double additionalX = 0;
					double additionalZ = 0;
					if (state.hasProperty(BlockStateProperties.CHEST_TYPE) && state.getValue(BlockStateProperties.CHEST_TYPE) != ChestType.SINGLE) {
						Direction direction = ChestBlock.getConnectedDirection(state);
						additionalX += (double)direction.getStepX() * 0.25;
						additionalZ += (double)direction.getStepZ() * 0.25;
					}
					jellyfish.setPos(chestPos.getX() + 0.5 + additionalX, chestPos.getY() + 0.75, chestPos.getZ() + 0.5 + additionalZ);
					jellyfish.setDeltaMovement(0, 0.1 + level.random.nextDouble() * 0.07, 0);
					level.addFreshEntity(jellyfish);
					WilderSharedConstants.log("SPAWNED JELLY", WilderSharedConstants.UNSTABLE_LOGGING);
				}
			}
		}
	}

}
