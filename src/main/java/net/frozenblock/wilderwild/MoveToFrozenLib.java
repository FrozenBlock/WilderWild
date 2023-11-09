package net.frozenblock.wilderwild;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MoveToFrozenLib {

	//TODO: add to that shape utils thing
	public static Optional<Vec3> closestPointTo(BlockPos originalPos, VoxelShape blockShape, Vec3 point) {
		if (blockShape.isEmpty()) {
			return Optional.empty();
		}
		double x = originalPos.getX();
		double y = originalPos.getY();
		double z = originalPos.getZ();
		Vec3[] vec3s = new Vec3[1];
		blockShape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
			double d = Mth.clamp(point.x(), minX + x, maxX + x);
			double e = Mth.clamp(point.y(), minY + y, maxY + y);
			double f = Mth.clamp(point.z(), minZ + z, maxZ + z);
			if (vec3s[0] == null || point.distanceToSqr(d, e, f) < point.distanceToSqr(vec3s[0])) {
				vec3s[0] = new Vec3(d, e, f);
			}
		});
		return Optional.of(vec3s[0]);
	}

}
