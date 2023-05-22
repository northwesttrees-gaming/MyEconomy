package net.nwtg.myeconomy.procedures;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

@Mod.EventBusSubscriber
public class GeneratePlayerConfigProcedure {
	@SubscribeEvent
	public static void onEntityJoin(EntityJoinLevelEvent event) {
		execute(event, event.getLevel(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		File file = new File("");
		com.google.gson.JsonObject mainObject = new com.google.gson.JsonObject();
		double localVersion = 0;
		double globalVersion = 0;
		if (!world.isClientSide()) {
			if (entity instanceof Player || entity instanceof ServerPlayer) {
				file = new File((FMLPaths.GAMEDIR.get().toString() + "/config/myeconomy/players"), File.separator + (entity.getDisplayName().getString() + ".json"));
				if (!file.exists()) {
					try {
						file.getParentFile().mkdirs();
						file.createNewFile();
					} catch (IOException exception) {
						exception.printStackTrace();
					}
					mainObject.addProperty("version", 1);
					mainObject.addProperty("cash", 500);
					mainObject.addProperty("bank", 0);
					mainObject.addProperty("bank_debt", 0);
					mainObject.addProperty("bank_debt_cap", 10000);
					mainObject.addProperty("credit_card", 0);
					mainObject.addProperty("credit_card_debt", 0);
					mainObject.addProperty("credit_card_debt_cap", 4000);
					mainObject.addProperty("lone", 0);
					mainObject.addProperty("lone_debt", 0);
					mainObject.addProperty("lone_debt_cap", 50000);
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
				if (file.exists()) {
					globalVersion = GetConfigVersionProcedure.execute(world);
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
							if (localVersion < globalVersion) {
								if (localVersion == 1) {
									mainObject.addProperty("version", 2);
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
