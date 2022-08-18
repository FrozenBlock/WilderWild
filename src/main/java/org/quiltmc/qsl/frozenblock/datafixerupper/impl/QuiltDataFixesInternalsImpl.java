/*
 * Copyright 2022 QuiltMC
 * Modified to work on Fabric
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quiltmc.qsl.frozenblock.datafixerupper.impl;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.datafix.DataFixTypes;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.Collections;
import java.util.Map;

/**
 * Modified to work on Fabric
 */
@ApiStatus.Internal
public final class QuiltDataFixesInternalsImpl extends QuiltDataFixesInternals {
    private final @NotNull Schema latestVanillaSchema;

    private Map<String, DataFixerEntry> modDataFixers;
    private boolean frozen;

    public QuiltDataFixesInternalsImpl(@NotNull Schema latestVanillaSchema) {
        this.latestVanillaSchema = latestVanillaSchema;

        this.modDataFixers = new Object2ReferenceOpenHashMap<>();
        this.frozen = false;
    }

    @Override
    public void registerFixer(@NotNull String modId,
                              @Range(from = 0, to = Integer.MAX_VALUE) int currentVersion,
                              @NotNull DataFixer dataFixer) {
        if (this.modDataFixers.containsKey(modId)) {
            throw new IllegalArgumentException("Mod '" + modId + "' already has a registered data fixer");
        }

        this.modDataFixers.put(modId, new DataFixerEntry(dataFixer, currentVersion));
    }

    @Override
    public @Nullable DataFixerEntry getFixerEntry(@NotNull String modId) {
        return modDataFixers.get(modId);
    }

    @Override
    public @NotNull Schema createBaseSchema() {
        return new Schema(0, this.latestVanillaSchema);
    }

    @Override
    public @NotNull CompoundTag updateWithAllFixers(@NotNull DataFixTypes dataFixTypes,
                                                    @NotNull CompoundTag compound) {
        var current = new Dynamic<>(NbtOps.INSTANCE, compound);

        for (Map.Entry<String, DataFixerEntry> entry : this.modDataFixers.entrySet()) {
            int modDataVersion = QuiltDataFixesInternals.getModDataVersion(compound, entry.getKey());
            DataFixerEntry dataFixerEntry = entry.getValue();

            current = dataFixerEntry.dataFixer()
                    .update(dataFixTypes.getType(),
                            current,
                            modDataVersion, dataFixerEntry.currentVersion());
        }

        return (CompoundTag) current.getValue();
    }

    @Override
    public @NotNull CompoundTag addModDataVersions(@NotNull CompoundTag compound) {
        for (Map.Entry<String, DataFixerEntry> entry : this.modDataFixers.entrySet()) {
            compound.putInt(entry.getKey() + "_DataVersion", entry.getValue().currentVersion());
        }

        return compound;
    }

    @Override
    public void freeze() {
        if (!this.frozen) {
            modDataFixers = Collections.unmodifiableMap(this.modDataFixers);
        }

        this.frozen = true;
    }

    @Override
    public boolean isFrozen() {
        return this.frozen;
    }

}
