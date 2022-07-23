package net.frozenblock.wilderwild.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.wilderwild.WilderWild;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class WilderWildMixinPlugin implements IMixinConfigPlugin {
    private static final String MIXIN_PACKAGE_ROOT = "net.frozenblock.wilderwild.mixin.";

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!mixinClassName.startsWith(MIXIN_PACKAGE_ROOT)) {
            return false;
        }

        String mixin = mixinClassName.substring(MIXIN_PACKAGE_ROOT.length());

        if (mixin.contains("simple_copper_pipes")) {
            return FabricLoader.getInstance().getModContainer("copper_pipe").isPresent();
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