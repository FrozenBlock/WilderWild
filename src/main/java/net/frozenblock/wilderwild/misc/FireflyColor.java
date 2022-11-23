package net.frozenblock.wilderwild.misc;

import com.mojang.serialization.Codec;
import java.util.List;
import net.frozenblock.wilderwild.WilderWild;
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

    public static final FireflyColor BLACK = register(WilderWild.id("black"), WilderWild.id("textures/entity/firefly/firefly_black.png"));
    public static final FireflyColor BLUE = register(WilderWild.id("blue"), WilderWild.id("textures/entity/firefly/firefly_blue.png"));
    public static final FireflyColor BROWN = register(WilderWild.id("brown"), WilderWild.id("textures/entity/firefly/firefly_brown.png"));
    public static final FireflyColor CYAN = register(WilderWild.id("cyan"), WilderWild.id("textures/entity/firefly/firefly_cyan.png"));
    public static final FireflyColor GRAY = register(WilderWild.id("gray"), WilderWild.id("textures/entity/firefly/firefly_gray.png"));
    public static final FireflyColor GREEN = register(WilderWild.id("green"), WilderWild.id("textures/entity/firefly/firefly_green.png"));
    public static final FireflyColor LIGHT_BLUE = register(WilderWild.id("light_blue"), WilderWild.id("textures/entity/firefly/firefly_light_blue.png"));
    public static final FireflyColor LIGHT_GRAY = register(WilderWild.id("light_gray"), WilderWild.id("textures/entity/firefly/firefly_light_gray.png"));
    public static final FireflyColor LIME = register(WilderWild.id("lime"), WilderWild.id("textures/entity/firefly/firefly_lime.png"));
    public static final FireflyColor MAGENTA = register(WilderWild.id("magenta"), WilderWild.id("textures/entity/firefly/firefly_magenta.png"));
    public static final FireflyColor ON = register(WilderWild.id("on"), WilderWild.id("textures/entity/firefly/firefly_on.png"));
    public static final FireflyColor ORANGE = register(WilderWild.id("orange"), WilderWild.id("textures/entity/firefly/firefly_orange.png"));
    public static final FireflyColor PINK = register(WilderWild.id("pink"), WilderWild.id("textures/entity/firefly/firefly_pink.png"));
    public static final FireflyColor PURPLE = register(WilderWild.id("purple"), WilderWild.id("textures/entity/firefly/firefly_purple.png"));
    public static final FireflyColor RED = register(WilderWild.id("red"), WilderWild.id("textures/entity/firefly/firefly_red.png"));
    public static final FireflyColor WHITE = register(WilderWild.id("white"), WilderWild.id("textures/entity/firefly/firefly_white.png"));
    public static final FireflyColor YELLOW = register(WilderWild.id("yellow"), WilderWild.id("textures/entity/firefly/firefly_yellow.png"));

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
