package net.nwtg.myeconomy.procedures;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class SellItemsHandCommandProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double itemTotal = 0;
		double sellPrice = 0;
		File file = new File("");
		com.google.gson.JsonObject mainObject = new com.google.gson.JsonObject();
		String name = "";
		if (!world.isClientSide()) {
			file = new File((FMLPaths.GAMEDIR.get().toString() + "/config/myeconomy/players"), File.separator + (entity.getDisplayName().getString() + ".json"));
			if (file.exists()) {
				sellPrice = FindSellItemPriceProcedure.execute(entity);
				if (sellPrice >= 0) {
					itemTotal = 1;
					if (((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)).getCount() >= itemTotal) {
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
								mainObject.addProperty("cash", (mainObject.get("cash").getAsDouble() + sellPrice * itemTotal));
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
						name = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getDisplayName().getString();
						name = name.replace("[", "");
						name = name.replace("]", "");
						if (entity instanceof Player _player && !_player.level.isClientSide())
							_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.sell_items_hand_command.success1").getString() + "" + itemTotal + " " + name
									+ Component.translatable("msg.myeconomy.sell_items_hand_command.success2").getString() + sellPrice * itemTotal + " " + GetConfigCurrencyIconProcedure.execute(world))), (false));
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
							_setstack.setCount((int) (((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)).getCount() - itemTotal));
							_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
					} else {
						if (entity instanceof Player _player && !_player.level.isClientSide())
							_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.sell_items_hand_command.error3").getString())), (false));
					}
				} else {
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.sell_items_hand_command.error2").getString())), (false));
				}
			} else {
				if (entity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.sell_items_hand_command.error1").getString())), (false));
			}
		}
	}
}
