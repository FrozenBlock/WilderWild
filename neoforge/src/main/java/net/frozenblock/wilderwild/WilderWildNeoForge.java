package net.frozenblock.wilderwild;

import net.frozenblock.lib.platform.FrozenLibEarlyPlatformUtils;
import net.frozenblock.lib.platform.FrozenLibInitPlatformUtils;
import net.frozenblock.lib.platform.networking.NeoNetworkingHelper;
import net.frozenblock.wilderwild.block.EchoGlassBlock;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.command.SpreadSculkCommand;
import net.frozenblock.wilderwild.levelgen.modification.WWWorldgen;
import net.frozenblock.wilderwild.networking.WWClientNetworking;
import net.frozenblock.wilderwild.networking.WWNetworking;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWCreativeInventorySorting;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.frozenblock.wilderwild.registry.WWSoundTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(WWPreLoadConstants.MOD_ID)
public final class WilderWildNeoForge {

	public WilderWildNeoForge(IEventBus modBus) {
		WilderWild.init();

		NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, event -> {
			SpreadSculkCommand.register(event.getDispatcher());
		});

		modBus.addListener(RegisterPayloadHandlersEvent.class, event -> {
			WWNetworking.init();
			if (FrozenLibEarlyPlatformUtils.LOADER.isClient()) {
				WWClientNetworking.registerPacketReceivers();
			}

			final NeoNetworkingHelper neoNetworking = (NeoNetworkingHelper) FrozenLibInitPlatformUtils.NETWORKING;
			final PayloadRegistrar registrar = event.registrar(WWPreLoadConstants.MOD_ID);
			neoNetworking.flush(registrar);
		});

		// AFTER register event
		modBus.addListener(FMLCommonSetupEvent.class, event -> {
			WWSoundTypes.setup();
			WWItems.init();
			WWItems.setup();
			WWBlocks.setupBlockProperties();
			WWCreativeInventorySorting.setup();
			WWParticleTypes.linkLeafParticles();
			WWWorldgen.setup();
		});

		// This is called on both the client and server, so we don't need two implementations like Fabric!
		NeoForge.EVENT_BUS.addListener(BreakBlockEvent.class, event -> {
			final BlockState state = event.getState();
			final LevelAccessor level = event.getLevel();
			final BlockPos pos = event.getPos();
			final Player player = event.getPlayer();

			if (SnowloggingUtils.isSnowlogged(state)) {
				level.setBlock(pos, state.setValue(SnowloggingUtils.SNOW_LAYERS, 0), Block.UPDATE_ALL);
				level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(SnowloggingUtils.getSnowEquivalent(state)));
				event.setCanceled(true);
			}

			if (state.getBlock() instanceof EchoGlassBlock && EchoGlassBlock.canDamage(state) && !player.isCreative()) {
				final Holder<Enchantment> silkTouch = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);
				if (EnchantmentHelper.getItemEnchantmentLevel(silkTouch, player.getMainHandItem()) < 1) {
					EchoGlassBlock.setDamagedState(level, pos, state);
					level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
					event.setCanceled(true);
				}
			}
		});
	}
}
