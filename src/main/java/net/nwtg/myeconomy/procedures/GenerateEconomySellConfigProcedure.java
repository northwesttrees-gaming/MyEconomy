package net.nwtg.myeconomy.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;

import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class GenerateEconomySellConfigProcedure {
	public static void execute(LevelAccessor world) {
		File file = new File("");
		com.google.gson.JsonObject mainObject = new com.google.gson.JsonObject();
		com.google.gson.JsonObject subObject0 = new com.google.gson.JsonObject();
		if ((world instanceof Level _lvl ? _lvl.dimension() : Level.OVERWORLD) == (Level.OVERWORLD)) {
			file = new File((FMLPaths.GAMEDIR.get().toString() + "/config/myeconomy"), File.separator + "sell_items.json");
			if (!file.exists()) {
				try {
					file.getParentFile().mkdirs();
					file.createNewFile();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
				subObject0.addProperty("1", (new java.text.DecimalFormat("##.##").format(0.25) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.STICK).toString()));
				subObject0.addProperty("2", (new java.text.DecimalFormat("##.##").format(0.5) + ",tag," + "myeconomy:economy/planks"));
				subObject0.addProperty("3", (new java.text.DecimalFormat("##.##").format(2) + ",tag," + "myeconomy:economy/logs"));
				subObject0.addProperty("4", (new java.text.DecimalFormat("##.##").format(2.5) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.CHARCOAL).toString()));
				subObject0.addProperty("5", (new java.text.DecimalFormat("##.##").format(3) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.COAL).toString()));
				subObject0.addProperty("6", (new java.text.DecimalFormat("##.##").format(4) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.RAW_COPPER).toString()));
				subObject0.addProperty("7", (new java.text.DecimalFormat("##.##").format(4.5) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.COPPER_INGOT).toString()));
				subObject0.addProperty("8", (new java.text.DecimalFormat("##.##").format(5) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.RAW_IRON).toString()));
				subObject0.addProperty("9", (new java.text.DecimalFormat("##.##").format(0.6) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.IRON_NUGGET).toString()));
				subObject0.addProperty("10", (new java.text.DecimalFormat("##.##").format(5.5) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.IRON_INGOT).toString()));
				subObject0.addProperty("11", (new java.text.DecimalFormat("##.##").format(0.5) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.EMERALD).toString()));
				subObject0.addProperty("12", (new java.text.DecimalFormat("##.##").format(0.5) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.AMETHYST_SHARD).toString()));
				subObject0.addProperty("13", (new java.text.DecimalFormat("##.##").format(2) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.QUARTZ).toString()));
				subObject0.addProperty("14", (new java.text.DecimalFormat("##.##").format(2) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.REDSTONE).toString()));
				subObject0.addProperty("15", (new java.text.DecimalFormat("##.##").format(9) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.GOLD_NUGGET).toString()));
				subObject0.addProperty("16", (new java.text.DecimalFormat("##.##").format(80.5) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.RAW_GOLD).toString()));
				subObject0.addProperty("17", (new java.text.DecimalFormat("##.##").format(81) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.GOLD_INGOT).toString()));
				subObject0.addProperty("18", (new java.text.DecimalFormat("##.##").format(100) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.DIAMOND).toString()));
				subObject0.addProperty("19", (new java.text.DecimalFormat("##.##").format(256) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.NETHERITE_SCRAP).toString()));
				subObject0.addProperty("20", (new java.text.DecimalFormat("##.##").format(1348) + ",reg," + ForgeRegistries.ITEMS.getKey(Items.NETHERITE_INGOT).toString()));
				mainObject.add("items", subObject0);
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
	}
}
