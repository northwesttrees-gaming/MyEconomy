package net.nwtg.myeconomy.procedures;

import net.nwtg.myeconomy.network.MyeconomyModVariables;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class SetPlayersHomeOnJoinIfNoneProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		File file = new File("");
		com.google.gson.JsonObject mainObject = new com.google.gson.JsonObject();
		boolean hasHome = false;
		double mySubstring = 0;
		String myWorld = "";
		String myNamespace = "";
		if (!world.isClientSide()) {
			file = new File((FMLPaths.GAMEDIR.get().toString() + "/config/myeconomy/players"), File.separator + (entity.getDisplayName().getString() + ".json"));
			if (file.exists()) {
				mySubstring = 0;
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
						if (!hasHome) {
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
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
