/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.datafix.wilderwild.datafixers;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.References;
import org.jetbrains.annotations.NotNull;

public final class NematocystStateFix extends DataFix {
	private static final String OLD_STATE = "facing";
	private static final String DEFAULT_VALUE = "up";
	private final String name;
	private final String blockId;

	public NematocystStateFix(Schema outputSchema, String name, @NotNull ResourceLocation blockId) {
		this(outputSchema, name, blockId.toString());
	}

	private NematocystStateFix(Schema outputSchema, String name, String blockId) {
		super(outputSchema, false);
		this.name = name;
		this.blockId = blockId;
	}

	private Dynamic<?> fix(@NotNull Dynamic<?> dynamic) {
		Optional<String> optional = dynamic.get("Name").asString().result();
		return optional.equals(Optional.of(this.blockId)) ? dynamic.update("Properties", dynamicx -> {
			String string = dynamicx.get(OLD_STATE).asString(DEFAULT_VALUE);
			String trueDirection;
			switch (string) {
				case "down" -> trueDirection = "up";
				case "north" -> trueDirection = "south";
				case "south" -> trueDirection = "north";
				case "east" -> trueDirection = "west";
				case "west" -> trueDirection = "east";
				default -> trueDirection = "down";
			}
			return dynamicx.remove(OLD_STATE).set(trueDirection, dynamicx.createString("true"));
		}) : dynamic;
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
			this.name, this.getInputSchema().getType(References.BLOCK_STATE), typed -> typed.update(DSL.remainderFinder(), this::fix)
		);
	}
}
