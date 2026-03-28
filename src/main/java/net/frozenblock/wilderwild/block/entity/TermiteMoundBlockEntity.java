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

package net.frozenblock.wilderwild.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.termite.TermiteManager;
import net.frozenblock.wilderwild.client.resources.sounds.TermiteEatingSoundInstance;
import net.frozenblock.wilderwild.client.resources.sounds.TermiteIdleSoundInstance;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class TermiteMoundBlockEntity extends BlockEntity {
	private final TermiteManager termiteManager;
	private boolean hadTermite = false;

	public TermiteMoundBlockEntity(BlockPos pos, BlockState state) {
		super(WWBlockEntityTypes.TERMITE_MOUND, pos, state);
		this.termiteManager = new TermiteManager();
	}

	public void tickServer(Level level, BlockPos pos, boolean natural, boolean awake) {
		this.termiteManager.tick(level, pos, natural, awake, this::markForUpdate);
	}

	public void tickClient(Level level) {
		final TermiteManager.Termite termite = this.termiteManager.termite;
		if (termite != null) {
			final boolean eating = termite.getEating();
			final int particleCount = (eating ? TermiteManager.PARTICLE_COUNT_WHILE_EATING : TermiteManager.PARTICLE_COUNT);
			for (int i = 0; i < particleCount; i++) {
				level.addParticle(
					WWParticleTypes.TERMITE,
					termite.pos.getX() + 0.5D, termite.pos.getY() + 0.5D, termite.pos.getZ() + 0.5D,
					0D, 0D, 0D
				);
			}
			if (!this.hadTermite) addTermiteSound(this, eating);
			this.hadTermite = true;
		} else {
			this.hadTermite = false;
		}
	}

	private void markForUpdate() {
		this.setChanged();
		this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
	}

	public TermiteManager termiteManager() {
		return this.termiteManager;
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(this.problemPath(), WWConstants.LOGGER)) {
			final TagValueOutput output = TagValueOutput.createWithContext(reporter, registries);
			this.termiteManager.saveWithoutMetadata(output);
			return output.buildResult();
		}
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		this.termiteManager.save(output);
	}

	@Override
	public void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		this.termiteManager.load(input);
	}

	@Environment(EnvType.CLIENT)
	public static void addTermiteSound(TermiteMoundBlockEntity mound, boolean eating) {
		final Minecraft client = Minecraft.getInstance();
		if (client.level == null) return;
		if (eating) {
			client.getSoundManager().play(new TermiteEatingSoundInstance<>(mound));
		} else {
			client.getSoundManager().play(new TermiteIdleSoundInstance<>(mound));
		}
	}
}
