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

public final class BushStateFix extends DataFix {
	private static final String STATE = "age";
	private static final String DEFAULT_VALUE = "0";

	private final String name;
	private final String blockId;

	public BushStateFix(Schema outputSchema, String name, Identifier blockId) {
		this(outputSchema, name, blockId.toString());
	}

	private BushStateFix(Schema outputSchema, String name, String blockId) {
		super(outputSchema, false);
		this.name = name;
		this.blockId = blockId;
	}

	private Dynamic<?> fix(Dynamic<?> dynamic) {
		final Optional<String> optionalName = dynamic.get("Name").asString().result();
		return optionalName.equals(Optional.of(this.blockId)) ? dynamic.update("Properties", dynamicx -> {
			final String string = dynamicx.get(STATE).asString(DEFAULT_VALUE);
			final String fixedString = string.equals("2") ? "3" : string;
			return dynamicx.remove(STATE).set(STATE, dynamicx.createString(fixedString));
		}) : dynamic;
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
			this.name, this.getInputSchema().getType(References.BLOCK_STATE), typed -> typed.update(DSL.remainderFinder(), this::fix)
		);
	}
}
