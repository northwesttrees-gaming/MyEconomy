package net.nwtg.myeconomy.procedures;

import net.nwtg.myeconomy.MyeconomyMod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class PlayerJoinsAWorldMainProcedure {
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		execute(event, event.getEntity().level, event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!world.isClientSide()) {
			MyeconomyMod.queueServerWork(20, () -> {
				GeneratePlayerConfigProcedure.execute(world, entity);
				GeneratePlayerConfigEconomyProcedure.execute(world, entity);
				GeneratePlayerConfigJailProcedure.execute(world, entity);
				GeneratePlayerConfigHomeProcedure.execute(world, entity);
				GeneratePlayerConfigUpdateVersionProcedure.execute(world, entity);
				SetPlayersHomeOnJoinIfNoneProcedure.execute(world, entity);
			});
		}
	}
}
