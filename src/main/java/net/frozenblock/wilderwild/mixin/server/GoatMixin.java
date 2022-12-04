package net.frozenblock.wilderwild.mixin.server;

import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.tags.InstrumentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Goat.class)
public class GoatMixin {

    @Shadow
    @Final
    private static EntityDataAccessor<Boolean> DATA_IS_SCREAMING_GOAT;

    @Unique
    private boolean isTreetrain1() {
        Goat goat = Goat.class.cast(this);
        String string = ChatFormatting.stripFormatting(goat.getName().getString());
        return Objects.equals(string, "Treetrain1");
    }

    @Inject(method = "isScreamingGoat", at = @At("RETURN"), cancellable = true)
    private void isScreamingGoat(CallbackInfoReturnable<Boolean> cir) {
        if (this.isTreetrain1()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "createHorn", at = @At("RETURN"), cancellable = true)
    public void createHorn(CallbackInfoReturnable<ItemStack> cir) {
        if (this.isTreetrain1()) {
            Goat goat = Goat.class.cast(this);
            RandomSource random = RandomSource.create(goat.getUUID().hashCode());
            TagKey<Instrument> tagKey = goat.getEntityData().get(DATA_IS_SCREAMING_GOAT) ? InstrumentTags.SCREAMING_GOAT_HORNS : InstrumentTags.REGULAR_GOAT_HORNS;
            HolderSet<Instrument> registryEntryList = Registry.INSTRUMENT.getOrCreateTag(tagKey);
            cir.setReturnValue(InstrumentItem.create(Items.GOAT_HORN, registryEntryList.getRandomElement(random).get()));
        }
    }

}
