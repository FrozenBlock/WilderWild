/*
 * Copyright 2025 FrozenBlock
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

import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.termite.TermiteManager;
import net.frozenblock.wilderwild.client.resources.sounds.TermiteEatingSoundInstance;
import net.frozenblock.wilderwild.client.resources.sounds.TermiteIdleSoundInstance;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TermiteMoundBlockEntity extends BlockEntity {
	public final TermiteManager termiteManager;
	public final IntArrayList clientTermiteIDs = new IntArrayList();
	public final IntArrayList prevClientTermiteIDs = new IntArrayList();

	public TermiteMoundBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		super(WWBlockEntityTypes.TERMITE_MOUND, pos, state);
		this.termiteManager = new TermiteManager();
	}

	public void tickServer(@NotNull Level level, @NotNull BlockPos pos, boolean natural, boolean awake, boolean canSpawn) {
		this.termiteManager.tick(level, pos, natural, awake, canSpawn, this::markForUpdate);
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

	private void markForUpdate() {
		this.setChanged();
		this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
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
		if (client.level == null) return;
		if (eating) {
			client.getSoundManager().play(new TermiteEatingSoundInstance<>(mound, termiteID));
		} else {
			client.getSoundManager().play(new TermiteIdleSoundInstance<>(mound, termiteID));
		}
	}
}
