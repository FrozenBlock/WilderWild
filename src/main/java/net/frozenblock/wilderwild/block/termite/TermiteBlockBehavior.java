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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.block.termite;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;

public final class TermiteBlockBehavior {
	public static final Codec<TermiteBlockBehavior> DIRECT_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("edible_blocks").forGetter(TermiteBlockBehavior::getEdibleBlocks),
			BuiltInRegistries.BLOCK.byNameCodec().lenientOptionalFieldOf("output_block").forGetter(TermiteBlockBehavior::getOutputBlock),
			Codec.BOOL.optionalFieldOf("copy_blockstate_properties", false).forGetter(TermiteBlockBehavior::copyProperties),
			Codec.BOOL.fieldOf("natural_termite_usable").forGetter(TermiteBlockBehavior::naturalTermiteUsable),
			Codec.BOOL.fieldOf("player_placed_termite_usable").forGetter(TermiteBlockBehavior::playerPlacedTermiteUsable),
			BuiltInRegistries.SOUND_EVENT.byNameCodec().lenientOptionalFieldOf("eat_sound").forGetter(TermiteBlockBehavior::getEatSound)
		).apply(instance, TermiteBlockBehavior::new)
	);

	private final HolderSet<Block> edibleBlocks;
	private final Optional<Block> outputBlock;
	private final boolean copyProperties;
	private final boolean naturalTermiteUsable;
	private final boolean playerPlacedTermiteUsable;
	private final Optional<SoundEvent> eatSound;

	public TermiteBlockBehavior(
		HolderSet<Block> edibleBlocks,
		Optional<Block> outputBlock,
		boolean copyProperties,
		boolean naturalTermiteUsable,
		boolean playerPlacedTermiteUsable,
		Optional<SoundEvent> eatSound
	) {
		this.edibleBlocks = edibleBlocks;
		this.outputBlock = outputBlock;
		this.copyProperties = copyProperties;
		this.naturalTermiteUsable = naturalTermiteUsable;
		this.playerPlacedTermiteUsable = playerPlacedTermiteUsable;
		this.eatSound = eatSound;
	}

	public HolderSet<Block> getEdibleBlocks() {
		return this.edibleBlocks;
	}

	public Optional<Block> getOutputBlock() {
		return this.outputBlock;
	}

	public boolean copyProperties() {
		return this.copyProperties;
	}

	public boolean destroysBlock() {
		return this.outputBlock.isEmpty();
	}

	public boolean naturalTermiteUsable() {
		return this.naturalTermiteUsable;
	}

	public boolean playerPlacedTermiteUsable() {
		return this.playerPlacedTermiteUsable;
	}

	public Optional<SoundEvent> getEatSound() {
		return this.eatSound;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		} else {
			return object instanceof TermiteBlockBehavior termiteBlockBehavior && Objects.equals(this.edibleBlocks, termiteBlockBehavior.getEdibleBlocks())
				&& Objects.equals(this.outputBlock, termiteBlockBehavior.getOutputBlock())
				&& Objects.equals(this.copyProperties, termiteBlockBehavior.copyProperties())
				&& Objects.equals(this.destroysBlock(), termiteBlockBehavior.destroysBlock())
				&& Objects.equals(this.naturalTermiteUsable, termiteBlockBehavior.naturalTermiteUsable())
				&& Objects.equals(this.playerPlacedTermiteUsable, termiteBlockBehavior.playerPlacedTermiteUsable())
				&& Objects.equals(this.eatSound, termiteBlockBehavior.getEatSound());
		}
	}

	@Override
	public int hashCode() {
		int i = 1;
		i = 31 * i + this.edibleBlocks.hashCode();
		i = 31 * i + this.outputBlock.hashCode();
		i = 31 * i + Boolean.hashCode(this.copyProperties);
		i = 31 * i + Boolean.hashCode(this.destroysBlock());
		i = 31 * i + Boolean.hashCode(this.naturalTermiteUsable);
		i = 31 * i + Boolean.hashCode(this.playerPlacedTermiteUsable);
		return 31 * i + this.eatSound.hashCode();
	}
}
