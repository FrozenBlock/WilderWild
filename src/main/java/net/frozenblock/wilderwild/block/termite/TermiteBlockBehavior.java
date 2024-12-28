/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.block.termite;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import java.util.Objects;
import java.util.Optional;

public final class TermiteBlockBehavior {
	public static final Codec<TermiteBlockBehavior> DIRECT_CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
			RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("edible_blocks").forGetter(TermiteBlockBehavior::getEdibleBlocks),
			BuiltInRegistries.BLOCK.byNameCodec().lenientOptionalFieldOf("output_block").forGetter(TermiteBlockBehavior::getOutputBlock),
			Codec.BOOL.optionalFieldOf("copy_blockstate_properties", false).forGetter(TermiteBlockBehavior::copyProperties),
			Codec.BOOL.fieldOf("natural_termite_usable").forGetter(TermiteBlockBehavior::naturalTermite),
			BuiltInRegistries.SOUND_EVENT.byNameCodec().lenientOptionalFieldOf("eat_sound").forGetter(TermiteBlockBehavior::getEatSound)
		).apply(instance, TermiteBlockBehavior::new)
	);

	private final HolderSet<Block> edibleBlocks;
	private final Optional<Block> outputBlock;
	private final boolean copyProperties;
	private final boolean naturalTermite;
	private final Optional<SoundEvent> eatSound;

	public TermiteBlockBehavior(HolderSet<Block> edibleBlocks, Optional<Block> outputBlock, boolean copyProperties, boolean naturalTermite, Optional<SoundEvent> eatSound) {
		this.edibleBlocks = edibleBlocks;
		this.outputBlock = outputBlock;
		this.copyProperties = copyProperties;
		this.naturalTermite = naturalTermite;
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

	public boolean naturalTermite() {
		return this.naturalTermite;
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
				&& Objects.equals(this.naturalTermite, termiteBlockBehavior.naturalTermite)
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
		i = 31 * i + Boolean.hashCode(this.naturalTermite);
		return 31 * i + this.eatSound.hashCode();
	}
}
