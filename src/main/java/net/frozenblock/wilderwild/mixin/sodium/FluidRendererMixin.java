/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.sodium;

import me.jellysquid.mods.sodium.client.model.light.LightMode;
import me.jellysquid.mods.sodium.client.model.light.LightPipeline;
import me.jellysquid.mods.sodium.client.model.light.LightPipelineProvider;
import me.jellysquid.mods.sodium.client.model.quad.ModelQuadView;
import me.jellysquid.mods.sodium.client.model.quad.ModelQuadViewMutable;
import me.jellysquid.mods.sodium.client.model.quad.blender.ColorSampler;
import me.jellysquid.mods.sodium.client.model.quad.properties.ModelQuadFacing;
import me.jellysquid.mods.sodium.client.model.quad.properties.ModelQuadWinding;
import me.jellysquid.mods.sodium.client.render.chunk.compile.buffers.ChunkModelBuilder;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.FluidRenderer;
import me.jellysquid.mods.sodium.common.util.DirectionUtil;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin {

	@Unique
	private final TextureAtlasSprite wilderWild$waterOverlay = ModelBakery.WATER_OVERLAY.sprite();
	@Shadow
	@Final
	private BlockPos.MutableBlockPos scratchPos;
	@Shadow
	@Final
	private ModelQuadViewMutable quad;
	@Shadow
	@Final
	private LightPipelineProvider lighters;
	@Unique
	private float wilderWild$u0;
	@Unique
	private float wilderWild$u1;
	@Unique
	private float wilderWild$v0;
	@Unique
	private float wilderWild$v1;
	@Unique
	private boolean wilderWild$isWater;

	@Shadow
	private static void setVertex(ModelQuadViewMutable quad, int i, float x, float y, float z, float u, float v) {
	}

	@Inject(method = "isFluidOccluded", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos$MutableBlockPos;set(III)Lnet/minecraft/core/BlockPos$MutableBlockPos;", ordinal = 1), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
	private void wilderWild$isFluidOccluded(BlockAndTintGetter world, int x, int y, int z, Direction dir, Fluid fluid, CallbackInfoReturnable<Boolean> info, BlockPos pos, BlockState blockState) {
		if (blockState.getBlock() instanceof MesogleaBlock && dir != Direction.UP) {
			info.setReturnValue(true);
		}
	}

	@Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
	private void wilderWild$getIsWater(BlockAndTintGetter world, FluidState fluidState, BlockPos pos, BlockPos offset, ChunkModelBuilder buffers, CallbackInfoReturnable<Boolean> info) {
		BlockState blockState;
		if (BlockConfig.get().mesogleaLiquid && (blockState = world.getBlockState(pos)).getBlock() instanceof MesogleaBlock) {
			this.wilderWild$renderWithSingleTexture(world, fluidState, pos, offset, buffers, blockState, Minecraft.getInstance().getModelManager().getBlockModelShaper().getBlockModel(blockState).getParticleIcon());
			info.cancel();
		}
		this.wilderWild$isWater = fluidState.is(FluidTags.WATER);
	}

	@Unique
	public boolean wilderWild$renderWithSingleTexture(BlockAndTintGetter world, FluidState fluidState, BlockPos pos, BlockPos offset, ChunkModelBuilder buffers, BlockState blockState, TextureAtlasSprite sprite) {
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
			ColorSampler<FluidState> colorizer = this.createColorProviderAdapter(FluidRenderHandlerRegistry.INSTANCE.get(Fluids.LAVA));
			boolean rendered = false;
			float fluidHeight = this.fluidHeight(world, fluid, pos);
			float h1;
			float h2;
			float h3;
			float h4;
			float yOffset;
			if (fluidHeight >= 1.0F) {
				h1 = 1.0F;
				h2 = 1.0F;
				h3 = 1.0F;
				h4 = 1.0F;
			} else {
				yOffset = this.fluidHeight(world, fluid, pos.north());
				float south1 = this.fluidHeight(world, fluid, pos.south());
				float east1 = this.fluidHeight(world, fluid, pos.east());
				float west1 = this.fluidHeight(world, fluid, pos.west());
				h1 = this.fluidCornerHeight(world, fluid, fluidHeight, yOffset, west1, pos.relative(Direction.NORTH).relative(Direction.WEST));
				h2 = this.fluidCornerHeight(world, fluid, fluidHeight, south1, west1, pos.relative(Direction.SOUTH).relative(Direction.WEST));
				h3 = this.fluidCornerHeight(world, fluid, fluidHeight, south1, east1, pos.relative(Direction.SOUTH).relative(Direction.EAST));
				h4 = this.fluidCornerHeight(world, fluid, fluidHeight, yOffset, east1, pos.relative(Direction.NORTH).relative(Direction.EAST));
			}

			yOffset = sfDown ? 0.0F : 0.001F;
			ModelQuadViewMutable quad = this.quad;
			LightMode lightMode = Minecraft.useAmbientOcclusion() ? LightMode.SMOOTH : LightMode.FLAT;
			LightPipeline lighter = this.lighters.getLighter(lightMode);
			quad.setFlags(0);
			float u1 = sprite.getU(0.0);
			float c1 = u1;
			float c2 = sprite.getU(16.0);
			float x1 = c2;
			float z1 = sprite.getV(0.0);
			float x2 = sprite.getV(16.0);
			float z2 = x2;
			if (!sfUp && this.isSideExposed(world, posX, posY, posZ, Direction.UP, Math.min(Math.min(h1, h2), Math.min(h3, h4)))) {
				h1 -= 0.001F;
				h2 -= 0.001F;
				h3 -= 0.001F;
				h4 -= 0.001F;
				Vec3 velocity = fluidState.getFlow(world, pos);
				ModelQuadFacing facing;
				float v4 = z1;
				float uAvg;
				float vAvg;
				float s1;
				if (velocity.x == 0.0 && velocity.z == 0.0) {
					facing = ModelQuadFacing.UP;
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
				setVertex(quad, 0, 0.0F, h1, 0.0F, u1, z1);
				setVertex(quad, 1, 0.0F, h2, 1.0F, c1, x2);
				setVertex(quad, 2, 1.0F, h3, 1.0F, c2, z2);
				setVertex(quad, 3, 1.0F, h4, 0.0F, x1, v4);
				this.updateQuad(quad, world, pos, lighter, Direction.UP, 1.0F, colorizer, fluidState);
				this.writeQuad(buffers, offset, quad, facing, ModelQuadWinding.CLOCKWISE);
				if (fluidState.shouldRenderBackwardUpFace(world, this.scratchPos.set(posX, posY + 1, posZ))) {
					this.writeQuad(buffers, offset, quad, ModelQuadFacing.DOWN, ModelQuadWinding.COUNTERCLOCKWISE);
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
				this.updateQuad(quad, world, pos, lighter, Direction.DOWN, 1.0F, colorizer, fluidState);
				this.writeQuad(buffers, offset, quad, ModelQuadFacing.DOWN, ModelQuadWinding.CLOCKWISE);

				if (!isOverlay) {
					this.writeQuad(buffers, offset, quad, ModelQuadFacing.UP, ModelQuadWinding.COUNTERCLOCKWISE);
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

						c1 = h1;
						c2 = h4;
						x1 = 0.0F;
						x2 = 1.0F;
						z1 = 0.001F;
						z2 = z1;
						break;
					case SOUTH:
						if (sfSouth) {
							continue;
						}

						c1 = h3;
						c2 = h2;
						x1 = 1.0F;
						x2 = 0.0F;
						z1 = 0.999F;
						z2 = z1;
						break;
					case WEST:
						if (sfWest) {
							continue;
						}

						c1 = h2;
						c2 = h1;
						x1 = 0.001F;
						x2 = x1;
						z1 = 1.0F;
						z2 = 0.0F;
						break;
					case EAST:
						if (!sfEast) {
							c1 = h4;
							c2 = h3;
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

					u1 = sprite.getU(0F);
					float u2 = sprite.getU(16F);
					float v1 = sprite.getV(0F);
					float v2 = sprite.getV(0F);
					float v3 = sprite.getV(16F);
					quad.setSprite(sprite);
					setVertex(quad, 0, x2, c2, z2, u2, v2);
					setVertex(quad, 1, x2, yOffset, z2, u2, v3);
					setVertex(quad, 2, x1, yOffset, z1, u1, v3);
					setVertex(quad, 3, x1, c1, z1, u1, v1);
					float br = dir.getAxis() == Direction.Axis.Z ? 0.8F : 0.6F;
					ModelQuadFacing facing = ModelQuadFacing.fromDirection(dir);
					this.updateQuad(quad, world, pos, lighter, dir, br, colorizer, fluidState);
					this.writeQuad(buffers, offset, quad, facing, ModelQuadWinding.CLOCKWISE);
					if (!isOverlay) {
						this.writeQuad(buffers, offset, quad, facing.getOpposite(), ModelQuadWinding.COUNTERCLOCKWISE);
					}

					rendered = true;
				}
			}

			return rendered;
		}
	}

	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/model/quad/ModelQuadViewMutable;setSprite(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V", ordinal = 1))
	private void wilderWild$switchSprites(Args args) {
		if (this.wilderWild$isWater) {
			args.set(0, this.wilderWild$waterOverlay);
		}
	}

	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/FluidRenderer;setVertex(Lme/jellysquid/mods/sodium/client/model/quad/ModelQuadViewMutable;IFFFFF)V", ordinal = 4))
	private void wilderWild$sideTextureBottom1(Args args) {
		if (this.wilderWild$isWater) {
			this.wilderWild$u0 = this.wilderWild$waterOverlay.getU0();
			this.wilderWild$u1 = this.wilderWild$waterOverlay.getU1();
			this.wilderWild$v0 = this.wilderWild$waterOverlay.getV0();
			this.wilderWild$v1 = this.wilderWild$waterOverlay.getV1();
			args.set(5, this.wilderWild$u0);
			args.set(6, this.wilderWild$v1);
		}
	}

	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/FluidRenderer;setVertex(Lme/jellysquid/mods/sodium/client/model/quad/ModelQuadViewMutable;IFFFFF)V", ordinal = 5))
	private void wilderWild$sideTextureBottom2(Args args) {
		if (this.wilderWild$isWater) {
			args.set(5, this.wilderWild$u0);
			args.set(6, this.wilderWild$v0);
		}
	}

	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/FluidRenderer;setVertex(Lme/jellysquid/mods/sodium/client/model/quad/ModelQuadViewMutable;IFFFFF)V", ordinal = 6))
	private void wilderWild$sideTextureBottom3(Args args) {
		if (this.wilderWild$isWater) {
			args.set(5, this.wilderWild$u1);
			args.set(6, this.wilderWild$v0);
		}
	}

	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/FluidRenderer;setVertex(Lme/jellysquid/mods/sodium/client/model/quad/ModelQuadViewMutable;IFFFFF)V", ordinal = 7))
	private void wilderWild$sideTextureBottom4(Args args) {
		if (this.wilderWild$isWater) {
			args.set(5, this.wilderWild$u1);
			args.set(6, this.wilderWild$v1);
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
	private ColorSampler<FluidState> createColorProviderAdapter(FluidRenderHandler handler) {
		throw new AssertionError("Mixin injection failed - Wilder Wild FluidRendererMixin.");
	}

	@Shadow
	private void updateQuad(ModelQuadView quad, BlockAndTintGetter world, BlockPos pos, LightPipeline lighter, Direction dir, float brightness, ColorSampler<FluidState> colorSampler, FluidState fluidState) {
	}

	@Shadow
	private void writeQuad(ChunkModelBuilder builder, BlockPos offset, ModelQuadView quad, ModelQuadFacing facing, ModelQuadWinding winding) {
	}

	@Shadow
	private float fluidCornerHeight(BlockAndTintGetter world, Fluid fluid, float fluidHeight, float fluidHeightX, float fluidHeightY, BlockPos blockPos) {
		throw new AssertionError("Mixin injection failed - Wilder Wild FluidRendererMixin.");
	}

	@Shadow
	private void modifyHeight(MutableFloat totalHeight, MutableInt samples, float target) {

	}

	@Shadow
	private float fluidHeight(BlockAndTintGetter world, Fluid fluid, BlockPos blockPos) {
		throw new AssertionError("Mixin injection failed - Wilder Wild FluidRendererMixin.");
	}

}
