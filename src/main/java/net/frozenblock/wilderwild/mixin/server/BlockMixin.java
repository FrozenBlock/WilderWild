package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.misc.BlockSoundGroupOverwrites;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "getSoundType", at = @At("RETURN"), cancellable = true)
    private void getSoundType(BlockState state, CallbackInfoReturnable<SoundType> info) {
        Block block = state.getBlock();
        ResourceLocation id = Registry.BLOCK.getKey(block);
        if (BlockSoundGroupOverwrites.ids.contains(id)) {
            int index = BlockSoundGroupOverwrites.ids.indexOf(id);
            info.setReturnValue(BlockSoundGroupOverwrites.soundGroups.get(index));
        } else if (BlockSoundGroupOverwrites.namespaces.contains(id.getNamespace())) {
            int index = BlockSoundGroupOverwrites.namespaces.indexOf(id.getNamespace());
            info.setReturnValue(BlockSoundGroupOverwrites.namespaceSoundGroups.get(index));
        }
    }
}
