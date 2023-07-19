package net.frozenblock.wilderwild.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.api.ClientWindManagerExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import static net.frozenblock.lib.wind.api.ClientWindManager.*;

@Environment(EnvType.CLIENT)
public class WilderClientWindManager implements ClientWindManagerExtension {

	public static double prevCloudX;

	public static double prevCloudY;

	public static double prevCloudZ;

	public static double cloudX;

	public static double cloudY;

	public static double cloudZ;

	@Override
	public void clientTick() {
		prevCloudX = cloudX;
		prevCloudY = cloudY;
		prevCloudZ = cloudZ;

		cloudX += (laggedWindX * 0.007);
		cloudY += (laggedWindY * 0.01);
		cloudZ += (laggedWindZ * 0.007);
	}

	@Override
	public void baseTick() {
	}

	@Override
	public void receiveSyncPacket(FriendlyByteBuf byteBuf, Minecraft minecraft) {
		double cloudX = byteBuf.readDouble();
		double cloudY = byteBuf.readDouble();
		double cloudZ = byteBuf.readDouble();

		minecraft.execute(() -> {
			if (minecraft.level != null) {
				WilderClientWindManager.cloudX = cloudX;
				WilderClientWindManager.cloudY = cloudY;
				WilderClientWindManager.cloudZ = cloudZ;
			}
		});
	}

	public static double getCloudX(float partialTick) {
		return Mth.lerp(partialTick, prevCloudX, cloudX);
	}

	public static double getCloudY(float partialTick) {
		return Mth.lerp(partialTick, prevCloudY, cloudY);
	}

	public static double getCloudZ(float partialTick) {
		return Mth.lerp(partialTick, prevCloudZ, cloudZ);
	}
}
