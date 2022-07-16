package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.Firefly;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class RegisterLoopingSoundRestrictions {
    private static boolean frozen;
    private static final ArrayList<Identifier> ids = new ArrayList<>();
    private static final ArrayList<LoopPredicate<?>> predicates = new ArrayList<>();

    public static void register(Identifier id, LoopPredicate<?> predicate) {
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
    public static LoopPredicate<?> getPredicate(Identifier id) {
        if (ids.contains(id)) {
            int index = ids.indexOf(id);
            return predicates.get(index);
        }
        return null;
    }

    @FunctionalInterface
    public static interface LoopPredicate<T extends Entity> {
        public boolean test(Entity entity);
    }

    public static void init() {
        register(WilderWild.id("default"), (var1) -> true);

        register(WilderWild.id("nectar"), (LoopPredicate<Firefly>) entity -> {
            if (entity.hasCustomName()) {
                Text name = entity.getCustomName();
                if (name != null) {
                    return name.getString().toLowerCase().contains("nectar");
                }
            }
            return false;
        });

        frozen = true;
    }
}
