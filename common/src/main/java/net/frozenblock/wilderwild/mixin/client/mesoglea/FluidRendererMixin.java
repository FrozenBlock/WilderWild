/*
 * Copyright 2025-2026 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.client.mesoglea;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.platform.Transparency;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidRenderer;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(FluidRenderer.class)
public class FluidRendererMixin {
	@Unique
	private static final CardinalLighting WILDERWILD$EMISSIVE_CARDINAL_LIGHTING = new CardinalLighting(1F, 1F, 1F, 1F, 1F, 1F);

	@Unique
	private static boolean wilderWild$isNeighborSameBlock(BlockState blockState, BlockState neighborBlockState) {
		return neighborBlockState.is(blockState.getBlock());
	}

	@Inject(method = "tesselate", at = @At("HEAD"))
	public void wilderWild$captureMesoglea(
		BlockAndTintGetter level, BlockPos pos, FluidRenderer.Output output, BlockState blockState, FluidState fluidState, CallbackInfo info,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea
	) {
		isMesoglea.set(blockState.getBlock() instanceof MesogleaBlock && WWBlockConfig.MESOGLEA_RENDERS_AS_FLUID.get());
	}

	@WrapOperation(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/block/FluidRenderer;isNeighborSameFluid(Lnet/minecraft/world/level/material/FluidState;Lnet/minecraft/world/level/material/FluidState;)Z"
		)
	)
	public boolean wilderWild$renderMesogleaUp(
		FluidState fluidState, FluidState neighborFluidState, Operation<Boolean> original,
		@Local(argsOnly = true) BlockState blockState,
		@Local(name = "blockStateUp") BlockState blockStateUp,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea
	) {
		if (isMesoglea != null && isMesoglea.get() && !wilderWild$isNeighborSameBlock(blockState, blockStateUp)) return false;
		return original.call(fluidState, neighborFluidState);
	}

	@WrapOperation(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/block/FluidRenderer;shouldRenderFace(Lnet/minecraft/world/level/material/FluidState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/material/FluidState;)Z",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/core/Direction;DOWN:Lnet/minecraft/core/Direction;",
				opcode = Opcodes.GETSTATIC,
				ordinal = 1
			)
		)
	)
	public boolean wilderWild$renderMesogleaDown(
		FluidState fluidState, BlockState blockState, Direction direction, FluidState neighborFluidState, Operation<Boolean> original,
		@Local(name = "blockStateDown") BlockState blockStateDown,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea
	) {
		if (isMesoglea != null && isMesoglea.get() && !wilderWild$isNeighborSameBlock(blockState, blockStateDown)) return false;
		return original.call(fluidState, blockState, direction, neighborFluidState);
	}

	@WrapOperation(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/block/FluidRenderer;shouldRenderFace(Lnet/minecraft/world/level/material/FluidState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/material/FluidState;)Z",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/core/Direction;NORTH:Lnet/minecraft/core/Direction;",
				opcode = Opcodes.GETSTATIC,
				ordinal = 1
			)
		)
	)
	public boolean wilderWild$renderMesogleaNorth(
		FluidState fluidState, BlockState blockState, Direction direction, FluidState neighborFluidState, Operation<Boolean> original,
		@Local(name = "blockStateNorth") BlockState blockStateNorth,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea
	) {
		if (isMesoglea != null && isMesoglea.get() && !wilderWild$isNeighborSameBlock(blockState, blockStateNorth)) return false;
		return original.call(fluidState, blockState, direction, neighborFluidState);
	}

	@WrapOperation(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/block/FluidRenderer;shouldRenderFace(Lnet/minecraft/world/level/material/FluidState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/material/FluidState;)Z",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/core/Direction;SOUTH:Lnet/minecraft/core/Direction;",
				opcode = Opcodes.GETSTATIC,
				ordinal = 1
			)
		)
	)
	public boolean wilderWild$renderMesogleaSouth(
		FluidState fluidState, BlockState blockState, Direction direction, FluidState neighborFluidState, Operation<Boolean> original,
		@Local(name = "blockStateSouth") BlockState blockStateSouth,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea
	) {
		if (isMesoglea != null && isMesoglea.get() && !wilderWild$isNeighborSameBlock(blockState, blockStateSouth)) return false;
		return original.call(fluidState, blockState, direction, neighborFluidState);
	}

	@WrapOperation(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/block/FluidRenderer;shouldRenderFace(Lnet/minecraft/world/level/material/FluidState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/material/FluidState;)Z",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/core/Direction;WEST:Lnet/minecraft/core/Direction;",
				opcode = Opcodes.GETSTATIC,
				ordinal = 1
			)
		)
	)
	public boolean wilderWild$renderMesogleaWest(
		FluidState fluidState, BlockState blockState, Direction direction, FluidState neighborFluidState, Operation<Boolean> original,
		@Local(name = "blockStateWest") BlockState blockStateWest,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea
	) {
		if (isMesoglea != null && isMesoglea.get() && !wilderWild$isNeighborSameBlock(blockState, blockStateWest)) return false;
		return original.call(fluidState, blockState, direction, neighborFluidState);
	}

	@WrapOperation(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/block/FluidRenderer;shouldRenderFace(Lnet/minecraft/world/level/material/FluidState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/material/FluidState;)Z",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/core/Direction;EAST:Lnet/minecraft/core/Direction;",
				opcode = Opcodes.GETSTATIC,
				ordinal = 1
			)
		)
	)
	public boolean wilderWild$renderMesogleaEast(
		FluidState fluidState, BlockState blockState, Direction direction, FluidState neighborFluidState, Operation<Boolean> original,
		@Local(name = "blockStateEast") BlockState blockStateEast,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea
	) {
		if (isMesoglea != null && isMesoglea.get() && !wilderWild$isNeighborSameBlock(blockState, blockStateEast)) return false;
		return original.call(fluidState, blockState, direction, neighborFluidState);
	}

	@ModifyExpressionValue(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/block/FluidModel;layer()Lnet/minecraft/client/renderer/chunk/ChunkSectionLayer;",
			ordinal = 0
		)
	)
	public ChunkSectionLayer wilderWild$setMesogleaMaterialAndLayer(
		ChunkSectionLayer original,
		@Local(argsOnly = true) BlockState blockState,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea,
		@Share("wilderWild$mesogleaMaterial") LocalRef<Material.Baked> mesogleaMaterial
	) {
		if (isMesoglea != null && isMesoglea.get()) {
			final Material.Baked material = Minecraft.getInstance().getModelManager().getBlockStateModelSet().getParticleMaterial(blockState);
			mesogleaMaterial.set(material);
			return ChunkSectionLayer.byTransparency(material.forceTranslucent() ? Transparency.TRANSLUCENT : material.sprite().transparency());
		}

		return original;
	}

	@ModifyExpressionValue(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/block/FluidModel;tintSource()Lnet/minecraft/client/color/block/BlockTintSource;",
			ordinal = 0
		)
	)
	public BlockTintSource wilderWild$removeMesogleaTint(
		BlockTintSource original,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea
	) {
		return isMesoglea != null && isMesoglea.get() ? null : original;
	}

	@ModifyExpressionValue(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/block/BlockAndTintGetter;cardinalLighting()Lnet/minecraft/world/level/CardinalLighting;"
		)
	)
	// TODO: Glowtone compat
	public CardinalLighting wilderWild$emissiveMesogleaCardinalLighting(
		CardinalLighting original,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea
	) {
		return isMesoglea != null && isMesoglea.get() ? WILDERWILD$EMISSIVE_CARDINAL_LIGHTING : original;
	}

	@ModifyExpressionValue(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/material/FluidState;getFlow(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/Vec3;"
		)
	)
	public Vec3 wilderWild$removeMesogleaFlow(
		Vec3 original,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea
	) {
		return isMesoglea != null && isMesoglea.get() ? Vec3.ZERO : original;
	}

	@ModifyExpressionValue(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/block/FluidModel;stillMaterial()Lnet/minecraft/client/resources/model/sprite/Material$Baked;"
		)
	)
	public Material.Baked wilderWild$useMesogleaSprite(
		Material.Baked original,
		@Share("wilderWild$mesogleaMaterial") LocalRef<Material.Baked> mesogleaMaterial
	) {
		return mesogleaMaterial != null && mesogleaMaterial.get() != null
			? mesogleaMaterial.get()
			: original;
	}

	@WrapOperation(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/block/FluidRenderer;getLightCoords(Lnet/minecraft/client/renderer/block/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;)I"
		)
	)
	public int wilderWild$emissiveMesogleaLevelLighting(
		FluidRenderer instance, BlockAndTintGetter level, BlockPos pos, Operation<Integer> original,
		@Share("wilderWild$isMesoglea") LocalBooleanRef isMesoglea
	) {
		return isMesoglea != null && isMesoglea.get()
			? LightCoordsUtil.FULL_BRIGHT
			: original.call(instance, level, pos);
	}
}
