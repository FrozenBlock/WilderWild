package net.frozenblock.wilderwild.misc.interfaces;

import net.minecraft.world.phys.Vec3;

public interface WilderSonicBoom {

	Vec3 particlePos();

	void setParticlePos(Vec3 pos);

	Vec3 vec32();

	void setVec32(Vec3 vec32);

	boolean particlesEnded();

	void endParticles();
}
