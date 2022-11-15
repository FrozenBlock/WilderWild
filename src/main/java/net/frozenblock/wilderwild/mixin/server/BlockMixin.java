package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.misc.BlockSoundGroupOverwrites;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public final class BlockMixin {

    @Inject(method = "getSoundType", at = @At("RETURN"), cancellable = true)
    private void getSoundGroupOverride(BlockState state, CallbackInfoReturnable<SoundType> info) {
        Block block = state.getBlock();
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
        if (BlockSoundGroupOverwrites.IDS.contains(id)) {
            int index = BlockSoundGroupOverwrites.IDS.indexOf(id);
            info.setReturnValue(BlockSoundGroupOverwrites.SOUND_GROUPS.get(index));
        } else if (BlockSoundGroupOverwrites.NAMESPACES.contains(id.getNamespace())) {
            int index = BlockSoundGroupOverwrites.NAMESPACES.indexOf(id.getNamespace());
            info.setReturnValue(BlockSoundGroupOverwrites.NAMESPACE_SOUND_GROUPS.get(index));
        }
    }

}
