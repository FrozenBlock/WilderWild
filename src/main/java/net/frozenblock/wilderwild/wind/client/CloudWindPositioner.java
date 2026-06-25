package net.frozenblock.wilderwild.wind.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public sealed interface CloudWindPositioner permits CloudWindPositioner.Pass, CloudWindPositioner.Success {
	CloudWindPositioner PASS = new Pass();

	record Pass() implements CloudWindPositioner {}

	record Success(double cloudX, double cloudZ) implements CloudWindPositioner {
		public double modifyCloudX(double cameraX) {
			return cameraX - (this.cloudX * 18D);
		}

		public double modifyCloudZ(double cameraZ) {
			return cameraZ - (this.cloudZ * 18D);
		}
	}
}
