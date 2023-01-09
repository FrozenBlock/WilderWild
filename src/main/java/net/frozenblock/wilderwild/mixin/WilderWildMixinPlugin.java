package net.frozenblock.wilderwild.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.lib.FrozenBools;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class WilderWildMixinPlugin implements IMixinConfigPlugin {
	private static final String MIXIN_PATH = "net.frozenblock.wilderwild.mixin.";

	@Override
	public void onLoad(String mixinPackage) {

	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		if (FrozenBools.HAS_SODIUM) {
			return mixinClassName.contains("sodium") && !mixinClassName.contains("LiquidBlockRenderer");
		}
		return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}
}
