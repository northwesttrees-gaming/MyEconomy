package net.nwtg.myeconomy.procedures;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.CommandSourceStack;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class BuyItemsItemAmountCommandProcedure {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		File file = new File("");
		com.google.gson.JsonObject mainObject = new com.google.gson.JsonObject();
		double itemTotal = 0;
		double sellPrice = 0;
		double cash = 0;
		String name = "";
		if (!world.isClientSide()) {
			file = new File((FMLPaths.GAMEDIR.get().toString() + "/config/myeconomy/players"), File.separator + (entity.getDisplayName().getString() + ".json"));
			if (file.exists()) {
				sellPrice = FindBuyItemPriceProcedure.execute(arguments);
				itemTotal = DoubleArgumentType.getDouble(arguments, "amount");
				if (sellPrice >= 0) {
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
							cash = mainObject.get("cash").getAsDouble();
							if (cash >= sellPrice * itemTotal) {
								mainObject.addProperty("cash", (mainObject.get("cash").getAsDouble() - sellPrice * itemTotal));
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
								for (int index0 = 0; index0 < (int) (itemTotal); index0++) {
									if (world instanceof Level _level && !_level.isClientSide()) {
										ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY()), (entity.getZ()), (ItemArgument.getItem(arguments, "item").getItem().getDefaultInstance()));
										entityToSpawn.setPickUpDelay(10);
										_level.addFreshEntity(entityToSpawn);
									}
								}
								name = (ItemArgument.getItem(arguments, "item").getItem().getDefaultInstance()).getDisplayName().getString();
								name = name.replace("[", "");
								name = name.replace("]", "");
								if (entity instanceof Player _player && !_player.level.isClientSide())
									_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.buy_items_item_command.success1").getString() + "" + itemTotal + " " + name
											+ Component.translatable("msg.myeconomy.sell_items_hand_command.success2").getString() + sellPrice * itemTotal + " " + GetConfigCurrencyIconProcedure.execute(world))), (false));
							} else {
								if (entity instanceof Player _player && !_player.level.isClientSide())
									_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.buy_items_item_command.error3").getString())), (false));
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} else {
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.buy_items_item_command.error2").getString())), (false));
				}
			} else {
				if (entity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.home_tp_player_default.error1").getString())), (false));
			}
		}
	}
}
