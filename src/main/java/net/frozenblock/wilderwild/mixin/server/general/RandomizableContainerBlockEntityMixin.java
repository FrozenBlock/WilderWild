package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.lib.math.api.EasyNoiseSampler;
import net.frozenblock.wilderwild.misc.interfaces.ChestBlockEntityInterface;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
			if (level.getBlockState(blockEntity.getBlockPos()).getValue(BlockStateProperties.WATERLOGGED)) {
				((ChestBlockEntityInterface) chest).setCanBubble(true);
				if (this.lootTable.getPath().toLowerCase().contains("shipwreck") && EasyNoiseSampler.localRandom.nextBoolean() && EasyNoiseSampler.localRandom.nextBoolean()) {
					((ChestBlockEntityInterface) chest).setHasJellyfish(true);
				}
			}
		}
	}

}
