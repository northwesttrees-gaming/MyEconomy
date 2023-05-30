package net.nwtg.myeconomy.procedures;

import net.nwtg.myeconomy.network.MyeconomyModVariables;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.StringArgumentType;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class HomeSetPlayerNameProcedure {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		File file = new File("");
		com.google.gson.JsonObject mainObject = new com.google.gson.JsonObject();
		if (!world.isClientSide()) {
			if (entity.hasPermissions(2)) {
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
							mainObject.addProperty("home_x", (Math.floor(entity.getX()) + 0.5));
							mainObject.addProperty("home_y", (Math.floor(entity.getY()) + 0.5));
							mainObject.addProperty("home_z", (Math.floor(entity.getZ()) + 0.5));
							mainObject.addProperty("home_world", MyeconomyModVariables.WorldVariables.get(world).worldNamespace);
							mainObject.addProperty("has_home", (true));
							{
								Gson mainGSONBuilderVariable = new GsonBuilder().setPrettyPrinting().create();
								try {
									FileWriter fileWriter = new FileWriter(file);
									fileWriter.write(mainGSONBuilderVariable.toJson(mainObject));
									fileWriter.close();
								} catch (IOException exception) {
									exception.printStackTrace();
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.home_set_player_name.success").getString())), (false));
				} else {
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.home_tp_player_name.error2").getString())), (false));
				}
			} else {
				if (entity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.home_set_player_name.error1").getString())), (false));
			}
		}
	}
}
