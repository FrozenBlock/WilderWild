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

package net.frozenblock.wilderwild.block.entity;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.Objects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.frozenblock.wilderwild.entity.ai.TermiteManager;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Objects;

public class TermiteMoundBlockEntity extends BlockEntity {

	public final TermiteManager termiteManager;
	public final IntArrayList clientTermiteIDs = new IntArrayList();
	public final IntArrayList prevClientTermiteIDs = new IntArrayList();

	public TermiteMoundBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		super(WWBlockEntityTypes.TERMITE_MOUND, pos, state);
		this.termiteManager = new TermiteManager();
	}

	public void tickServer(@NotNull Level level, @NotNull BlockPos pos, boolean natural, boolean awake, boolean canSpawn) {
		this.termiteManager.tick(level, pos, natural, awake, canSpawn);
		this.updateSync();
	}

	public void updateSync() {
		for (ServerPlayer player : PlayerLookup.tracking(this)) {
			player.connection.send(Objects.requireNonNull(this.getUpdatePacket()));
		}
	}

	public void tickClient() {
		for (TermiteManager.Termite termite : this.termiteManager.termites()) {
			int termiteID = termite.getID();
			if (clientTermiteIDs.contains(termiteID) && !this.prevClientTermiteIDs.contains(termiteID)) {
				addTermiteSound(this, termiteID, termite.getEating());
			}
		}
		this.prevClientTermiteIDs.clear();
		this.prevClientTermiteIDs.addAll(this.clientTermiteIDs);
		this.clientTermiteIDs.clear();
		for (TermiteManager.Termite termite : this.termiteManager.termites()) {
			this.clientTermiteIDs.add(termite.getID());
		}
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@NotNull
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		return this.saveWithoutMetadata(provider);
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.saveAdditional(tag, provider);
		this.termiteManager.saveAdditional(tag);
	}

	@Override
	public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.loadAdditional(tag, provider);
		this.termiteManager.load(tag);
	}

	@Environment(EnvType.CLIENT)
	public static void addTermiteSound(TermiteMoundBlockEntity mound, int termiteID, boolean eating) {
		Minecraft client = Minecraft.getInstance();
		if (client.level != null) {
			client.getSoundManager().play(
				new TermiteSoundInstance<>(
					mound,
					termiteID,
					eating ? WWSounds.BLOCK_TERMITE_MOUND_TERMITE_GNAW : WWSounds.BLOCK_TERMITE_MOUND_TERMITE_IDLE,
					SoundSource.BLOCKS,
					0.2F,
					1F,
					eating
				)
			);
		}
	}

	@Environment(EnvType.CLIENT)
	public static class TermiteSoundInstance<T extends TermiteMoundBlockEntity> extends AbstractTickableSoundInstance {
		private final T mound;
		private final int termiteID;
		private final boolean eating;
		private boolean initialTermiteCheck;

		public TermiteSoundInstance(T mound, int termiteID, SoundEvent sound, SoundSource category, float volume, float pitch, boolean eating) {
			super(sound, category, SoundInstance.createUnseededRandom());
			this.mound = mound;
			this.looping = true;
			this.delay = 0;
			this.volume = volume;
			this.pitch = pitch;
			this.termiteID = termiteID;
			this.eating = eating;
		}

		@Nullable
		public TermiteManager.Termite getTermite() {
			if (this.mound != null && !this.mound.isRemoved()) {
				for (TermiteManager.Termite termite : this.mound.termiteManager.termites()) {
					if (termite.getID() == this.termiteID) {
						return termite;
					}
				}
			}
			return null;
		}

		@Override
		public boolean canStartSilent() {
			return true;
		}

		@Override
		public void tick() {
			TermiteManager.Termite termite = this.getTermite();
			if (termite != null) {
				BlockPos pos = termite.getPos();
				this.x = pos.getX();
				this.y = pos.getY();
				this.z = pos.getZ();
				if (termite.getEating() != this.eating) {
					this.mound.clientTermiteIDs.removeIf((i -> i == this.termiteID));
					this.stop();
				}
			} else {
				if (!this.initialTermiteCheck) {
					this.initialTermiteCheck = true;
				} else {
					this.stop();
				}
			}
		}

		@Override
		public String toString() {
			return "TermiteSoundInstance[" + this.location + "]";
		}
	}
}
