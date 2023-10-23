package net.frozenblock.wilderwild.datagen.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.frozenblock.wilderwild.registry.RegisterItems;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class WWEngUSProvider extends FabricLanguageProvider {
	public WWEngUSProvider(FabricDataOutput dataOutput) {
		super(dataOutput);
	}

	@Override
	public void generateTranslations(TranslationBuilder builder) {
		builder.add(RegisterItems.CRAB_CLAW, "Crab Claw");
		builder.add(RegisterItems.COOKED_CRAB_CLAW, "Cooked Crab Claw");
	}
}
