package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.entity.AncientHornProjectileEntity;
import net.frozenblock.wilderwild.registry.RegisterEnchantments;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AncientCityGoatHorn extends Item {
    public static final int shriekerCooldown = 900;
    public static final int sensorCooldown = 400;
    public static final int echoerCooldown = 580;
    public static final int tendrilCooldown = 380;

    public static int getCooldown(@Nullable Entity entity, int i) {
        if (entity != null) {
            if (entity instanceof PlayerEntity player) {
                i = i - (getCooldownLevel(getHorns(player)) * 40);
                i = player.isCreative() ? 5 : i;
            }
        } return i;
    }

    public static int getCooldownLevel(ArrayList<ItemStack> horns) {
        int i = 99;
        for (ItemStack horn : horns) {
            i = Math.min(EnchantmentHelper.getLevel(RegisterEnchantments.ANCIENT_HORN_COOLDOWN_ENCHANTMENT, horn), i);
        } return i==99 ? 0 : i;
    }

    public static ArrayList<ItemStack> getHorns(PlayerEntity player) {
        ArrayList<ItemStack> horns = new ArrayList<>();
        if (player.getMainHandStack().isOf(RegisterItems.ANCIENT_HORN)) { horns.add(player.getMainHandStack()); }
        if (player.getOffHandStack().isOf(RegisterItems.ANCIENT_HORN)) { horns.add(player.getOffHandStack()); }
        return horns;
    }

    public AncientCityGoatHorn(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        MutableText mutableText = Text.translatable("item.wilderwild.ancient_horn.sound.0");
        tooltip.add(mutableText.formatted(Formatting.GRAY));
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        world.playSoundFromEntity(user, user, RegisterSounds.ANCIENT_HORN_CALL, SoundCategory.RECORDS, 8.0F, 1.0F);
        user.getItemCooldownManager().set(RegisterItems.ANCIENT_HORN, getCooldown(user, 300));
        if (world instanceof ServerWorld server) {
            AncientHornProjectileEntity projectileEntity = new AncientHornProjectileEntity(world, user.getX(), user.getEyeY(), user.getZ());
            projectileEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.0F, 0.0F);
            server.spawnEntity(projectileEntity);
        }
        return TypedActionResult.consume(itemStack);
    }

    public int getMaxUseTime(ItemStack stack) {
        return 140;
    }
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.TOOT_HORN;
    }

}
