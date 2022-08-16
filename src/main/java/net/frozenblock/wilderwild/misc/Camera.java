package net.frozenblock.wilderwild.misc;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camera extends Item {

    public Camera(Properties settings) {
        super(settings);
    }

    private boolean canGo;

    @Override
    public void inventoryTick(ItemStack itemStack, Level world, Entity entity, int i, boolean bl) {
        if (entity instanceof Player player) {
            if (player.getCooldowns().isOnCooldown(this) && player.getCooldowns().getCooldownPercent(this, 0) == 0.9F) {
                if (world.isClientSide && canGo) {
                    WilderWild.LOGGER.warn("PLAYER HAS ACCESS TO DEV CAMERA AND HAS JUST USED IT");
                    Minecraft client = Minecraft.getInstance();
                    File directory = getPanoramaFolderName(new File(client.gameDirectory, "panoramas"));
                    File directory1 = new File(directory, "screenshots");
                    directory1.mkdir();
                    directory1.mkdirs();
                    client.grabPanoramixScreenshot(directory, 2048, 2048);
                    canGo = false;
                }
            }
        }
    }

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

    private static File getPanoramaFolderName(File directory) {
        String string = DATE_FORMAT.format(new Date());
        int i = 1;
        while (true) {
            File file = new File(directory, string + (i == 1 ? "" : "_" + i));
            if (!file.exists()) {
                return file;
            }
            ++i;
        }
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (!user.getCooldowns().isOnCooldown(this)) {
            user.getCooldowns().addCooldown(this, 10);
            if (world.isClientSide) {
                canGo = true;
            }
            return InteractionResultHolder.success(itemStack);
        }
        return InteractionResultHolder.fail(itemStack);
    }

}
