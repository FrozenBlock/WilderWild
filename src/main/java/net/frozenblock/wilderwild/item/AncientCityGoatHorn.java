package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectileEntity;
import net.frozenblock.wilderwild.misc.PVZGWSound.MovingSoundLoop;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Instrument;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class AncientCityGoatHorn extends Item {
    private static final String INSTRUMENT_KEY = "instrument";
    private final TagKey<Instrument> instrumentTag;

    public static final int SHRIEKER_COOLDOWN = 900;
    public static final int SENSOR_COOLDOWN = 400;
    public static final int TENDRIL_COOLDOWN = 380;

    public AncientCityGoatHorn(Settings settings, TagKey<Instrument> instrumentTag) {
        super(settings);
        this.instrumentTag = instrumentTag;
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Optional<RegistryKey<Instrument>> optional = this.getInstrument(stack).flatMap(RegistryEntry::getKey);
        if (optional.isPresent()) {
            MutableText mutableText = Text.translatable("item.wilderwild.ancient_horn.sound.0");
            tooltip.add(mutableText.formatted(Formatting.GRAY));
        }
    }

    public static ItemStack getStackForInstrument(Item item, RegistryEntry<Instrument> instrument) {
        ItemStack itemStack = new ItemStack(item);
        setInstrument(itemStack, instrument);
        return itemStack;
    }

    private static void setInstrument(ItemStack stack, RegistryEntry<Instrument> instrument) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putString(
                INSTRUMENT_KEY, (instrument.getKey().orElseThrow(() -> new IllegalStateException("Invalid instrument"))).getValue().toString()
        );
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            for (RegistryEntry<Instrument> registryEntry : Registry.INSTRUMENT.iterateEntries(this.instrumentTag)) {
                stacks.add(getStackForInstrument(RegisterItems.ANCIENT_HORN, registryEntry));
            }
        }

    }

    private Optional<RegistryEntry<Instrument>> getInstrument(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound != null) {
            Identifier identifier = Identifier.tryParse(nbtCompound.getString(INSTRUMENT_KEY));
            if (identifier != null) {
                return Registry.INSTRUMENT.getEntry(RegistryKey.of(Registry.INSTRUMENT_KEY, identifier));
            }
        }

        Iterator<RegistryEntry<Instrument>> iterator = Registry.INSTRUMENT.iterateEntries(this.instrumentTag).iterator();
        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
    }

    public static int getCooldown(@Nullable Entity entity, int cooldown) {
        if (entity != null) {
            if (entity instanceof PlayerEntity player) {
                cooldown = player.isCreative() ? 5 : cooldown;
            }
        }
        return cooldown;
    }

    public static int getCooldown(@Nullable Entity entity, int cooldown, int additionalCooldown) {
        if (entity != null) {
            if (entity instanceof PlayerEntity player) {
                cooldown = player.isCreative() ? 5 : cooldown + additionalCooldown;
            }
        }
        return cooldown;
    }

    public static ArrayList<ItemStack> getHorns(PlayerEntity player) {
        ArrayList<ItemStack> horns = new ArrayList<>();
        if (player != null) {
            if (player.getMainHandStack().isOf(RegisterItems.ANCIENT_HORN)) {
                horns.add(player.getMainHandStack());
            }
            if (player.getOffHandStack().isOf(RegisterItems.ANCIENT_HORN)) {
                horns.add(player.getOffHandStack());
            }
        }
        return horns;
    }

    public static int decreaseCooldown(PlayerEntity user, int time) {
        if (!user.isCreative()) {
            ItemCooldownManager manager = user.getItemCooldownManager();
            ItemCooldownManager.Entry entry = manager.entries.get(RegisterItems.ANCIENT_HORN);
            if (entry != null) {
                int between = entry.endTick - entry.startTick;
                if (between > 140 & between >= time) {
                    int cooldown = Math.max(between - time, 1);
                    manager.remove(RegisterItems.ANCIENT_HORN);
                    manager.set(RegisterItems.ANCIENT_HORN, cooldown);
                    return time;
                }
            }
        }
        return -1;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        WilderWild.log(user, "Used Ancient Goat Horn", WilderWild.DEV_LOGGING);
        ItemStack itemStack = user.getStackInHand(hand);
        Optional<RegistryEntry<Instrument>> optional = this.getInstrument(itemStack);
        if (optional.isPresent()) {
            Instrument instrument = optional.get().value();
            user.setCurrentHand(hand);
            SoundEvent soundEvent = instrument.soundEvent();
            float range = instrument.range() / 16.0F;
            world.playSoundFromEntity(user, user, soundEvent, SoundCategory.RECORDS, range, 1.0F);
            user.getItemCooldownManager().set(RegisterItems.ANCIENT_HORN, getCooldown(user, 300));
            if (world instanceof ServerWorld server) {
                AncientHornProjectileEntity projectileEntity = new AncientHornProjectileEntity(world, user.getX(), user.getEyeY(), user.getZ());
                projectileEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.0F, 0.0F);
                projectileEntity.shotByPlayer = true;
                server.spawnEntity(projectileEntity);
                MovingSoundLoop.createMovingLoopingSound(server, projectileEntity, SoundEvents.BLOCK_SCULK_CHARGE, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                //FlyBySoundPacket.createFlybySound(world, projectileEntity, RegisterSounds.ANCIENT_HORN_VIBRATION_DISSIPATE, SoundCategory.PLAYERS, 1.0F, 0.7F);
            }
            return TypedActionResult.consume(itemStack);
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        Optional<RegistryEntry<Instrument>> optional = this.getInstrument(stack);
        return optional.map(instrumentRegistryEntry -> instrumentRegistryEntry.value().useDuration()).orElse(0);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.TOOT_HORN;
    }

}
