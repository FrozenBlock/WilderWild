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

package net.frozenblock.wilderwild.misc.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlock;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundTypes.LEAVES;

public class BetterNetherIntegration extends ModIntegration {
    public BetterNetherIntegration() {
        super("betternether");
    }

    @Override
    public void init() {
        addBlock(id("willow_leaves"), LEAVES, WilderSharedConstants.config()::leafSounds);
        addBlock(id("rubeus_leaves"), LEAVES, WilderSharedConstants.config()::leafSounds);
        addBlock(id("anchor_tree_leaves"), LEAVES, WilderSharedConstants.config()::leafSounds);
    }
}
