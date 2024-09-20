/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.client.sodium;

import com.llamalad7.mixinextras.sugar.Local;
import me.jellysquid.mods.sodium.client.model.color.ColorProvider;
import me.jellysquid.mods.sodium.client.model.light.LightMode;
import me.jellysquid.mods.sodium.client.model.light.LightPipeline;
import me.jellysquid.mods.sodium.client.model.light.LightPipelineProvider;
import me.jellysquid.mods.sodium.client.model.quad.ModelQuadView;
import me.jellysquid.mods.sodium.client.model.quad.ModelQuadViewMutable;
import me.jellysquid.mods.sodium.client.model.quad.properties.ModelQuadFacing;
import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildBuffers;
import me.jellysquid.mods.sodium.client.render.chunk.compile.buffers.ChunkModelBuilder;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.FluidRenderer;
import me.jellysquid.mods.sodium.client.render.chunk.terrain.material.DefaultMaterials;
import me.jellysquid.mods.sodium.client.render.chunk.terrain.material.Material;
import me.jellysquid.mods.sodium.client.util.DirectionUtil;
import me.jellysquid.mods.sodium.client.world.WorldSlice;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Pseudo
@Mixin(FluidRenderer.class)
public abstract class FluidRendererMixin {

	@Shadow
	protected abstract ColorProvider<FluidState> getColorProvider(Fluid fluid, FluidRenderHandler handler);

	@Shadow
	@Final
	private BlockPos.MutableBlockPos scratchPos;
	@Shadow(remap = false)
	@Final
	private ModelQuadViewMutable quad;
	@Shadow(remap = false)
	@Final
	private LightPipelineProvider lighters;

	@Shadow(remap = false)
	private static void setVertex(ModelQuadViewMutable quad, int i, float x, float y, float z, float u, float v) {
	}

	@Inject(
		method = "isFluidOccluded",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getFluidState()Lnet/minecraft/world/level/material/FluidState;",
			ordinal = 0
		),
		cancellable = true,
		locals = LocalCapture.CAPTURE_FAILSOFT,
		require = 0
	)
	private void wilderWild$isFluidOccluded(
		BlockAndTintGetter world, int x, int y, int z, Direction dir, Fluid fluid, CallbackInfoReturnable<Boolean> info,
		@Local BlockState blockState
	) {
		if (blockState.getBlock() instanceof MesogleaBlock && dir != Direction.UP) {
			info.setReturnValue(true);
		}
	}

	@Inject(method = "render", at = @At(value = "HEAD"), cancellable = true, require = 0)
	private void wilderWild$getIsWater(WorldSlice world, FluidState fluidState, BlockPos pos, BlockPos offset, ChunkBuildBuffers buffers, CallbackInfo info) {
		BlockState blockState = world.getBlockState(pos);
		if (WWBlockConfig.get().mesoglea.mesogleaLiquid && blockState.getBlock() instanceof MesogleaBlock) {
			this.wilderWild$renderWithSingleTexture(world, fluidState, pos, offset, buffers, blockState, Minecraft.getInstance().getModelManager().getBlockModelShaper().getBlockModel(blockState).getParticleIcon());
			info.cancel();
		}
	}

	@Unique
	public boolean wilderWild$renderWithSingleTexture(WorldSlice world, FluidState fluidState, @NotNull BlockPos pos, BlockPos offset, @NotNull ChunkBuildBuffers buffers, BlockState blockState, TextureAtlasSprite sprite) {
		Material material = DefaultMaterials.forFluidState(fluidState);
		ChunkModelBuilder meshBuilder = buffers.get(material);
		int posX = pos.getX();
		int posY = pos.getY();
		int posZ = pos.getZ();
		Fluid fluid = fluidState.getType();
		boolean sfUp = this.isFluidOccludedAndNotSameBlock(world, posX, posY, posZ, Direction.UP, fluid, blockState);
		boolean sfDown = this.isFluidOccludedAndNotSameBlock(world, posX, posY, posZ, Direction.DOWN, fluid, blockState) || !this.isSideExposed(world, posX, posY, posZ, Direction.DOWN, 0.8888889F);
		boolean sfNorth = this.isFluidOccludedAndNotSameBlock(world, posX, posY, posZ, Direction.NORTH, fluid, blockState);
		boolean sfSouth = this.isFluidOccludedAndNotSameBlock(world, posX, posY, posZ, Direction.SOUTH, fluid, blockState);
		boolean sfWest = this.isFluidOccludedAndNotSameBlock(world, posX, posY, posZ, Direction.WEST, fluid, blockState);
		boolean sfEast = this.isFluidOccludedAndNotSameBlock(world, posX, posY, posZ, Direction.EAST, fluid, blockState);
		if (sfUp && sfDown && sfEast && sfWest && sfNorth && sfSouth) {
			return false;
		} else {
			float minU = sprite.getU0();
			float maxU = sprite.getU1();
			FluidRenderHandler handler = FluidRenderHandlerRegistry.INSTANCE.get(Fluids.LAVA);
			ColorProvider<FluidState> colorProvider = this.getColorProvider(Fluids.LAVA, handler);
			boolean rendered = false;
			float fluidHeight = this.fluidHeight(world, fluid, pos, Direction.UP);
			float northWestHeight;
			float southWestHeight;
			float southEastHeight;
			float northEastHeight;
			if (fluidHeight >= 1.0F) {
				northWestHeight = 1.0F;
				southWestHeight = 1.0F;
				southEastHeight = 1.0F;
				northEastHeight = 1.0F;
			} else {
				BlockPos.MutableBlockPos scratchPos = new BlockPos.MutableBlockPos();
				float heightNorth = this.fluidHeight(world, fluid, scratchPos.setWithOffset(pos, Direction.NORTH), Direction.NORTH);
				float heightSouth = this.fluidHeight(world, fluid, scratchPos.setWithOffset(pos, Direction.SOUTH), Direction.SOUTH);
				float heightEast = this.fluidHeight(world, fluid, scratchPos.setWithOffset(pos, Direction.EAST), Direction.EAST);
				float heightWest = this.fluidHeight(world, fluid, scratchPos.setWithOffset(pos, Direction.WEST), Direction.WEST);
				northWestHeight = this.fluidCornerHeight(world, fluid, fluidHeight, heightNorth, heightWest, scratchPos.set(pos).move(Direction.NORTH).move(Direction.WEST));
				southWestHeight = this.fluidCornerHeight(world, fluid, fluidHeight, heightSouth, heightWest, scratchPos.set(pos).move(Direction.SOUTH).move(Direction.WEST));
				southEastHeight = this.fluidCornerHeight(world, fluid, fluidHeight, heightSouth, heightEast, scratchPos.set(pos).move(Direction.SOUTH).move(Direction.EAST));
				northEastHeight = this.fluidCornerHeight(world, fluid, fluidHeight, heightNorth, heightEast, scratchPos.set(pos).move(Direction.NORTH).move(Direction.EAST));
			}

			float yOffset = sfDown ? 0.0F : 0.001F;
			ModelQuadViewMutable quad = this.quad;
			LightMode lightMode = Minecraft.useAmbientOcclusion() ? LightMode.SMOOTH : LightMode.FLAT;
			LightPipeline lighter = this.lighters.getLighter(lightMode);
			quad.setFlags(0);
			float u1 = sprite.getU(0.0F);
			float c1 = u1;
			float c2 = sprite.getU(1.0F);
			float x1 = c2;
			float z1 = sprite.getV(0.0F);
			float x2 = sprite.getV(1.0F);
			float z2 = x2;
			if (!sfUp && this.isSideExposed(world, posX, posY, posZ, Direction.UP, Math.min(Math.min(northWestHeight, southWestHeight), Math.min(southEastHeight, northEastHeight)))) {
				northWestHeight -= 0.001F;
				southWestHeight -= 0.001F;
				southEastHeight -= 0.001F;
				northEastHeight -= 0.001F;
				Vec3 velocity = fluidState.getFlow(world, pos);
				ModelQuadFacing facing;
				float v4 = z1;
				float uAvg;
				float vAvg;
				float s1;
				if (velocity.x == 0.0 && velocity.z == 0.0) {
					facing = ModelQuadFacing.POS_Y;
				} else {
					facing = ModelQuadFacing.UNASSIGNED;
				}

				uAvg = (u1 + c1 + c2 + x1) / 4.0F;
				vAvg = (z1 + x2 + z2 + v4) / 4.0F;
				s1 = sprite.contents().width() / (sprite.getU1() - sprite.getU0());
				float s2 = sprite.contents().height() / (sprite.getV1() - sprite.getV0());
				float lerpThing = 4.0F / Math.max(s2, s1);
				u1 = Mth.lerp(lerpThing, u1, uAvg);
				c1 = Mth.lerp(lerpThing, c1, uAvg);
				c2 = Mth.lerp(lerpThing, c2, uAvg);
				x1 = Mth.lerp(lerpThing, x1, uAvg);
				z1 = Mth.lerp(lerpThing, z1, vAvg);
				x2 = Mth.lerp(lerpThing, x2, vAvg);
				z2 = Mth.lerp(lerpThing, z2, vAvg);
				v4 = Mth.lerp(lerpThing, v4, vAvg);
				quad.setSprite(sprite);
				setVertex(quad, 0, 0.0F, northWestHeight, 0.0F, u1, z1);
				setVertex(quad, 1, 0.0F, southWestHeight, 1.0F, c1, x2);
				setVertex(quad, 2, 1.0F, southEastHeight, 1.0F, c2, z2);
				setVertex(quad, 3, 1.0F, northEastHeight, 0.0F, x1, v4);
				this.updateQuad(quad, world, pos, lighter, Direction.UP, 1.0F, colorProvider, fluidState);
				this.writeQuad(meshBuilder, material, offset, quad, facing, false);
				if (fluidState.shouldRenderBackwardUpFace(world, this.scratchPos.set(posX, posY + 1, posZ))) {
					this.writeQuad(meshBuilder, material, offset, quad, ModelQuadFacing.NEG_Y, true);
				}

				rendered = true;
			}

			if (!sfDown) {
				Direction dir = Direction.DOWN;
				int adjX = posX + dir.getStepX();
				int adjY = posY + dir.getStepY();
				int adjZ = posZ + dir.getStepZ();
				boolean isOverlay = false;
				BlockPos adjPos = this.scratchPos.set(adjX, adjY, adjZ);
				BlockState adjBlock = world.getBlockState(adjPos);
				if (FluidRenderHandlerRegistry.INSTANCE.isBlockTransparent(adjBlock.getBlock())) {
					isOverlay = true;
				}

				u1 = sprite.getV0();
				c1 = sprite.getV1();
				quad.setSprite(sprite);
				setVertex(quad, 0, 0.0F, yOffset, 1.0F, minU, c1);
				setVertex(quad, 1, 0.0F, yOffset, 0.0F, minU, u1);
				setVertex(quad, 2, 1.0F, yOffset, 0.0F, maxU, u1);
				setVertex(quad, 3, 1.0F, yOffset, 1.0F, maxU, c1);
				this.updateQuad(quad, world, pos, lighter, Direction.DOWN, 1.0F, colorProvider, fluidState);
				this.writeQuad(meshBuilder, material, offset, quad, ModelQuadFacing.NEG_Y, false);

				if (!isOverlay) {
					this.writeQuad(meshBuilder, material, offset, quad, ModelQuadFacing.POS_Y, true);
				}
				rendered = true;
			}

			quad.setFlags(4);
			for (Direction dir : DirectionUtil.HORIZONTAL_DIRECTIONS) {
				switch (dir) {
					case NORTH:
						if (sfNorth) {
							continue;
						}

						c1 = northWestHeight;
						c2 = northEastHeight;
						x1 = 0.0F;
						x2 = 1.0F;
						z1 = 0.001F;
						z2 = z1;
						break;
					case SOUTH:
						if (sfSouth) {
							continue;
						}

						c1 = southEastHeight;
						c2 = southWestHeight;
						x1 = 1.0F;
						x2 = 0.0F;
						z1 = 0.999F;
						z2 = z1;
						break;
					case WEST:
						if (sfWest) {
							continue;
						}

						c1 = southWestHeight;
						c2 = northWestHeight;
						x1 = 0.001F;
						x2 = x1;
						z1 = 1.0F;
						z2 = 0.0F;
						break;
					case EAST:
						if (!sfEast) {
							c1 = northEastHeight;
							c2 = southEastHeight;
							x1 = 0.999F;
							x2 = x1;
							z1 = 0.0F;
							z2 = 1.0F;
							break;
						}
					default:
						continue;
				}

				if (this.isSideExposed(world, posX, posY, posZ, dir, Math.max(c1, c2))) {
					int adjX = posX + dir.getStepX();
					int adjY = posY + dir.getStepY();
					int adjZ = posZ + dir.getStepZ();
					boolean isOverlay = false;
					BlockPos adjPos = this.scratchPos.set(adjX, adjY, adjZ);
					BlockState adjBlock = world.getBlockState(adjPos);
					if (FluidRenderHandlerRegistry.INSTANCE.isBlockTransparent(adjBlock.getBlock())) {
						isOverlay = true;
					}
					u1 = sprite.getU(0.0F);
					float u2 = sprite.getU(1.0F);
					float v1 = sprite.getV((1.0F - c1) * 0.5F);
					float v2 = sprite.getV((1.0F - c2) * 0.5F);
					float v3 = sprite.getV(1.0F);
					quad.setSprite(sprite);
					setVertex(quad, 0, x2, c2, z2, u2, v2);
					setVertex(quad, 1, x2, yOffset, z2, u2, v3);
					setVertex(quad, 2, x1, yOffset, z1, u1, v3);
					setVertex(quad, 3, x1, c1, z1, u1, v1);
					float br = dir.getAxis() == Direction.Axis.Z ? 0.8F : 0.6F;
					ModelQuadFacing facing = ModelQuadFacing.fromDirection(dir);
					this.updateQuad(quad, world, pos, lighter, dir, br, colorProvider, fluidState);
					this.writeQuad(meshBuilder, material, offset, quad, facing, false);
					if (!isOverlay) {
						this.writeQuad(meshBuilder, material, offset, quad, facing.getOpposite(), true);
					}

					rendered = true;
				}
			}

			return rendered;
		}
	}

	@Unique
	private boolean isFluidOccludedAndNotSameBlock(BlockAndTintGetter world, int x, int y, int z, Direction dir, Fluid fluid, BlockState blockState) {
		return isFluidOccluded(world, x, y, z, dir, fluid) && isNeighborSameFluidAndBlock(world, x, y, z, dir, fluid, blockState);
	}

	@Unique
	private boolean isNeighborSameFluidAndBlock(BlockAndTintGetter world, int x, int y, int z, Direction dir, Fluid fluid, BlockState blockState) {
		BlockState secondBlock = world.getBlockState(this.scratchPos.set(x + dir.getStepX(), y + dir.getStepY(), z + dir.getStepZ()));
		return secondBlock.getFluidState().getType().isSame(fluid) && blockState.getBlock() == secondBlock.getBlock();
	}

	@Shadow
	private boolean isFluidOccluded(BlockAndTintGetter world, int x, int y, int z, Direction dir, Fluid fluid) {
		throw new AssertionError("Mixin injection failed - Wilder Wild FluidRendererMixin.");
	}

	@Shadow
	private boolean isSideExposed(BlockAndTintGetter world, int x, int y, int z, Direction dir, float height) {
		throw new AssertionError("Mixin injection failed - Wilder Wild FluidRendererMixin.");
	}

	@Shadow
	private float fluidCornerHeight(BlockAndTintGetter world, Fluid fluid, float fluidHeight, float fluidHeightX, float fluidHeightY, BlockPos blockPos) {
		throw new AssertionError("Mixin injection failed - Wilder Wild FluidRendererMixin.");
	}

	@Shadow
	protected abstract void writeQuad(ChunkModelBuilder builder, Material material, BlockPos offset, ModelQuadView quad, ModelQuadFacing facing, boolean flip);

	@Shadow
	protected abstract void updateQuad(ModelQuadView quad, WorldSlice world, BlockPos pos, LightPipeline lighter, Direction dir, float brightness, ColorProvider<FluidState> colorProvider, FluidState fluidState);

	@Shadow
	protected abstract float fluidHeight(BlockAndTintGetter world, Fluid fluid, BlockPos blockPos, Direction direction);
}
