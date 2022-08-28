package net.frozenblock.wilderwild.entity.variant;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public record JellyfishVariant(ResourceLocation texture) {
    public static final JellyfishVariant PALE_BLUE = JellyfishVariant.register("pale_blue", "textures/entity/jellyfish/pale_blue.png");
    public static final JellyfishVariant PINK = JellyfishVariant.register("pale_blue", "textures/entity/jellyfish/pink.png");

    private static JellyfishVariant register(String string, String string2) {
        return Registry.register(WilderWild.JELLYFISH_VARIANTS, string, new JellyfishVariant(WilderWild.id(string2)));
    }
}
