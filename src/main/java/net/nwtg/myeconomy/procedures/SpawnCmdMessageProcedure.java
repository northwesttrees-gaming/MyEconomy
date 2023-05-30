package net.nwtg.myeconomy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

public class SpawnCmdMessageProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!world.isClientSide()) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.spawn_cmd_message.title").getString())), (false));
			if (entity.hasPermissions(2)) {
				if (entity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.spawn_cmd_message.cmd1").getString())), (false));
			}
			if (GetSettingsSpawnTpPermissionProcedure.execute(world)) {
				if (entity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.spawn_cmd_message.cmd2a").getString())), (false));
			} else {
				if (entity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.spawn_cmd_message.cmd2b").getString())), (false));
			}
		}
	}
}
