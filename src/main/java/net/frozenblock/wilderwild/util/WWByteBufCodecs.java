package net.frozenblock.wilderwild.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;

public class WWByteBufCodecs {

	public static final StreamCodec<FriendlyByteBuf, Vec3> VEC3 = new StreamCodec<FriendlyByteBuf, Vec3>() {
		@Override
		public Vec3 decode(FriendlyByteBuf buf) {
			return buf.readVec3();
		}

		@Override
		public void encode(FriendlyByteBuf buf, Vec3 vec) {
			buf.writeVec3(vec);
		}
	};
}
