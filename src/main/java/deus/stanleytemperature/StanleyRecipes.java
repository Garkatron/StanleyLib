package deus.stanleytemperature;

import deus.stanleytemperature.items.StanleyItems;
import net.minecraft.core.block.Block;
import turniplabs.halplibe.helper.RecipeBuilder;

public class StanleyRecipes {
	public static void initialize() {

		RecipeBuilder.Shaped(StanleyTemperature.MOD_ID)
			.setShape(
				"   ",
				"ppp",
				"   ")
			.addInput('p', Block.permafrost.asItem())
			.create("StanleyRecipeIceCubes", StanleyItems.iceCubes.getDefaultStack());

		RecipeBuilder.Shaped(StanleyTemperature.MOD_ID)
			.setShape(
				"   ",
				"ppp",
				"   ")
			.addInput('p', Block.netherrack.asItem())
			.create("StanleyRecipeIceCubes", StanleyItems.netherrackMeatBalls.getDefaultStack());

	}
}
