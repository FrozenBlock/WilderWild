package net.frozenblock.wilderwild.item;

import net.frozenblock.lib.interfaces.CooldownInterface;
import net.frozenblock.lib.sound.FrozenSoundPackets;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.particle.AncientHornParticleEffect;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class AncientHorn extends Item {
    private static final String INSTRUMENT_KEY = "instrument";
    private final TagKey<Instrument> instrumentTag;

    public static final int SHRIEKER_COOLDOWN = 900;
    public static final int SENSOR_COOLDOWN = 400;
    public static final int TENDRIL_COOLDOWN = 380;

    public AncientHorn(Properties settings, TagKey<Instrument> instrumentTag) {
        super(settings);
        this.instrumentTag = instrumentTag;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);
        Optional<ResourceKey<Instrument>> optional = this.getInstrument(stack).flatMap(Holder::unwrapKey);
        if (optional.isPresent()) {
            MutableComponent mutableText = Component.translatable("item.wilderwild.ancient_horn.sound.0");
            tooltip.add(mutableText.withStyle(ChatFormatting.GRAY));
        }

    }

    public static ItemStack getStackForInstrument(Item item, Holder<Instrument> instrument) {
        ItemStack itemStack = new ItemStack(item);
        setInstrument(itemStack, instrument);
        return itemStack;
    }

    private static void setInstrument(ItemStack stack, Holder<Instrument> instrument) {
        CompoundTag nbtCompound = stack.getOrCreateTag();
        nbtCompound.putString(
                INSTRUMENT_KEY, (instrument.unwrapKey().orElseThrow(() -> new IllegalStateException("Invalid instrument"))).location().toString()
        );
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> stacks) {
        if (this.allowedIn(group)) {
            for (Holder<Instrument> registryEntry : Registry.INSTRUMENT.getTagOrEmpty(this.instrumentTag)) {
                stacks.add(getStackForInstrument(RegisterItems.ANCIENT_HORN, registryEntry));
            }
        }

    }

    private Optional<Holder<Instrument>> getInstrument(ItemStack stack) {
        CompoundTag nbtCompound = stack.getTag();
        if (nbtCompound != null) {
            ResourceLocation identifier = ResourceLocation.tryParse(nbtCompound.getString(INSTRUMENT_KEY));
            if (identifier != null) {
                return Registry.INSTRUMENT.getHolder(ResourceKey.create(Registry.INSTRUMENT_REGISTRY, identifier));
            }
        }

        Iterator<Holder<Instrument>> iterator = Registry.INSTRUMENT.getTagOrEmpty(this.instrumentTag).iterator();
        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
    }

    public static int getCooldown(@Nullable Entity entity, int cooldown) {
        if (entity != null) {
            if (entity instanceof Player player) {
                cooldown = player.isCreative() ? 5 : cooldown;
            }
        }
        return cooldown;
    }

    public static int decreaseCooldown(Player user, int time) {
        if (!user.isCreative()) {
            ItemCooldowns manager = user.getCooldowns();
            ItemCooldowns.CooldownInstance entry = manager.cooldowns.get(RegisterItems.ANCIENT_HORN);
            if (entry != null) {
                int between = entry.endTime - entry.startTime;
                if (between > 140 & between >= time) {
                    ((CooldownInterface)user.getCooldowns()).changeCooldown(RegisterItems.ANCIENT_HORN, -time);
                    return time;
                }
            }
        }
        return -1;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        WilderWild.log(user, "Used Ancient Goat Horn", WilderWild.DEV_LOGGING);
        ItemStack itemStack = user.getItemInHand(hand);
        Optional<Holder<Instrument>> optional = this.getInstrument(itemStack);
        if (optional.isPresent()) {
            Instrument instrument = optional.get().value();
            user.startUsingItem(hand);
            SoundEvent soundEvent = instrument.soundEvent();
            float range = instrument.range() / 16.0F;
            if (!world.isClientSide) {
                FrozenSoundPackets.createMovingRestrictionSound(world, user, soundEvent, SoundSource.RECORDS, range, 1.0F, WilderWild.id("ancient_horn"));
            }
            AncientHornParticleEffect ancientHornParticle = new AncientHornParticleEffect(0);
            world.addParticle(ancientHornParticle, true, user.getX(), user.getY(), user.getZ(), user.getYRot(), user.getXRot(), -user.getYRot()); //change this to the new particle whenever we add it
            user.getCooldowns().addCooldown(RegisterItems.ANCIENT_HORN, getCooldown(user, 300));
            if (world instanceof ServerLevel server) {
                AncientHornProjectile projectileEntity = new AncientHornProjectile(world, user.getX(), user.getEyeY(), user.getZ());
                projectileEntity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.0F, 0.0F);
                projectileEntity.shotByPlayer = true;
                server.addFreshEntity(projectileEntity);
                FrozenSoundPackets.createMovingLoopingSound(server, projectileEntity, RegisterSounds.ANCIENT_HORN_PROJECTILE_LOOP, SoundSource.NEUTRAL, 1.0F, 1.0F, WilderWild.id("default"));
                ItemStack mainHand = user.getItemInHand(InteractionHand.MAIN_HAND);
                ItemStack offHand = user.getItemInHand(InteractionHand.OFF_HAND);
                if (mainHand.is(Items.WATER_BUCKET) || mainHand.is(Items.POTION) || offHand.is(Items.WATER_BUCKET) || offHand.is(Items.POTION)) {
                    projectileEntity.bubbles = world.random.nextIntBetweenInclusive(10, 25);
                    /*float yawNew = user.getYaw() * 0.017453292F;
                    float pitchNew = MathHelper.cos(user.getPitch() * 0.017453292F);
                    float f = -MathHelper.sin(yawNew) * pitchNew;
                    float h = MathHelper.cos(yawNew) * pitchNew;
                    for (int bubble=0; bubble < Math.random()*10; bubble++) {
                        EasyPacket.EasyFloatingSculkBubblePacket.createParticle(server, user.getEyePos(), Math.random() > 0.7 ? 1 : 0, 20 + (int)(Math.random()*40), 0.05, 1);
                    }*/
                }
                //FlyBySoundPacket.createFlybySound(world, projectileEntity, RegisterSounds.ANCIENT_HORN_VIBRATION_DISSIPATE, SoundCategory.PLAYERS, 1.0F, 0.7F);
            }
            return InteractionResultHolder.consume(itemStack);
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        Optional<Holder<Instrument>> optional = this.getInstrument(stack);
        return optional.map(instrumentRegistryEntry -> instrumentRegistryEntry.value().useDuration()).orElse(0);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.TOOT_HORN;
    }

}
