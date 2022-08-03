package net.frozenblock.wilderwild.misc.mod_compat.ufu;

import io.github.silverandro.ufu.UpdateFixerUpper;
import net.frozenblock.wilderwild.WilderWild;
import net.lunade.copper.block_entity.CopperPipeEntity;
import net.lunade.copper.pipe_nbt.MoveablePipeDataHandler;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class InteractionHandler {

    public static void addToUFU() {
        UpdateFixerUpper.fixerMap.putAll(WilderWild.DataFixMap);
    }

}
