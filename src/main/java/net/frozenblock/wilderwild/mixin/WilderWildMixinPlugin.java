/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin;

import java.util.List;
import java.util.Set;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class WilderWildMixinPlugin implements IMixinConfigPlugin {
	private static final String MIXIN_PATH = "net.frozenblock.wilderwild.mixin.";

	@Override
	public void onLoad(String mixinPackage) {

	}

	@Override
	@Nullable
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, @NotNull String mixinClassName) {
		if (mixinClassName.contains("sodium")) {
			return FrozenBools.HAS_SODIUM;
		}
		if (mixinClassName.contains("LiquidBlockRenderer") || mixinClassName.contains("CloudRenderer") || mixinClassName.contains("EntityRenderDispatcher")) {
			return !FrozenBools.HAS_SODIUM;
		}
		if (mixinClassName.contains("fallingleaves")) {
			return WilderSharedConstants.HAS_FALLINGLEAVES;
		}
		return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

	}

	@Override
	@Nullable
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}
}
