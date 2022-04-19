package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.noise.EasyNoiseSampler;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SculkBlock;
import net.minecraft.block.SculkShriekerBlock;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.fluid.Fluids;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(SculkBlock.class)
public class SculkBlockMixin {

	public static final double sculkBoneAreaSize = 0.1; //The smaller, the larger the area pillars can grow, but the larger the gaps between them.
	public static final double sculkBoneThreshold = 0.15; //The higher, the harder it is for pillars to appear. If set to 1 or higher, they'll never grow.

	@Inject(at = @At("HEAD"), method = "spread")
	public int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, AbstractRandom random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock, CallbackInfoReturnable info) {
		int i = cursor.getCharge();
		if (i != 0 && random.nextInt(spreadManager.getSpreadChance()) == 0) {
			BlockPos blockPos = cursor.getPos();
			boolean bl = blockPos.isWithinDistance(catalystPos, spreadManager.getMaxDistance());
			if (!bl && shouldNotDecay(world, blockPos)) {
				int j = spreadManager.getExtraBlockChance();
				if (random.nextInt(j) < i) {
					BlockPos blockPos2 = blockPos.up();
					BlockState blockState = this.getExtraBlockState(world, blockPos2, random, spreadManager.isWorldGen());

					if (blockState.getBlock()!=RegisterBlocks.SCULK_JAW) {
						world.setBlockState(blockPos2, blockState, 3);
					} else { world.setBlockState(blockPos2.down(), blockState, 3); }

					world.playSound(null, blockPos, blockState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				return Math.max(0, i - j);
			} else {
				return random.nextInt(spreadManager.getDecayChance()) != 0 ? i : i - (bl ? 1 : getDecay(spreadManager, blockPos, catalystPos, i));
			}
		} else {
			return i;
		}
	}

	private BlockState getExtraBlockState(WorldAccess world, BlockPos pos, AbstractRandom random, boolean allowShrieker) {
		BlockState blockState;
		if (random.nextInt(11) == 0) {
			blockState = Blocks.SCULK_SHRIEKER.getDefaultState().with(SculkShriekerBlock.CAN_SUMMON, allowShrieker);
		} else {
			if (random.nextInt(3) == 0) {
				blockState = RegisterBlocks.SCULK_JAW.getDefaultState();
			} else if (EasyNoiseSampler.samplePerlinXoro(pos, sculkBoneAreaSize, true, true) > sculkBoneThreshold) {
				blockState=RegisterBlocks.SCULK_BONE.getDefaultState();
			} else {
				blockState = Blocks.SCULK_SENSOR.getDefaultState();
			}
		}
		return blockState.contains(Properties.WATERLOGGED) && !world.getFluidState(pos).isEmpty() ? blockState.with(Properties.WATERLOGGED, true) : blockState;
	}


	@Shadow private static boolean shouldNotDecay(WorldAccess world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos.up());
		if (blockState.isAir() || blockState.isOf(Blocks.WATER) && blockState.getFluidState().isOf(Fluids.WATER)) {
			int i = 0;
			Iterator var4 = BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 2, 4)).iterator();

			do {
				if (!var4.hasNext()) {
					return true;
				}

				BlockPos blockPos = (BlockPos)var4.next();
				BlockState blockState2 = world.getBlockState(blockPos);
				if (blockState2.isOf(Blocks.SCULK_SENSOR) || blockState2.isOf(Blocks.SCULK_SHRIEKER)) {
					++i;
				}
			} while(i <= 2);

			return false;
		} else {
			return false;
		}
	}

	@Shadow private static int getDecay(SculkSpreadManager spreadManager, BlockPos cursorPos, BlockPos catalystPos, int charge) {
		int i = spreadManager.getMaxDistance();
		float f = MathHelper.square((float)Math.sqrt(cursorPos.getSquaredDistance(catalystPos)) - (float)i);
		int j = MathHelper.square(24 - i);
		float g = Math.min(1.0F, f / (float)j);
		return Math.max(1, (int)((float)charge * g * 0.5F));
	}
}
