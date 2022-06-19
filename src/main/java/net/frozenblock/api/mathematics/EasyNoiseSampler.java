package net.frozenblock.api.mathematics;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.*;

public class EasyNoiseSampler {

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
    public static Random checkedRandom = new CheckedRandom(seed);
    public static Random threadSafeRandom = new ThreadSafeRandom(seed);
    public static Random localRandom = new LocalRandom(seed);
    public static Xoroshiro128PlusPlusRandom xoroRandom = new Xoroshiro128PlusPlusRandom(seed);
    public static PerlinNoiseSampler perlinAtomic = new PerlinNoiseSampler(checkedRandom);
    public static PerlinNoiseSampler perlinBlocking = new PerlinNoiseSampler(threadSafeRandom);
    public static PerlinNoiseSampler perlinSimple = new PerlinNoiseSampler(localRandom);
    public static PerlinNoiseSampler perlinXoro = new PerlinNoiseSampler(xoroRandom);

    public static double sample(PerlinNoiseSampler sampler, BlockPos pos, double multiplier, boolean multiplyY, boolean useY) {
        if (useY) {
            if (multiplyY) {
                return sampler.sample(pos.getX() * multiplier, pos.getY() * multiplier, pos.getZ() * multiplier);
            }
            return sampler.sample(pos.getX() * multiplier, pos.getY(), pos.getZ() * multiplier);
        }
        return sampler.sample(pos.getX() * multiplier, 64, pos.getZ() * multiplier);
    }

    public static double samplePositive(PerlinNoiseSampler sampler, BlockPos pos, double multiplier, boolean multiplyY, boolean useY) {
        double ret = 0;
        if (useY) {
            if (multiplyY) {
                ret = sampler.sample(pos.getX() * multiplier, pos.getY() * multiplier, pos.getZ() * multiplier);
            } else {
                ret = sampler.sample(pos.getX() * multiplier, pos.getY(), pos.getZ() * multiplier);
            }
        } else {
            ret = sampler.sample(pos.getX() * multiplier, 64, pos.getZ() * multiplier);
        }
        if (ret < 0) {
            return ret * -1;
        }
        return ret;
    }

    public static void setSeed(long newSeed) {
        if (newSeed != seed) {
            seed = newSeed;
            checkedRandom = new CheckedRandom(seed);
            threadSafeRandom = new ThreadSafeRandom(seed);
            localRandom = new LocalRandom(seed);
            xoroRandom = new Xoroshiro128PlusPlusRandom(seed);
            perlinAtomic = new PerlinNoiseSampler(checkedRandom);
            perlinBlocking = new PerlinNoiseSampler(threadSafeRandom);
            perlinSimple = new PerlinNoiseSampler(localRandom);
            perlinXoro = new PerlinNoiseSampler(xoroRandom);
        }
    }

}
