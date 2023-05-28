package net.nwtg.myeconomy.procedures;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.commands.CommandSourceStack;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.StringArgumentType;

import com.google.gson.Gson;

public class CheckPlayerPreJailPositionPlayerNameZScriptProcedure {
	public static double execute(CommandContext<CommandSourceStack> arguments) {
		File file = new File("");
		com.google.gson.JsonObject mainObject = new com.google.gson.JsonObject();
		double myValue = 0;
		boolean isJailed = false;
		file = new File((FMLPaths.GAMEDIR.get().toString() + "/config/myeconomy/players"), File.separator + (StringArgumentType.getString(arguments, "playerName") + ".json"));
		if (file.exists()) {
			{
				try {
					BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
					StringBuilder jsonstringbuilder = new StringBuilder();
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						jsonstringbuilder.append(line);
					}
					bufferedReader.close();
					mainObject = new Gson().fromJson(jsonstringbuilder.toString(), com.google.gson.JsonObject.class);
					isJailed = mainObject.get("is_jailed").getAsBoolean();
					if (isJailed) {
						myValue = mainObject.get("pre_jail_z").getAsDouble();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (isJailed) {
				return myValue;
			}
		}
		return 0;
	}
}
