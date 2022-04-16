package net.frozenblock.wilderwild.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MusicDiscItem.class)
public interface MusicDiscItemInvoker {

    @Invoker("<init>")
    static MusicDiscItem invokeConstructor(int i, SoundEvent soundEvent, Item.Settings settings) {
        throw new AssertionError();
    }
}
