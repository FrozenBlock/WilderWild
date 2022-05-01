package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.entity.AncientHornProjectileEntity;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.item.TooltipContext;
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

import java.util.List;

public class AncientCityGoatHorn extends Item {
    public static final int shriekerCooldown = 600;
    public static final int sensorCooldown = 240;
    public static final int echoerCooldown = 480;
    public static final int tendrilCooldown = 160;

    public static int getCooldown(@Nullable Entity entity, int i) {
        if (entity != null) {
            if (entity instanceof PlayerEntity player) {
                i = player.isCreative() ? 5 : i;
            }
        } return i;
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
        user.getItemCooldownManager().set(RegisterItems.ANCIENT_HORN, getCooldown(user, 100));
        if (world instanceof ServerWorld server) {
            AncientHornProjectileEntity projectileEntity = new AncientHornProjectileEntity(world, user.getX(), user.getEyeY(), user.getZ());
            projectileEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.0F, 0.0F);
            projectileEntity.setDamage(10D);
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
