package deus.stanleylib.management;

import deus.godotsignalsystem.core.Signal;
import deus.stanleylib.enums.PlayerTemperatureState;

import java.math.BigDecimal;

/**
 * Manages various signals related to temperature and player states.
 */
public class SignalAccessor {

	/**
	 * Signal emitted when the temperature decreases.
	 * Carries a {@link BigDecimal} value representing the amount of temperature decrease.
	 */
	public Signal<BigDecimal> temperatureDecreased = new Signal<>();

	/**
	 * Signal emitted when the temperature increases.
	 * Carries a {@link BigDecimal} value representing the amount of temperature increase.
	 */
	public Signal<BigDecimal> temperatureIncremented = new Signal<>();

	/**
	 * Signal emitted when the temperature state of the player is updated.
	 * Carries a {@link PlayerTemperatureState} value representing the new temperature state.
	 */
	public Signal<PlayerTemperatureState> updatedTemperatureState = new Signal<>();

	/**
	 * Signal emitted when the player is killed by freezing.
	 * Does not carry any value.
	 */
	public Signal<Void> killedByFreezing = new Signal<>();

	/**
	 * Signal emitted when the player is killed by overheating.
	 * Does not carry any value.
	 */
	public Signal<Void> killedByOverheating = new Signal<>();

	/**
	 * Signal emitted when the player is hurt by overheating.
	 * Does not carry any value.
	 */
	public Signal<Void> wasHurtByOverheating = new Signal<>();

	/**
	 * Signal emitted when the player is hurt by freezing.
	 * Does not carry any value.
	 */
	public Signal<Void> wasHurtFreezing = new Signal<>();

	private static SignalAccessor instance;

	private SignalAccessor() {}

	public static synchronized SignalAccessor getInstance() {
		if (instance == null) {
			instance = new SignalAccessor();
		}
		return instance;
	}
}
