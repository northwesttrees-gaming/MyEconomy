package net.nwtg.myeconomy.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;

public class CheckPlayerHomePositionPlayerProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		double myPosX = 0;
		double myPosY = 0;
		double myPosZ = 0;
		boolean myLogic = false;
		myLogic = CheckPlayerHomePositionPlayerHasHomeScriptProcedure.execute(arguments);
		if (myLogic) {
			myPosX = CheckPlayerHomePositionPlayerXScriptProcedure.execute(arguments);
			myPosY = CheckPlayerHomePositionPlayerYScriptProcedure.execute(arguments);
			myPosZ = CheckPlayerHomePositionPlayerZScriptProcedure.execute(arguments);
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
				}.getEntity()).getDisplayName().getString() + " " + Component.translatable("msg.myeconomy.check_player_home_player_name.msg1").getString() + new java.text.DecimalFormat("##.##").format(myPosX)
						+ Component.translatable("msg.myeconomy.check_player_home_player_name.msg2").getString() + new java.text.DecimalFormat("##.##").format(myPosY)
						+ Component.translatable("msg.myeconomy.check_player_home_player_name.msg3").getString() + new java.text.DecimalFormat("##.##").format(myPosZ))), (false));
		} else {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.check_player_bank_player_name.error").getString())), (false));
		}
	}
}
