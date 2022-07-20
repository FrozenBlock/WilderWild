package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.Objects;

public class WildSignBlock extends StandingSignBlock {
    public WildSignBlock(Properties settings, WoodType signType) {
        super(settings, signType);
    }

    @Override
    public final ResourceLocation getLootTable() {
        ResourceLocation correctedLootTableId = WilderWild.id("blocks/" + this.type().name() + "_sign");

        if (!Objects.equals(this.drops, correctedLootTableId)) {
            this.drops = correctedLootTableId;
        }

        return this.drops;
    }
}
