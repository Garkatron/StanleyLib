package deus.stanleytemperature;

import deus.stanleytemperature.items.StanleyItems;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.RecipeBuilder;

public class StanleyRecipes {
	public static void initialize() {

		RecipeBuilder.Shaped(StanleyTemperature.MOD_ID)
			.setShape(
				"   ",
				"ppp",
				"   ")
			.addInput('p', Blocks.PERMAFROST.asItem())
			.create("StanleyRecipeIceCubes", StanleyItems.iceCubes.getDefaultStack());

	}
}
