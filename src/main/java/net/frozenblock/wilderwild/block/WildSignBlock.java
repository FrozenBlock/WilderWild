package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.block.SignBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;

import java.util.Objects;

public class WildSignBlock extends SignBlock {
    public WildSignBlock(Settings settings, SignType signType) {
        super(settings, signType);
    }

    @Override
    public final Identifier getLootTableId() {
        Identifier correctedLootTableId = WilderWild.id("blocks/" + this.getSignType().getName() + "_sign");

        if (!Objects.equals(this.lootTableId, correctedLootTableId)) {
            this.lootTableId = correctedLootTableId;
        }

        return this.lootTableId;
    }
}
