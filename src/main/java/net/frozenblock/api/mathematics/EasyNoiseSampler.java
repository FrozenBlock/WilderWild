package net.frozenblock.api.mathematics;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
import net.minecraft.world.level.levelgen.ThreadSafeLegacyRandomSource;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;

public final class EasyNoiseSampler {

    /**
     * EASY NOISE SAMPLER
     * <p>
     * Adds easy-to-use noise sampling and random number generators
     * <p>
     * Only for FrozenBlock Modders and Lunade, ALL RIGHTS RESERVED
     * <p>
     *
     * @author Lunade (2021-2022)
     */

    public static long seed = 0;
    public static RandomSource checkedRandom = new LegacyRandomSource(seed);
    public static RandomSource threadSafeRandom = new ThreadSafeLegacyRandomSource(seed);
    public static RandomSource localRandom = new SingleThreadedRandomSource(seed);
    public static XoroshiroRandomSource xoroRandom = new XoroshiroRandomSource(seed);
    public static ImprovedNoise perlinAtomic = new ImprovedNoise(checkedRandom);
    public static ImprovedNoise perlinBlocking = new ImprovedNoise(threadSafeRandom);
    public static ImprovedNoise perlinSimple = new ImprovedNoise(localRandom);
    public static ImprovedNoise perlinXoro = new ImprovedNoise(xoroRandom);

    public static double sample(ImprovedNoise sampler, BlockPos pos, double multiplier, boolean multiplyY, boolean useY) {
        if (useY) {
            if (multiplyY) {
                return sampler.noise(pos.getX() * multiplier, pos.getY() * multiplier, pos.getZ() * multiplier);
            }
            return sampler.noise(pos.getX() * multiplier, pos.getY(), pos.getZ() * multiplier);
        }
        return sampler.noise(pos.getX() * multiplier, 64, pos.getZ() * multiplier);
    }

    public static double samplePositive(ImprovedNoise sampler, BlockPos pos, double multiplier, boolean multiplyY, boolean useY) {
        double ret = 0;
        if (useY) {
            if (multiplyY) {
                ret = sampler.noise(pos.getX() * multiplier, pos.getY() * multiplier, pos.getZ() * multiplier);
            } else {
                ret = sampler.noise(pos.getX() * multiplier, pos.getY(), pos.getZ() * multiplier);
            }
        } else {
            ret = sampler.noise(pos.getX() * multiplier, 64, pos.getZ() * multiplier);
        }
        if (ret < 0) {
            return ret * -1;
        }
        return ret;
    }

    public static void setSeed(long newSeed) {
        if (newSeed != seed) {
            seed = newSeed;
            checkedRandom = new LegacyRandomSource(seed);
            threadSafeRandom = new ThreadSafeLegacyRandomSource(seed);
            localRandom = new SingleThreadedRandomSource(seed);
            xoroRandom = new XoroshiroRandomSource(seed);
            perlinAtomic = new ImprovedNoise(checkedRandom);
            perlinBlocking = new ImprovedNoise(threadSafeRandom);
            perlinSimple = new ImprovedNoise(localRandom);
            perlinXoro = new ImprovedNoise(xoroRandom);
        }
    }

}
