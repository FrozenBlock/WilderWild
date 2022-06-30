package net.frozenblock.wilderwild.misc;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraItem extends Item {

    public CameraItem(Settings settings) {
        super(settings);
    }

    private boolean canGo;

    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity, int i, boolean bl) {
        if (entity instanceof PlayerEntity player) {
            if (player.getItemCooldownManager().isCoolingDown(this) && player.getItemCooldownManager().getCooldownProgress(this, 0) == 0.9F) {
                if (world.isClient && canGo) {
                    WilderWild.LOGGER.warn("PLAYER HAS ACCESS TO DEV CAMERA AND HAS JUST USED IT");
                    MinecraftClient client = MinecraftClient.getInstance();
                    File directory = getPanoramaFolderName(new File(client.runDirectory, "panoramas"));
                    File directory1 = new File(directory, "screenshots");
                    directory1.mkdir();
                    directory1.mkdirs();
                    client.takePanorama(directory, 2048, 2048);
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

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            user.getItemCooldownManager().set(this, 10);
            if (world.isClient) {
                canGo = true;
            }
            return TypedActionResult.success(itemStack);
        }
        return TypedActionResult.fail(itemStack);
    }

}
