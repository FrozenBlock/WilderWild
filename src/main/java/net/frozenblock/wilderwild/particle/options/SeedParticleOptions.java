package net.frozenblock.wilderwild.particle.options;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public class SeedParticleOptions implements ParticleOptions {
	private final boolean isMilkweed;
	private final boolean controlled;

	public static final Codec<SeedParticleOptions> CODEC = RecordCodecBuilder.create((instance) ->
			instance.group(
					Codec.BOOL.fieldOf("is_milkweed").forGetter((particleOptions) -> particleOptions.isMilkweed),
							Codec.BOOL.fieldOf("is_controlled").forGetter((particleOptions) -> particleOptions.controlled)
					)
					.apply(instance, SeedParticleOptions::new)
	);

	public static final Deserializer<SeedParticleOptions> DESERIALIZER = new Deserializer<>() {

		@Override
		public SeedParticleOptions fromCommand(@NotNull ParticleType<SeedParticleOptions> particleType, @NotNull StringReader stringReader) throws CommandSyntaxException {
			boolean milkweed = stringReader.readBoolean();
			boolean controlled = stringReader.readBoolean();
			stringReader.expect(' ');
			return new SeedParticleOptions(milkweed, controlled);
		}

		@Override
		public SeedParticleOptions fromNetwork(@NotNull ParticleType<SeedParticleOptions> particleType, @NotNull FriendlyByteBuf friendlyByteBuf) {
			return new SeedParticleOptions(friendlyByteBuf.readBoolean(), friendlyByteBuf.readBoolean());
		}
	};

	public SeedParticleOptions(boolean isMilkweed, boolean controlled) {
		this.isMilkweed = isMilkweed;
		this.controlled = controlled;
	}

	@Override
	public ParticleType<?> getType() {
		return RegisterParticles.SEED;
	}

	public void writeToNetwork(FriendlyByteBuf buffer) {
		buffer.writeBoolean(this.isMilkweed);
		buffer.writeBoolean(this.controlled);
	}

	public String writeToString() {
		return String.format(Locale.ROOT, "%s %b %b", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.isMilkweed, this.controlled);
	}

	public boolean isMilkweed() {
		return this.isMilkweed;
	}

	public boolean isControlled() {
		return this.controlled;
	}

}
