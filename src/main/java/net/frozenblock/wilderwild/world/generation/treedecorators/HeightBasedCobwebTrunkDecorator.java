package net.frozenblock.wilderwild.world.generation.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;


public class HeightBasedCobwebTrunkDecorator extends TreeDecorator {
	public static final Codec<HeightBasedCobwebTrunkDecorator> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((treeDecorator) -> {
			return treeDecorator.probability;
		}), Codec.intRange(-63, 319).fieldOf("maxHeight").forGetter((treeDecorator) -> {
			return treeDecorator.maxHeight;
		}), Codec.floatRange(0.0F, 1.0F).fieldOf("cobweb_count").forGetter((treeDecorator) -> {
			return treeDecorator.cobweb_count;
		})).apply(instance, HeightBasedCobwebTrunkDecorator::new);
	});
	private final float probability;
	private final int maxHeight;
	private final float cobweb_count;

	public HeightBasedCobwebTrunkDecorator(float probability, int maxHeight, float cobweb_count) {
		this.probability = probability;
		this.maxHeight = maxHeight;
		this.cobweb_count = cobweb_count;
	}

	protected TreeDecoratorType<?> type() {
		return WilderTreeDecorators.HEIGHT_BASED_COBWEB_TRUNK_DECORATOR;
	}

	public void place(Context generator) {
		RandomSource abstractRandom = generator.random();
		if (abstractRandom.nextFloat() <= this.probability) {
			List<BlockPos> list = generator.logs();
			list.forEach((pos) -> {
				if (pos.getY() <= this.maxHeight) {
					for (Direction direction : Direction.Plane.HORIZONTAL) {
						if (abstractRandom.nextFloat() <= this.cobweb_count) {
							BlockPos blockPos = pos.offset(direction.getStepX(), 0, direction.getStepZ());
							if (generator.isAir(blockPos)) {
								generator.setBlock(blockPos, Blocks.COBWEB.defaultBlockState());
							}
						}
					}
				}
			});
		}
	}

}
