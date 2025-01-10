package deus.stanleytemperature.enums;

import deus.stanleytemperature.StanleyTemperature;
import net.minecraft.core.util.helper.DamageType;

public class CustomDamageTypes extends DamageType {

	public static final DamageType OVERHEATING = new DamageType("damagetype.overheating", false, true, StanleyTemperature.MOD_ID+":gui/heatIcon");
	public static final DamageType FREEZING = new DamageType("damagetype.freezing", false, true, StanleyTemperature.MOD_ID+":gui/coldIcon");
	public static final DamageType HEAT = new DamageType("damagetype.heat", false, true, StanleyTemperature.MOD_ID+":gui/heatIcon");
	public static final DamageType COLD = new DamageType("damagetype.cold", false, true, StanleyTemperature.MOD_ID+":gui/coldIcon");

	public CustomDamageTypes(String languageKey, boolean shouldDamageArmor, boolean shouldDisplay, String icon) {
		super(languageKey, shouldDamageArmor, shouldDisplay, icon);
	}
}
