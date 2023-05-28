package net.nwtg.myeconomy.procedures;

import org.checkerframework.checker.units.qual.s;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.CommandSourceStack;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import com.mojang.brigadier.context.CommandContext;

import com.google.gson.Gson;

public class CheckPriceSellFindItemPriceScriptProcedure {
	public static double execute(CommandContext<CommandSourceStack> arguments) {
		File file = new File("");
		com.google.gson.JsonObject mainObject = new com.google.gson.JsonObject();
		com.google.gson.JsonObject subObject0 = new com.google.gson.JsonObject();
		boolean foundMatch = false;
		String priceString = "";
		String configString = "";
		String typeString = "";
		String namespaceString = "";
		double configNumber = 0;
		double substring = 0;
		ItemStack item = ItemStack.EMPTY;
		file = new File((FMLPaths.GAMEDIR.get().toString() + "/config/myeconomy"), File.separator + "sell_items.json");
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
					subObject0 = mainObject.get("items").getAsJsonObject();
					configNumber = 1;
					for (int index0 = 0; index0 < (int) (subObject0.size()); index0++) {
						configString = subObject0.get((new java.text.DecimalFormat("##").format(configNumber))).getAsString();
						priceString = configString;
						typeString = configString;
						namespaceString = configString;
						foundMatch = false;
						substring = 0;
						for (int index1 = 0; index1 < (int) ((configString).length() - 1); index1++) {
							if ((configString.substring((int) substring, (int) (substring + 1))).equals(",")) {
								priceString = configString.substring((int) 0, (int) substring);
								configString = configString.substring((int) (substring + 1), (int) (configString).length());
								break;
							}
							substring = substring + 1;
						}
						substring = 0;
						for (int index2 = 0; index2 < (int) ((configString).length() - 1); index2++) {
							if ((configString.substring((int) substring, (int) (substring + 1))).equals(",")) {
								typeString = configString.substring((int) 0, (int) substring);
								namespaceString = configString.substring((int) (substring + 1), (int) (configString).length());
								break;
							}
							substring = substring + 1;
						}
						item = (ItemArgument.getItem(arguments, "item").getItem().getDefaultInstance());
						if ((typeString).equals("reg") && (ForgeRegistries.ITEMS.getKey(item.getItem()).toString()).equals(namespaceString)) {
							foundMatch = true;
							break;
						} else if ((typeString).equals("tag") && item.is(ItemTags.create(new ResourceLocation((namespaceString).toLowerCase(java.util.Locale.ENGLISH))))) {
							foundMatch = true;
							break;
						} else {
							configNumber = configNumber + 1;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (foundMatch) {
				return new Object() {
					double convert(String s) {
						try {
							return Double.parseDouble(s.trim());
						} catch (Exception e) {
						}
						return 0;
					}
				}.convert(priceString);
			}
		}
		return -1;
	}
}
