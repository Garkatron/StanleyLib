package deus.stanleylib.interfaces.mixin;

import deus.stanleylib.core.enums.PlayerTemperatureState;

public interface IStanleyPlayerEntity {
	/**
	 * Gets the current temperature of the player.
	 *
	 * @return The current temperature as a float.
	 */
	double stanley_lib$getPlayerTemperature();
	/**
	 * Sets the player's temperature to a specific value.
	 *
	 * @param temperature The new temperature value.
	 */
	void stanley_lib$setPlayerTemperature(double temperature);
	/**
	 * Increases the player's temperature by a specified amount.
	 *
	 * @param amount The amount to increase the temperature by.
	 */
	void stanley_lib$increasePlayerTemperature(double amount);
	/**
	 * Decreases the player's temperature by a specified amount.
	 *
	 * @param amount The amount to decrease the temperature by.
	 */
	void stanley_lib$decreasePlayerTemperature(double amount);

	/**
	 * Checks if the player is overheating.
	 *
	 * @return True if the player is overheating, false otherwise.
	 */
	boolean stanley_lib$isPlayerOverheating();
	/**
	 * Checks if the player is freezing.
	 *
	 * @return True if the player is freezing, false otherwise.
	 */
	boolean stanley_lib$isPlayerFreezing();

	/**
	 * Resets the player's temperature to the default value.
	 *
	 **/
	void stanley_lib$resetPlayerTemperature();

	/**
	 * Kills the player's by freezing.
	 *
	 **/
	void stanley_lib$killByFreezing();

	/**
	 * Kills the player's by overheating.
	 *
	 **/
	void stanley_lib$killByOverheating();

	/**
	 * Hurt's the player by freezing
	 *
	 * @param amount The amount to hurt the player.
	 **/
	void stanley_lib$hurtByFreezing(int amount);

	/**
	 * Hurt's the player by freezing
	 *
	 * @param amount The amount to hurt the player.
	 **/
	void stanley_lib$hurtByOverheating(int amount);

	/**
	 * Hurt's the player by cold
	 *
	 * @param amount The amount to hurt the player.
	 **/
	void stanley_lib$hurtByCold(int amount);

	/**
	 * Hurt's the player by heat
	 *
	 * @param amount The amount to hurt the player.
	 **/
	void stanley_lib$hurtByHeat(int amount);

	/**
	 * Update's player temperature
	 *
	 **/
	void stanley_lib$updateTemperature();

	/**
	 * Sets the player's temperature state to a specific value.
	 *
	 * @param state The new temperature value.
	 **/
	void stanley_lib$setTemperatureState(PlayerTemperatureState state);

	/**
	 * Get the player's temperature state.
	 *
	 **/
	PlayerTemperatureState stanley_lib$getState();
}
