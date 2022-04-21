package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.block.OsseousSculkBlock;
import net.frozenblock.wilderwild.noise.EasyNoiseSampler;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.fluid.Fluids;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;

@Mixin(SculkBlock.class)
public class SculkBlockMixin {

	private static final int heightMultiplier = 17; //The higher, the less short pillars you'll see.
	private static final int worldGenHeightMultiplier = 19; //The higher, the less short pillars you'll see (WORLDGEN ONLY).
	private static final int maxHeight = 15; //The rarest and absolute tallest height of pillars
	private static final double randomness = 0.9; //The higher, the more random. The lower, the more gradual the heights change.

	private static final double sculkBoneAreaSize = 0.09; //The smaller, the larger the area pillars can grow, but the larger the gaps between them.
	private static final double sculkBoneThreshold = 0.13; //The higher, the harder it is for pillars to appear. If set to 1 or higher, they'll never grow.

	@Overwrite
	public int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, AbstractRandom random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock) {
		int i = cursor.getCharge();
		boolean isWorldGen = spreadManager.isWorldGen();
		if (world.getServer()!=null) {
			if (world.getServer().getOverworld().getSeed()!=EasyNoiseSampler.seed) {
				EasyNoiseSampler.setSeed(world.getServer().getOverworld().getSeed());
			}
		}
		if (i != 0 && random.nextInt(spreadManager.getSpreadChance()) == 0) {
			BlockPos blockPos = cursor.getPos();
			boolean bl = blockPos.isWithinDistance(catalystPos, spreadManager.getMaxDistance());
			if (!bl && shouldNotDecay(world, blockPos)) {
				int j = spreadManager.getExtraBlockChance();
				if (random.nextInt(j) < i) {
					BlockPos blockPos2 = blockPos.up();
					BlockState blockState = this.getExtraBlockState(world, blockPos2, random, spreadManager.isWorldGen());

					BlockState placeBlockBelow = null;
					BlockState stateDown = world.getBlockState(blockPos.down());
					Block blockDown = stateDown.getBlock();
					if (canPlaceBone(blockPos) && (stateDown.isAir() || blockDown==Blocks.WATER || blockDown==Blocks.LAVA || blockDown==Blocks.SCULK_VEIN)) {
						int pillarHeight = (int) MathHelper.clamp(EasyNoiseSampler.samplePerlinXoroPositive(blockPos.down(), randomness, false, false) * heightMultiplier, 2, maxHeight);
						if (isWorldGen) {
							pillarHeight = (int) MathHelper.clamp(EasyNoiseSampler.samplePerlinXoroPositive(blockPos.down(), randomness, false, false) * worldGenHeightMultiplier, 2, maxHeight);
						}
						blockState=RegisterBlocks.OSSEOUS_SCULK.getDefaultState().with(OsseousSculkBlock.HEIGHT_LEFT, pillarHeight).with(OsseousSculkBlock.TOTAL_HEIGHT, pillarHeight+1).with(OsseousSculkBlock.UPSIDEDOWN, true).with(OsseousSculkBlock.CAME_FROM, Direction.UP);
						blockPos2=blockPos.down();
					}

					if (blockState.getBlock()==RegisterBlocks.SCULK_JAW) {blockPos2=blockPos;}
					if (blockState.getBlock()==RegisterBlocks.SCULK_ECHOER) {placeBlockBelow=RegisterBlocks.OSSEOUS_SCULK.getDefaultState();}

					world.setBlockState(blockPos2, blockState, 3);

					if (isWorldGen && world.getBlockState(blockPos2).getBlock()==RegisterBlocks.OSSEOUS_SCULK) {
						int amount = Math.max(0, blockState.get(OsseousSculkBlock.HEIGHT_LEFT) - random.nextInt(1));
						for (int a = 0; a < amount; a++) {
							OsseousSculkBlock.worldGenSpread(blockPos2, world, random);
						}
					}

					if (placeBlockBelow != null) {
						world.removeBlock(blockPos2.down(), false);
						world.setBlockState(blockPos2.down(), placeBlockBelow, 3);
						blockPos2=blockPos2.down().down();
						for (int o=0; o<4; o++) {
							stateDown = world.getBlockState(blockPos2);
							blockDown = stateDown.getBlock();
							if (stateDown.isIn(BlockTags.SCULK_REPLACEABLE) || stateDown.isAir() || blockDown==Blocks.WATER || blockDown==Blocks.LAVA || blockDown==Blocks.SCULK_VEIN || blockDown==Blocks.SCULK) {
								if (EasyNoiseSampler.simpleRandom.nextInt(o+1)==0) {
									world.setBlockState(blockPos2, placeBlockBelow, 3);
									blockPos2=blockPos2.down();
								} else { break; }
							}
						}
					}

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

	private static boolean canPlaceBone(BlockPos pos) {
		return EasyNoiseSampler.samplePerlinXoro(pos, sculkBoneAreaSize, true, true) > sculkBoneThreshold;
	}

	private BlockState getExtraBlockState(WorldAccess world, BlockPos pos, AbstractRandom random, boolean allowShrieker) {
		BlockState blockState = Blocks.SCULK_SENSOR.getDefaultState();
		boolean decided = false;
		if (random.nextInt(11) == 0) {
			blockState = Blocks.SCULK_SHRIEKER.getDefaultState().with(SculkShriekerBlock.CAN_SUMMON, allowShrieker);
			decided=true;
		}
		if (!decided && random.nextInt(5) <= 1) {
			blockState = RegisterBlocks.SCULK_JAW.getDefaultState();
			decided = true;
		}
		if (canPlaceBone(pos) && blockState.isOf(Blocks.SCULK_SENSOR)) {
			int pillarHeight = (int) MathHelper.clamp(EasyNoiseSampler.samplePerlinXoroPositive(pos, randomness, false, false) * heightMultiplier, 2, maxHeight);
			blockState = RegisterBlocks.OSSEOUS_SCULK.getDefaultState().with(OsseousSculkBlock.HEIGHT_LEFT, pillarHeight).with(OsseousSculkBlock.TOTAL_HEIGHT, pillarHeight + 1);
			decided = true;
		}
		if (!decided && (Math.random()*5 > 4)) {
			blockState = RegisterBlocks.SCULK_ECHOER.getDefaultState();
		}
		return blockState.contains(Properties.WATERLOGGED) && !world.getFluidState(pos).isEmpty() ? blockState.with(Properties.WATERLOGGED, true) : blockState;
	}


	private static boolean shouldNotDecay(WorldAccess world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos.up());
		BlockState blockState1 = world.getBlockState(pos.down());
		if (((blockState.isAir()) || (blockState.isOf(Blocks.WATER) && blockState.getFluidState().isOf(Fluids.WATER))) || (canPlaceBone(pos) && ((blockState1.isAir()) || (blockState1.isOf(Blocks.WATER) && blockState1.getFluidState().isOf(Fluids.WATER))))) {
			int i = 0;
			Iterator<BlockPos> var4 = BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 2, 4)).iterator();

			do {
				if (!var4.hasNext()) {
					return true;
				}

				BlockPos blockPos = var4.next();
				BlockState blockState2 = world.getBlockState(blockPos);
				if (blockState2.isOf(Blocks.SCULK_SENSOR) || blockState2.isOf(Blocks.SCULK_SHRIEKER)) {
					++i;
				}
			} while(i <= 2);

		}
		return false;
	}

	@Shadow private static int getDecay(SculkSpreadManager spreadManager, BlockPos cursorPos, BlockPos catalystPos, int charge) {
		int i = spreadManager.getMaxDistance();
		float f = MathHelper.square((float)Math.sqrt(cursorPos.getSquaredDistance(catalystPos)) - (float)i);
		int j = MathHelper.square(24 - i);
		float g = Math.min(1.0F, f / (float)j);
		return Math.max(1, (int)((float)charge * g * 0.5F));
	}
}
