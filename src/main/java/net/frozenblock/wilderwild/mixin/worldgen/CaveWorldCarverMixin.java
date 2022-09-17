package net.frozenblock.wilderwild.mixin.worldgen;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(CaveWorldCarver.class)
public class CaveWorldCarverMixin extends WorldCarver<CaveCarverConfiguration> {

    public CaveWorldCarverMixin(Codec<CaveCarverConfiguration> configCodec) {
        super(configCodec);
    }

    @Shadow
    protected void createTunnel(CarvingContext carvingContext, CaveCarverConfiguration caveCarverConfiguration, ChunkAccess chunkAccess, Function<BlockPos, Holder<Biome>> function, long l, Aquifer aquifer, double d, double e, double f, double g, double h, float i, float j, float k, int m, int n, double o, CarvingMask carvingMask, WorldCarver.CarveSkipChecker carveSkipChecker) {
    }

    @Inject(method = "createTunnel", at = @At("TAIL"))
    private void createTunnel(CarvingContext context, CaveCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> posToBiome, long seed, Aquifer aquifer, double x, double y, double z, double horizontalScale, double verticalScale, float width, float yaw, float pitch, int branchStartIndex, int branchCount, double yawPitchRatio, CarvingMask carvingMask, WorldCarver.CarveSkipChecker skipPredicate, CallbackInfo ci) {
        RandomSource randomSource = RandomSource.create(seed);
        int i = randomSource.nextInt(branchCount / 2) + branchCount / 4;
        boolean bl = randomSource.nextInt(6) == 0;
        float f = 0.0F;
        float g = 0.0F;

        for (int j = branchStartIndex - 100; j < branchCount; ++j) {
            double d = 1.5 + (double) (Mth.sin((float) Math.PI * (float) j / (float) branchCount) * width);
            double e = d * yawPitchRatio;
            float h = Mth.cos(pitch);
            x += Mth.cos(yaw) * h;
            y += Mth.sin(pitch);
            z += Mth.sin(yaw) * h;
            pitch *= bl ? 0.92F : 0.7F;
            pitch += g * 0.1F;
            yaw += f * 0.1F;
            g *= 0.9F;
            f *= 0.75F;
            g += (randomSource.nextFloat() - randomSource.nextFloat()) * randomSource.nextFloat() * 2.0F;
            f += (randomSource.nextFloat() - randomSource.nextFloat()) * randomSource.nextFloat() * 4.0F;
            for (int moreWater = -64; moreWater < 40; moreWater += 4) {
                if (chunk.getNoiseBiome(moreWater, moreWater, moreWater).is(RegisterWorldgen.JELLYFISH_CAVES)) {
                    this.createTunnel(
                            context,
                            config,
                            chunk,
                            posToBiome,
                            randomSource.nextLong(),
                            aquifer,
                            x,
                            y,
                            z,
                            horizontalScale,
                            verticalScale,
                            randomSource.nextFloat() * 0.5F + 0.5F,
                            yaw - (float) (Math.PI / 2),
                            pitch / 3.0F,
                            j,
                            branchCount,
                            1.0,
                            carvingMask,
                            skipPredicate
                    );
                    this.createTunnel(
                            context,
                            config,
                            chunk,
                            posToBiome,
                            randomSource.nextLong(),
                            aquifer,
                            x,
                            y,
                            z,
                            horizontalScale,
                            verticalScale,
                            randomSource.nextFloat() * 0.5F + 0.5F,
                            yaw + (float) (Math.PI / 2),
                            pitch / 3.0F,
                            j,
                            branchCount,
                            1.0,
                            carvingMask,
                            skipPredicate
                    );
                    return;
                }
            }
        }
    }

    @Shadow
    public boolean carve(@NotNull CarvingContext context, @NotNull CaveCarverConfiguration config, @NotNull ChunkAccess chunk, @NotNull Function<BlockPos, Holder<Biome>> posToBiome, RandomSource random, Aquifer aquifer, ChunkPos pos, CarvingMask carvingMask) {
        return false;
    }

    @Shadow
    public boolean isStartChunk(@NotNull CaveCarverConfiguration config, @NotNull RandomSource random) {
        return false;
    }
}
