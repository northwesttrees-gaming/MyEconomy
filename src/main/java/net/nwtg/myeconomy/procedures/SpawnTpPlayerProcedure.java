package net.nwtg.myeconomy.procedures;

import net.nwtg.myeconomy.network.MyeconomyModVariables;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import com.google.gson.Gson;

public class SpawnTpPlayerProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		File file = new File("");
		com.google.gson.JsonObject mainObject = new com.google.gson.JsonObject();
		boolean isSpawnSet = false;
		double spawnX = 0;
		double spawnY = 0;
		double spawnZ = 0;
		String spawnWorld = "";
		if (!world.isClientSide()) {
			file = new File((FMLPaths.GAMEDIR.get().toString() + "/config/myeconomy/worlds"), File.separator + (MyeconomyModVariables.WorldVariables.get(world).worldName + ".json"));
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
						isSpawnSet = mainObject.get("is_spawn_set").getAsBoolean();
						if (isSpawnSet) {
							spawnX = mainObject.get("spawn_x").getAsDouble();
							spawnY = Math.floor(mainObject.get("spawn_y").getAsDouble());
							spawnZ = mainObject.get("spawn_z").getAsDouble();
							spawnWorld = mainObject.get("spawn_world").getAsString();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (isSpawnSet) {
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(
								new CommandSourceStack(CommandSource.NULL, new Vec3((entity.getX()), (entity.getY()), (entity.getZ())), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("execute in " + spawnWorld + " run tp @p " + (spawnX + " ") + (spawnY + " ") + ("" + spawnZ)));
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.spawn_tp_player.success").getString())), (false));
				} else {
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.spawn_tp_player.error2").getString())), (false));
				}
			} else {
				if (entity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.spawn_tp_player.error1").getString())), (false));
			}
		}
	}
}
