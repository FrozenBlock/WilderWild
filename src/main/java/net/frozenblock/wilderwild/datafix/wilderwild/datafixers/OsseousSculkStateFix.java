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

public final class OsseousSculkStateFix extends DataFix {
	private static final String OLD_STATE = "axis";
	private static final String NEW_STATE = "facing";
	private static final String DEFAULT_VALUE = "y";
	private static final String UPSIDE_DOWN_STATE = "upside_down";
	private final String name;
	private final String blockId;

	public OsseousSculkStateFix(Schema outputSchema, String name, @NotNull ResourceLocation blockId) {
		this(outputSchema, name, blockId.toString());
	}

	private OsseousSculkStateFix(Schema outputSchema, String name, String blockId) {
		super(outputSchema, false);
		this.name = name;
		this.blockId = blockId;
	}

	private Dynamic<?> fix(@NotNull Dynamic<?> dynamic) {
		Optional<String> optional = dynamic.get("Name").asString().result();
		return optional.equals(Optional.of(this.blockId)) ? dynamic.update("Properties", dynamicx -> {
			String string = dynamicx.get(OLD_STATE).asString(DEFAULT_VALUE);
			String direction;
			switch (string) {
				case "x" -> direction = "west";
				case "y" -> direction = dynamicx.get(UPSIDE_DOWN_STATE).asBoolean(true) ? "down" : "up";
				case "z" -> direction = "north";
				default -> direction = "down";
			}
			return dynamicx.remove(OLD_STATE).set(NEW_STATE, dynamicx.createString(direction));
		}) : dynamic;
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
			this.name, this.getInputSchema().getType(References.BLOCK_STATE), typed -> typed.update(DSL.remainderFinder(), this::fix)
		);
	}
}
