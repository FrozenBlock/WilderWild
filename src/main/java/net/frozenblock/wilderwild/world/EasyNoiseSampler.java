package net.frozenblock.wilderwild.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.*;

public class EasyNoiseSampler {
    public static long seed = 0;
    public static Random checkedRandom = new CheckedRandom(seed);
    public static Random threadSafeRandom = new ThreadSafeRandom(seed);
    public static Random localRandom = new LocalRandom(seed);
    public static Xoroshiro128PlusPlusRandom xoroRandom = new Xoroshiro128PlusPlusRandom(seed);
    public static PerlinNoiseSampler perlinAtomic = new PerlinNoiseSampler(checkedRandom);
    public static PerlinNoiseSampler perlinBlocking = new PerlinNoiseSampler(threadSafeRandom);
    public static PerlinNoiseSampler perlinSimple = new PerlinNoiseSampler(localRandom);
    public static PerlinNoiseSampler perlinXoro = new PerlinNoiseSampler(xoroRandom);

    public static double samplePerlinAtomic(BlockPos pos, double multiplier, boolean multiplyY, boolean useY) {
        if (useY) {
            if (multiplyY) {
                return perlinAtomic.sample(pos.getX() * multiplier, pos.getY()*multiplier, pos.getZ()*multiplier);
            } return perlinAtomic.sample(pos.getX() * multiplier, pos.getY(), pos.getZ()*multiplier);
        } return perlinAtomic.sample(pos.getX() * multiplier, 64, pos.getZ()*multiplier);
    }

    public static double samplePerlinBlocking(BlockPos pos, double multiplier, boolean multiplyY, boolean useY) {
        if (useY) {
            if (multiplyY) {
                return perlinBlocking.sample(pos.getX() * multiplier, pos.getY()*multiplier, pos.getZ()*multiplier);
            } return perlinBlocking.sample(pos.getX() * multiplier, pos.getY(), pos.getZ()*multiplier);
        } return perlinBlocking.sample(pos.getX() * multiplier, 64, pos.getZ()*multiplier);
    }

    public static double samplePerlinSimple(BlockPos pos, double multiplier, boolean multiplyY, boolean useY) {
        if (useY) {
            if (multiplyY) {
                return perlinSimple.sample(pos.getX() * multiplier, pos.getY()*multiplier, pos.getZ()*multiplier);
            } return perlinSimple.sample(pos.getX() * multiplier, pos.getY(), pos.getZ()*multiplier);
        } return perlinSimple.sample(pos.getX() * multiplier, 64, pos.getZ()*multiplier);
    }

    public static double samplePerlinXoro(BlockPos pos, double multiplier, boolean multiplyY, boolean useY) {
        if (useY) {
            if (multiplyY) {
                return perlinXoro.sample(pos.getX() * multiplier, pos.getY()*multiplier, pos.getZ()*multiplier);
            } return perlinXoro.sample(pos.getX() * multiplier, pos.getY(), pos.getZ()*multiplier);
        } return perlinXoro.sample(pos.getX() * multiplier, 64, pos.getZ()*multiplier);
    }

    public static double samplePerlinAtomicPositive(BlockPos pos, double multiplier, boolean multiplyY, boolean useY) {
        double ret = 0;
        if (useY) {
            if (multiplyY) {
                ret = perlinAtomic.sample(pos.getX() * multiplier, pos.getY()*multiplier, pos.getZ()*multiplier);
            } ret = perlinAtomic.sample(pos.getX() * multiplier, pos.getY(), pos.getZ()*multiplier);
        } ret = perlinAtomic.sample(pos.getX() * multiplier, 64, pos.getZ()*multiplier);
        if (ret<0) { return ret*-1; }
        return ret;
    }

    public static double samplePerlinBlockingPositive(BlockPos pos, double multiplier, boolean multiplyY, boolean useY) {
        double ret = 0;
        if (useY) {
            if (multiplyY) {
                ret = perlinBlocking.sample(pos.getX() * multiplier, pos.getY()*multiplier, pos.getZ()*multiplier);
            } ret = perlinBlocking.sample(pos.getX() * multiplier, pos.getY(), pos.getZ()*multiplier);
        } ret = perlinBlocking.sample(pos.getX() * multiplier, 64, pos.getZ()*multiplier);
        if (ret<0) { return ret*-1; }
        return ret;
    }

    public static double samplePerlinSimplePositive(BlockPos pos, double multiplier, boolean multiplyY, boolean useY) {
        double ret = 0;
        if (useY) {
            if (multiplyY) {
                ret = perlinSimple.sample(pos.getX() * multiplier, pos.getY()*multiplier, pos.getZ()*multiplier);
            } ret = perlinSimple.sample(pos.getX() * multiplier, pos.getY(), pos.getZ()*multiplier);
        } ret = perlinSimple.sample(pos.getX() * multiplier, 64, pos.getZ()*multiplier);
        if (ret<0) { return ret*-1; }
        return ret;
    }

    public static double samplePerlinXoroPositive(BlockPos pos, double multiplier, boolean multiplyY, boolean useY) {
        double ret = 0;
        if (useY) {
            if (multiplyY) {
                ret = perlinXoro.sample(pos.getX() * multiplier, pos.getY()*multiplier, pos.getZ()*multiplier);
            } ret = perlinXoro.sample(pos.getX() * multiplier, pos.getY(), pos.getZ()*multiplier);
        } ret = perlinXoro.sample(pos.getX() * multiplier, 64, pos.getZ()*multiplier);
        if (ret<0) { return ret*-1; }
        return ret;
    }

    public static void setSeed(long newSeed) {
        if (newSeed!=seed) {
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
