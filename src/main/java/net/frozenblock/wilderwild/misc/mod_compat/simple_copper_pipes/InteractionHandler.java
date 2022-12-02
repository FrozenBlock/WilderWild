package net.frozenblock.wilderwild.misc.mod_compat.simple_copper_pipes;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.lunade.copper.block_entity.CopperPipeEntity;
import net.lunade.copper.pipe_nbt.MoveablePipeDataHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;

public class InteractionHandler {
    public static final ResourceLocation HORN = WilderSharedConstants.id("ancient_horn");

    public static boolean addHornNbtToBlock(ServerLevel level, BlockPos pos, Entity owner) {
        BlockEntity entity = level.getBlockEntity(pos);
        if (entity != null) {
            if (entity instanceof CopperPipeEntity pipe) {
                level.playSound(null, pos, BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("lunade", "block.copper_pipe.item_in")), SoundSource.BLOCKS, 0.2F, (level.random.nextFloat() * 0.25F) + 0.8F);
                pipe.moveablePipeDataHandler.addSaveableMoveablePipeNbt(new MoveablePipeDataHandler.SaveableMovablePipeNbt().withVec3d(owner.position()).withVec3d2(owner.position()).withString(owner.getStringUUID()).withOnlyThroughOnePipe(true).withOnlyUseableOnce(true).withNBTID(HORN));
                return true;
            }
        }
        return false;
    }

}
