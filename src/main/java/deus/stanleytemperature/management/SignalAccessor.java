package deus.stanleytemperature.management;

import deus.stanleytemperature.enums.PlayerTemperatureState;
import gssl.Signal;

/**
 * Manages various signals related to temperature and player states.
 */
public class SignalAccessor {

	private static SignalAccessor instance;
	/**
	 * Signal emitted when the temperature decreases.
	 * Carries a {@link Double} value representing the amount of temperature decrease.
	 */
	public Signal<Double> temperatureDecreased = new Signal<>();
	/**
	 * Signal emitted when the temperature increases.
	 * Carries a {@link Double} value representing the amount of temperature increase.
	 */
	public Signal<Double> temperatureIncremented = new Signal<>();
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

	private SignalAccessor() {
	}

	public static synchronized SignalAccessor getInstance() {
		if (instance == null) {
			instance = new SignalAccessor();
		}
		return instance;
	}
}
