package deus.stanleytemperature.items;

import deus.stanleytemperature.StanleyTemperature;
import net.minecraft.core.item.ItemFood;
import turniplabs.halplibe.helper.ItemBuilder;

public class StanleyItems {

	public static ItemFood iceCubes;
	public static ItemFood netherrackMeatBalls;

	private static int currentBlockId = StanleyTemperature.MOD_CONFIG.getConfig().getInt("StartingIDs.blocks");
	private static int currentItemId = StanleyTemperature.MOD_CONFIG.getConfig().getInt("StartingIDs.items");

	private static final int DEFAULT_STACK_SIZE = 64;

	public static int newItemId() {
		return currentItemId++;
	}

	public static int newBlockId() {
		return currentBlockId++;
	}

	public static void initialize() {
		ItemBuilder genericItemBuilder = new ItemBuilder(StanleyTemperature.MOD_ID);

		iceCubes = genericItemBuilder
			.setIcon(StanleyTemperature.MOD_ID + ":item/iceCubes")
			.build(new ItemFood(
				"iceCubes",
				StanleyTemperature.MOD_ID + ":item/ice_cubes",
				newItemId(),
				1,
				1,
				false,
				DEFAULT_STACK_SIZE
			));

		netherrackMeatBalls = genericItemBuilder
			.setIcon(StanleyTemperature.MOD_ID + ":item/netherrackMeatBalls")
			.build(new ItemFood(
				"netherrackMeatBalls",
				StanleyTemperature.MOD_ID + ":item/netherrack_meat_balls",
				newItemId(),
				1,
				1,
				false,
				DEFAULT_STACK_SIZE
			));
	}
}
