package net.frozenblock.wilderwild.mixin;

import java.util.List;
import java.util.Set;
import net.frozenblock.lib.FrozenBools;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

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
		if (mixinClassName.contains("sodium")) {
			return FrozenBools.HAS_SODIUM;
		}
		if (mixinClassName.contains("LiquidBlockRenderer")) {
			return !FrozenBools.HAS_SODIUM;
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
