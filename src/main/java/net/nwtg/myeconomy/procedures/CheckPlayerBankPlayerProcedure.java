package net.nwtg.myeconomy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;

public class CheckPlayerBankPlayerProcedure {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		double myNumber = 0;
		myNumber = CheckPlayerBankPlayerScriptProcedure.execute(arguments);
		if (myNumber >= 0) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.check_player_bank_player_name.msg1").getString() + "" + (new Object() {
					public Entity getEntity() {
						try {
							return EntityArgument.getEntity(arguments, "player");
						} catch (CommandSyntaxException e) {
							e.printStackTrace();
							return null;
						}
					}
				}.getEntity()).getDisplayName().getString() + " " + Component.translatable("msg.myeconomy.check_player_bank_player_name.msg2").getString() + new java.text.DecimalFormat("##.##").format(myNumber) + " "
						+ GetConfigCurrencyIconProcedure.execute(world) + " " + Component.translatable("msg.myeconomy.check_player_bank_player_name.msg3").getString())), (false));
		} else {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.check_player_bank_player_name.error").getString())), (false));
		}
	}
}
