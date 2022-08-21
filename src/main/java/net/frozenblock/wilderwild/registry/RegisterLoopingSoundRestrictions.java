package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.Firefly;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public final class RegisterLoopingSoundRestrictions {
    private static boolean frozen;
    private static final ArrayList<ResourceLocation> ids = new ArrayList<>();
    private static final ArrayList<LoopPredicate<?>> predicates = new ArrayList<>();

    public static void register(ResourceLocation id, LoopPredicate<?> predicate) {
        if (!frozen) {
            if (!ids.contains(id)) {
                ids.add(id);
                predicates.add(predicate);
            } else {
                predicates.set(ids.indexOf(id), predicate);
            }
        } else {
            throw new RuntimeException("Can't register a Sound Loop Predicate whilst registry is frozen!");
        }
    }

    @Nullable
    public static LoopPredicate<?> getPredicate(ResourceLocation id) {
        if (ids.contains(id)) {
            int index = ids.indexOf(id);
            return predicates.get(index);
        }
        return null;
    }

    @FunctionalInterface
    public interface LoopPredicate<T extends Entity> {
        boolean test(Entity entity);
    }

    public static void init() {
        WilderWild.logWild("Registering Moving Looping Sound Restrictions for", WilderWild.UNSTABLE_LOGGING);

        register(WilderWild.id("default"), (var1) -> !var1.isSilent());

        register(WilderWild.id("nectar"), (LoopPredicate<Firefly>) entity -> {
            if (entity.isSilent()) {
                return false;
            }
            if (entity.hasCustomName()) {
                Component name = entity.getCustomName();
                if (name != null) {
                    return name.getString().toLowerCase().contains("nectar");
                }
            }
            return false;
        });

        frozen = true;
    }
}
