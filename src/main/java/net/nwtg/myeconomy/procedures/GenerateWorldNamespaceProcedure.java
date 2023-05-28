package net.nwtg.myeconomy.procedures;

import net.nwtg.myeconomy.network.MyeconomyModVariables;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

public class GenerateWorldNamespaceProcedure {
	public static void execute(LevelAccessor world) {
		String dimensionID = "";
		double substring = 0;
		if (!world.isClientSide()) {
			if ((MyeconomyModVariables.WorldVariables.get(world).worldNamespace).equals("") || (MyeconomyModVariables.WorldVariables.get(world).worldName).equals("")) {
				substring = 0;
				dimensionID = "" + (world instanceof Level _lvl ? _lvl.dimension() : Level.OVERWORLD);
				for (int index0 = 0; index0 < (int) ((dimensionID).length() - 2); index0++) {
					if ((dimensionID.substring((int) substring, (int) (substring + 2))).equals("/ ")) {
						dimensionID = dimensionID.substring((int) (substring + 2), (int) ((dimensionID).length() - 1));
						break;
					}
					substring = substring + 1;
				}
				MyeconomyModVariables.WorldVariables.get(world).worldNamespace = dimensionID;
				MyeconomyModVariables.WorldVariables.get(world).syncData(world);
				dimensionID = dimensionID.replace(":", "_");
				MyeconomyModVariables.WorldVariables.get(world).worldName = dimensionID;
				MyeconomyModVariables.WorldVariables.get(world).syncData(world);
			}
		}
	}
}
