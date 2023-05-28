package net.nwtg.myeconomy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class CheckPlayerCmdHelpMessageProcedure {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		if (!world.isClientSide()) {
			if (DoubleArgumentType.getDouble(arguments, "page") == 1) {
				CheckPlayerCmdHelpMessage1Procedure.execute(world, entity);
			} else if (DoubleArgumentType.getDouble(arguments, "page") == 2) {
				CheckPlayerCmdHelpMessage2Procedure.execute(world, entity);
			}
		}
	}
}
