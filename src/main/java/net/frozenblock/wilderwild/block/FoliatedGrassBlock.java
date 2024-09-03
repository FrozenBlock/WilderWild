package net.frozenblock.wilderwild.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

public class FoliatedGrassBlock extends SpreadingSnowyDirtBlock {
	public static final MapCodec<FoliatedGrassBlock> CODEC = simpleCodec(FoliatedGrassBlock::new);

	@Override
	public @NotNull MapCodec<FoliatedGrassBlock> codec() {
		return CODEC;
	}

	public FoliatedGrassBlock(BlockBehaviour.Properties settings) {
		super(settings);
	}

}
