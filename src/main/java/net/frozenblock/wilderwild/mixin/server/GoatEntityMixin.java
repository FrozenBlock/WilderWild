package net.frozenblock.wilderwild.mixin.server;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.item.GoatHornItem;
import net.minecraft.item.Instrument;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.InstrumentTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntryList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(GoatEntity.class)
public abstract class GoatEntityMixin {

    @Shadow
    @Final
    private static TrackedData<Boolean> SCREAMING;

    private boolean isTreetrain1() {
        GoatEntity goat = GoatEntity.class.cast(this);
        String string = Formatting.strip(goat.getName().getString());
        return Objects.equals(string, "Treetrain1");
    }

    @Inject(method = "isScreaming", at = @At("HEAD"), cancellable = true)
    private void isScreaming(CallbackInfoReturnable<Boolean> cir) {
        if (this.isTreetrain1()) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Inject(method = "getGoatHornStack", at = @At("HEAD"), cancellable = true)
    public void getGoatHornStack(CallbackInfoReturnable<ItemStack> cir) {
        if (this.isTreetrain1()) {
            GoatEntity goat = GoatEntity.class.cast(this);
            Random random = Random.create(goat.getUuid().hashCode());
            TagKey<Instrument> tagKey = goat.getDataTracker().get(SCREAMING) ? InstrumentTags.SCREAMING_GOAT_HORNS : InstrumentTags.REGULAR_GOAT_HORNS;
            RegistryEntryList<Instrument> registryEntryList = Registry.INSTRUMENT.getOrCreateEntryList(tagKey);
            cir.setReturnValue(GoatHornItem.getStackForInstrument(Items.GOAT_HORN, registryEntryList.getRandom(random).get()));
            cir.cancel();
        }
    }
}
