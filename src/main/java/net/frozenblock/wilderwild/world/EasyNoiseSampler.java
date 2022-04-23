package net.frozenblock.wilderwild.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.util.math.random.AtomicSimpleRandom;
import net.minecraft.util.math.random.BlockingSimpleRandom;
import net.minecraft.util.math.random.SimpleRandom;
import net.minecraft.world.gen.random.Xoroshiro128PlusPlusRandom;

public class EasyNoiseSampler {
    public static long seed = 0;
    public static AbstractRandom atomicRandom = new AtomicSimpleRandom(seed);
    public static AbstractRandom blockingRandom = new BlockingSimpleRandom(seed);
    public static AbstractRandom simpleRandom = new SimpleRandom(seed);
    public static Xoroshiro128PlusPlusRandom xoroRandom = new Xoroshiro128PlusPlusRandom(seed);
    public static PerlinNoiseSampler perlinAtomic = new PerlinNoiseSampler(atomicRandom);
    public static PerlinNoiseSampler perlinBlocking = new PerlinNoiseSampler(blockingRandom);
    public static PerlinNoiseSampler perlinSimple = new PerlinNoiseSampler(simpleRandom);
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
            atomicRandom = new AtomicSimpleRandom(seed);
            blockingRandom = new BlockingSimpleRandom(seed);
            simpleRandom = new SimpleRandom(seed);
            xoroRandom = new Xoroshiro128PlusPlusRandom(seed);
            perlinAtomic = new PerlinNoiseSampler(atomicRandom);
            perlinBlocking = new PerlinNoiseSampler(blockingRandom);
            perlinSimple = new PerlinNoiseSampler(simpleRandom);
            perlinXoro = new PerlinNoiseSampler(xoroRandom);
        }
    }

}
