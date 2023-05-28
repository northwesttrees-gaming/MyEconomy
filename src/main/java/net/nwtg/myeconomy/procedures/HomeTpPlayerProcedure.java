package net.nwtg.myeconomy.procedures;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;

import com.google.gson.Gson;

public class HomeTpPlayerProcedure {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		File file = new File("");
		com.google.gson.JsonObject mainObject = new com.google.gson.JsonObject();
		boolean hasHome = false;
		double homePosX = 0;
		double homePosY = 0;
		double homePosZ = 0;
		String homeWorld = "";
		if (!world.isClientSide()) {
			if (GetSettingsHomeTpPermissionProcedure.execute(world)) {
				file = new File((FMLPaths.GAMEDIR.get().toString() + "/config/myeconomy/players"), File.separator + ((new Object() {
					public Entity getEntity() {
						try {
							return EntityArgument.getEntity(arguments, "player");
						} catch (CommandSyntaxException e) {
							e.printStackTrace();
							return null;
						}
					}
				}.getEntity()).getDisplayName().getString() + ".json"));
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
							hasHome = mainObject.get("has_home").getAsBoolean();
							if (hasHome) {
								homePosX = mainObject.get("home_x").getAsDouble();
								homePosY = mainObject.get("home_y").getAsDouble();
								homePosZ = mainObject.get("home_z").getAsDouble();
								homeWorld = mainObject.get("home_world").getAsString();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (hasHome) {
						if (world instanceof ServerLevel _level)
							_level.getServer().getCommands().performPrefixedCommand(
									new CommandSourceStack(CommandSource.NULL, new Vec3((entity.getX()), (entity.getY()), (entity.getZ())), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
									("execute in " + homeWorld + " run tp @p " + (homePosX + " ") + (homePosY + " ") + ("" + homePosZ)));
					}
				}
			}
		}
	}
}
