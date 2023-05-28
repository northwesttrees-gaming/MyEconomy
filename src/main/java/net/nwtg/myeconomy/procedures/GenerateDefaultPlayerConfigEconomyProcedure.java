package net.nwtg.myeconomy.procedures;

import net.nwtg.myeconomy.network.MyeconomyModVariables;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.world.level.LevelAccessor;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class GenerateDefaultPlayerConfigEconomyProcedure {
	public static void execute(LevelAccessor world) {
		File file = new File("");
		com.google.gson.JsonObject mainObject = new com.google.gson.JsonObject();
		double localVersion = 0;
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
						localVersion = mainObject.get("version").getAsDouble();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (localVersion < MyeconomyModVariables.MapVariables.get(world).version) {
					if (localVersion <= 1) {
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
								mainObject.addProperty("cash", 500);
								mainObject.addProperty("bank", 0);
								mainObject.addProperty("bank_debt", 0);
								mainObject.addProperty("bank_debt_cap", 10000);
								mainObject.addProperty("credit_card", 0);
								mainObject.addProperty("credit_card_debt", 0);
								mainObject.addProperty("credit_card_debt_cap", 4000);
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
					}
					if (localVersion <= 2) {
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
								mainObject.addProperty("loan", 0);
								mainObject.addProperty("loan_debt", 0);
								mainObject.addProperty("loan_debt_cap", 50000);
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
					}
				}
			}
		}
	}
}
