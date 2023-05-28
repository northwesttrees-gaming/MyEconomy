package net.nwtg.myeconomy.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class MinecraftLoadsAWorldMainProcedure {
	@SubscribeEvent
	public static void onWorldLoad(net.minecraftforge.event.level.LevelEvent.Load event) {
		execute(event, event.getLevel());
	}

	public static void execute(LevelAccessor world) {
		execute(null, world);
	}

	private static void execute(@Nullable Event event, LevelAccessor world) {
		if (!world.isClientSide()) {
			GenerateWorldNamespaceProcedure.execute(world);
			if ((world instanceof Level _lvl ? _lvl.dimension() : Level.OVERWORLD) == (Level.OVERWORLD)) {
				GenerateSettingsConfigProcedure.execute(world);
				GenerateSettingsConfigSettingsProcedure.execute(world);
				GenerateSettingsConfigUpdateVersionProcedure.execute(world);
				GenerateEconomyBuyConfigProcedure.execute(world);
				GenerateEconomySellConfigProcedure.execute(world);
				GenerateDefaultWorldConfigProcedure.execute(world);
				GenerateDefaultWorldConfigSpawnProcedure.execute(world);
				GenerateDefaultWorldConfigUpdateVersionProcedure.execute(world);
				GenerateDefaultPlayerConfigProcedure.execute(world);
				GenerateDefaultPlayerConfigEconomyProcedure.execute(world);
				GenerateDefaultPlayerConfigJailProcedure.execute(world);
				GenerateDefaultPlayerConfigHomeProcedure.execute(world);
				GenerateDefaultPlayerConfigUpdateVersionProcedure.execute(world);
			}
			GenerateWorldConfigProcedure.execute(world);
			GenerateWorldConfigSpawnProcedure.execute(world);
			GenerateWorldConfigUpdateVersionProcedure.execute(world);
		}
	}
}
