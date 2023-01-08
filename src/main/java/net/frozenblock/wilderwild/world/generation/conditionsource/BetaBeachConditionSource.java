package net.frozenblock.wilderwild.world.generation.conditionsource;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;

public final class BetaBeachConditionSource implements SurfaceRules.ConditionSource {
	public static final KeyDispatchDataCodec<BetaBeachConditionSource> CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec((instance) ->
			instance.group(
							Codec.INT
							.fieldOf("useless")
							.forGetter(BetaBeachConditionSource::useless))
					.apply(instance, BetaBeachConditionSource::new)
			)
	);

	public int useless;

	public static BetaBeachConditionSource betaBeachConditionSource(int useless) {
		return new BetaBeachConditionSource(useless);
	}

	BetaBeachConditionSource(int useless) {
		this.useless = useless;
	}

	public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
		return CODEC;
	}

	public SurfaceRules.Condition apply(SurfaceRules.Context context) {
		class BetaBeachConditionSource extends SurfaceRules.LazyYCondition {
			BetaBeachConditionSource(SurfaceRules.Context context) {
				super(context);
			}

			protected boolean compute() {
				return ClothConfigInteractionHandler.betaBeaches();
			}
		}

		return new BetaBeachConditionSource(context);
	}

	public boolean equals(Object object) {
		return this == object;
	}

	public String toString() {
		return "BiomeConditionSource[betaBeach]";
	}

	public static int useless(Object o) {
		return 0;
	}
}
