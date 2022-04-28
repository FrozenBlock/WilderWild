package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.class_7430;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.LargeEntitySpawnHelper;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AncientCityGoatHorn extends class_7430 {

    public AncientCityGoatHorn(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        MutableText mutableText = Text.translatable("item.wilderwild.ancient_goat_horn.sound.0");
        tooltip.add(mutableText.formatted(Formatting.GRAY));
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        world.playSoundFromEntity(user, user, RegisterSounds.ANCIENT_GOAT_HORN_CALL_0, SoundCategory.RECORDS, 16.0F, 1.0F);
        user.getItemCooldownManager().set(RegisterItems.ANCIENT_GOAT_HORN, 140);
        if (world instanceof ServerWorld server) {
            Optional<RegistryKey<Biome>> key = server.getBiome(user.getBlockPos()).getKey();
            if (key.isPresent()) {
                if (key.get()==BiomeKeys.DEEP_DARK) {
                    trySpawnWarden(server, user.getBlockPos());
                }
            }
        }
        return TypedActionResult.consume(itemStack);
    }

    private static void trySpawnWarden(ServerWorld world, BlockPos pos) {
        if (world.getGameRules().getBoolean(GameRules.DO_WARDEN_SPAWNING)) {
            LargeEntitySpawnHelper.trySpawnAt(EntityType.WARDEN, SpawnReason.TRIGGERED, world, pos, 20, 5, 6).ifPresent((entity) -> {
                entity.playSound(SoundEvents.ENTITY_WARDEN_AGITATED, 16.0F, 1.0F);
            });
        }

    }

}
