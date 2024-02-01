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

package net.frozenblock.wilderwild.particle.options;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class SeedParticleOptions implements ParticleOptions {
	public static final Codec<SeedParticleOptions> CODEC = RecordCodecBuilder.create((instance) ->
		instance.group(
				Codec.BOOL.fieldOf("isMilkweed").forGetter(SeedParticleOptions::isMilkweed),
				Codec.BOOL.fieldOf("isControlled").forGetter(SeedParticleOptions::isControlled),
				Codec.FLOAT.fieldOf("xSpeed").forGetter(seedParticleOptions -> seedParticleOptions.getSpeed().x),
				Codec.FLOAT.fieldOf("ySpeed").forGetter(seedParticleOptions -> seedParticleOptions.getSpeed().y),
				Codec.FLOAT.fieldOf("zSpeed").forGetter(seedParticleOptions -> seedParticleOptions.getSpeed().z)
			)
			.apply(instance, SeedParticleOptions::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, SeedParticleOptions> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.BOOL, SeedParticleOptions::isMilkweed,
		ByteBufCodecs.BOOL, SeedParticleOptions::isControlled,
		ByteBufCodecs.VECTOR3F, SeedParticleOptions::getSpeed,
		SeedParticleOptions::new
	);
	public static final Deserializer<SeedParticleOptions> DESERIALIZER = new Deserializer<>() {
        @Override
        @NotNull
        public SeedParticleOptions fromCommand(ParticleType<SeedParticleOptions> type, @NotNull StringReader reader, HolderLookup.Provider provider) throws CommandSyntaxException {
            boolean milkweed = reader.readBoolean();
            boolean controlled = reader.readBoolean();
            Vector3f speed = DustParticleOptions.readVector3f(reader);
			reader.expect(' ');
            return new SeedParticleOptions(milkweed, controlled, speed);
        }
    };

	private final boolean isMilkweed;
	private final boolean controlled;
	private final Vector3f speed;

	@NotNull
	@Contract(value = "_, _, _, _ -> new", pure = true)
	public static SeedParticleOptions controlled(boolean isMilkweed, float xSpeed, float ySpeed, float zSpeed) {
		return new SeedParticleOptions(isMilkweed, true, xSpeed, ySpeed, zSpeed);
	}

	@NotNull
	@Contract(value = "_ -> new", pure = true)
	public static SeedParticleOptions unControlled(boolean isMilkweed) {
		return new SeedParticleOptions(isMilkweed, false, 0F, 0F, 0F);
	}

	private SeedParticleOptions(boolean isMilkweed, boolean controlled, float xSpeed, float ySpeed, float zSpeed) {
		this(isMilkweed, controlled, new Vector3f(xSpeed, ySpeed, zSpeed));
	}

	private SeedParticleOptions(boolean isMilkweed, boolean controlled, Vector3f speed) {
		this.isMilkweed = isMilkweed;
		this.controlled = controlled;
		this.speed = speed;
	}

	@NotNull
	@Override
	public ParticleType<?> getType() {
		return RegisterParticles.SEED;
	}

	@NotNull
	@Override
	public String writeToString(HolderLookup.Provider provider) {
		return String.format(Locale.ROOT, "%s %b %b", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.isMilkweed, this.controlled);
	}

	public boolean isMilkweed() {
		return this.isMilkweed;
	}

	public boolean isControlled() {
		return this.controlled;
	}

	public Vector3f getSpeed() {
		return this.speed;
	}

}
