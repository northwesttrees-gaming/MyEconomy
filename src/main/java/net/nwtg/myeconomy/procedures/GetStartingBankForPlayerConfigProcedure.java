package net.nwtg.myeconomy.procedures;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.world.level.LevelAccessor;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import com.google.gson.Gson;

public class GetStartingBankForPlayerConfigProcedure {
	public static double execute(LevelAccessor world) {
		File file = new File("");
		com.google.gson.JsonObject mainObject = new com.google.gson.JsonObject();
		double myValue = 0;
		if (!world.isClientSide()) {
			file = new File((FMLPaths.GAMEDIR.get().toString() + "/config/myeconomy"), File.separator + "default_player.json");
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
						myValue = mainObject.get("bank").getAsDouble();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return myValue;
			}
		}
		return 0;
	}
}
