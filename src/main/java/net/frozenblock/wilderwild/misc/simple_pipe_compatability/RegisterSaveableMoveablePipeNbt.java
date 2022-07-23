package net.frozenblock.wilderwild.misc.simple_pipe_compatability;

import net.frozenblock.wilderwild.WilderWild;
import net.lunade.copper.Main;
import net.lunade.copper.block_entity.CopperPipeEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class RegisterSaveableMoveablePipeNbt {
    public static final Identifier horn = new Identifier(WilderWild.MOD_ID, "ancient_horn");
    public static void init() {
        WilderWild.log("Registering Saveable Moveable Pipe NBT For WildeWild!", true);
        Main.SAVEABLE_PIPE_NBT_IDS.add(horn);
        Main.SAVEABLE_PIPE_NBT_CLASSES.add(SaveableAncientHorn.class);
    }

    public static boolean addHornNbtToBlock(ServerWorld world, BlockPos pos, Entity owner) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity != null) {
            if (entity instanceof CopperPipeEntity pipe) {
                world.playSound(null, pos, Registry.SOUND_EVENT.get(new Identifier("lunade", "block.copper_pipe.item_in")), SoundCategory.BLOCKS, 0.2F, (world.random.nextFloat() * 0.25F) + 0.8F);
                pipe.moveablePipeDataHandler.addSaveableMoveablePipeNbt(new SaveableAncientHorn(owner.getPos(), owner.getUuid().toString(), false));
            }
        }
        return false;
    }

}
