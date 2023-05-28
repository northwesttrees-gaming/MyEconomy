package net.nwtg.myeconomy.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.context.CommandContext;

public class CheckPriceBuyItemProcedure {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		ItemStack myItem = ItemStack.EMPTY;
		double amount = 0;
		double price = 0;
		String displayName = "";
		BlockState myBlockstate = Blocks.AIR.defaultBlockState();
		price = CheckPriceBuyFindItemPriceScriptProcedure.execute(arguments);
		if (price >= 0) {
			price = 1;
			myItem = (ItemArgument.getItem(arguments, "item").getItem().getDefaultInstance());
			myBlockstate = (myItem.getItem() instanceof BlockItem _bi ? _bi.getBlock().defaultBlockState() : Blocks.AIR.defaultBlockState());
			displayName = myItem.getDisplayName().getString();
			displayName = displayName.replace("[", "");
			displayName = displayName.replace("]", "");
			price = price * amount;
			if (myBlockstate.getBlock() == Blocks.AIR) {
				if (amount == 1) {
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.check_price_buy_item.mod_buying").getString() + "" + new java.text.DecimalFormat("##").format(amount) + " " + displayName + " "
								+ Component.translatable("msg.myeconomy.check_price_buy_item.item_gives").getString() + new java.text.DecimalFormat("##.##").format(price) + " " + GetConfigCurrencyIconProcedure.execute(world))), (false));
				} else {
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.check_price_buy_item.mod_buying").getString() + "" + new java.text.DecimalFormat("##").format(amount) + " " + displayName + " "
								+ Component.translatable("msg.myeconomy.check_price_buy_item.items_gives").getString() + new java.text.DecimalFormat("##.##").format(price) + " " + GetConfigCurrencyIconProcedure.execute(world))), (false));
				}
			} else {
				if (amount == 1) {
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.check_price_buy_item.mod_buying").getString() + "" + new java.text.DecimalFormat("##").format(amount) + " " + displayName + " "
								+ Component.translatable("msg.myeconomy.check_price_buy_item.block_gives").getString() + new java.text.DecimalFormat("##.##").format(price) + " " + GetConfigCurrencyIconProcedure.execute(world))), (false));
				} else {
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.check_price_buy_item.mod_buying").getString() + "" + new java.text.DecimalFormat("##").format(amount) + " " + displayName + " "
								+ Component.translatable("msg.myeconomy.check_price_buy_item.blocks_gives").getString() + new java.text.DecimalFormat("##.##").format(price) + " " + GetConfigCurrencyIconProcedure.execute(world))), (false));
				}
			}
		} else {
			price = 1;
			myItem = (ItemArgument.getItem(arguments, "item").getItem().getDefaultInstance());
			myBlockstate = (myItem.getItem() instanceof BlockItem _bi ? _bi.getBlock().defaultBlockState() : Blocks.AIR.defaultBlockState());
			if (myBlockstate.getBlock() == Blocks.AIR) {
				if (amount == 1) {
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.check_price_buy_item.error1").getString())), (false));
				} else {
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.check_price_buy_item.error2").getString())), (false));
				}
			} else {
				if (amount == 1) {
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.check_price_buy_item.error3").getString())), (false));
				} else {
					if (entity instanceof Player _player && !_player.level.isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("msg.myeconomy.check_price_buy_item.error4").getString())), (false));
				}
			}
		}
	}
}
