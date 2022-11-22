package net.frozenblock.wilderwild.mixin.worldgen;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.world.gen.foliage.PalmFoliagePlacer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TreeFeature.class)
public class TreeFeatureMixin {

	@Inject(method = "place", at = @At("RETURN"), cancellable = true)
	public final void place(FeaturePlaceContext<TreeConfiguration> context, CallbackInfoReturnable<Boolean> info) {
		TreeConfiguration treeConfiguration = context.config();
		if (treeConfiguration.foliagePlacer instanceof PalmFoliagePlacer) {
			WorldGenLevel worldGenLevel = context.level();
			RandomSource randomSource = context.random();
			BlockPos blockPos = context.origin();
			HashSet<BlockPos> set = Sets.newHashSet();
			HashSet<BlockPos> set2 = Sets.newHashSet();
			HashSet<BlockPos> set3 = Sets.newHashSet();
			HashSet set4 = Sets.newHashSet();
			BiConsumer<BlockPos, BlockState> biConsumer = (pos, state) -> {
				set.add(pos.immutable());
				worldGenLevel.setBlock(pos, state, 19);
			};
			BiConsumer<BlockPos, BlockState> biConsumer2 = (pos, state) -> {
				set2.add(pos.immutable());
				worldGenLevel.setBlock(pos, state, 19);
			};
			BiConsumer<BlockPos, BlockState> biConsumer3 = (pos, state) -> {
				set3.add(pos.immutable());
				worldGenLevel.setBlock(pos, state, 19);
			};
			BiConsumer<BlockPos, BlockState> biConsumer4 = (pos, state) -> {
				set4.add(pos.immutable());
				worldGenLevel.setBlock(pos, state, 19);
			};
			boolean bl = this.doPlace(worldGenLevel, randomSource, blockPos, biConsumer, biConsumer2, biConsumer3, treeConfiguration);
			if (!bl || set2.isEmpty() && set3.isEmpty()) {
				info.setReturnValue(false);
				return;
			}
			if (!treeConfiguration.decorators.isEmpty()) {
				TreeDecorator.Context context2 = new TreeDecorator.Context(worldGenLevel, biConsumer4, randomSource, set2, set3, set);
				treeConfiguration.decorators.forEach(treeDecorator -> treeDecorator.place(context2));
			}
			info.setReturnValue(palmLeafCheat(set, set2, set3, set4, worldGenLevel));
		}
	}

	@Unique
	private static boolean palmLeafCheat(HashSet<BlockPos> set, HashSet<BlockPos> set2, HashSet<BlockPos> set3, HashSet<BlockPos> set4, WorldGenLevel worldGenLevel) {
		BoundingBox.encapsulatingPositions(Iterables.concat(set, set2, set3, set4)).map(boundingBox -> {
			DiscreteVoxelShape discreteVoxelShape = updatePalmLeaves(worldGenLevel, boundingBox, set2, set4, set);
			StructureTemplate.updateShapeAtEdge(worldGenLevel, 3, discreteVoxelShape, boundingBox.minX(), boundingBox.minY(), boundingBox.minZ());
			return true;
		}).orElse(false);
		return false;
	}

	@Unique
	private static DiscreteVoxelShape updatePalmLeaves(LevelAccessor level, BoundingBox box, Set<BlockPos> rootPositions, Set<BlockPos> trunkPositions, Set<BlockPos> foliagePositions) {
		for (BlockPos pos : foliagePositions) {
			level.scheduleTick(pos, RegisterBlocks.PALM_LEAVES, 20);
		}
		BitSetDiscreteVoxelShape discreteVoxelShape = new BitSetDiscreteVoxelShape(box.getXSpan(), box.getYSpan(), box.getZSpan());
		return discreteVoxelShape;
	}

	@Shadow
	private boolean doPlace(WorldGenLevel level, RandomSource random, BlockPos pos, BiConsumer<BlockPos, BlockState> rootBlockSetter, BiConsumer<BlockPos, BlockState> trunkBlockSetter, BiConsumer<BlockPos, BlockState> foliageBlockSetter, TreeConfiguration config) {
		throw new AssertionError("Mixin injection failed - WilderWild TreeFeatureMixin");
	}
}
