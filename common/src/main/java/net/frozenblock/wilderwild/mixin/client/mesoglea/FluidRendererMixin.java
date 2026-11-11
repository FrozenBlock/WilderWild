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
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
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

	@Inject(method = "tesselate", at = @At("HEAD"), cancellable = true)
	public void wilderWild$captureMesoglea(
		BlockAndTintGetter level, BlockPos pos, FluidRenderer.Output output, BlockState blockState, FluidState fluidState, CallbackInfo info,
		@Share("wilderWild$mesogleaMaterial") LocalRef<Material.Baked> mesogleaMaterial
	) {
		if (!(blockState.getBlock() instanceof MesogleaBlock)) return;

		if (!WWBlockConfig.MESOGLEA_RENDERS_AS_FLUID.get()) {
			info.cancel();
			return;
		}

		final Material.Baked material = Minecraft.getInstance().getModelManager().getBlockStateModelSet().getParticleMaterial(blockState);
		mesogleaMaterial.set(material);
	}

	@Unique
	private static boolean wilderWild$useMesoglea(@Nullable LocalRef<Material.Baked> mesogleaMaterial) {
		return mesogleaMaterial != null && mesogleaMaterial.get() != null;
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
		@Share("wilderWild$mesogleaMaterial") LocalRef<Material.Baked> mesogleaMaterial
	) {
		if (wilderWild$useMesoglea(mesogleaMaterial) && !wilderWild$isNeighborSameBlock(blockState, blockStateUp)) return false;
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
	public boolean wilderWild$renderMesogleaDownA(
		FluidState fluidState, BlockState blockState, Direction direction, FluidState neighborFluidState, Operation<Boolean> original,
		@Local(name = "blockStateDown") BlockState blockStateDown,
		@Share("wilderWild$mesogleaMaterial") LocalRef<Material.Baked> mesogleaMaterial
	) {
		if (wilderWild$useMesoglea(mesogleaMaterial) && !wilderWild$isNeighborSameBlock(blockState, blockStateDown)) return true;
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
		@Share("wilderWild$mesogleaMaterial") LocalRef<Material.Baked> mesogleaMaterial
	) {
		if (wilderWild$useMesoglea(mesogleaMaterial) && !wilderWild$isNeighborSameBlock(blockState, blockStateNorth)) return true;
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
		@Share("wilderWild$mesogleaMaterial") LocalRef<Material.Baked> mesogleaMaterial
	) {
		if (wilderWild$useMesoglea(mesogleaMaterial) && !wilderWild$isNeighborSameBlock(blockState, blockStateSouth)) return true;
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
		@Share("wilderWild$mesogleaMaterial") LocalRef<Material.Baked> mesogleaMaterial
	) {
		if (wilderWild$useMesoglea(mesogleaMaterial) && !wilderWild$isNeighborSameBlock(blockState, blockStateWest)) return true;
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
		@Share("wilderWild$mesogleaMaterial") LocalRef<Material.Baked> mesogleaMaterial
	) {
		if (wilderWild$useMesoglea(mesogleaMaterial) && !wilderWild$isNeighborSameBlock(blockState, blockStateEast)) return true;
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
	public ChunkSectionLayer wilderWild$setMesogleaLayer(
		ChunkSectionLayer original,
		@Share("wilderWild$mesogleaMaterial") LocalRef<Material.Baked> mesogleaMaterial
	) {
		if (!wilderWild$useMesoglea(mesogleaMaterial)) return original;
		return ChunkSectionLayer.byTransparency(mesogleaMaterial.get().forceTranslucent() ? Transparency.TRANSLUCENT : mesogleaMaterial.get().sprite().transparency());
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
		@Share("wilderWild$mesogleaMaterial") LocalRef<Material.Baked> mesogleaMaterial
	) {
		return wilderWild$useMesoglea(mesogleaMaterial) ? null : original;
	}

	// TODO: Glowtone compat
	/*
	@ModifyExpressionValue(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/block/BlockAndTintGetter;cardinalLighting()Lnet/minecraft/world/level/CardinalLighting;"
		)
	)
	public CardinalLighting wilderWild$emissiveMesogleaCardinalLighting(
		CardinalLighting original,
		@Share("wilderWild$mesogleaMaterial") LocalRef<Material.Baked> mesogleaMaterial
	) {
		return wilderWild$useMesoglea(mesogleaMaterial) ? WILDERWILD$EMISSIVE_CARDINAL_LIGHTING : original;
	}
	 */

	@ModifyExpressionValue(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/material/FluidState;getFlow(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/Vec3;"
		)
	)
	public Vec3 wilderWild$removeMesogleaFlow(
		Vec3 original,
		@Share("wilderWild$mesogleaMaterial") LocalRef<Material.Baked> mesogleaMaterial
	) {
		return wilderWild$useMesoglea(mesogleaMaterial) ? Vec3.ZERO : original;
	}

	@ModifyExpressionValue(
		method = "tesselate",
		at = {
			@At(
				value = "INVOKE",
				target = "Lnet/minecraft/client/renderer/block/FluidModel;stillMaterial()Lnet/minecraft/client/resources/model/sprite/Material$Baked;"
			),
			@At(
				value = "INVOKE",
				target = "Lnet/minecraft/client/renderer/block/FluidModel;flowingMaterial()Lnet/minecraft/client/resources/model/sprite/Material$Baked;"
			),
			@At(
				value = "INVOKE",
				target = "Lnet/minecraft/client/renderer/block/FluidModel;overlayMaterial()Lnet/minecraft/client/resources/model/sprite/Material$Baked;"
			)
		}
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
		@Share("wilderWild$mesogleaMaterial") LocalRef<Material.Baked> mesogleaMaterial
	) {
		return wilderWild$useMesoglea(mesogleaMaterial)
			? LightCoordsUtil.FULL_BRIGHT
			: original.call(instance, level, pos);
	}

	@WrapOperation(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;getU(F)F",
			ordinal = 1
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/core/Direction$Plane;HORIZONTAL:Lnet/minecraft/core/Direction$Plane;",
				opcode = Opcodes.GETSTATIC
			)
		)
	)
	public float wilderWild$fixSideU1(
		TextureAtlasSprite instance,
		float offset,
		Operation<Float> original,
		@Share("wilderWild$mesogleaMaterial") LocalRef<Material.Baked> mesogleaMaterial
	) {
		return wilderWild$useMesoglea(mesogleaMaterial)
			? original.call(instance, 1F)
			: original.call(instance, offset);
	}

	@WrapOperation(
		method = "tesselate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;getV(F)F",
			ordinal = 2
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/core/Direction$Plane;HORIZONTAL:Lnet/minecraft/core/Direction$Plane;",
				opcode = Opcodes.GETSTATIC
			)
		)
	)
	public float wilderWild$fixSideV1(
		TextureAtlasSprite instance,
		float offset,
		Operation<Float> original,
		@Share("wilderWild$mesogleaMaterial") LocalRef<Material.Baked> mesogleaMaterial
	) {
		return wilderWild$useMesoglea(mesogleaMaterial)
			? original.call(instance, 1F)
			: original.call(instance, offset);
	}
}
