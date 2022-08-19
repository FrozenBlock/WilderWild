package net.frozenblock.wilderwild.mixin.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.frozenblock.wilderwild.world.structure.WilderStructureProcessors;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.structures.NbtToSnbt;
import net.minecraft.data.worldgen.AncientCityStructurePools;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

@Mixin(AncientCityStructurePools.class)
public class AncientCityStructurePoolsMixin {

    private static final Comparator<Object> YXZ_LISTTAG_INT_COMPARATOR = Comparator.comparingInt(nbt -> ((ListTag)nbt).getInt(1)).thenComparingInt(nbt -> ((ListTag)nbt).getInt(0)).thenComparingInt(nbt -> ((ListTag)nbt).getInt(2));
    private static final Comparator<Object> YXZ_LISTTAG_DOUBLE_COMPARATOR = Comparator.comparingDouble(nbt -> ((ListTag)nbt).getDouble(1)).thenComparingDouble(nbt -> ((ListTag)nbt).getDouble(0)).thenComparingDouble(nbt -> ((ListTag)nbt).getDouble(2));

    @Inject(method = "convertStructure", at = @At("HEAD"), cancellable = true)
    private static void convertStructure(CachedOutput cachedOutput, Path inputPath, String location, Path outputPath, CallbackInfoReturnable<Path> info) throws IOException {
        if (inputPath.toString().contains("ancient_city")) {
            info.cancel();
            Path path = null;
            {
                InputStream inputStream = Files.newInputStream(inputPath);
                try {
                    Path path2 = outputPath.resolve(location + ".snbt");
                    NbtToSnbt.writeSnbt(cachedOutput, path2, cityToSnbt(NbtIo.readCompressed(inputStream)));
                    //LOGGER.info("Converted {} from NBT to SNBT", (Object)location);
                    path = path2;
                } catch (Throwable throwable) {
                    try {
                        try {
                            inputStream.close();
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                        throw throwable;
                    } catch (IOException iOException) {
                        //LOGGER.error("Couldn't convert {} from NBT to SNBT at {}", location, inputPath, iOException);
                        info.setReturnValue(null);
                    }
                }
                inputStream.close();
            }
            info.setReturnValue(path);
        }
    }

    private static String cityToSnbt(CompoundTag nbt) {
        return new SnbtPrinterTagVisitor().visit(packCityTemplate(nbt));
    }

    private static CompoundTag packCityTemplate(CompoundTag nbt) {
        ListTag listTag4;
        ListTag listTag3;
        boolean bl = nbt.contains("palettes", 9);
        ListTag listTag = bl ? nbt.getList("palettes", 9).getList(0) : nbt.getList("palette", 10);
        ListTag listTag2 = listTag.stream().map(CompoundTag.class::cast).map(AncientCityStructurePoolsMixin::packBlockState).map(StringTag::valueOf).collect(Collectors.toCollection(ListTag::new));
        nbt.put("palette", listTag2);
        if (bl) {
            listTag3 = new ListTag();
            listTag4 = nbt.getList("palettes", 9);
            ListTag finalListTag = listTag3;
            listTag4.stream().map(ListTag.class::cast).forEach(nbtList -> {
                CompoundTag compoundTag = new CompoundTag();
                for (int i = 0; i < nbtList.size(); ++i) {
                    compoundTag.putString(listTag2.getString(i), packBlockState(nbtList.getCompound(i)));
                }
                finalListTag.add(compoundTag);
            });
            nbt.put("palettes", listTag3);
        }
        if (nbt.contains("entities", 10)) {
            listTag3 = nbt.getList("entities", 10);
            listTag4 = listTag3.stream().map(CompoundTag.class::cast).sorted(Comparator.comparing(nbtCompound -> nbtCompound.getList("pos", 6), YXZ_LISTTAG_DOUBLE_COMPARATOR)).collect(Collectors.toCollection(ListTag::new));
            nbt.put("entities", listTag4);
        }
        listTag3 = nbt.getList("blocks", 10).stream().map(CompoundTag.class::cast).sorted(Comparator.comparing(nbtCompound -> nbtCompound.getList("pos", 3), YXZ_LISTTAG_INT_COMPARATOR)).peek(compoundTag -> compoundTag.putString("state", listTag2.getString(compoundTag.getInt("state")))).collect(Collectors.toCollection(ListTag::new));
        nbt.put("data", listTag3);
        nbt.remove("blocks");
        return nbt;
    }

    private static String packBlockState(CompoundTag nbt) {
        String name = nbt.getString("Name");
        if (name.equals("minecraft:chest")) {
            name = "wilderwild:stone_chest";
        }
        StringBuilder stringBuilder = new StringBuilder(name);
        if (nbt.contains("Properties", 10)) {
            CompoundTag compoundTag = nbt.getCompound("Properties");
            String string = compoundTag.getAllKeys().stream().sorted().map(key -> key + ":" + Objects.requireNonNull(compoundTag.get(key)).getAsString()).collect(Collectors.joining(","));
            stringBuilder.append('{').append(string).append('}');
        }
        return stringBuilder.toString();
    }
}