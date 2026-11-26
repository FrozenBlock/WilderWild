/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.object.projectile.HarpoonModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.HarpoonRenderState;
import net.frozenblock.wilderwild.entity.projectile.arrow.Harpoon;
import net.frozenblock.wilderwild.mixin.client.item.harpoon.EntityRendererAccessor;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

@ClientOnly
public class HarpoonRenderer extends ArrowRenderer<Harpoon, HarpoonRenderState> {
	private static final Vec3 LEASH_ATTACH_OFFSET = new Vec3(0D, 0.2D, 0D);
	public static final Identifier HARPOON_LOCATION = WWConstants.id("textures/entity/projectiles/harpoon.png");
	private final ArrowModel defaultModel;
	private final ArrowModel foilModel;

	public HarpoonRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.defaultModel = new HarpoonModel(context.bakeLayer(WWModelLayers.HARPOON));
		this.foilModel = new HarpoonModel(context.bakeLayer(WWModelLayers.HARPOON_FOIL));
		this.model = this.defaultModel;
	}

	@Override
	protected Identifier getTextureLocation(HarpoonRenderState state) {
		return HARPOON_LOCATION;
	}

	public static @Nullable HumanoidArm getHoldingArm(@Nullable Harpoon harpoon, LivingEntity owner) {
		return Harpoon.isEmptyCrossbow(owner.getMainHandItem())
			? owner.getMainArm()
			: Harpoon.isEmptyCrossbow(owner.getOffhandItem())
				? owner.getMainArm().getOpposite()
				: harpoon != null && harpoon.getPreventRopeSnap()
					? owner.getMainArm()
					: null;
	}

	@Override
	public HarpoonRenderState createRenderState() {
		return new HarpoonRenderState();
	}

	@Override
	public boolean shouldRender(Harpoon harpoon, Frustum culler, double camX, double camY, double camZ, float partialTicks) {
		if (harpoon.getAttachedTo() == Minecraft.getInstance().player) return true;
		return super.shouldRender(harpoon, culler, camX, camY, camZ, partialTicks);
	}

	@Override
	protected AABB getBoundingBoxForCulling(Harpoon harpoon, float partialTicks) {
		AABB boundingBox = super.getBoundingBoxForCulling(harpoon, partialTicks);

		final Entity hookedIn = harpoon.getHookedIn();
		if (hookedIn != null) boundingBox = boundingBox.minmax(hookedIn.getInterpolatedBoundingBox(partialTicks));

		final Entity attachedTo = harpoon.getAttachedTo();
		if (attachedTo != null) boundingBox = boundingBox.minmax(attachedTo.getInterpolatedBoundingBox(partialTicks));

		return boundingBox;
	}

	@Override
	public void submit(HarpoonRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
		this.model = state.isFoil ? this.foilModel : this.defaultModel;
		super.submit(state, poseStack, submitNodeCollector, camera);
	}

	@Override
	public void extractRenderState(Harpoon harpoon, HarpoonRenderState state, float partialTicks) {
		super.extractRenderState(harpoon, state, partialTicks);
		state.isFoil = harpoon.isFoil();

		final Entity attachedTo = harpoon.getAttachedTo();
		if (attachedTo == null) {
			state.hookedInEntity = false;
			state.leashStates = null;
			return;
		}

		state.hookedInEntity = harpoon.getHookedIn() != null;

		if (attachedTo instanceof LivingEntity livingOwner) {
			final HumanoidArm holdingArm = getHoldingArm(harpoon, livingOwner);
			if (holdingArm == null) {
				state.leashStates = null;
				return;
			}

			final EntityRenderState.LeashState leashState;
			if (state.leashStates == null || state.leashStates.size() != 1) {
				leashState = new EntityRenderState.LeashState();
				state.leashStates = List.of(leashState);
			} else {
				leashState = state.leashStates.getFirst();
			}

			leashState.offset = LEASH_ATTACH_OFFSET;
			leashState.start = harpoon.getPosition(partialTicks).add(LEASH_ATTACH_OFFSET);
			leashState.end = attachedTo.getRopeHoldPosition(partialTicks);

			// TODO: Glowtone compat
			final Level level = harpoon.level();
			final BlockPos harpoonEyePos = BlockPos.containing(harpoon.getEyePosition(partialTicks));
			final BlockPos attachedToEyePos = BlockPos.containing(attachedTo.getEyePosition(partialTicks));
			final int startBlockLight = this.getBlockLightLevel(harpoon, harpoonEyePos);
			final int endBlockLight = ((EntityRendererAccessor)this.entityRenderDispatcher.getRenderer(attachedTo)).wilderWild$getBlockLightLevel(attachedTo, attachedToEyePos);
			final int startSkyLight = level.getBrightness(LightLayer.SKY, harpoonEyePos);
			final int endSkyLight = level.getBrightness(LightLayer.SKY, attachedToEyePos);
			leashState.startBlockLight = startBlockLight;
			leashState.endBlockLight = endBlockLight;
			leashState.startSkyLight = startSkyLight;
			leashState.endSkyLight = endSkyLight;
		}
	}
}
