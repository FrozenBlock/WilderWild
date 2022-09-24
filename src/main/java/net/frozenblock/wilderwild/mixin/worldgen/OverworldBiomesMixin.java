package net.frozenblock.wilderwild.mixin.worldgen;

import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.sounds.Music;
import net.minecraft.world.level.biome.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(OverworldBiomes.class)
public class OverworldBiomesMixin {

    @Inject(method = "deepDark()Lnet/minecraft/world/level/biome/Biome;", at = @At(value = "INVOKE", target =
            "Lnet/minecraft/data/worldgen/biome/OverworldBiomes;biome(Lnet/minecraft/world/level/biome/Biome$Precipitation;FFLnet/minecraft/world/level/biome/MobSpawnSettings$Builder;Lnet/minecraft/world/level/biome/BiomeGenerationSettings$Builder;Lnet/minecraft/sounds/Music;)Lnet/minecraft/world/level/biome/Biome;",
            shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void deepDark(CallbackInfoReturnable<Biome> info, MobSpawnSettings.Builder builder, BiomeGenerationSettings.Builder builder2, Music music) {
        info.cancel();
        info.setReturnValue(
                new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .temperature(0.8F)
                .downfall(0.4F)
                .specialEffects(
                        new BiomeSpecialEffects.Builder()
                                .waterColor(4159204)
                                .waterFogColor(329011)
                                .fogColor(0)
                                .skyColor(0)
                                //.foliageColorOverride(5879634)
                                //.grassColorOverride(8437607)
                                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                                .backgroundMusic(music).build())
                .mobSpawnSettings(builder.build())
                .generationSettings(builder2.build())
                .build()
                );
    }

}
