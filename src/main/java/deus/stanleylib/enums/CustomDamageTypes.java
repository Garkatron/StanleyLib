package deus.stanleylib.enums;

import net.minecraft.core.util.helper.DamageType;

public class CustomDamageTypes extends DamageType {

	public static final DamageType OVERHEATING = new DamageType("damagetype.overheating", false, true, 0);
	public static final DamageType FREEZING = new DamageType("damagetype.freezing", false, true, 0);
	public static final DamageType HEAT = new DamageType("damagetype.heat", false, true, 0);
	public static final DamageType COLD = new DamageType("damagetype.cold", false, true, 0);

	public CustomDamageTypes(String languageKey, boolean shouldDamageArmor, boolean shouldDisplay, int iconIndex) {
		super(languageKey, shouldDamageArmor, shouldDisplay, iconIndex);
	}
}
