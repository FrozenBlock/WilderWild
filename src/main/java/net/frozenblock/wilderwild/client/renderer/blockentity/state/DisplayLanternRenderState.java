/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.client.renderer.blockentity.state;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColor;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class DisplayLanternRenderState extends BlockEntityRenderState {
	public float age;
	public boolean isHanging;
	public ItemStackRenderState item = new ItemStackRenderState();
	public List<Occupant> occupants;

	public void extractOccupants(@NotNull DisplayLanternBlockEntity displayLantern, float partialTicks) {
		ArrayList<Occupant> occupants = new ArrayList<>();
		displayLantern.getFireflies().forEach(occupant -> {
			if (!occupant.canRender()) return;

			final float age = occupant.age + partialTicks;
			occupants.add(
				new Occupant(
					age,
					((age * Mth.PI) * -4F) / 255F,
					occupant.getColorForRendering(),
					(float) occupant.pos.x,
					(this.isHanging ? 0.38F : 0.225F) + (float) Math.sin(age * 0.03F) * 0.15F,
					(float) occupant.pos.z
				)
			);
		});

		this.occupants = occupants;
	}

	public static class Occupant {
		public final float age;
		public final float calcColor;
		public final FireflyColor color;
		public final float x;
		public final float y;
		public final float z;

		public Occupant(float age, float calcColor, FireflyColor color, float x, float y, float z) {
			this.age = age;
			this.calcColor = calcColor;
			this.color = color;
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
}
