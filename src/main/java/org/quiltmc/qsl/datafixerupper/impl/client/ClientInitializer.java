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

package org.quiltmc.qsl.datafixerupper.impl.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.frozenblock.wilderwild.WilderWild;
import org.quiltmc.qsl.datafixerupper.impl.QuiltDataFixesInternals;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.ApiStatus;

/**
 * Modified to work on Fabric
 */
@Environment(EnvType.CLIENT)
@ApiStatus.Internal
public final class ClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            WilderWild.log("QuiltMC's DataFixer Client Registry is about to freeze", true);
            QuiltDataFixesInternals.freeze();
            WilderWild.log("QuiltMC's DataFixer Client Registry was frozen", true);
        });
    }
}