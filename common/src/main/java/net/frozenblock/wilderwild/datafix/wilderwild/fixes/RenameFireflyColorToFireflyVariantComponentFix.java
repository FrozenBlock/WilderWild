/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.datafix.wilderwild.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.util.datafix.fixes.References;

public final class RenameFireflyColorToFireflyVariantComponentFix extends DataFix {

	public RenameFireflyColorToFireflyVariantComponentFix(Schema outputSchema) {
		super(outputSchema, false);
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
			"Remame wilderwild:firefly/color component to wilderwild:firefly/variant",
			this.getInputSchema().getType(References.DATA_COMPONENTS),
			components -> components.update(
				DSL.remainderFinder(), remainder -> remainder.renameField(WWConstants.string("firefly/color"), WWConstants.string("firefly/variant"))
			)
		);
	}
}
