package deus.stanleylib.config;

import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.FloatOption;

public interface IStanleySettings {
	BooleanOption stanley_lib_BTA$activateManagement();

	FloatOption stanley_lib_BTA$overHeatingTemperature();
	FloatOption stanley_lib_BTA$hotTemperature();
	FloatOption stanley_lib_BTA$defaultTemperature();
	FloatOption stanley_lib_BTA$coldTemperature();
	FloatOption stanley_lib_BTA$freezingTemperature();

	BooleanOption stanley_lib_BTA$weatherAffectsTemperature();
	FloatOption stanley_lib_BTA$overworldRain();
	FloatOption stanley_lib_BTA$overworldSnow();
	FloatOption stanley_lib_BTA$overworldStorm();
	FloatOption stanley_lib_BTA$overworldWinterSnow();

	BooleanOption stanley_lib_BTA$itemAffectsTemperature();
	FloatOption stanley_lib_BTA$torch();
	FloatOption stanley_lib_BTA$redstoneTorch();
	FloatOption stanley_lib_BTA$lavaBucket();
	FloatOption stanley_lib_BTA$netherCoal();
	FloatOption stanley_lib_BTA$iceCream();

	BooleanOption stanley_lib_BTA$foodAffectsTemperature();
	FloatOption stanley_lib_BTA$soup();
	FloatOption stanley_lib_BTA$milk();

	BooleanOption stanley_lib_BTA$lifeAffectsTemperature();
	FloatOption stanley_lib_BTA$lowLifePenalization();
	FloatOption stanley_lib_BTA$heightLifeAdvantage();

	BooleanOption stanley_lib_BTA$playerOverBlockAffectsTemperature();
	FloatOption stanley_lib_BTA$snowBlock();
	FloatOption stanley_lib_BTA$waterBlock();
	FloatOption stanley_lib_BTA$iceBlock();

	BooleanOption stanley_lib_BTA$snowballAffectsTemperature();
	FloatOption stanley_lib_BTA$snowballEffect();

	BooleanOption stanley_lib_BTA$biomeAffectsTemperature();
	FloatOption stanley_lib_BTA$plains();
	FloatOption stanley_lib_BTA$taiga();
	FloatOption stanley_lib_BTA$nether();
	FloatOption stanley_lib_BTA$birchForest();
	FloatOption stanley_lib_BTA$caatinga();
	FloatOption stanley_lib_BTA$borealForest();
	FloatOption stanley_lib_BTA$swampLand();
	FloatOption stanley_lib_BTA$desert();
	FloatOption stanley_lib_BTA$forest();

	BooleanOption stanley_lib_BTA$leatherProtectsTemperature();
	FloatOption stanley_lib_BTA$leatherProtectionPercentage();

	BooleanOption stanley_lib_BTA$seasonAffectsTemperature();
	FloatOption stanley_lib_BTA$summerTemperature();
	FloatOption stanley_lib_BTA$fallTemperature();
	FloatOption stanley_lib_BTA$winterTemperature();
	FloatOption stanley_lib_BTA$springTemperature();
}
