package net.frozenblock.wilderwild.mixin;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class WilderMixinPlugin implements IMixinConfigPlugin {
    private static final String MIXIN_PATH = "net.frozenblock.wilderwild.mixin.";
    private static final Logger LOGGER = LogManager.getLogger("WilderWild");

    public static HashMap<String, String> ModsToMixinsDisableMap = new HashMap<>() {{
        put("WilderIDReplacerMixin", "updatefixerupper");
    }};

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String mixinString = mixinClassName.substring(mixinClassName.lastIndexOf(".") + 1);
        if (ModsToMixinsDisableMap.containsKey(mixinString)) {
            return FabricLoader.getInstance().getModContainer(ModsToMixinsDisableMap.get(mixinString)).isEmpty();
        }
        return mixinClassName.contains(MIXIN_PATH);
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