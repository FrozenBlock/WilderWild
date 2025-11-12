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
import net.minecraft.resources.Identifier;
import net.minecraft.util.datafix.fixes.References;
import org.jetbrains.annotations.NotNull;

public final class ScorchedSandStateFix2 extends DataFix {
	private static final String STATE = "crackedness";
	private static final String NEW_STATE = "cracked";
	private static final String DEFAULT_VALUE = "false";

	private final String name;
	private final String blockId;

	public ScorchedSandStateFix2(Schema outputSchema, String name, @NotNull Identifier blockId) {
		this(outputSchema, name, blockId.toString());
	}

	private ScorchedSandStateFix2(Schema outputSchema, String name, String blockId) {
		super(outputSchema, false);
		this.name = name;
		this.blockId = blockId;
	}

	private Dynamic<?> fix(@NotNull Dynamic<?> dynamic) {
		final Optional<String> name = dynamic.get("Name").asString().result();
		return name.equals(Optional.of(this.blockId)) ? dynamic.update("Properties", dynamicx -> {
			final String string = dynamicx.get(STATE).asString(DEFAULT_VALUE);
			final String boolValue = string.equals("1") ? "true" : "false";
			return dynamicx.remove(STATE).set(NEW_STATE, dynamicx.createString(boolValue));
		}) : dynamic;
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
			this.name, this.getInputSchema().getType(References.BLOCK_STATE), typed -> typed.update(DSL.remainderFinder(), this::fix)
		);
	}
}
