package net.nwtg.myeconomy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.StringArgumentType;

public class CheckPlayerLoanDebtCapPlayerNameProcedure {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		double myNumber = 0;
		myNumber = CheckPlayerLoanDebtCapPlayerNameScriptProcedure.execute(arguments);
		if (myNumber >= 0) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.check_player_bank_player_name.msg1").getString() + "" + StringArgumentType.getString(arguments, "playerName") + " "
						+ Component.translatable("msg.myeconomy.check_player_bank_player_name.msg2").getString() + new java.text.DecimalFormat("##.##").format(myNumber) + " " + GetConfigCurrencyIconProcedure.execute(world) + " "
						+ Component.translatable("msg.myeconomy.check_player_loan_debt_cap_player_name.msg1").getString())), (false));
		} else {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.check_player_bank_player_name.error").getString())), (false));
		}
	}
}
