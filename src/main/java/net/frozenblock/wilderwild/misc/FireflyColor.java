package net.frozenblock.wilderwild.misc;

import com.mojang.serialization.Codec;
import java.util.List;
import net.frozenblock.wilderwild.registry.WilderRegistry;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;

public class FireflyColor {

    public static final Codec<FireflyColor> CODEC = ResourceLocation.CODEC
            .listOf()
            .comapFlatMap(
                    list -> Util.fixedSize(list, 2).map(listx -> new FireflyColor(listx.get(0), listx.get(1))), color -> List.of(color.getKey(), color.getTexture())
            );

    public static final EntityDataSerializer<FireflyColor> SERIALIZER = EntityDataSerializer.simpleId(WilderRegistry.FIREFLY_COLOR);

    public static final FireflyColor BLACK = register(WilderSharedConstants.id("black"), WilderSharedConstants.id("textures/entity/firefly/firefly_black.png"));
    public static final FireflyColor BLUE = register(WilderSharedConstants.id("blue"), WilderSharedConstants.id("textures/entity/firefly/firefly_blue.png"));
    public static final FireflyColor BROWN = register(WilderSharedConstants.id("brown"), WilderSharedConstants.id("textures/entity/firefly/firefly_brown.png"));
    public static final FireflyColor CYAN = register(WilderSharedConstants.id("cyan"), WilderSharedConstants.id("textures/entity/firefly/firefly_cyan.png"));
    public static final FireflyColor GRAY = register(WilderSharedConstants.id("gray"), WilderSharedConstants.id("textures/entity/firefly/firefly_gray.png"));
    public static final FireflyColor GREEN = register(WilderSharedConstants.id("green"), WilderSharedConstants.id("textures/entity/firefly/firefly_green.png"));
    public static final FireflyColor LIGHT_BLUE = register(WilderSharedConstants.id("light_blue"), WilderSharedConstants.id("textures/entity/firefly/firefly_light_blue.png"));
    public static final FireflyColor LIGHT_GRAY = register(WilderSharedConstants.id("light_gray"), WilderSharedConstants.id("textures/entity/firefly/firefly_light_gray.png"));
    public static final FireflyColor LIME = register(WilderSharedConstants.id("lime"), WilderSharedConstants.id("textures/entity/firefly/firefly_lime.png"));
    public static final FireflyColor MAGENTA = register(WilderSharedConstants.id("magenta"), WilderSharedConstants.id("textures/entity/firefly/firefly_magenta.png"));
    public static final FireflyColor ON = register(WilderSharedConstants.id("on"), WilderSharedConstants.id("textures/entity/firefly/firefly_on.png"));
    public static final FireflyColor ORANGE = register(WilderSharedConstants.id("orange"), WilderSharedConstants.id("textures/entity/firefly/firefly_orange.png"));
    public static final FireflyColor PINK = register(WilderSharedConstants.id("pink"), WilderSharedConstants.id("textures/entity/firefly/firefly_pink.png"));
    public static final FireflyColor PURPLE = register(WilderSharedConstants.id("purple"), WilderSharedConstants.id("textures/entity/firefly/firefly_purple.png"));
    public static final FireflyColor RED = register(WilderSharedConstants.id("red"), WilderSharedConstants.id("textures/entity/firefly/firefly_red.png"));
    public static final FireflyColor WHITE = register(WilderSharedConstants.id("white"), WilderSharedConstants.id("textures/entity/firefly/firefly_white.png"));
    public static final FireflyColor YELLOW = register(WilderSharedConstants.id("yellow"), WilderSharedConstants.id("textures/entity/firefly/firefly_yellow.png"));

    private final ResourceLocation key;
    private final ResourceLocation texture;

    public FireflyColor(ResourceLocation key, ResourceLocation texture) {
        this.key = key;
        this.texture = texture;
    }

    public ResourceLocation getKey() {
        return this.key;
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }

    public static FireflyColor register(ResourceLocation key, ResourceLocation texture) {
        return Registry.register(WilderRegistry.FIREFLY_COLOR, key, new FireflyColor(key, texture));
    }

    static {
        EntityDataSerializers.registerSerializer(SERIALIZER);
    }
}
