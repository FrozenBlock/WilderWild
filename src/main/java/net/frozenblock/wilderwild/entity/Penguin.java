package net.frozenblock.wilderwild.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

public class Penguin extends Animal {
	public Penguin(EntityType<? extends Animal> entityType, Level level) {
		super(entityType, level);
	}

	public static boolean checkPenguinSpawnRules(EntityType<? extends Penguin> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return true;
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		// TODO: use a tag
		return itemStack.getItem() == Items.COD || itemStack.getItem() == Items.SALMON;
	}

	@Override
	public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return null;
	}

}
