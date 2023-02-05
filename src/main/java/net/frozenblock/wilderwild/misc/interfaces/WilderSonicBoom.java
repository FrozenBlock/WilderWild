package net.frozenblock.wilderwild.misc.interfaces;

import net.minecraft.world.phys.Vec3;

public interface WilderSonicBoom {

	Vec3 wilderWild$particlePos();

	void wilderWild$setParticlePos(Vec3 pos);

	Vec3 wilderWild$vec32();

	void wilderWild$setVec32(Vec3 vec32);

	boolean wilderWild$particlesEnded();

	void wilderWild$endParticles();
}
